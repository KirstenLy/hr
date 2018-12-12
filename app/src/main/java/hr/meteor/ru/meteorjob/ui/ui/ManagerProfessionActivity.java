package hr.meteor.ru.meteorjob.ui.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;

import java.io.File;
import java.util.LinkedHashMap;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.beans.ManagerData;
import hr.meteor.ru.meteorjob.ui.retrofit.services.MeteorService;
import hr.meteor.ru.meteorjob.ui.utility.MeteorUtility;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.setLinearLayoutParam;


public class ManagerProfessionActivity extends AbstractActivity implements View.OnClickListener {
    TextView userBriefFile;
    LinearLayout invisibleLayoutWithExtraQuestion;

    RadioButton contactsFormYesButton;
    RadioButton contactsFormNoButton;
    EditText name;
    EditText phone;
    EditText email;

    Uri fileUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == TAKE_USER_BRIEF_FILE_REQUEST && resultCode == Activity.RESULT_OK) {
            fileUri = data.getData();
            File file = Utils.getFileForUri(data.getData());
            userBriefFile.setText(file.getName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profession);
        createToolbar(R.id.actionbar_profession_manager, 0, R.string.profession_manager, true);

        contactsFormYesButton = findViewById(R.id.radiobutton_profession_manager_yes);
        contactsFormYesButton.setOnClickListener(this);

        contactsFormNoButton = findViewById(R.id.radiobutton_profession_manager_no);
        contactsFormNoButton.setOnClickListener(this);

        invisibleLayoutWithExtraQuestion = findViewById(R.id.layout_professions_manager_contacts_invisible);

        userBriefFile = findViewById(R.id.text_profession_manager_get_brief);
        userBriefFile.setOnClickListener(this);

        ImageView clearUserBriefFile = findViewById(R.id.image_profession_manager_brief_clear);
        clearUserBriefFile.setOnClickListener(this);

        Button sendData = findViewById(R.id.button_profession_manager_send);
        sendData.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        boolean isDefaultStringText = userBriefFile.getText().toString().equals(getString(R.string.manager_sent_file));
        if (userBriefFile != null && !isDefaultStringText) {
            outState.putString("userBriefFileKey", String.valueOf(userBriefFile.getText()));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (userBriefFile != null && savedInstanceState.getString("userBriefFileKey") != null) {
            userBriefFile.setText(savedInstanceState.getString("userBriefFileKey"));
        }
    }

    @Override
    public void onClick(View v) {
        int elementId = v.getId();

        if (elementId == R.id.text_profession_manager_get_brief) {
            Intent intent = new Intent(this, FilePickerActivity.class);
            intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
            intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
            intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
            intent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
            startActivityForResult(intent, TAKE_USER_BRIEF_FILE_REQUEST);
        }

        if (elementId == R.id.radiobutton_profession_manager_yes) {
            setLinearLayoutParam(invisibleLayoutWithExtraQuestion, LinearLayout.LayoutParams.MATCH_PARENT, 0, View.INVISIBLE);
        }

        if (elementId == R.id.radiobutton_profession_manager_no) {
            setLinearLayoutParam(invisibleLayoutWithExtraQuestion, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, View.VISIBLE);
        }

        if (elementId == R.id.image_profession_manager_brief_clear) {
            userBriefFile.setText(MeteorUtility.getUnderlineSpanned(getString(R.string.manager_sent_file)));
        }

        if (elementId == R.id.button_profession_manager_send) {
            if (validationSuccess()) {
                EditText comment = findViewById(R.id.edit_profession_manager_contacts_comment);
                EditText question = null;

                if (contactsFormYesButton.isChecked()) {
                    question = findViewById(R.id.edit_profession_manager_contacts_invisible);
                }

                ManagerData managerData = new ManagerData();
                managerData.setSkilled(contactsFormYesButton.isChecked());
                managerData.setName(name.getText().toString());
                managerData.setPhone(phone.getText().toString());
                managerData.setEmail(email.getText().toString());
                managerData.setAnswer(question == null ? null : question.getText().toString());
                managerData.setComment(comment.getText().toString());

                sendData(managerData, fileUri);

            } else {
                StyleableToast.makeText(this, getString(R.string.error_validation), Toast.LENGTH_LONG, R.style.ToastValidationError).show();
            }
        }
    }

    public boolean validationSuccess() {
        boolean isSuccess = true;
        name = findViewById(R.id.edit_profession_manager_contacts_name);
        phone = findViewById(R.id.edit_profession_manager_contacts_phone);
        email = findViewById(R.id.edit_profession_manager_contacts_email);

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

    private void sendData(ManagerData managerData, Uri fileUri) {
        MeteorService service = getMeteorService();
        File file = new File(fileUri.getPath());

        RequestBody requestFileBody = RequestBody.create(
                MediaType.parse(getContentResolver().getType(fileUri)),
                file
        );

        MultipartBody.Part requestFile = MultipartBody.Part.createFormData("brief_file", "ds", requestFileBody);

        LinkedHashMap<String, String> requestHashMap = new LinkedHashMap<>();
        requestHashMap.put("TestKey", "TestKeyResult");

        Call<ManagerData> call = service.postManagerData(new LinkedHashMap<String, String>(), requestFile);
        call.enqueue(new Callback<ManagerData>() {
            @Override
            public void onResponse(Call<ManagerData> call,
                                   Response<ManagerData> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ManagerData> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }
}
