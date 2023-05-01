<?php

namespace App\Controller;

use App\Entity\Answers;
use App\Entity\Questions;

use App\Entity\Subjects;
use App\Entity\Users;
use App\Entity\Votes;
use App\Form\AnswerType;
use App\Form\QuestionType;
use App\Repository\AnswersRepository;
use App\Repository\QuestionsRepository;
use App\Repository\VotesRepository;
use App\Services\MailerService;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\FormError;
use Symfony\Component\Form\Test\FormInterface;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Knp\Component\Pager\PaginatorInterface;

use App\Controller\UserController; 

use Doctrine\ORM\EntityManagerInterface;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\Security\Core\Security;
use Symfony\Component\Yaml\Dumper;





class QuestionsListController extends AbstractController

{  

    #[Route('/app_question', name: 'app_question')]
    public function index(Request $request, PaginatorInterface $paginator, QuestionsRepository  $questionRepositoy,Security $security): Response

    { $user = $security->getUser();

        if (!$user) {
            return $this->redirectToRoute('app_login');
        }
         $question= $this->getDoctrine()->getManager()->getRepository(Questions::class)->findAll();
        $allIds = "no";
        $subjects = $this->getDoctrine()->getRepository(Subjects::class)->findAll();
        $pagination = $paginator->paginate(
            // $query,
            // $request->query->getInt('page', 1), // numéro de la page par défaut
            // 10 // nombre d'éléments par page
            $questionRepositoy->paginationQuery(),
            $request->query->get('page',1),
            5
        );
        return $this->render('forum/questionsList.html.twig', [
            'questions'=>$question,
            'answerId' =>  $allIds,
            'subjects' => $subjects,
            'pagination' => $pagination
        ]);
    }
    #[Route('/showtab', name: 'show_tab')]
    public function index2(Request $request, PaginatorInterface $paginator, QuestionsRepository  $questionRepositoy,Security $security): Response
    { $user = $security->getUser();

        if (!$user) {
            return $this->redirectToRoute('app_login');
        }
        $question= $this->getDoctrine()->getManager()->getRepository(Questions::class)->findAll();
        $entityManager = $this->getDoctrine()->getManager();

        // $queryBuilder = $entityManager->getRepository(Questions::class)->createQueryBuilder('q');
        // $query = $question;
        $pagination = $paginator->paginate(
            // $query,
            // $request->query->getInt('page', 1), // numéro de la page par défaut
            // 10 // nombre d'éléments par page
            $questionRepositoy->paginationQuery(),
            $request->query->get('page',1),
            3
        );
       
        return $this->render('admin/QuestionsForumTable.html.twig', [
            'questions'=>$question,
            'pagination' => $pagination
        ]);
    }
 
#[Route('/block-user', name: 'block_user')]
public function blockUser(Request $request, EntityManagerInterface $entityManager)
{
    $userId = $request->request->get('userId');
    $user = $entityManager->getRepository(Users::class)->find($userId);

    if (!$user) {
        throw $this->createNotFoundException('User not found.');
    }

    // Bloquer l'utilisateur
    $user->setBlocked(1);
 $blockedUntil = (new \DateTime())->add(new \DateInterval('PT2M')); // Ajoute 2 minutes à la date et l'heure actuelles
$user->setBlockedUntil($blockedUntil->format('Y-m-d H:i:s')); // Stocke la date et l'heure formatées dans la propriété "blockedUntil"

    //$user->setWarnings(0);
    $entityManager->flush();

    $this->addFlash('success', 'User has been blocked.');

    return $this->redirectToRoute('show_tab');
}



    #[Route('/deleteQuestion2/{id}', name: 'delete_question2')]
    public function deleteQuestion2(int $id): Response
    {
     
        $question= $this->getDoctrine()->getManager()->getRepository(Questions::class)->find($id);
        
        // Vérifie que l'objet $question existe avant de le supprimer
        if (!$question) {
            throw $this->createNotFoundException('Question non trouvée');
        }
        $em = $this->getDoctrine()->getManager();
        $em->remove($question);
        $em->flush();
    
        return $this->redirectToRoute('show_tab');
    }
    #[Route('/retourn', name: 'ma_page')]
    public function maPage(): Response
{
    // Votre code ici
    
    return $this->redirectToRoute('show_tab');
}
    #[Route('/search2', name: 'app_search_question2')]
    public function search2(Request $request,PaginatorInterface $paginator)
    {
        $subjects = $this->getDoctrine()->getRepository(Subjects::class)->findAll();
        $keywords = $request->query->get('keywords');
     
        
        $qb = $this->getDoctrine()->getRepository(Questions::class)->createQueryBuilder('q');
     
        
     
      
        
        // Recherche par titre
        if ($keywords) {
            $keywordsArray = explode(' ', $keywords);
            $qb->andWhere($qb->expr()->like('q.title', ':keywords'));
            $qb->setParameter('keywords', '%' . implode('%', $keywordsArray) . '%');
        }
       
        $entityManager = $this->getDoctrine()->getManager();
  


        
        $queryBuilder = $qb;
        $query = $queryBuilder->getQuery();
    
        $pagination = $paginator->paginate(
            $query,
            $request->query->get('page', 1), // Get the page number from the URL parameter 'page'
            10 // Number of items per page
        );
        $questions = $qb->getQuery()->getResult();
        
       
        return $this->render('admin/QuestionsForumTable.html.twig', [
            'questions'=>$questions,
            'pagination'=>$pagination,
          
          
        ]);
    }
  
  
    #[Route('/search', name: 'app_search_question')]
    public function search(Request $request, PaginatorInterface $paginator)
    {
        $subjects = $this->getDoctrine()->getRepository(Subjects::class)->findAll();
        $keywords = $request->query->get('keywords');
        $subject_id = $request->query->get('subject');
        $sortOrder = $request->query->get('sortOrder');
        
    
        $qb = $this->getDoctrine()->getRepository(Questions::class)->createQueryBuilder('q'); //creer un objet query builder 
        $qb->leftJoin('q.subject', 's');

    
if ($sortOrder == 1) {
    $qb->orderBy('q.createdAt', 'DESC');
} 
if ($sortOrder == 2) {
    $qb->orderBy('q.createdAt', 'ASC');
}
    
    
        // Filtre par matière si un identifiant de matière est fourni
        if ($subject_id ) {
            $qb->andWhere('q.subject = :subject')
                ->setParameter('subject', $subject_id);
        }
        if ($subject_id  && $sortOrder == 1) {
            $qb->andWhere('q.subject = :subject')
                ->setParameter('subject', $subject_id);
            $qb->orderBy('q.createdAt', 'DESC');
        }
        if ($subject_id  && $sortOrder == 2) {
            $qb->andWhere('q.subject = :subject')
            ->setParameter('subject', $subject_id);
            $qb->orderBy('q.createdAt', 'ASC');
        } 
    
        // Recherche par titre
        if ($keywords) {
            $keywordsArray = explode(' ', $keywords);
            $qb->andWhere($qb->expr()->like('q.title', ':keywords')); //filtrer les resutltat selon title
            $qb->setParameter('keywords', '%' . implode('%', $keywordsArray) . '%'); //concaténation des element de tab
        }
       
        
      
        elseif (!$subject_id && !$sortOrder) {
            // Si aucun filtre de matière n'est fourni et aucun tri n'est spécifié, recherchez toutes les questions par titre
            $qb->andWhere($qb->expr()->like('q.title', ':keywords'));
            $qb->setParameter('keywords', '%' . $keywords . '%');
        }
     
        
    

    
        // Utiliser la méthode "paginate()" pour paginer les résultats
        $pagination = $paginator->paginate(
            $qb->getQuery(),
            $request->query->get('page', 1), // Numéro de la page par défaut
            3 // Nombre d'éléments par page
        );
    
        return $this->render('forum/questionsList.html.twig', [
            'questions' => $pagination,
            'subjects' => $subjects,
            'pagination'=>$pagination
        ]);
    }
    public function search3(Request $request, PaginatorInterface $paginator)
    {
        $subjects = $this->getDoctrine()->getRepository(Subjects::class)->findAll();
        $keywords = $request->query->get('keywords');
        $subject_id = $request->query->get('subject');
        $sortOrder = $request->query->getInt('sortOrder', 0); // Valeur par défaut = 0
    
        $qb = $this->getDoctrine()->getRepository(Questions::class)->createQueryBuilder('q'); //creer un objet query builder 
        $qb->leftJoin('q.subject', 's');
    
        // Filtre par matière si un identifiant de matière est fourni
        if ($subject_id) {
            $qb->andWhere('q.subject = :subject')
                ->setParameter('subject', $subject_id);
        }
    
        // Recherche par titre
        if ($keywords) {
            $keywordsArray = explode(' ', $keywords);
            $qb->andWhere($qb->expr()->like('q.title', ':keywords'));
            $qb->setParameter('keywords', '%' . implode('%', $keywordsArray) . '%'); //concaténation des element de tab
        }
        elseif (!$subject_id && $sortOrder == 0) {
            // Si aucun filtre de matière n'est fourni et aucun tri n'est spécifié, recherchez toutes les questions par titre
            $qb->andWhere($qb->expr()->like('q.title', ':keywords'));
            $qb->setParameter('keywords', '%' . $keywords . '%');
        }
    
        // Tri par ordre croissant ou décroissant si spécifié
        if ($sortOrder == 1) {
            $qb->orderBy('q.createdAt', $sortOrder == 1 ? 'DESC' : 'ASC')
            ->getQuery();
        } elseif ($sortOrder == 2) {
            $qb->orderBy('q.createdAt', $sortOrder == 2 ? 'ASC' : 'DESC')
            ->getQuery();
        }
    
        // Utiliser la méthode "paginate()" pour paginer les résultats
        $pagination = $paginator->paginate(
            $qb->getQuery(),
            $request->query->get('page', 1), // Numéro de la page par défaut
            3 // Nombre d'éléments par page
        );
    
        return $this->render('forum/questionsList.html.twig', [
            'questions' => $pagination,
            'subjects' => $subjects,
            'pagination'=>$pagination,
            'sortOrder'=> $sortOrder
        ]);
    }
    

public function trie(Request $request, PaginatorInterface $paginator): Response
{
    $subjects = $this->getDoctrine()->getRepository(Subjects::class)->findAll();
    $sortOrder = $request->query->getInt('sortOrder', 1); // Valeur par défaut = 1

    $questionRepository = $this->getDoctrine()->getRepository(Questions::class);

    // Créer une requête paginée personnalisée
    $query = $questionRepository->createQueryBuilder('q')
        ->orderBy('q.createdAt', $sortOrder == 2 ? 'ASC' : 'DESC')
        ->getQuery();

    $pagination = $paginator->paginate(
        $query,
        $request->query->getInt('page', 1), // numéro de la page par défaut
        3 // nombre d'éléments par page
    );

    return $this->render('forum/questionsList.html.twig', [
        'questions' => $pagination,
        'sortOrder' => $sortOrder,
        'subjects' => $subjects,
        'pagination'=>$pagination
    ]);
}


    public function containsProfanity(Questions $question)
    {
        $words1 = $question->getTitle();
        $words = $question->getDescription(); // or any other property that contains the text to check for profanity
        $clean_words = \ConsoleTVs\Profanity\Builder::blocker($words)->filter();
        $clean_words1 = \ConsoleTVs\Profanity\Builder::blocker($words1)->filter();
      

        return $clean_words !== $words || $clean_words1 !== $words1  ;
    }

    #[Route('/addQuestion1', name: 'addQuestion1')]
    public function addQuestion(Request $request,Security $security): Response
    { $user = $security->getUser();

        if (!$user) {
            return $this->redirectToRoute('app_login');
        }
        $user_id=$user->getUserIdentifier();
        $question = new Questions();
        $question->setUser($user);
        $question->setCreatedAt(new \DateTime()); // ajouter la date de création
        $form = $this->createForm(QuestionType::class,$question);
        $form->handleRequest($request);
       // ...
       if ($form->isSubmitted() && $form->isValid()) {
        $question = $form->getData();
        $hasProfanity = $this->containsProfanity($question);
    
        if ($hasProfanity) {
            // la chaîne contient un mot interdit
            //return new JsonResponse(['message' => 'La chaîne contient un mot interdit']);
          ;
            return $this->redirectToRoute('addQuestion1', ['true' => true, 'containsProfanity' => $hasProfanity]);


        } if ($question->getUser()->isBlocked() && $question->getUser()->getBlockedUntil() > new \DateTime()) {
            // L'utilisateur est bloqué, on empêche l'ajout de la question
      $block='b';
            return $this->redirectToRoute('addQuestion1', ['true' => true, 'block' => $block]);
        }
        else {
            // la chaîne ne contient pas de mot interdit
            $question->setCreatedAt(new \DateTime());
            $em  = $this->getDoctrine()->getManager();
            $em->persist($question);
            $em->flush();
            return $this->redirectToRoute('app_question');

        }
        
       }
       return $this->render('forum/questionIntput.html.twig',['f'=>$form->createView()]); //pour l'envoyer l form et l 'afficher
    }
       
    #[Route('/deleteQuestion/{id}', name: 'delete_question')]
    public function deleteQuestion(int $id): Response
    {
     
        $question= $this->getDoctrine()->getManager()->getRepository(Questions::class)->find($id);
        
        // Vérifie que l'objet $question existe avant de le supprimer
        if (!$question) {
            throw $this->createNotFoundException('Question non trouvée');
        }
        $em = $this->getDoctrine()->getManager();
        $em->remove($question);
        $em->flush();
    
        return $this->redirectToRoute('app_question');
    }
    

    #[Route('/deleteVote/{id}', name: 'delete_v')]
    public function deleteVote(int  $id): Response
    
    {   $vote = $this->getDoctrine()->getManager()->getRepository(Votes::class)->find($id);
         $this->updateAnswerNbVote(-1, $vote);
        $em= $this->getDoctrine()->getManager();
        $em->remove($vote);
        $em->flush();
       
        $question=$vote->getAnswer()->getQuestion();
        return $this->redirectToRoute('question',['id'=>$question->getId(),] , )  ;    
        
    }
    #[Route('/deleteVoted/{id}', name: 'delete_vd')]
    public function deleteVoted(int  $id): Response
    
    {   $vote = $this->getDoctrine()->getManager()->getRepository(Votes::class)->find($id);
         $this->updateAnswerNbVote(1, $vote);
        $em= $this->getDoctrine()->getManager();
        $em->remove($vote);
        $em->flush();
       
        $question=$vote->getAnswer()->getQuestion();
        return $this->redirectToRoute('question',['id'=>$question->getId(),] , )  ;    
        
    }
    public function containsProfanity2(Answers $answer)
    {
       
        $words = $answer->getMessage(); // or any other property that contains the text to check for profanity
        $clean_words = \ConsoleTVs\Profanity\Builder::blocker($words)->filter();
       
      

        return $clean_words !== $words   ;
    }



    #[Route('/question/{id}', name: 'question')]
    public function showQuestion(QuestionsRepository $questionRepository, VotesRepository $voteRepository,$id,Request $request, MailerService $mailer ,Security $security): Response
    {$user = $security->getUser();

        if (!$user) {
            return $this->redirectToRoute('app_login');
        }
        $question = $questionRepository->findById($id);
        $id_user=$user->getId();
       
        $userVotes=0;
        $question = $this->getDoctrine()->getRepository(Questions::class)->find($id);
        $form = $this->createForm(QuestionType::class,$question);
        $form->handleRequest($request);
        $invalidData = false;
        if ($form->isSubmitted() && $form->isValid()) {
            $hasProfanity = $this->containsProfanity($question );
    
            if ($hasProfanity) {
                // la chaîne contient un mot interdit
                //return new JsonResponse(['message' => 'La chaîne contient un mot interdit']);
              
                return $this->redirectToRoute('question', ['id'=>$id, 'containsP' => $hasProfanity]);
        
        
            } else
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($question);
            $entityManager->flush();
            return $this->redirectToRoute('question',['id'=>$id,]  ) ;
        
        
            
            
        } 
        if ($form->isSubmitted() && !$form->isValid()) {
            $invalidData = true;
        
          
            return $this->redirectToRoute('question', ['id'=>$id,'contains' => true]);
        }
     
        
    
        if (!$question) {
            throw $this->createNotFoundException('Question not found');
        }
        
             // Création d'une nouvelle réponse associée à la question
             $answers = new Answers();
             $answers->setCreatedAt(new \DateTime()); 
             $answers->setUser($user);
             $answers->setQuestion($question);
     
             // Création du formulaire pour la réponse
             $answerForm = $this->createForm(AnswerType::class, $answers);
             $answerForm->handleRequest($request);
             if ( $answerForm->isSubmitted() &&  $answerForm->isValid()) {
                $answers = $answerForm->getData();
               
        $hasProfanity = $this->containsProfanity2($answers );
    
        if ($hasProfanity) {
            // la chaîne contient un mot interdit
            //return new JsonResponse(['message' => 'La chaîne contient un mot interdit']);
          
            return $this->redirectToRoute('question', ['id'=>$id,'true' => true, 'containsProfanity' => $hasProfanity]);


        } else {
                 $em  = $this->getDoctrine()->getManager();
               $em->persist($answers);
               $em->flush();
            
              // $mailMessage = $answer->getMessage(). '    '.' de la part de'.'  '.$answer->getUser(); 
                //$mailer ->sendEmail(content: $mailMessage); 
                $mailMessage = $answers->getMessage() ;
                $to="najiba.gragba@esprit.tn";
               $mailer->sendEmail($to, $mailMessage, $question->getUser());
               return $this->redirectToRoute('question',['id'=>$id,]  ) ;
               
             
        }   
                 // Redirection vers la page de la question avec un message de succès
               
            
                      
                
             }
         
           
           

             $answers = $question->getAnswers();
             $hasVotedArray = array();
             $votes = $this->getDoctrine()->getManager()->getRepository(Votes::class)->findAll();
             $userVotesByAnswer = array(); // initialize an empty array to store user IDs who voted for each answer
             
             foreach ($answers as $answer) {
                 $userVotes = $voteRepository->findByVoteByiduser($user->getId(), $answer->getId());
                 $hasVoted = ($userVotes != null) ? 1 : 0;
                 $hasVotedArray[$answer->getId()] = $hasVoted;
                
                 //check if the current user voted for this answer
                 foreach ($votes as $vote) {
                    $userVotesByAnswer[$vote->getAnswer()->getId()][] = $vote->getUser()->getId();
                     
                 }
            
             
             }
         
        

    // now you can use $hasVotedArray to render the voting buttons for each answer
    
    $previousVotes = isset($_COOKIE['votes']) ? json_decode($_COOKIE['votes'], true) : array();

  
 
             return $this->render('forum/question.html.twig', [
                 'question' => $question,
                 'answers' => $answers,
            
                 'userVotesByAnswer'=> $userVotesByAnswer,
                 'previousVotes' => $previousVotes,
                 'hasVotedArray'=>$hasVotedArray ,
                 'answerForm' => $answerForm->createView(),
                 'f' => $form->createView(),
                 'answerForms'=>$form->createView(),
                 'invalidData'=> $invalidData ,
                 'user'=>$user ,
                 'userid'=>$user->getId() ,
                // 'userVotesArray'=> $userVotesArray ,
                
             
             
               
                     
             
             ]);

}

public function hasUserVoted(Users $user,Answers $a, VotesRepository $voteRepository ): ?bool
{ $userVotes = $voteRepository->findByVoteByiduser($user->getId(), $a->getId());
    if($userVotes != null){
        return 1;
    }
  
   return 0;
}

public function getVotesByUser(Users $user,VotesRepository $voteRepository ) :votes 
{ $vote = $voteRepository->findByuser($user->getId());
   
        return $vote ;
    
    
}

#[Route('/editQuestionModal/{id}', name: 'editQuestionModal')]
public function editQuestionModal(Request $request, $id,VotesRepository $voteRepository,Security $security):Response
{ $user = $security->getUser();

    if (!$user) {
        return $this->redirectToRoute('app_login');
    }
    $question = $this->getDoctrine()->getManager()->getRepository(Questions::class)->find($id);
   $answers= $question->getAnswers();
    $form = $this->createForm(QuestionType::class,$question);
    $form->handleRequest($request);
    $invalidData = false;
  if ($form->isSubmitted() && $form->isValid()) {
    $hasProfanity = $this->containsProfanity($question );
    
    if ($hasProfanity) {
        // la chaîne contient un mot interdit
        //return new JsonResponse(['message' => 'La chaîne contient un mot interdit']);
      
        return $this->redirectToRoute('question', ['id'=>$id, 'containsP' => $hasProfanity]);


    } else{
    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($question);
    $entityManager->flush();
    return $this->redirectToRoute('question',['id'=>$id,]  ) ;

    }
    
    
} 
if ($form->isSubmitted() && !$form->isValid()) {
    $invalidData = true;

  
    return $this->redirectToRoute('question', ['id'=>$id,'contains' => true]);
}
$a= $this->getDoctrine()->getManager()->getRepository(Answers::class)->findAll();
$answers = $question->getAnswers();
foreach ($answers as $answer) {
    $hasVoted = $this->hasUserVoted($user,$answer,$voteRepository);
   
}
$previousVotes = isset($_COOKIE['votes']) ? json_decode($_COOKIE['votes'], true) : array();

  
return $this->render('forum/question.html.twig', [
    'question' => $question,
    'hasVoted'=>$hasVoted ,
    'previousVotes'=>$previousVotes,
    'f' => $form->createView(),
    'answerForms'=>$form->createView(),
    'invalidData'=> $invalidData ,
   
    'a'=>$a,
    'answers'=>$answers

  
        

]);


}
private function getErrorsFromForm(FormInterface $form)
{
    $errors = array();

    foreach ($form->getErrors() as $error) {
        $errors[] = $error->getMessage();
    }

    foreach ($form->all() as $childForm) {
        if ($childForm instanceof FormInterface) {
            if ($childErrors = $this->getErrorsFromForm($childForm)) {
                $errors[$childForm->getName()] = $childErrors;
            }
        }
    }

    return $errors;
}

public function submitForm(Request $request, $form)
{ 
    if ($form->isSubmitted() && !$form->isValid()) {
        // If the form is not valid, return an error message
       // return $this->redirectToRoute('question', ['id' => $id]);
    }
    

    // Otherwise, return a success message
   
}
#[Route('/vote/{id}/{userid}', name: 'vote')]
public function vote(Request $request,VotesRepository $votesRepository, AnswersRepository $answersRepository, $id,$userid,Security $security)
{$user = $security->getUser();

    if (!$user) {
        return $this->redirectToRoute('app_login');
    }
    // Récupérer l'utilisateur actuel
 

    // Récupérer la réponse concernée par l'upvote
    $answer = $answersRepository->find($id);
      // Récupérer la réponse concernée par le downvote
    
      $user1 = $this->getDoctrine()->getManager()->getRepository(Users::class)->find($userid);


      $id_user=$user->getId();
    $userVotes = $votesRepository->findVoteByiduser($id_user, $id);
    
        // faire quelque chose avec $userVotes pour cette réponse
 

    // Vérifier si l'utilisateur a déjà voté pour cette réponse
  
    $existingVote = $votesRepository->findOneByUserAndAnswer($user, $answer);
  
   

    if (!$existingVote) {
        // Si l'utilisateur n'a pas encore voté, créer un nouveau vote et l'ajouter à la base de données
        $newVote = new Votes();
        $newVote->setUser($user);
        $newVote->setAnswer($answer);
        $newVote->setVoteType(1);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($newVote);
        $entityManager->flush();

        // Mettre à jour le nombre de votes pour la réponse
        $this-> updateAnswerNbVote(1,  $newVote);
        return new Response('Vote successful');
       
    } elseif ($existingVote->getVoteType() == -1) {
        // Si l'utilisateur a déjà voté négativement, mettre à jour le vote existant et le nombre de votes de la réponse
        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->remove($existingVote);
        $existingVote->setVoteType(1);
        $entityManager->persist($existingVote);
        $entityManager->flush();

        $this->updateAnswerNbVote(2, $existingVote);
        return new Response('Vote successful');
     
    } elseif ($existingVote->getVoteType() == 1) {
        // Si l'utilisateur a déjà voté positivement, supprimer le vote existant et mettre à jour le nombre de votes de la réponse
        $this->deleteVote( $existingVote->getId());
        return new Response('Vote successful');
       
    }

}
#[Route('/get-vote-count/{answerId}', name: 'get-vote-count')]
public function getVoteCount($answerId)
{
    $entityManager = $this->getDoctrine()->getManager();
    $answer = $entityManager->getRepository(Answers::class)->find($answerId);

    $count = $answer->getVoteNb(); // utiliser la méthode "count()" de votre entité pour obtenir le nombre de votes

    return new JsonResponse(['count' => $count]);
}

#[Route('/voted/{id}/{userid}', name: 'voted')]
public function voted(Request $request,VotesRepository $votesRepository, AnswersRepository $answersRepository, $id,$userid,Security $security)
{
    // Récupérer l'utilisateur actuel
    //$user = $this->getUser();

    // Récupérer la session actuelle
    $user = $security->getUser();

    if (!$user) {
        return $this->redirectToRoute('app_login');
    }

    $user1 = $this->getDoctrine()->getManager()->getRepository(Users::class)->find($userid);

    // Récupérer la réponse concernée par le downvote
    $answer = $answersRepository->find($id);

    $id_user=$user->getId();
    $userVotes = $votesRepository->findVoteByiduser($id_user, $id);
    
 

    // Vérifier si l'utilisateur a déjà voté pour cette réponse
    $existingVote = $votesRepository->findOneByUserAndAnswer($user, $answer);
    dump($existingVote, 'Contenu de $existingVote :');

    if (!$existingVote) {
        // Si l'utilisateur n'a pas encore voté, créer un nouveau vote et l'ajouter à la base de données
        $newVote = new Votes();
        $newVote->setUser($user);
        $newVote->setAnswer($answer);
        $newVote->setVoteType(-1);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($newVote);
        $entityManager->flush();

        // Mettre à jour le nombre de votes pour la réponse
        $this->updateAnswerVoteCount($answer, -1);
        return new Response('Vote successful');
    } elseif ($existingVote->getVoteType() == 1) {
        // Si l'utilisateur a déjà voté positivement, mettre à jour le vote existant et le nombre de votes de la réponse
        $existingVote->setVoteType(-1);

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->flush();

        $this->updateAnswerNbVote(2, $existingVote);
        return new Response('Vote successful');
    }
    elseif ($existingVote->getVoteType() == -1) {
        // Si l'utilisateur a déjà voté negativement, supprimer le vote existant et mettre à jour le nombre de votes de la réponse
        $this->deleteVote( $existingVote->getId());
        return new Response('Vote successful');
       
    }
 


}






public function updateAnswerVoteCount(Answers $answer, int $modifier): void
{
    $answer->setVoteNb($answer->getVoteNb() + $modifier);
    $em = $this->getDoctrine()->getManager();
    $em->persist($answer);
    $em->flush();
}

// public function updateAnswerVoteCount(Answers $answer, int $modifier): void
// {
//     $answer->setVoteNb($answer->getVoteNb() + $modifier);
//     $em = $this->getDoctrine()->getManager();
//     $em->persist($answer);
//     $em->flush();
// }

// private function updateAnswerNbVoteu(int $modifier, Votes $vote): void
// {
//     $answer = $vote->getAnswer();
//     $answerVoteNb = $answer->getVoteNb();

//     // Mettre à jour le nombre de votes pour la réponse

//     $this->updateAnswerVoteCount($vote->getAnswer(),-1 );

//     // Mettre à jour l'entité Vote associée
//     $entityManager = $this->getDoctrine()->getManager();
//     $vote->setVoteType($vote->getVoteType() * $modifier);
//     $entityManager->persist($vote);
//     $entityManager->flush();
// }
private function updateAnswerNbVote(int $modifier, Votes $vote): void
{// vote.get_answer().set_vote_nb(answer_vote + vote.get_type_vote() * modifier);
    $answer = $vote->getAnswer();
    $answerVoteNb = $answer->getVoteNb();
  
    // Mettre à jour le nombre de votes pour la réponse
    //$answerVoteNb += $vote->getVoteType() * $modifier;
    $answer->setVoteNb($answerVoteNb);
    $vote->getAnswer()->setVoteNb( $answerVoteNb + $vote->getVoteType() * $modifier);

    // Mettre à jour l'entité Vote associée
    $entityManager = $this->getDoctrine()->getManager();
    $entityManager->persist($answer);
    $entityManager->flush();
    $entityManager->persist($vote);
    $entityManager->flush();
}


// le filtrage est  avec Query Builder 

#[Route('/filter', name: 'filter')]
public function filter(Request $request, PaginatorInterface $paginator): Response
{
    $subjectId = $request->query->get('subject');
    $keywords = $request->query->get('keywords');
    
    
    $qb = $this->getDoctrine()->getRepository(Questions::class)->createQueryBuilder('q');
    $qb->leftJoin('q.subject', 's');
    
    // Filtre par matière si un identifiant de matière est fourni
    if ($subjectId) {
        $qb->andWhere('s.id = :subjectId')
            ->setParameter('subjectId', $subjectId);
    }
    
    // Recherche par titre
    if ($keywords) {
        $keywordsArray = explode(' ', $keywords);
        $qb->andWhere($qb->expr()->like('q.title', ':keywords'));
        $qb->setParameter('keywords', '%' . implode('%', $keywordsArray) . '%');
    }
    
    // Créer la requête paginée
    $pagination = $paginator->paginate(
        $qb, // Requête à paginer
        $request->query->getInt('page', 1), // Numéro de la page par défaut
        3 // Nombre d'éléments par page
    );

    return $this->render('forum/questionsList.html.twig', [
        'pagination' => $pagination,
        'subjectId'=>$subjectId ,
        'answers' => $qb ,
        'subjects' => $this->getDoctrine()->getRepository(Subjects::class)->findAll(),
       
    ]);

}










}