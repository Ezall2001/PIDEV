package entities;

public class Test_qs {
    private Integer id, question_number;
    private String question, correct_option, optionA, optionB, optionC, optionD;
    private Test test;

    public Test_qs() {
    }

    public Test_qs(Integer id, Integer question_number, String question, String correct_option, String optionA,
            String optionB, String optionC, String optionD) {
        this.id = id;
        this.question_number = question_number;
        this.question = question;
        this.correct_option = correct_option;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    public Test_qs(Integer question_number, String question, String correct_option, String optionA, String optionB,
            String optionC, String optionD) {
        this.question_number = question_number;
        this.question = question;
        this.correct_option = correct_option;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }

    public Test get_test() {
        return test;
    }

    public void set_test(Test test) {
        this.test = test;
    }

    public Integer get_id() {
        return id;
    }

    public void set_id(Integer id) {
        this.id = id;
    }

    public Integer get_question_number() {
        return question_number;
    }

    public void set_question_number(Integer question_number) {
        this.question_number = question_number;
    }

    public String get_question() {
        return question;
    }

    public void set_question(String question) {
        this.question = question;
    }

    public String get_correct_option() {
        return correct_option;
    }

    public void set_correct_option(Integer index) {
        if (index.equals(0))
            correct_option = optionA;

        if (index.equals(1))
            correct_option = optionB;

        if (index.equals(2))
            correct_option = optionC;

        if (index.equals(3))
            correct_option = optionD;

    }

    public Integer get_correct_option_index() {
        if (correct_option.equals(optionA))
            return 0;

        if (correct_option.equals(optionB))
            return 1;

        if (correct_option.equals(optionC))
            return 2;

        if (correct_option.equals(optionD))
            return 3;

        return -1;
    }

    public void set_correct_option(String correct_option) {
        this.correct_option = correct_option;
    }

    public String get_optionA() {
        return optionA;
    }

    public void set_optionA(String optionA) {
        this.optionA = optionA;
    }

    public String get_optionB() {
        return optionB;
    }

    public void set_optionB(String optionB) {
        this.optionB = optionB;
    }

    public String get_optionC() {
        return optionC;
    }

    public void set_optionC(String optionC) {
        this.optionC = optionC;
    }

    public String get_optionD() {
        return optionD;
    }

    public void set_optionD(String optionD) {
        this.optionD = optionD;
    }

    @Override
    public String toString() {
        return "Test_qs [id=" + id + ", question_number=" + question_number + ", question=" + question
                + ", correct_option=" + correct_option + ", optionA=" + optionA + ", optionB=" + optionB + ", optionC="
                + optionC + ", optionD=" + optionD + ", test=" + test + "]";
    }

}