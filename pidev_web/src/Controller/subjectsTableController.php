<?php

namespace App\Controller;

use App\Entity\Subjects;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\HttpFoundation\Request;
use App\Form\SubjectType;
use App\Form\SearchFormType;
use App\Repository\SubjectsRepository;
use App\Repository\CoursesRepository;
use Symfony\Component\Form\Extension\Core\Type\SubmitType;
use Doctrine\ORM\QueryBuilder;
use Symfony\Component\HttpFoundation\JsonResponse;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\Serializer\Annotation\Groups;
use Symfony\Component\Serializer\Serializer;
use RuntimeException;


class SubjectsTableController extends AbstractController
{
    #[Route('/subjects', name: 'app_subjects')]
    public function index(): Response
    {
        return $this->render('admin/subjectsTable.html.twig', [
            'controller_name' => 'subjectsTableController',
        ]);
    }

    #[Route('/admin/subjects', name: 'search')]
    public function afficherSubject(Request $request): Response
    {
        //execution de url
        $em = $this->getDoctrine()->getManager();
        $subjects = $em->getRepository(Subjects::class)->findAll();

        $form = $this->createForm(SearchFormType::class);
        $form->handleRequest($request);
        //verification with retrieves values
        if ($form->isSubmitted() && $form->isValid()) {
            $nameSearchTerm = $form->get('name')->getData();
            $classes_espritSearchTerm = $form->get('classes_esprit')->getData();
            $descriptionSearchTerm = $form->get('description')->getData();
            //create a new query builder and select from subbjects
            $qb = $em->createQueryBuilder();
            $qb->select('s')
                ->from(Subjects::class, 's');
            //criteria
            if ($nameSearchTerm) {
                $qb->andWhere('s.name LIKE :nameSearchTerm')
                    ->setParameter('nameSearchTerm', '%' . $nameSearchTerm . '%');
            }

            if ($descriptionSearchTerm) {
                $qb->andWhere('s.description LIKE :descriptionSearchTerm')
                    ->setParameter('descriptionSearchTerm', '%' . $descriptionSearchTerm . '%');
            }

            if ($classes_espritSearchTerm) {
                $qb->andWhere('s.classes_esprit LIKE :classes_espritSearchTerm')
                    ->setParameter('classes_espritSearchTerm', '%' . $classes_espritSearchTerm . '%');
            }


            $subjects = $qb->getQuery()->getResult();

            // Render a different template with search results
            return $this->render('admin/subjects.html.twig', [
                'form' => $form->createView(),
                'bla' => $subjects,
                'subjects' => $subjects,
            ]);
        }

        return $this->render('admin/subjectsTable.html.twig', [
            'form' => $form->createView(),
            'bla' => $subjects,
            'subjects' => $subjects,
        ]);
    }



    #[Route('/admin/addsubject', name: 'addsubject')]

    public function addSubject(Request $request): Response
    {
        $Subject = new Subjects();
        $form = $this->createForm(SubjectType::class, $Subject);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            $em = $this->getDoctrine()->getManager();
            $em->persist($Subject);
            $em->flush();
            return $this->redirectToRoute('search');
        } else
            return $this->render('admin/addsubject.html.twig', ['f' => $form->createView()]);
    }


    #[Route('/admin/modifiersubject/{id}', name: 'modifiersubject')]

    public function modifiersubject(Request $request, $id): Response
    {

        $subject = $this->getDoctrine()->getManager()->getRepository(Subjects::class)->find($id);
        $form = $this->createForm(SubjectType::class, $subject);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {


            $em = $this->getDoctrine()->getManager();

            $em->flush();

            return $this->redirectToRoute('search');
        } else
            return $this->render('admin/modifiersubject.html.twig', ['f' => $form->createView()]);
    }

    #[Route('/admin/deletesubject', name: 'deletesubject')]

    public function deletesubject(Request $request)
    {

        $subject = $this->getDoctrine()->getRepository(Subjects::class)->findOneBy(array('id' => $request->query->get("id")));
        $em = $this->getDoctrine()->getManager();
        $em->remove($subject);
        $em->flush();
        $form = $this->createForm(SearchFormType::class);
        $form->handleRequest($request);
        $subjects = $this->getDoctrine()->getManager()->getRepository(Subjects::class)->findAll();
        return $this->render('admin/subjectsTable.html.twig', [
            'form' => $form->createView(),
            'bla' => $subjects,
            'subjects' => $subjects,
        ]);
    }
}
