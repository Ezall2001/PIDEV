<?php
namespace App\Controller;
use App\Entity\Subjects;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Form\SubjectType;

class SubjectsTableController extends AbstractController
{
    #[Route('/subjects', name: 'app_subjects')]
    public function index(): Response
    {
        return $this->render('admin/subjectsTable.html.twig', [
            'controller_name' => 'subjectsTableController',
        ]);
    }
    
     /**
     * @Route("/admin", name="displaySubject")
     */
    public function afficherSubject(): Response
    {
        $subjects = $this->getDoctrine()->getManager()->getRepository(Subjects::class)->findAll();
        

        return $this->render('admin/subjectsTable.html.twig', [
            'bla'=> $subjects,
            
        ]);
    }
   

    /**
     * @Route("/addsubject", name="addsubject")
     */
    public function addSubject(Request $request): Response
    {

        $Subject = new Subjects();
        $form = $this->createForm(SubjectType::class, $Subject);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($Subject);
            $em->flush();
            return $this->redirectToRoute('displaySubject');
        } else
            return $this->render('admin/addsubject.html.twig', ['f' => $form->createView()]);
    }

/**
     * @Route("/modifiersubject/{id}", name="modifiersubject")
     */
    public function modifiersubject(Request $request, $id): Response
    {

        $subject = $this->getDoctrine()->getManager()->getRepository(Subjects::class)->find($id);
        $form = $this->createForm(SubjectType::class, $subject);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {


            $em = $this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('displaySubject');
        } else
            return $this->render('admin/modifiersubject.html.twig', ['f' => $form->createView()]);
    }

    /**
     * @Route("/deletesubject", name="deletesubject")
     */
    public function deletesubject(
        Request $request,

    ) {

        $subject = $this->getDoctrine()->getRepository(Subjects::class)->findOneBy(array('id' => $request->query->get("id")));
        $em = $this->getDoctrine()->getManager();
        $em->remove($subject);
        $em->flush();
        $subjects = $this->getDoctrine()->getManager()->getRepository(Subjects::class)->findAll();
        return $this->render('admin/subjectsTable.html.twig', [
            'bla' => $subjects
        ]);
    }


 
//     /**
//  * @Route("/search", name="search")
//  */
// public function search(Request $request): Response
// {
//     $query = $request->query->get('q'); // get the search query from the request

//     // perform the search using the Doctrine ORM
//     $entityManager = $this->getDoctrine()->getManager();
//     $admin = $entityManager->getRepository(Subjects::class)->createQueryBuilder('s')
//         ->where('s.name LIKE :query')
//         ->setParameter('query', '%'.$query.'%')
//         ->getQuery()
//         ->getResult();

//     // render the result using a template
//     return $this->render('admin/result.html.twig', [
//         'query' => $query,
//         'admin' => $admin,
//     ]);
// }

}
