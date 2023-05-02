<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\UsersRepository;
use App\Repository\SubjectsRepository;


#[Route('/admin', name: 'admin_')]
class DashboardController extends AbstractController
{
    #[Route('/userLevelByYear', name: 'userLevelByYear')]
    public function statistics_UserScore(UsersRepository $usersRepository,SubjectsRepository $subjectsRepository): Response
    {
        $users = $usersRepository->findAll();

        $data = [];
        $levels = [];

        foreach ($users as $user) {
            $level = $user->getLevel();
            $year = $user->getCreatedAt()->format('Y');
            if (!array_key_exists($year, $data)) {
                $data[$year] = [];
            }
            if (!array_key_exists($level, $data[$year])) {
                $data[$year][$level] = 1;
                if (!in_array($level, $levels)) {
                    $levels[] = $level;
                }
            } else {
                $data[$year][$level]++;
            }
        }

        $chartData = [];
        $chartData[] = ['Year', ...$levels];
        foreach ($data as $year => $yearData) {
            $row = [$year];
            foreach ($levels as $level) {
                $count = $yearData[$level] ?? 0;
                $row[] = $count;
            }
            $chartData[] = $row;
        }



        // ! RIM 


         // Retrieve all subjects from the database
         $subjects = $subjectsRepository->findAll();

         // Initialize an empty array to store the subject data
         $subjectData = [];
 
         // Iterate through the subjects and count the number of times each class appears
         foreach ($subjects as $subject) {
             $className = $subject->getClassesEsprit();
             if (!array_key_exists($className, $subjectData)) {
                 $subjectData[$className] = 1;
             } else {
                 $subjectData[$className]++;
             }
         }
 
         // Prepare the data for the pie chart by formatting it as an array of arrays
         $chartDataRim = [];
         foreach ($subjectData as $className => $countRim) {
             $chartDataRim[] = [
                 'name' => $className,
                 'y' => $countRim,
             ];
         }

        return $this->render('admin/dashboard.html.twig', [
            'chartData' => json_encode($chartData),
            'chartDataRim' => json_encode($chartDataRim),
        ]);
    }

}
