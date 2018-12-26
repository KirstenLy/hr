package hr.meteor.ru.meteorjob.ui.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.muddzdev.styleabletoast.StyleableToast;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.ProfessionFilesAdapter;
import hr.meteor.ru.meteorjob.ui.beans.ManagerData;
import hr.meteor.ru.meteorjob.ui.retrofit.services.ResultJson;
import hr.meteor.ru.meteorjob.ui.utility.DialogUtility;
import hr.meteor.ru.meteorjob.ui.utility.MeteorUtility;
import jp.wasabeef.recyclerview.animators.FadeInRightAnimator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.correctMultiplyTextSize;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.createMultipartBodyList;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getAgreementString;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getFilesNamesList;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getJsonFromManagerObject;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.putArrayListOnSharedPreference;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.restoreEditText;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.restoreFilesClips;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.restoreRadioButtons;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.setLinearLayoutParam;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.showDuplicatedFilesIfExist;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.updateFileListOnIntentResult;


public class ManagerProfessionActivity extends AbstractActivity implements View.OnClickListener {
    private RelativeLayout invisibleLayoutWithExtraQuestion;

    private RadioButton contactsFormYesButton;
    private RadioButton contactsFormNoButton;
    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText comment;
    private EditText question;

    private RecyclerView filesRecycler;
    private ProfessionFilesAdapter filesAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == TAKE_USER_BRIEF_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            String duplicateFiles = updateFileListOnIntentResult(data, filesAdapter);
            showDuplicatedFilesIfExist(duplicateFiles, this);
            filesRecycler.startLayoutAnimation();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profession);
        createToolbar(R.id.actionbar_profession_manager, 0, getString(R.string.profession_manager), true);

        contactsFormYesButton = findViewById(R.id.radiobutton_profession_manager_yes);
        contactsFormNoButton = findViewById(R.id.radiobutton_profession_manager_no);
        invisibleLayoutWithExtraQuestion = findViewById(R.id.layout_professions_manager_contacts_invisible);
        LinearLayout briefFilesLayout = findViewById(R.id.layout_professions_manager_files_brief);
        Button sendData = findViewById(R.id.button_profession_manager_send);
        TextInputEditText editText = findViewById(R.id.edit_profession_manager_contacts_question);

        contactsFormYesButton.setOnClickListener(this);
        contactsFormNoButton.setOnClickListener(this);
        briefFilesLayout.setOnClickListener(this);
        sendData.setOnClickListener(this);

        correctMultiplyTextSize(editText, this);

        TextView agreement = findViewById(R.id.text_profession_manager_agreement);
        agreement.setText(getAgreementString(this));
        agreement.setMovementMethod(LinkMovementMethod.getInstance());
        agreement.setHighlightColor(Color.BLUE);

        filesRecycler = findViewById(R.id.recycler_profession_manager_files);
        filesRecycler.setItemAnimator(new FadeInRightAnimator());
        filesRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        filesAdapter = new ProfessionFilesAdapter(this, new ArrayList<File>(), filesRecycler);
        filesRecycler.setAdapter(filesAdapter);
        filesRecycler.setNestedScrollingEnabled(false);

        loadPreferences();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<File> filesList = (ArrayList<File>) filesAdapter.getFileList();
        if (filesList != null && filesList.size() > 0) {
            outState.putSerializable("files", filesList);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getSerializable("files") != null) {
            ArrayList<File> filesList = (ArrayList<File>) savedInstanceState.getSerializable("files");
            filesAdapter.setFileList(filesList);
            filesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        int elementId = v.getId();

        if (elementId == R.id.radiobutton_profession_manager_yes) {
            setLinearLayoutParam(invisibleLayoutWithExtraQuestion, LinearLayout.LayoutParams.MATCH_PARENT, 0, View.INVISIBLE);
        }

        if (elementId == R.id.radiobutton_profession_manager_no) {
            setLinearLayoutParam(invisibleLayoutWithExtraQuestion, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, View.VISIBLE);
        }

        if (elementId == R.id.layout_professions_manager_files_brief) {
            Intent intent = new Intent(this, FilePickerActivity.class);
            intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
            intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
            intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
            intent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
            startActivityForResult(intent, TAKE_USER_BRIEF_FILE_REQUEST);
        }

        if (elementId == R.id.button_profession_manager_send) {
            if (validationSuccess()) {
                showLoadingDialog();

                ManagerData managerData = new ManagerData();
                managerData.setName(name.getText().toString());
                managerData.setPhone(phone.getText().toString());
                managerData.setEmail(email.getText().toString());
                managerData.setSkilled(contactsFormYesButton.isChecked() ? "y" : "n");
                managerData.setComment(comment.getText().toString());
                managerData.setAnswer(question == null ? null : question.getText().toString());

                sendData(managerData);
            } else {
                StyleableToast.makeText(this, getString(R.string.error_validation), Toast.LENGTH_LONG, R.style.ToastValidationError).show();
            }
        }
    }

    public void viewsInitialize() {

    }

    //TODO Необходим рефакторинг в соответствии с требованиями для каждого поля. Нужно будет создать функцию для каждого из них.
    public boolean validationSuccess() {
        boolean isSuccess = true;

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
            EditText extraQuestion = findViewById(R.id.edit_profession_manager_contacts_question);
            if (extraQuestion == null || extraQuestion.getText().length() == 0) {
                extraQuestion.setError(getString(R.string.error_validation_question));
                isSuccess = false;
            }
        }

        if (comment.getText() == null || comment.getText().length() == 0) {
            comment.setError(getString(R.string.error_validation_comment));
            isSuccess = false;
        }

        return isSuccess;
    }

    private void sendData(ManagerData managerData) {
        String json = getJsonFromManagerObject(managerData);
        RequestBody jsonBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), json);

        List<MultipartBody.Part> requestFileList = createMultipartBodyList((ArrayList<File>) filesAdapter.getFileList());

        Call<ResultJson> call = getMeteorService("vacancy/").postManagerData(jsonBody, requestFileList);
        call.enqueue(new Callback<ResultJson>() {
            @Override
            public void onResponse(Call<ResultJson> call,
                                   Response<ResultJson> response) {
                hideLoadingDialog();
                LinkedTreeMap<String, String> responseArray = (LinkedTreeMap<String, String>) response.body().getContent();
                if (responseArray.get("Status").equals("success")) {
                    DialogUtility.showErrorDialog(ManagerProfessionActivity.this, getString(R.string.success_data_send), false);
                } else {
//                    String responseFromServer = responseArray.get("Error");
//                    String fileFormat = responseFromServer.substring(responseFromServer.lastIndexOf('/') + 1);
//                    String errorText = String.format(getString(R.string.error_file_format), fileFormat);
                    DialogUtility.showErrorDialog(ManagerProfessionActivity.this, responseArray.get("Error"), false);
                }
            }

            @Override
            public void onFailure(Call<ResultJson> call, Throwable t) {
                hideLoadingDialog();
                DialogUtility.showErrorDialog(ManagerProfessionActivity.this, getString(R.string.error_loading_data), false);
            }
        });
    }

    @Override
    public void savePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("managerName", String.valueOf(name.getText()));
        editor.putString("managerPhone", String.valueOf(phone.getText()));
        editor.putString("managerEmail", String.valueOf(email.getText()));
        editor.putString("managerComment", String.valueOf(comment.getText()));
        editor.putString("managerQuestion", String.valueOf(question.getText()));
        editor.putBoolean("managerExperience", contactsFormYesButton.isChecked());

        ArrayList<File> filesList = (ArrayList<File>) filesAdapter.getFileList();
        if (filesList != null) {
            putArrayListOnSharedPreference(getFilesNamesList(filesList), editor, "managerFilesNames");
        }
        editor.apply();
    }

    public void loadPreferences() {
        name = findViewById(R.id.edit_profession_manager_contacts_name);
        phone = findViewById(R.id.edit_profession_manager_contacts_phone);
        email = findViewById(R.id.edit_profession_manager_contacts_email);
        comment = findViewById(R.id.edit_profession_manager_contacts_comment);
        question = findViewById(R.id.edit_profession_manager_contacts_question);
        restoreValues();
    }

    public void restoreValues() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        restoreEditText(sharedPreferences, "managerName", name);
        restoreEditText(sharedPreferences, "managerPhone", phone);
        restoreEditText(sharedPreferences, "managerEmail", email);
        restoreEditText(sharedPreferences, "managerComment", comment);
        restoreEditText(sharedPreferences, "managerQuestion", question);
        restoreEditText(sharedPreferences, "managerName", name);

        restoreRadioButtons(sharedPreferences, "managerExperience", contactsFormYesButton, contactsFormNoButton, invisibleLayoutWithExtraQuestion);

        restoreFilesClips(sharedPreferences, "managerFilesNames", filesAdapter);
    }
}
