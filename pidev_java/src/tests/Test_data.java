package tests;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import entities.Answer;
import entities.Course;
import entities.Participation;
import entities.Question;
import entities.Resource;
import entities.Session;
import entities.Subject;
import entities.Test;
import entities.User;
import entities.Vote;
import entities.Test_qs;
import entities.Test_result;
import services.Answer_service;
import services.Course_service;
import services.Participation_service;
import services.Question_service;
import services.Resource_service;
import services.Session_service;
import services.Subject_service;
import services.Test_service;
import services.User_service;
import services.Vote_service;
import services.Test_qs_service;
import services.Test_result_service;
import utils.Jdbc_connection;
import utils.Log;

public class Test_data {

        static private Connection cnx = Jdbc_connection.getInstance();

        // *question-1 // types des variables en C
        static private String question_1 = "Lequel des énoncés suivants n’est pas une déclaration de nom de variable valide ?";
        static private String question_1_response = "int __A7;";
        static private String question_1_optionA = "int __7a;";
        static private String question_1_optionB = "int __A7;";
        static private String question_1_optionC = "int __a7;";
        static private String question_1_optionD = "aucune réponses.";

        // *question-2 // structures de données en C++
        static private String question_2 = "Lequel des opérateurs suivants s’appelle « Opérateur d’adresse »?";
        static private String question_2_response = "&";
        static private String question_2_optionA = "*";
        static private String question_2_optionB = "$";
        static private String question_2_optionC = "%";
        static private String question_2_optionD = "&";

        // *question-3 // pointeurs C
        static private String question_3 = "Quelle différence entre char *chaine[256] et char chaine[256] ?";
        static private String question_3_response = "Dans le premier cas, nous déclarons un tableau de 256 pointeurs (sur des caractères) alors que dans le second nous déclarons un tableau de 256 caractères qui pourra contenir une chaîne.";
        static private String question_3_optionA = "La première forme permet de créer des tableaux d'entiers.";
        static private String question_3_optionB = "Dans le premier cas, nous déclarons un tableau de 255 pointeurs (sur des caractères) alors que dans le second nous déclarons un tableau de 255 caractères qui pourra contenir une chaîne.";
        static private String question_3_optionC = "Dans le premier cas, nous déclarons un tableau de 256 pointeurs (sur des caractères) alors que dans le second nous déclarons un tableau de 256 caractères qui pourra contenir une chaîne.";
        static private String question_3_optionD = "Aucune réponse";

        // *question-4 // classes C++
        static private String question_4 = "Lequel des éléments suivants est un moyen de lier les données et leurs fonctions associées ?";
        static private String question_4_response = "Classes";
        static private String question_4_optionA = "Classes";
        static private String question_4_optionB = "Méthodes";
        static private String question_4_optionC = "Données";
        static private String question_4_optionD = "Tables";

        // !----------------------------------------------------------------

        // *question-5 // test 0 : pointeurs
        static private String question_5 = "Qu'est-ce qu'un pointeur en programmation C ?";
        static private String question_5_response = "Une variable qui stocke l'adresse mémoire d'une autre variable";
        static private String question_5_optionA = "Une variable qui stocke une chaîne de caractères";
        static private String question_5_optionB = "Une variable qui stocke une valeur";
        static private String question_5_optionC = "Aucun de ces réponses";
        static private String question_5_optionD = "Une variable qui stocke l'adresse mémoire d'une autre variable";

        // *question-8 // test 0 : pointeurs
        static private String question_8 = "Comment obtenir l'adresse mémoire d'une variable en C ?";
        static private String question_8_response = "En utilisant l'opérateur '&'";
        static private String question_8_optionA = "En utilisant l'opérateur '*'";
        static private String question_8_optionB = "En utilisant l'opérateur '/'";
        static private String question_8_optionC = "En utilisant l'opérateur '&'";
        static private String question_8_optionD = "Aucun de ces réponses";

        // *question-9 // test 0 : pointeurs
        static private String question_9 = "Quelle est la valeur par défaut d'un pointeur en C ?";
        static private String question_9_response = "null";
        static private String question_9_optionA = "null";
        static private String question_9_optionB = "0";
        static private String question_9_optionC = "1";
        static private String question_9_optionD = "Aucune réponse";

        // *question-10 // test 0 : pointeurs
        static private String question_10 = "Comment accéder à la valeur pointée par un pointeur en C ?";
        static private String question_10_response = "En utilisant l'opérateur ''";
        static private String question_10_optionA = "En utilisant l'opérateur '/'";
        static private String question_10_optionB = "En utilisant l'opérateur '&'";
        static private String question_10_optionC = "En utilisant l'opérateur ''";
        static private String question_10_optionD = "Toutes les réponses sont correctes";

        // *question-11 // test 0 : pointeurs
        static private String question_11 = "Comment allouer de la mémoire dynamiquement pour un tableau en C ?";
        static private String question_11_response = "En utilisant la fonction malloc()";
        static private String question_11_optionA = "En utilisant la fonction printf()";
        static private String question_11_optionB = "En utilisant la fonction scanf()";
        static private String question_11_optionC = "En utilisant la fonction malloc()";
        static private String question_11_optionD = "Aucune réponses";

        // !----------------------------------------------------------------

        // *question-6 // subject C++
        static private String question_6 = "Lequel des éléments suivants est utilisé pour les commentaires en C++ ?";
        static private String question_6_response = "Les deux // commentaire ou /* commentaire */";
        static private String question_6_optionA = "// commentaire";
        static private String question_6_optionB = "lettres majuscules";
        static private String question_6_optionC = "/* commentaire */";
        static private String question_6_optionD = "Les deux // commentaire ou /* commentaire */";

        // *question-7 // subject Java
        static private String question_7 = "Le constructeur d'une classe doit porter le même nom que la classe ?";
        static private String question_7_response = "Oui";
        static private String question_7_optionA = "pas obligatoirement";
        static private String question_7_optionB = "Oui";
        static private String question_7_optionC = "non, c'est interdit";
        static private String question_7_optionD = "c'est déconseillé";

        static private String topic_1 = "Comprendre les fondamentaux des variables de la programmation orientée objet en C";
        static private String topic_2 = "Comprendre les variables, constantes et opérateurs du langage C";
        static private String topic_3 = "Comprendre les jetons, les expressions et les structures de contrôle du langage C";
        static private String topic_4 = "Comprende bien les structures des données en C++";
        static private String topic_5 = "Apprendre à programmer en C, déclaration des variables.";
        static private String topic_6 = "Comprendre l'utilité des classes en C++.";

        static private String bio_1 = "Je suis un développeur Java enthousiaste sur lequel vous pouvez compter, j'ai de solides connaissances en javascript et plus de 4 ans d'expérience dans l'industrie au sein de l'équipe de développement de programmes informatiques.";
        static private String bio_2 = "Étudiant enthousiaste de premier cycle en génie logiciel expérimenté avec les langages de programmation Python, C++ et MATLAB.";
        static private String bio_3 = "Rédacteur de contenu expérimenté et expert en marketing numérique avec plus de dix ans d'expérience de travail dans des startups.";
        static private String bio_4 = "Etudiante en 4 spécialité intelligence artificielle je donne des cours d'informatique et de programmation je suis ravi de partager mes connaissances et de travailler avec vous pour vous aider à atteindre vos objectifs en programmation";
        static private String bio_5 = "Développeur full stack certifié Scrum, avec plus de 2 ans d'expérience. J'aime partager mes connaissances et aider les autres à comprendre les concepts complexes de la programmation.";
        static private String bio_6 = "J'ai commencé à apprendre la programmation à l'âge de 15 ans, en utilisant diverses ressources en ligne. Au fil des ans, j'ai perfectionné mes compétences en programmation en travaillant sur des projets personnels et en participant à des hackathons et des concours de programmation.";
        static private String bio_7 = "I am a highly organised individual with great communication and interpersonal skills, and have three years' experience working as an administrator. I have strong typing and data entry skills, and enjoy working independently as well as in a team.";

        static private String course_description_1 = "Voici les principaux types de variables existant en langage C, que l'on peut classer en deux catégories : ceux qui permettent de stocker des nombres entiers : signed char , int , long ; ceux qui permettent de stocker des nombres décimaux : float , double.";
        static private String course_description_2 = "Le langage C++ permet la définition de types personnalisés construits à partir des types de base du langage. Outre les tableaux, que l'on a déjà présentés, il est possible de définir différents types de données évolués, principalement à l'aide de la notion de structure. Par ailleurs, les variables déclarées dans un programme se distinguent, outre par leur type, par ce que l'on appelle leur classe de stockage.";
        static private String course_description_3 = "Les pointeurs en C constituent ce qui est appelé une fonctionnalité bas niveau, c'est-à-dire un mécanisme qui nécessite de connaître quelques détails sur le fonctionnement d'un ordinateur pour être compris et utilisé correctement. Dans le cas des pointeurs, il s'agira surtout de disposer de quelques informations sur la mémoire vive.";
        static private String course_description_4 = "Une classe se compose d'un type d'instance et d'un objet de classe : un type d'instance est une structure contenant des membres variables appelés variables d'instance et des membres de fonction appelés méthodes d'instance.";

        static private String description_1 = "Un langage de programmation imperatif generaliste, de bas niveau.";
        static private String description_2 = "le langage de programmation le plus utilise par les developpeurs, notamment en ce qui concerne les applications ";
        static private String description_3 = "Java definit a la fois un langage de programmation oriente objet et une plateforme informatique";

        static private String answer_1 = "Cela signifie que == vérifie si les deux objets pointent vers le même emplacement de mémoire, tandis qu'equals() compare les valeurs contenues dans les objets";
        static private String answer_2 = "Y'a pas de différence.";
        static private String answer_3 = "Le polymorphisme en termes simples signifie avoir plusieurs formes. Son comportement est différent dans différentes situations.";
        static private String answer_4 = "Le constructeur est une fonction membre qui est exécutée automatiquement à chaque fois qu’un objet est créé.";
        static private String answer_5 = "Les constructeurs ne portent necéssairement pas le même nom que sa classe, et retourne obligatoirement un object.";
        static private String answer_6 = "Une variable déclarée dans une fonction ou un bloc est appelée variable locale, et une variable déclarée en dehors d'une fonction ou d'un bloc est appelée variable globale.";

        static private String question_description_1 = "str1.equals(str2.length()) cette ligne de code ne fonctionne pas et aucune erreur ne s'affiche";
        static private String question_description_2 = "En tant que débutant en C++,j'essaie d'apprendre en ligne mais je n'arrive pas à comprendre le principe du polymorphisme.Pourriez-vous m'expliquer ce concept plus en détail?";
        static private String question_description_3 = "j'ai essayer d'utiliser la variable globale counter sans la déclarer. Cela m'a entraîné une erreur de compilation";
        static private String question_description_4 = "Que signifie la définition du constructeur : myclass (unsigned int param) : param_ (param) signifie et l'avantage pour le code ?";

        static private User_service user_service = new User_service();
        static private Subject_service subject_service = new Subject_service();
        static private Course_service course_service = new Course_service();
        static private Session_service session_service = new Session_service();
        static private Answer_service answer_service = new Answer_service();
        static private Question_service question_service = new Question_service();
        static private Test_service test_service = new Test_service();
        static private Test_qs_service test_qs_service = new Test_qs_service();
        static private Test_result_service test_result_service = new Test_result_service();
        static private Participation_service participation_service = new Participation_service();
        static private Resource_service resource_service = new Resource_service();
        static private Vote_service vote_service = new Vote_service();

        public static void main(String[] args) {
                clear_db();
                List<User> users = insert_users();
                List<Subject> subjects = insert_subjects();
                List<Course> courses = insert_courses(subjects);
                List<Session> sessions = insert_sessions(courses, users);
                List<Participation> participations = insert_participations(users, sessions);
                List<Resource> resources = insert_resources(sessions);
                List<Test> tests = insert_tests(subjects, courses);
                List<Test_qs> test_questions = insert_test_questions(tests);
                List<Test_result> test_result = insert_test_results(users, tests);
                List<Question> questions = insert_questions(users, subjects);
                List<Answer> answers = insert_answers(users, questions);
                List<Vote> votes = insert_votes(users, answers);
        }

        public static void clear_db() {

                List<String> tables = Arrays.asList(
                                "votes", "answers", "questions", "participations", "test_results", "test_qs", "tests",
                                "resources",
                                "sessions",
                                "courses", "subjects", "users");

                tables.forEach(table -> {
                        try {
                                cnx.prepareStatement(String.format("delete from %s", table)).executeUpdate();
                        } catch (Exception e) {
                                Log.file(e.getMessage());
                        }
                });
        }

        public static List<User> insert_users() {
                User user_1 = new User("armen", "bakir", 22, bio_1, "armen.bakir@esprit.tn", "test1111",
                                "server/profile_avatars/armen_pic.jpg");
                User user_2 = new User("eya", "harbi", 22, bio_2, "eya.harbi@esprit.tn", "test2222",
                                "server/profile_avatars/eya_pic.jpg");
                User user_3 = new User("najiba", "gragba", 22, bio_3, "najiba.gragba@esprit.tn", "test3333",
                                "server/profile_avatars/najiba_pic.jpg");
                User user_4 = new User("nour", "el houda kchaou", 21, bio_4, "nour.elhoudakchaou@esprit.tn", "test4444",
                                "server/profile_avatars/nour_pic.jpg");
                User user_5 = new User("rym", "aissa", 22, bio_5, "rym.aissa@esprit.tn", "test5555",
                                "server/profile_avatars/rim_pic.jpg");
                User user_6 = new User("mohammed", "zaiene", 25, bio_6, "mohammed.zaiene@esprit.tn", "test6666", null);
                User user_7 = new User("Mr", "Admin", 30, bio_7, "admin@esprit.tn", "test7777",
                                "server/profile_avatars/admin_pic.png");
                user_7.set_type(User.Type.ADMIN.toString());

                user_1.set_hashed_password();
                user_2.set_hashed_password();
                user_3.set_hashed_password();
                user_4.set_hashed_password();
                user_5.set_hashed_password();
                user_6.set_hashed_password();
                user_7.set_hashed_password();

                List<User> users = Arrays.asList(user_1, user_2, user_3, user_4, user_5, user_6, user_7);

                return users.stream().map(
                                user -> user_service.add(user)).collect(Collectors.toList());
        }

        public static List<Subject> insert_subjects() {
                List<Subject> subjects = new ArrayList<>();

                List<Subject.Class_esprit> classes_esprit_1 = Arrays.asList(Subject.Class_esprit.A1,
                                Subject.Class_esprit.P2);

                List<Subject.Class_esprit> classes_esprit_2 = Arrays.asList(
                                Subject.Class_esprit.A2, Subject.Class_esprit.P2);

                List<Subject.Class_esprit> classes_esprit_3 = Arrays.asList(
                                Subject.Class_esprit.A3, Subject.Class_esprit.B3);

                subjects.add(new Subject("C", description_1));
                subjects.get(0).set_classes_esprit(classes_esprit_1);

                subjects.add(new Subject("C++", description_2));
                subjects.get(1).set_classes_esprit(classes_esprit_2);

                subjects.add(new Subject("java", description_3));
                subjects.get(2).set_classes_esprit(classes_esprit_3);

                return subjects.stream().map(subject -> subject_service.add(subject)).collect(Collectors.toList());
        }

        public static List<Course> insert_courses(List<Subject> subjects) {
                List<Course> courses_list = new ArrayList<>();

                courses_list.add(new Course("Types des variables", course_description_1,
                                Course.Difficulty.EASY.toString()));
                courses_list.get(0).set_subject(subjects.get(0));

                courses_list.add(new Course("Structure de données", course_description_2,
                                Course.Difficulty.EASY.toString()));
                courses_list.get(1).set_subject(subjects.get(1));

                courses_list.add(new Course("Pointeurs", course_description_3, Course.Difficulty.HARD.toString()));
                courses_list.get(2).set_subject(subjects.get(0));

                courses_list.add(new Course("Classes", course_description_4, Course.Difficulty.MEDIUM.toString()));
                courses_list.get(3).set_subject(subjects.get(1));

                return courses_list.stream().map(
                                course -> course_service.add(course)).collect(Collectors.toList());
        }

        public static List<Session> insert_sessions(List<Course> courses, List<User> users) {
                List<Session> sessions = new ArrayList<>();

                LocalDate date_1 = LocalDate.of(2023, 2, 23);
                LocalDate date_2 = LocalDate.of(2023, 2, 24);
                LocalTime time_1 = LocalTime.of(12, 00, 00);
                LocalTime time_2 = LocalTime.of(13, 00, 00);
                LocalTime time_3 = LocalTime.of(17, 30, 00);
                LocalTime time_4 = LocalTime.of(18, 30, 00);

                sessions.add(new Session(18.5, date_1, time_2, time_3, topic_1));
                sessions.get(0).set_course(courses.get(0));
                sessions.get(0).set_user(users.get(0));

                sessions.add(new Session(20.5, date_2, time_3, time_4, topic_2));
                sessions.get(1).set_course(courses.get(0));
                sessions.get(1).set_user(users.get(1));

                sessions.add(new Session(10.3, date_1, time_1, time_2, topic_3));
                sessions.get(2).set_course(courses.get(0));
                sessions.get(2).set_user(users.get(0));

                sessions.add(new Session(15.5, date_2, time_1, time_2, topic_4));
                sessions.get(3).set_course(courses.get(1));
                sessions.get(3).set_user(users.get(1));

                sessions.add(new Session(19.2, date_2, time_2, time_4, topic_5));
                sessions.get(4).set_course(courses.get(0));
                sessions.get(4).set_user(users.get(2));

                sessions.add(new Session(11.5, date_1, time_1, time_2, topic_6));
                sessions.get(5).set_course(courses.get(3));
                sessions.get(5).set_user(users.get(2));

                return sessions.stream().map(
                                session -> session_service.add(session)).collect(Collectors.toList());
        }

        public static List<Test> insert_tests(List<Subject> subjects, List<Course> courses) {
                List<Test> tests = new ArrayList<>();

                tests.add(new Test(3, 11, Test.Type.COURSE.toString()));
                tests.get(0).set_course(courses.get(0));

                tests.add(new Test(10, 11, Test.Type.COURSE.toString()));
                tests.get(1).set_course(courses.get(1));

                tests.add(new Test(5, 1, Test.Type.COURSE.toString()));
                tests.get(2).set_course(courses.get(2));

                tests.add(new Test(2, 11, Test.Type.COURSE.toString()));
                tests.get(3).set_course(courses.get(3));

                tests.add(new Test(5, 11, Test.Type.SUBJECT.toString()));
                tests.get(4).set_subject(subjects.get(0));

                tests.add(new Test(7, 120, Test.Type.SUBJECT.toString()));
                tests.get(5).set_subject(subjects.get(1));

                tests.add(new Test(10, 11, Test.Type.SUBJECT.toString()));
                tests.get(6).set_subject(subjects.get(2));

                return tests.stream().map(
                                test -> test_service.add(test)).collect(Collectors.toList());
        }

        public static List<Test_qs> insert_test_questions(List<Test> tests) {
                List<Test_qs> test_questions = new ArrayList<>();

                test_questions.add(
                                new Test_qs(1, question_1, question_1_response, question_1_optionA, question_1_optionB,
                                                question_1_optionC, question_1_optionD));
                test_questions.get(0).set_test(tests.get(0));

                test_questions.add(
                                new Test_qs(1, question_2, question_2_response, question_2_optionA, question_2_optionB,
                                                question_2_optionC, question_2_optionD));
                test_questions.get(1).set_test(tests.get(1));

                test_questions.add(
                                new Test_qs(1, question_3, question_3_response, question_3_optionA, question_3_optionB,
                                                question_3_optionC, question_3_optionD));

                test_questions.get(2).set_test(tests.get(2));

                test_questions.add(
                                new Test_qs(1, question_4, question_4_response, question_4_optionA, question_4_optionB,
                                                question_4_optionC, question_4_optionD));

                test_questions.get(3).set_test(tests.get(3));

                // ! 
                test_questions.add(
                                new Test_qs(1, question_5, question_5_response, question_5_optionA, question_5_optionB,
                                                question_5_optionC, question_5_optionD));
                test_questions.get(4).set_test(tests.get(2));

                test_questions.add(
                                new Test_qs(2, question_8, question_8_response, question_8_optionA, question_8_optionB,
                                                question_8_optionC, question_8_optionD));
                test_questions.get(5).set_test(tests.get(2));

                test_questions.add(
                                new Test_qs(3, question_9, question_9_response, question_9_optionA, question_9_optionB,
                                                question_9_optionC, question_9_optionD));
                test_questions.get(6).set_test(tests.get(2));

                test_questions.add(new Test_qs(4, question_10, question_10_response, question_10_optionA,
                                question_10_optionB,
                                question_10_optionC, question_10_optionD));
                test_questions.get(7).set_test(tests.get(2));

                test_questions.add(new Test_qs(5, question_11, question_11_response, question_11_optionA,
                                question_11_optionB,
                                question_11_optionC, question_11_optionD));
                test_questions.get(8).set_test(tests.get(2));
                //!
                test_questions.add(
                                new Test_qs(1, question_6, question_6_response, question_6_optionA, question_6_optionB,
                                                question_6_optionC, question_6_optionD));
                test_questions.get(9).set_test(tests.get(5));

                test_questions.add(
                                new Test_qs(1, question_7, question_7_response, question_7_optionA, question_7_optionB,
                                                question_7_optionC, question_7_optionD));
                test_questions.get(10).set_test(tests.get(6));

                return test_questions.stream().map(
                                test_question -> test_qs_service.add(test_question)).collect(Collectors.toList());
        }

        public static List<Test_result> insert_test_results(List<User> users, List<Test> tests) {
                List<Test_result> test_results = new ArrayList<>();

                test_results.add(new Test_result(19, users.get(0), tests.get(0)));
                test_results.add(new Test_result(16, users.get(0), tests.get(1)));

                test_results.add(new Test_result(20, users.get(1), tests.get(0)));
                test_results.add(new Test_result(15, users.get(1), tests.get(1)));

                return test_results.stream().map(
                                test_result -> test_result_service.add(test_result)).collect(Collectors.toList());
        }

        public static List<Participation> insert_participations(List<User> users, List<Session> sessions) {

                Participation participation_1 = new Participation();
                participation_1.set_user(users.get(1));
                participation_1.set_session(sessions.get(0));

                Participation participation_2 = new Participation();
                participation_2.set_user(users.get(2));
                participation_2.set_session(sessions.get(0));

                Participation participation_3 = new Participation();
                participation_3.set_user(users.get(0));
                participation_3.set_session(sessions.get(1));

                Participation participation_4 = new Participation();
                participation_4.set_user(users.get(2));
                participation_4.set_session(sessions.get(1));

                Participation participation_5 = new Participation();
                participation_5.set_user(users.get(0));
                participation_5.set_session(sessions.get(2));

                Participation participation_6 = new Participation();
                participation_6.set_user(users.get(1));
                participation_6.set_session(sessions.get(2));

                List<Participation> participations = Arrays.asList(participation_1, participation_2, participation_3,
                                participation_4, participation_5, participation_6);

                participations = participations.stream().map(
                                participation -> participation_service.add(participation)).collect(Collectors.toList());

                participation_1.set_state(Participation.State.ACCEPTED);
                participation_3.set_state(Participation.State.DENIED);
                participation_4.set_state(Participation.State.ACCEPTED);
                participation_5.set_state(Participation.State.DENIED);
                participation_6.set_state(Participation.State.ACCEPTED);

                participations.stream().forEach(
                                participation -> participation_service.update(participation));

                return participations;
        }

        public static List<Resource> insert_resources(List<Session> sessions) {
                List<Resource> resources = new ArrayList<>();

                Resource resource_1 = new Resource();
                resource_1.set_session(sessions.get(0));
                resource_1.set_name("Chapitre 1 : Types de variables");
                resource_1.set_file_path("server/user_files/Types_de_variables.ppt");
                resources.add(resource_1);

                Resource resource_2 = new Resource();
                resource_2.set_session(sessions.get(0));
                resource_2.set_name("Types de variables en C");
                resource_2.set_file_path("server/user_files/type de variables-2.pdf");
                resources.add(resource_2);

                Resource resource_3 = new Resource();
                resource_3.set_session(sessions.get(1));
                resource_3.set_name("Chapitre 1 : Types de variables");
                resource_3.set_file_path("server/user_files/Types_de_variables.ppt");
                resources.add(resource_3);

                Resource resource_4 = new Resource();
                resource_4.set_session(sessions.get(1));
                resource_4.set_name("Introduction");
                resource_4.set_file_path("server/user_files/type de variables-2.pdf");
                resources.add(resource_4);

                Resource resource_5 = new Resource();
                resource_5.set_session(sessions.get(2));
                resource_5.set_name("Chapitre 1 : Types de variables");
                resource_5.set_file_path("server/user_files/Types_de_variables.ppt");
                resources.add(resource_5);

                Resource resource_6 = new Resource();
                resource_6.set_session(sessions.get(2));
                resource_6.set_name("Introduction");
                resource_6.set_file_path("server/user_files/type de variables-2.pdf");
                resources.add(resource_6);

                Resource resource_7 = new Resource();
                resource_7.set_session(sessions.get(3));
                resource_7.set_name("Chapitre 4 : Les structures de données");
                resource_7.set_file_path("server/user_files/Les structures de données.mp4");
                resources.add(resource_7);

                Resource resource_8 = new Resource();
                resource_8.set_session(sessions.get(3));
                resource_8.set_name("Exercice d'application 1");
                resource_8.set_file_path("server/user_files/exercice1.cpp");
                resources.add(resource_8);

                Resource resource_9 = new Resource();
                resource_9.set_session(sessions.get(4));
                resource_9.set_name("Chapitre 1 : Types de variables");
                resource_9.set_file_path("server/user_files/Types_de_variables.ppt");
                resources.add(resource_9);

                Resource resource_10 = new Resource();
                resource_10.set_session(sessions.get(4));
                resource_10.set_name("Introduction");
                resource_10.set_file_path("server/user_files/type de variables-2.pdf");
                resources.add(resource_10);

                Resource resource_11 = new Resource();
                resource_11.set_session(sessions.get(5));
                resource_11.set_name("Chapitre 4 : Les classes en C++");
                resource_11.set_file_path("server/user_files/les classes en C++.mp4");
                resources.add(resource_11);

                Resource resource_12 = new Resource();
                resource_12.set_session(sessions.get(5));
                resource_12.set_name("Exercice d'application 1");
                resource_12.set_file_path("server/user_files/exercice1.cpp");
                resources.add(resource_12);

                Resource resource_13 = new Resource();
                resource_13.set_session(sessions.get(5));
                resource_13.set_name("Exercice d'application 2");
                resource_13.set_file_path("server/user_files/exercice2.cpp");
                resources.add(resource_13);

                return resources.stream().map(
                                resource -> resource_service.add(resource)).collect(Collectors.toList());
        }

        public static List<Question> insert_questions(List<User> users, List<Subject> subjects) {
                List<Question> questions_list = new ArrayList<>();

                questions_list.add(new Question("Quelle est la différence entre equals() et == ?",
                                question_description_1));
                questions_list.get(0).set_user(users.get(0));
                questions_list.get(0).set_subject(subjects.get(2));

                questions_list.add(new Question("Qu'est-ce que le polymorphisme en C++ ?", question_description_2));
                questions_list.get(1).set_user(users.get(1));
                questions_list.get(1).set_subject(subjects.get(1));

                questions_list
                                .add(new Question(
                                                "Quelle est la différence entre la variable locale et la variable globale en C ?",
                                                question_description_3));
                questions_list.get(2).set_user(users.get(2));
                questions_list.get(2).set_subject(subjects.get(0));

                questions_list.add(
                                new Question("Qu'est-ce que le rôle du constructeur en C++ ?", question_description_4));
                questions_list.get(3).set_user(users.get(2));
                questions_list.get(3).set_subject(subjects.get(1));

                return questions_list.stream().map(
                                question -> question_service.add(question)).collect(Collectors.toList());
        }

        public static List<Answer> insert_answers(List<User> users, List<Question> questions) {
                List<Answer> answers_list = new ArrayList<>();

                answers_list.add(new Answer(answer_1));
                answers_list.get(0).set_question(questions.get(0));
                answers_list.get(0).set_user(users.get(3));

                answers_list.add(new Answer(answer_2));
                answers_list.get(1).set_question(questions.get(0));
                answers_list.get(1).set_user(users.get(4));

                answers_list.add(new Answer(answer_3));
                answers_list.get(2).set_question(questions.get(1));
                answers_list.get(2).set_user(users.get(5));

                answers_list.add(new Answer(answer_4));
                answers_list.get(3).set_question(questions.get(2));
                answers_list.get(3).set_user(users.get(4));

                answers_list.add(new Answer(answer_5));
                answers_list.get(4).set_question(questions.get(2));
                answers_list.get(4).set_user(users.get(0));

                answers_list.add(new Answer(answer_6));
                answers_list.get(5).set_question(questions.get(2));
                answers_list.get(5).set_user(users.get(4));

                return answers_list.stream().map(
                                answer -> answer_service.add(answer)).collect(Collectors.toList());
        }

        public static List<Vote> insert_votes(List<User> users, List<Answer> answers) {
                List<Vote> votes_list = new ArrayList<>();

                votes_list.add(new Vote(1, users.get(0), answers.get(0)));
                votes_list.add(new Vote(-1, users.get(1), answers.get(0)));
                votes_list.add(new Vote(1, users.get(2), answers.get(0)));

                votes_list.add(new Vote(-1, users.get(5), answers.get(1)));
                votes_list.add(new Vote(-1, users.get(3), answers.get(1)));

                votes_list.add(new Vote(1, users.get(1), answers.get(2)));
                votes_list.add(new Vote(1, users.get(2), answers.get(2)));
                votes_list.add(new Vote(1, users.get(3), answers.get(2)));
                votes_list.add(new Vote(1, users.get(4), answers.get(2)));

                votes_list.add(new Vote(-1, users.get(1), answers.get(3)));
                votes_list.add(new Vote(-1, users.get(6), answers.get(3)));
                votes_list.add(new Vote(-1, users.get(2), answers.get(3)));

                votes_list.add(new Vote(1, users.get(3), answers.get(4)));

                votes_list.add(new Vote(-1, users.get(0), answers.get(5)));
                votes_list.add(new Vote(1, users.get(2), answers.get(5)));

                return votes_list.stream().map(
                                vote -> vote_service.add(vote)).collect(Collectors.toList());
        }

}
