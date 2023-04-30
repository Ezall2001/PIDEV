// Exercice 2 :
// Écrivez une classe C++ appelée « Student » avec les membres suivant :
// nom(de type char),
// note1,note2(de type int)
// Le programme demande à l’utilisateur d’entrer le nom et les notes.calc_moy()
// calcule la note moyenne et show() affiche le nom et la note moyenne.

#include <iostream>
using namespace std;

class Student
{

public:
    string nom;
    int note1, note2;

    Student(string _nom, int _note1, int _note2)
    {
        nom = _nom;
        note1 = _note1;
        note2 = _note2;
    }

    int calc_moy()
    {
        return (note1 + note2) / 2;
    }

    void show()
    {
        cout << "Étudiant: " << nom << " \n moyenne: " << calc_moy() << endl;
    }
};

int main()
{

    string nom;
    int note1, note2;

    cout << "Entrez le nom: ";
    cin >> nom;

    cout << "Entrez les notes de deux matiéres: ";
    cin >> note1;
    cin >> note2;

    Student s(nom, note1, note2);

    s.show();

    return 0;
}