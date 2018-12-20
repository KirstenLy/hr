package hr.meteor.ru.meteorjob.ui.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.LinkedTreeMap;
import com.muddzdev.styleabletoast.StyleableToast;
import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.ProfessionFilesAdapter;
import hr.meteor.ru.meteorjob.ui.beans.ManagerData;
import hr.meteor.ru.meteorjob.ui.retrofit.services.ResultJson;
import hr.meteor.ru.meteorjob.ui.utility.DialogUtility;
import hr.meteor.ru.meteorjob.ui.utility.MeteorUtility;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.createMultipartBodyList;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getAgreementString;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getFilesNamesList;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getJsonFromManagerObject;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.putArrayListOnSharedPreference;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.putArrayListOutFromSharedPreference;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.rvHeightCorrector;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.setLinearLayoutParam;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.showDuplicatedFilesIfExist;


public class ManagerProfessionActivity extends AbstractActivity implements View.OnClickListener {
    private LinearLayout invisibleLayoutWithExtraQuestion;

    private RadioButton contactsFormYesButton;
    private RadioButton contactsFormNoButton;
    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText comment;
    private EditText question;

    RecyclerView briefRecycler;
    private ProfessionFilesAdapter briefFilesAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == TAKE_USER_BRIEF_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            List<Uri> filesUtiList = Utils.getSelectedFilesFromResult(data);
            ArrayList<File> filesList = (ArrayList<File>) briefFilesAdapter.getFileList();

            StringBuilder duplicateNamesBuilder = new StringBuilder();

            for (Uri uri : filesUtiList) {
                File file = Utils.getFileForUri(uri);
                if (!filesList.contains(file)) {
                    filesList.add(file);
                } else {
                    duplicateNamesBuilder.append("\n").append(file.getName());
                }
            }

            rvHeightCorrector(briefRecycler);
            briefFilesAdapter.notifyDataSetChanged();
            showDuplicatedFilesIfExist(duplicateNamesBuilder.toString(), this);
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

        LinearLayout briefFilesLayout = findViewById(R.id.layout_professions_manager_files_brief);
        briefFilesLayout.setOnClickListener(this);

        Button sendData = findViewById(R.id.button_profession_manager_send);
        sendData.setOnClickListener(this);

//        ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(this)
//                .setChildGravity(Gravity.TOP)
//                .setScrollingEnabled(false)
//                .setGravityResolver(new IChildGravityResolver() {
//                    @Override
//                    public int getItemGravity(int position) {
//                        return Gravity.CENTER;
//                    }
//                })
//                .setOrientation(ChipsLayoutManager.HORIZONTAL)
//                .setRowStrategy(ChipsLayoutManager.STRATEGY_CENTER_DENSE)
//                .build();

        TextView agreement = findViewById(R.id.text_profession_manager_agreement);
        agreement.setText(getAgreementString(this));
        agreement.setMovementMethod(LinkMovementMethod.getInstance());
        agreement.setHighlightColor(Color.BLUE);

        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
        flowLayoutManager.setAutoMeasureEnabled(true);

        briefRecycler = findViewById(R.id.recycler_profession_manager_files);
        briefRecycler.setLayoutManager(flowLayoutManager);
        briefFilesAdapter = new ProfessionFilesAdapter(this, new ArrayList<File>(), briefRecycler);
        briefRecycler.setAdapter(briefFilesAdapter);
        briefRecycler.setNestedScrollingEnabled(false);

        loadPreferences();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        ArrayList<File> filesList = (ArrayList<File>) briefFilesAdapter.getFileList();
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
            rvHeightCorrector(briefRecycler);
            briefFilesAdapter.setFileList(filesList);
            briefFilesAdapter.notifyDataSetChanged();
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
        return isSuccess;
    }

    private void sendData(ManagerData managerData) {
        String json = getJsonFromManagerObject(managerData);
        RequestBody jsonBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), json);

        List<MultipartBody.Part> requestFileList = createMultipartBodyList((ArrayList<File>) briefFilesAdapter.getFileList());

        Call<ResultJson> call = getMeteorService().postManagerData(jsonBody, requestFileList);
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

    public void savePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("managerName", String.valueOf(name.getText()));
        editor.putString("managerPhone", String.valueOf(phone.getText()));
        editor.putString("managerEmail", String.valueOf(email.getText()));
        editor.putString("managerComment", String.valueOf(comment.getText()));
        editor.putString("managerQuestion", String.valueOf(question.getText()));
        editor.putBoolean("managerExperience", contactsFormYesButton.isChecked());

        ArrayList<File> filesList = (ArrayList<File>) briefFilesAdapter.getFileList();
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
        if (!sharedPreferences.getString("managerName", "").equals("")) {
            name.setText(sharedPreferences.getString("managerName", ""));
        }

        if (!sharedPreferences.getString("managerPhone", "").equals("")) {
            phone.setText(sharedPreferences.getString("managerPhone", ""));
        }

        if (!sharedPreferences.getString("managerEmail", "").equals("")) {
            email.setText(sharedPreferences.getString("managerEmail", ""));
        }

        if (!sharedPreferences.getString("managerComment", "").equals("")) {
            comment.setText(sharedPreferences.getString("managerComment", ""));
        }

        if (!sharedPreferences.getString("managerQuestion", "").equals("")) {
            question.setText(sharedPreferences.getString("managerQuestion", ""));
        }

        if (sharedPreferences.contains("managerFilesNames")) {
            ArrayList<String> filePaths = putArrayListOutFromSharedPreference(sharedPreferences, "managerFilesNames");
            ArrayList<File> filesList = (ArrayList<File>) briefFilesAdapter.getFileList();
            for (int i = 0; i < filePaths.size(); i++) {
                filesList.add(new File(filePaths.get(i)));
            }
            rvHeightCorrector(briefRecycler);
            briefFilesAdapter.setFileList(filesList);
            briefFilesAdapter.notifyDataSetChanged();
        }

        if (sharedPreferences.getBoolean("managerExperience", false)) {
            contactsFormYesButton.setChecked(true);
        } else {
            setLinearLayoutParam(invisibleLayoutWithExtraQuestion, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, View.VISIBLE);
            contactsFormNoButton.setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            savePreferences();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        savePreferences();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
