package hr.meteor.ru.meteorjob.ui.beans;

public class TestQuestion {
    private String title;
    private TestAnswer[] answers;

    public TestQuestion(String title, TestAnswer[] answers) {
        this.title = title;
        this.answers = answers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TestAnswer[] getAnswers() {
        return answers;
    }

    public void setAnswers(TestAnswer[] answers) {
        this.answers = answers;
    }
}
