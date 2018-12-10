package hr.meteor.ru.meteorjob.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.utility.MeteorUtility;

import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.setLinearLayoutParam;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.setTextOnTextViewFromFilePath;


public class ManagerProfessionActivity extends AbstractActivity implements View.OnClickListener {
    TextView userBriefFile;
    LinearLayout invisibleLayoutWithExtraQuestion;
    View invisibleLineUnderLayout;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            if (requestCode == TAKE_USER_BRIEF_FILE_REQUEST && data.getData() != null) {
                setTextOnTextViewFromFilePath(data.getData().getPath(), userBriefFile);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profession);
        createToolbar(R.id.actionbar_profession_manager, 0, R.string.profession_manager, true);

        RadioButton contactsFormYesButton = findViewById(R.id.radiobutton_profession_manager_yes);
        contactsFormYesButton.setOnClickListener(this);

        RadioButton contactsFormNoButton = findViewById(R.id.radiobutton_profession_manager_no);
        contactsFormNoButton.setOnClickListener(this);

        invisibleLayoutWithExtraQuestion = findViewById(R.id.layout_professions_manager_contacts_invisible);
        invisibleLineUnderLayout = findViewById(R.id.line_professions_manager_contacts_invisible);

        userBriefFile = findViewById(R.id.text_profession_manager_get_brief);
        userBriefFile.setOnClickListener(this);

        ImageView clearUserBriefFile = findViewById(R.id.image_profession_manager_brief_clear);
        clearUserBriefFile.setOnClickListener(this);

        Button sendData = findViewById(R.id.button_profession_manager_send);
        sendData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int elementId = v.getId();

        if (elementId == R.id.text_profession_manager_get_brief) {
            Intent intent = new Intent();
            intent.setType("file/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.default_choose_file)), TAKE_USER_BRIEF_FILE_REQUEST);
        }

        if (elementId == R.id.radiobutton_profession_manager_yes) {
            setLinearLayoutParam(invisibleLayoutWithExtraQuestion, LinearLayout.LayoutParams.MATCH_PARENT, 0, View.INVISIBLE);
            invisibleLineUnderLayout.setVisibility(View.INVISIBLE);
        }

        if (elementId == R.id.radiobutton_profession_manager_no) {
            setLinearLayoutParam(invisibleLayoutWithExtraQuestion, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, View.VISIBLE);
            invisibleLineUnderLayout.setVisibility(View.VISIBLE);
        }

        if (elementId == R.id.image_profession_manager_brief_clear) {
            userBriefFile.setText(MeteorUtility.getUnderlineSpanned(getString(R.string.manager_sent_file)));
        }

        if (elementId == R.id.button_profession_manager_send) {
            if (validationSuccess()) {
                MeteorUtility.sendData();
            } else {
                Toast.makeText(this, R.string.error_validation, Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean validationSuccess() {
        boolean isSuccess = true;
        EditText name = findViewById(R.id.edit_profession_manager_contacts_name);
        EditText phone = findViewById(R.id.edit_profession_manager_contacts_phone);
        EditText email = findViewById(R.id.edit_profession_manager_contacts_email);

        if (name.getText() == null || name.getText().length() == 0) {
            name.setError(getString(R.string.error_validation_name));
            isSuccess = false;
        }
        if (phone.getText() == null || phone.getText().length() == 0) {
            phone.setError(getString(R.string.error_validation_phone));
            isSuccess = false;
        }

        if (email.getText() == null || email.getText().length() == 0 || !MeteorUtility.isValidEmail(String.valueOf(email.getText()))) {
            email.setError(getString(R.string.error_validation_mail));
            isSuccess = false;
        }

        if (invisibleLayoutWithExtraQuestion.getVisibility() == View.VISIBLE) {
            EditText extraQuestion = findViewById(R.id.edit_profession_manager_contacts_invisible);
            if (extraQuestion == null || extraQuestion.getText().length() == 0) {
                extraQuestion.setError(getString(R.string.error_validation_question));
                isSuccess = false;
            }
        }
        return isSuccess;
    }
}
