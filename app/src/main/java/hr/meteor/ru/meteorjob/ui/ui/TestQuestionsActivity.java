package hr.meteor.ru.meteorjob.ui.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.beans.TestAnswer;
import hr.meteor.ru.meteorjob.ui.beans.TestQuestion;
import hr.meteor.ru.meteorjob.ui.utility.DialogUtility;

import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getCheckedPosition;

public class TestQuestionsActivity extends AbstractActivity implements View.OnClickListener {
    private RadioGroup radioButtonGroup;
    private ArrayList<TestQuestion> questions;
    private TestQuestion currentQuestion;
    private int currentQuestionNumber;
    private int correctAnswersCounter;

    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;

    private TextView questionTitle;
    private TextView currentQuestionCounter;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_questions);

        createToolbar(R.id.actionbar_test_questions, 0, createTitleString(), false);
        questions = initializeTestQuestionList();

        if (savedInstanceState == null) {
            currentQuestionNumber = 0;
            correctAnswersCounter = 0;
            currentQuestion = questions.get(0);
            viewsInitialize();
            updateQuestionCounter();
        } else {
            currentQuestionNumber = savedInstanceState.getInt("currentQuestionNumber");
            correctAnswersCounter = savedInstanceState.getInt("correctAnswersCounter");
            currentQuestion = questions.get(currentQuestionNumber);
            viewsInitialize();
            updateQuestionCounter();
            if (isLastQuestion()) {
                updateNextButtonText();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_test_next) {
            int idx = getCheckedPosition(radioButtonGroup);

            if (currentQuestion.getAnswers()[idx].isAnswer()) {
                correctAnswersCounter++;
            }

            currentQuestionNumber++;

            if (currentQuestionNumber < questions.size()) {
                currentQuestion = questions.get(currentQuestionNumber);

                updateQuestionTitle();
                animateAndUpdateTextRadioButton(radioButton1, 0);
                animateAndUpdateTextRadioButton(radioButton2, 1);
                animateAndUpdateTextRadioButton(radioButton3, 2);
                animateAndUpdateTextRadioButton(radioButton4, 3);
                updateQuestionCounter();

                if (isLastQuestion()) {
                    updateNextButtonText();
                }
            } else {
                DialogUtility.showTestResult(this, correctAnswersCounter);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("currentQuestionNumber", currentQuestionNumber);
        outState.putInt("correctAnswersCounter", correctAnswersCounter);
        super.onSaveInstanceState(outState);
    }

    public void animateAndUpdateTextRadioButton(RadioButton radioButton, int position) {
        radioButton.animate().alpha(0.0f).setDuration(1000).setListener(new radioButtonsAnimator(radioButton, position));
    }

    public void updateQuestionCounter() {
        currentQuestionCounter.setText(String.format(getString(R.string.test_current_question_number), currentQuestionNumber + 1, questions.size()));
    }

    public void viewsInitialize() {
        currentQuestionCounter = findViewById(R.id.text_test_current_question_number);
        questionTitle = findViewById(R.id.text_test_question_title);
        radioButton1 = findViewById(R.id.radiobutton_test_answer_1);
        radioButton2 = findViewById(R.id.radiobutton_test_answer_2);
        radioButton3 = findViewById(R.id.radiobutton_test_answer_3);
        radioButton4 = findViewById(R.id.radiobutton_test_answer_4);
        radioButtonGroup = findViewById(R.id.radiogroup_test_answers);
        nextButton = findViewById(R.id.button_test_next);

        questionTitle.setText(currentQuestion.getTitle());
        radioButton1.setText(currentQuestion.getAnswers()[0].getTitle());
        radioButton2.setText(currentQuestion.getAnswers()[1].getTitle());
        radioButton3.setText(currentQuestion.getAnswers()[2].getTitle());
        radioButton4.setText(currentQuestion.getAnswers()[3].getTitle());

        nextButton.setOnClickListener(this);
    }

    public static ArrayList<TestQuestion> initializeTestQuestionList() {
        ArrayList<TestQuestion> testQuestionList = new ArrayList<>();

        testQuestionList.add(new TestQuestion(
                "Question 1 title",
                new TestAnswer[]{
                        new TestAnswer("Activity", false),
                        new TestAnswer("Fragment", false),
                        new TestAnswer("Layout", true),
                        new TestAnswer("Class", false)}
        ));


        testQuestionList.add(new TestQuestion(
                "Question 2 title",
                new TestAnswer[]{
                        new TestAnswer("ArrayList", false),
                        new TestAnswer("HashMap", false),
                        new TestAnswer("LinkedList", true),
                        new TestAnswer("LinkedHashMap", false)}
        ));

        testQuestionList.add(new TestQuestion(
                "Question 3 title",
                new TestAnswer[]{
                        new TestAnswer("Integer", false),
                        new TestAnswer("Boolean", false),
                        new TestAnswer("String", true),
                        new TestAnswer("Object", false)}));

        return testQuestionList;
    }

    public boolean isLastQuestion() {
        return currentQuestionNumber == questions.size() - 1;
    }

    public String createTitleString() {
        int testNumber = getIntent().getExtras().getInt("test_id", 0);
        int professionKey = getIntent().getExtras().getInt("profession_ID", 0);
        String profession = professionKey == 1 ? "Менеджер" : "Разработчик";
        return String.format(getString(R.string.actionbar_test_questions), testNumber, profession);
    }

    public void updateQuestionTitle() {
        questionTitle.animate().alpha(0.0f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                questionTitle.animate().alpha(1.0f).setDuration(1000);
                questionTitle.setText(currentQuestion.getTitle());
            }
        });
    }

    public void updateNextButtonText() {
        nextButton.setText(R.string.test_question_finish);
    }

    public class radioButtonsAnimator extends AnimatorListenerAdapter {
        private RadioButton radioButton;
        private int position;

        radioButtonsAnimator(RadioButton radioButton, int position) {
            this.radioButton = radioButton;
            this.position = position;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            this.radioButton.animate().alpha(1.0f).setDuration(1000);
            radioButton.setText(currentQuestion.getAnswers()[position].getTitle());
        }
    }
}
