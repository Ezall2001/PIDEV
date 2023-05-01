<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Repository\UsersRepository;



#[Route('/admin', name: 'admin_')]
class DashboardController extends AbstractController
{
    #[Route('/userLevelByYear', name: 'userLevelByYear')]
    public function statistics_UserScore(UsersRepository $usersRepository): Response
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

        return $this->render('admin/dashboard.html.twig', [
            'chartData' => json_encode($chartData),
        ]);
    }
}
