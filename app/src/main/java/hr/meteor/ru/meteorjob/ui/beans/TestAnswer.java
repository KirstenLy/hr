package hr.meteor.ru.meteorjob.ui.beans;

public class TestAnswer {
    private String title;
    private boolean isAnswer;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public TestAnswer(String title, boolean isAnswer) {
        this.title = title;
        this.isAnswer = isAnswer;
    }
}
