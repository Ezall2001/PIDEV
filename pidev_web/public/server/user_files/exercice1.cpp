// Exercice 1 :
// Écrivez une classe « Somme » ayant deux variables « n1 » et « n2 » et une fonction membre « sum() »
// qui calcule la somme.Dans la méthode principale main demandez à l’utilisateur d’entrez deux entiers
// et passez - les au constructeur par défaut de la classe « Somme » et afficher le résultat de l’addition des deux nombres.

#include <iostream>
using namespace std;

class Somme
{
public:
    int n1, n2;

    // constructeur par défaut
    Somme(int nbr1, int nbr2)
    {
        n1 = nbr1;
        n2 = nbr2;
        cout << "Les nombres sont initialisés.\n";
    }

    int sum()
    {
        return n1 + n2;
    }
};

int main()
{

    int n1, n2;

    cout << "Entrez le premier nombre : \n";
    cin >> n1;

    cout << "Entrez le deuxième nombre : \n";
    cin >> n2;

    // appeler le constructeur par défaut.
    Somme obj(n1, n2);

    cout << "Le résultat de l'addition est :" << obj.sum() << "\n";

    return 0;
}