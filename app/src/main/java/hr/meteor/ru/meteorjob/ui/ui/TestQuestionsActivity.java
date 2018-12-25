package hr.meteor.ru.meteorjob.ui.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.beans.TestQuestion;
import hr.meteor.ru.meteorjob.ui.utility.DialogUtility;

import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getCheckedPosition;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.initializeTestQuestionList;

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

        int testNumber = getIntent().getExtras().getInt("test_id", 0);
        int professionKey = getIntent().getExtras().getInt("profession_ID", 0);

        String profession = professionKey == 1 ? "Менеджер" : "Разработчик";
        String title = String.format(getString(R.string.actionbar_test_questions), testNumber, profession);

        Toolbar toolbar = findViewById(R.id.actionbar_test_questions);
        toolbar.setTitle(title);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        questions = initializeTestQuestionList();
        currentQuestionCounter = findViewById(R.id.text_test_current_question_number);

        if (savedInstanceState == null) {
            currentQuestionNumber = 0;
            correctAnswersCounter = 0;
            currentQuestion = questions.get(0);
            currentQuestionCounter.setText(String.format(getString(R.string.test_current_question_number), 1, questions.size()));
        } else {
            currentQuestionNumber = savedInstanceState.getInt("currentQuestionNumber");
            correctAnswersCounter = savedInstanceState.getInt("correctAnswersCounter");
            currentQuestion = questions.get(currentQuestionNumber);
            currentQuestionCounter.setText(String.format(getString(R.string.test_current_question_number), currentQuestionNumber + 1, questions.size()));
        }

        questionTitle = findViewById(R.id.text_test_question_title);
        radioButton1 = findViewById(R.id.radiobutton_test_answer_1);
        radioButton2 = findViewById(R.id.radiobutton_test_answer_2);
        radioButton3 = findViewById(R.id.radiobutton_test_answer_3);
        radioButton4 = findViewById(R.id.radiobutton_test_answer_4);
        radioButtonGroup = findViewById(R.id.radiogroup_test_answers);
        currentQuestionCounter = findViewById(R.id.text_test_current_question_number);
        nextButton = findViewById(R.id.button_test_next);

        questionTitle.setText(currentQuestion.getTitle());
        radioButton1.setText(currentQuestion.getAnswers()[0].getTitle());
        radioButton2.setText(currentQuestion.getAnswers()[1].getTitle());
        radioButton3.setText(currentQuestion.getAnswers()[2].getTitle());
        radioButton4.setText(currentQuestion.getAnswers()[3].getTitle());

        nextButton.setOnClickListener(this);
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
                questionTitle.animate().alpha(0.0f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        questionTitle.animate().alpha(1.0f).setDuration(1000);
                        questionTitle.setText(currentQuestion.getTitle());
                    }
                });
                radioButton1.animate().alpha(0.0f).setDuration(1000).setListener(new radioButtonsAnimator(radioButton1, 0));
                radioButton2.animate().alpha(0.0f).setDuration(1000).setListener(new radioButtonsAnimator(radioButton2, 1));
                radioButton3.animate().alpha(0.0f).setDuration(1000).setListener(new radioButtonsAnimator(radioButton3, 2));
                radioButton4.animate().alpha(0.0f).setDuration(1000).setListener(new radioButtonsAnimator(radioButton4, 3));

                currentQuestionCounter.setText(String.format(getString(R.string.test_current_question_number), currentQuestionNumber + 1, questions.size()));
                if (currentQuestionNumber == questions.size() - 1) {
                    nextButton.setText(R.string.test_question_finish);
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

    public class radioButtonsAnimator extends AnimatorListenerAdapter {
        private RadioButton radioButton;
        private int position;

        public radioButtonsAnimator(RadioButton radioButton, int position) {
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
