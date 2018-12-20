package hr.meteor.ru.meteorjob.ui.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import java.util.Arrays;
import java.util.List;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.DeveloperTechnologiesAdapter;
import hr.meteor.ru.meteorjob.ui.adapters.ProfessionFilesAdapter;
import hr.meteor.ru.meteorjob.ui.beans.DeveloperData;
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
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getArrayFromArrayList;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getJsonFromDeveloperObject;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.putArrayListOnSharedPreference;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.putArrayListOutFromSharedPreference;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.rvHeightCorrector;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.showDuplicatedFilesIfExist;

public class DeveloperProfessionActivity extends AbstractActivity implements View.OnClickListener {
    private EditText name;
    private EditText phone;
    private EditText email;
    private RadioButton contactsFormYesButton;
    private RadioButton contactsFormNoButton;
    private EditText comment;

    private DeveloperTechnologiesAdapter languagesAdapter;
    private DeveloperTechnologiesAdapter databasesAdapter;
    private DeveloperTechnologiesAdapter frameworkAdapter;
    private DeveloperTechnologiesAdapter mobilesAdapter;

    RecyclerView codeRecycler;
    RecyclerView briefRecycler;

    private ProfessionFilesAdapter codeFilesAdapter;
    private ProfessionFilesAdapter briefFilesAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((requestCode == TAKE_USER_TASK_OR_CODE_FILE_REQUEST) && resultCode == Activity.RESULT_OK) {
            List<Uri> filesUtiList = Utils.getSelectedFilesFromResult(data);
            ArrayList<File> filesList = (ArrayList<File>) codeFilesAdapter.getFileList();

            StringBuilder duplicateNamesBuilder = new StringBuilder();
            for (Uri uri : filesUtiList) {
                File file = Utils.getFileForUri(uri);
                if (!filesList.contains(file)) {
                    filesList.add(file);
                } else {
                    duplicateNamesBuilder.append("\n").append(file.getName());
                }
            }
            rvHeightCorrector(codeRecycler);
            codeFilesAdapter.notifyDataSetChanged();
            showDuplicatedFilesIfExist(duplicateNamesBuilder.toString(), this);
        }

        if ((requestCode == TAKE_USER_BRIEF_FILE_REQUEST) && resultCode == Activity.RESULT_OK) {
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
        setContentView(R.layout.activity_developer_profession);

        createToolbar(R.id.actionbar_profession_developer, 0, R.string.profession_dev, true);

        RecyclerView languagesRecycler = findViewById(R.id.recycler_profession_developer_languages);
        RecyclerView databasesRecycler = findViewById(R.id.recycler_profession_developer_database);
        RecyclerView frameworksRecycler = findViewById(R.id.recycler_profession_developer_frameworks);
        RecyclerView mobilesRecycler = findViewById(R.id.recycler_profession_developer_mobile);

        RecyclerView.LayoutManager layoutManagerForLanguages = new GridLayoutManager(this, 3);
        RecyclerView.LayoutManager layoutManagerForDatabases = new GridLayoutManager(this, 3);
        RecyclerView.LayoutManager layoutManagerForFrameworks = new GridLayoutManager(this, 3);
        RecyclerView.LayoutManager layoutManagerForMobile = new GridLayoutManager(this, 3);

        languagesRecycler.setLayoutManager(layoutManagerForLanguages);
        databasesRecycler.setLayoutManager(layoutManagerForDatabases);
        frameworksRecycler.setLayoutManager(layoutManagerForFrameworks);
        mobilesRecycler.setLayoutManager(layoutManagerForMobile);

        languagesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeLanguageList(this));
        databasesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeDatabaseLanguageList(this));
        frameworkAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeFrameworksList(this));
        mobilesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeMobileTechnologiesList(this));

        languagesRecycler.setAdapter(languagesAdapter);
        databasesRecycler.setAdapter(databasesAdapter);
        frameworksRecycler.setAdapter(frameworkAdapter);
        mobilesRecycler.setAdapter(mobilesAdapter);

        languagesRecycler.setNestedScrollingEnabled(false);
        databasesRecycler.setNestedScrollingEnabled(false);
        frameworksRecycler.setNestedScrollingEnabled(false);
        mobilesRecycler.setNestedScrollingEnabled(false);

        contactsFormYesButton = findViewById(R.id.radiobutton_profession_developer_yes);
        contactsFormNoButton = findViewById(R.id.radiobutton_profession_developer_no);

        TextView webTestTaskLink = findViewById(R.id.text_profession_developer_web_task_link);
        webTestTaskLink.setOnClickListener(this);

        TextView androidTestTaskLink = findViewById(R.id.text_profession_developer_android_task_link);
        androidTestTaskLink.setOnClickListener(this);

        Button sendData = findViewById(R.id.button_profession_developer_send);
        sendData.setOnClickListener(this);

        LinearLayout codeFilesLayout = findViewById(R.id.layout_professions_developer_files_code);
        codeFilesLayout.setOnClickListener(this);

        LinearLayout briefFilesLayout = findViewById(R.id.layout_professions_developer_files_brief);
        briefFilesLayout.setOnClickListener(this);

        FlowLayoutManager flowLayoutManagerForCodeFiles = new FlowLayoutManager();
        FlowLayoutManager flowLayoutManagerForBriefFiles = new FlowLayoutManager();

        flowLayoutManagerForCodeFiles.setAutoMeasureEnabled(true);
        flowLayoutManagerForBriefFiles.setAutoMeasureEnabled(true);

        codeRecycler = findViewById(R.id.recycler_profession_developer_code_files);
        briefRecycler = findViewById(R.id.recycler_profession_developer_brief_files);

        codeRecycler.setLayoutManager(flowLayoutManagerForCodeFiles);
        briefRecycler.setLayoutManager(flowLayoutManagerForBriefFiles);

        codeFilesAdapter = new ProfessionFilesAdapter(this, new ArrayList<File>(), codeRecycler);
        briefFilesAdapter = new ProfessionFilesAdapter(this, new ArrayList<File>(), briefRecycler);

        codeRecycler.setAdapter(codeFilesAdapter);
        briefRecycler.setAdapter(briefFilesAdapter);

        codeRecycler.setNestedScrollingEnabled(false);
        briefRecycler.setNestedScrollingEnabled(false);

        Log.d("OkHttpTAG1", "ONCReATE " + Arrays.toString(languagesAdapter.getSelectedCheckboxArray()));

        loadPreferences();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        boolean[] languagesSelectedCheckboxData = languagesAdapter.getSelectedCheckboxArray();
        boolean[] databasesSelectedCheckboxData = databasesAdapter.getSelectedCheckboxArray();
        boolean[] frameworksSelectedCheckboxData = frameworkAdapter.getSelectedCheckboxArray();
        boolean[] mobilesSelectedCheckboxData = mobilesAdapter.getSelectedCheckboxArray();

        outState.putBooleanArray("languagesCheckboxes", languagesSelectedCheckboxData);
        outState.putBooleanArray("databasesCheckboxes", databasesSelectedCheckboxData);
        outState.putBooleanArray("frameworksCheckboxes", frameworksSelectedCheckboxData);
        outState.putBooleanArray("mobilesCheckboxes", mobilesSelectedCheckboxData);

        ArrayList<File> filesList = (ArrayList<File>) briefFilesAdapter.getFileList();
        if (filesList != null && filesList.size() > 0) {
            outState.putSerializable("briefFiles", filesList);
        }

        filesList = (ArrayList<File>) codeFilesAdapter.getFileList();
        if (filesList != null && filesList.size() > 0) {
            outState.putSerializable("codeFiles", filesList);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        languagesAdapter.setSelectedCheckboxArray(savedInstanceState.getBooleanArray("languagesCheckboxes"));
        databasesAdapter.setSelectedCheckboxArray(savedInstanceState.getBooleanArray("databasesCheckboxes"));
        frameworkAdapter.setSelectedCheckboxArray(savedInstanceState.getBooleanArray("frameworksCheckboxes"));
        mobilesAdapter.setSelectedCheckboxArray(savedInstanceState.getBooleanArray("mobilesCheckboxes"));

        ArrayList<File> filesList;

        if (savedInstanceState.getSerializable("briefFiles") != null) {
            filesList = (ArrayList<File>) savedInstanceState.getSerializable("briefFiles");
            briefFilesAdapter.setFileList(filesList);
            briefFilesAdapter.notifyDataSetChanged();
            rvHeightCorrector(briefRecycler);
        }

        if (savedInstanceState.getSerializable("codeFiles") != null) {
            filesList = (ArrayList<File>) savedInstanceState.getSerializable("codeFiles");
            codeFilesAdapter.setFileList(filesList);
            codeFilesAdapter.notifyDataSetChanged();
            rvHeightCorrector(codeRecycler);
        }
    }

    @Override
    public void onClick(View v) {
        int elementId = v.getId();

        if (elementId == R.id.layout_professions_developer_files_code
                || elementId == R.id.layout_professions_developer_files_brief) {
            Intent intent = new Intent(this, FilePickerActivity.class);
            intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
            intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
            intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
            intent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

            if (elementId == R.id.layout_professions_developer_files_code) {
                startActivityForResult(intent, TAKE_USER_TASK_OR_CODE_FILE_REQUEST);
            } else {
                startActivityForResult(intent, TAKE_USER_BRIEF_FILE_REQUEST);
            }
        }

        if (elementId == R.id.text_profession_developer_web_task_link || elementId == R.id.text_profession_developer_android_task_link) {
            String url = "http://eralash.ru";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }

        if (elementId == R.id.button_profession_developer_send) {
            if (validationSuccess()) {
                showLoadingDialog();

                DeveloperData developerData = new DeveloperData();
                developerData.setSkilled(contactsFormYesButton.isChecked() ? "y" : "n");
                developerData.setName(name.getText().toString());
                developerData.setPhone(phone.getText().toString());
                developerData.setEmail(email.getText().toString());
                developerData.setComment(comment.getText().toString());
                developerData.setLanguages(languagesAdapter.getSelectedTechnologiesArray());
                developerData.setDatabases(databasesAdapter.getSelectedTechnologiesArray());
                developerData.setFrameworks(frameworkAdapter.getSelectedTechnologiesArray());
                developerData.setMobiles(mobilesAdapter.getSelectedTechnologiesArray());

                sendData(developerData);

            } else {
                StyleableToast.makeText(this, getString(R.string.error_validation), Toast.LENGTH_LONG, R.style.ToastValidationError).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        savePreferences();
        if (item.getItemId() == android.R.id.home) {
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

    public boolean validationSuccess() {
        boolean isSuccess = true;
        name = findViewById(R.id.edit_profession_developer_contacts_name);
        phone = findViewById(R.id.edit_profession_developer_contacts_phone);
        email = findViewById(R.id.edit_profession_developer_contacts_email);

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
        return isSuccess;
    }

    public void sendData(DeveloperData developerData) {
        String json = getJsonFromDeveloperObject(developerData);
        RequestBody jsonBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), json);

        List<MultipartBody.Part> codeFilesList = createMultipartBodyList((ArrayList<File>) codeFilesAdapter.getFileList());
        List<MultipartBody.Part> briefFilesList = createMultipartBodyList((ArrayList<File>) briefFilesAdapter.getFileList());

        ArrayList<MultipartBody.Part> resultList = new ArrayList<>();
        resultList.addAll(codeFilesList);
        resultList.addAll(briefFilesList);

        Call<ResultJson> call = getMeteorService().postDeveloperData(jsonBody, resultList);

        call.enqueue(new Callback<ResultJson>() {
            @Override
            public void onResponse(Call<ResultJson> call,
                                   Response<ResultJson> response) {
                hideLoadingDialog();
                LinkedTreeMap<String, String> responseArray = (LinkedTreeMap<String, String>) response.body().getContent();
                if (responseArray.get("Status").equals("success")) {
                    DialogUtility.showErrorDialog(DeveloperProfessionActivity.this, getString(R.string.success_data_send), false);
                } else {
//                    String responseFromServer = responseArray.get("Error");
//                    String fileFormat = responseFromServer.substring(responseFromServer.lastIndexOf('/') + 1);
//                    String errorText = String.format(getString(R.string.error_file_format), fileFormat);
                    DialogUtility.showErrorDialog(DeveloperProfessionActivity.this, responseArray.get("Error"), false);
                }
            }

            @Override
            public void onFailure(Call<ResultJson> call, Throwable t) {
                hideLoadingDialog();
                DialogUtility.showErrorDialog(DeveloperProfessionActivity.this, getString(R.string.error_loading_data), false);
            }
        });
    }

    public void savePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("developerName", String.valueOf(name.getText()));
        editor.putString("developerPhone", String.valueOf(phone.getText()));
        editor.putString("developerEmail", String.valueOf(email.getText()));
        editor.putString("developerComment", String.valueOf(comment.getText()));
        editor.putBoolean("developerExperience", contactsFormYesButton.isChecked());

        boolean[] checkedLanguages = languagesAdapter.getSelectedCheckboxArray();
        boolean[] checkedDatabases = databasesAdapter.getSelectedCheckboxArray();
        boolean[] checkedFrameworks = frameworkAdapter.getSelectedCheckboxArray();
        boolean[] checkedMobiles = mobilesAdapter.getSelectedCheckboxArray();

        ArrayList<String> checkedLanguagesArrayList = new ArrayList<>();
        ArrayList<String> checkedDatabasesArrayList = new ArrayList<>();
        ArrayList<String> checkedFrameworksArrayList = new ArrayList<>();
        ArrayList<String> checkedMobilesArrayList = new ArrayList<>();

        for (boolean checkedLanguage : checkedLanguages) {
            checkedLanguagesArrayList.add(String.valueOf(checkedLanguage));
        }

        for (boolean checkedDatabase : checkedDatabases) {
            checkedDatabasesArrayList.add(String.valueOf(checkedDatabase));
        }

        for (boolean checkedFramework : checkedFrameworks) {
            checkedFrameworksArrayList.add(String.valueOf(checkedFramework));
        }

        for (boolean checkedMobile : checkedMobiles) {
            checkedMobilesArrayList.add(String.valueOf(checkedMobile));
        }

        putArrayListOnSharedPreference(checkedLanguagesArrayList, editor, "developerLanguages");
        putArrayListOnSharedPreference(checkedDatabasesArrayList, editor, "developerDatabases");
        putArrayListOnSharedPreference(checkedFrameworksArrayList, editor, "developerFrameworks");
        putArrayListOnSharedPreference(checkedMobilesArrayList, editor, "developerMobiles");
        editor.apply();
    }

    public void loadPreferences() {
        name = findViewById(R.id.edit_profession_developer_contacts_name);
        phone = findViewById(R.id.edit_profession_developer_contacts_phone);
        email = findViewById(R.id.edit_profession_developer_contacts_email);
        comment = findViewById(R.id.edit_profession_developer_contacts_comment);
        restoreValues();
    }

    public void restoreValues() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        if (!sharedPreferences.getString("developerName", "").equals("")) {
            name.setText(sharedPreferences.getString("developerName", ""));
        }

        if (!sharedPreferences.getString("developerPhone", "").equals("")) {
            phone.setText(sharedPreferences.getString("developerPhone", ""));
        }

        if (!sharedPreferences.getString("developerEmail", "").equals("")) {
            email.setText(sharedPreferences.getString("developerEmail", ""));
        }

        if (!sharedPreferences.getString("developerComment", "").equals("")) {
            comment.setText(sharedPreferences.getString("developerComment", ""));
        }

        if (!sharedPreferences.getString("developerLanguages", "").equals("")) {
            ArrayList<String> checkedLanguages = putArrayListOutFromSharedPreference(sharedPreferences, "developerLanguages");

            boolean[] restoredCheckers = getArrayFromArrayList(checkedLanguages);

            languagesAdapter.setSelectedCheckboxArray(restoredCheckers);
            languagesAdapter.notifyDataSetChanged();
        }

        if (!sharedPreferences.getString("developerDatabases", "").equals("")) {
            ArrayList<String> checkedDarabases = putArrayListOutFromSharedPreference(sharedPreferences, "developerDatabases");
            boolean[] restoredCheckers = getArrayFromArrayList(checkedDarabases);
            databasesAdapter.setSelectedCheckboxArray(restoredCheckers);
            languagesAdapter.notifyDataSetChanged();
        }

        if (!sharedPreferences.getString("developerFrameworks", "").equals("")) {
            ArrayList<String> checkedFrameworks = putArrayListOutFromSharedPreference(sharedPreferences, "developerFrameworks");
            boolean[] restoredCheckers = getArrayFromArrayList(checkedFrameworks);
            frameworkAdapter.setSelectedCheckboxArray(restoredCheckers);
            languagesAdapter.notifyDataSetChanged();
        }

        if (!sharedPreferences.getString("developerMobiles", "").equals("")) {
            ArrayList<String> checkedMobiles = putArrayListOutFromSharedPreference(sharedPreferences, "developerMobiles");
            boolean[] restoredCheckers = getArrayFromArrayList(checkedMobiles);
            mobilesAdapter.setSelectedCheckboxArray(restoredCheckers);
            languagesAdapter.notifyDataSetChanged();
        }

        if (!sharedPreferences.getBoolean("developerExperience", false)) {
            contactsFormYesButton.setChecked(true);
        } else {
            contactsFormNoButton.setChecked(true);
        }
    }
}

