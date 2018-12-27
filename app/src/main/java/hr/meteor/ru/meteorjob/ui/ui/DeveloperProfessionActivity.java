package hr.meteor.ru.meteorjob.ui.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.chuross.library.ExpandableLayout;
import com.google.gson.internal.LinkedTreeMap;
import com.muddzdev.styleabletoast.StyleableToast;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.DeveloperTechnologiesAdapter;
import hr.meteor.ru.meteorjob.ui.adapters.ProfessionFilesAdapter;
import hr.meteor.ru.meteorjob.ui.beans.DeveloperData;
import hr.meteor.ru.meteorjob.ui.beans.TestTasksRetrofit;
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

import static hr.meteor.ru.meteorjob.ui.utility.DialogUtility.showTestTaskDialog;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.createMultipartBodyList;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getAgreementString;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getFilesNamesList;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getJsonFromDeveloperObject;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.putArrayListOnSharedPreference;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.restoreDeveloperFlags;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.restoreEditText;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.restoreFilesClips;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.restoreRadioButtons;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.saveDeveloperFlags;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.showDuplicatedFilesIfExist;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.updateFileListOnIntentResult;

public class DeveloperProfessionActivity extends AbstractActivity implements View.OnClickListener {
    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText gitHub;
    private RadioButton contactsFormYesButton;
    private RadioButton contactsFormNoButton;
    private EditText comment;

    private DeveloperTechnologiesAdapter languagesAdapter;
    private DeveloperTechnologiesAdapter databasesAdapter;
    private DeveloperTechnologiesAdapter frameworkAdapter;
    private DeveloperTechnologiesAdapter mobilesAdapter;
    private DeveloperTechnologiesAdapter expectedLanguagesAdapter;
    private DeveloperTechnologiesAdapter expectedDatabasesAdapter;
    private DeveloperTechnologiesAdapter expectedFrameworksAdapter;
    private DeveloperTechnologiesAdapter expectedMobilesAdapter;

    RecyclerView languagesRecycler;
    RecyclerView databasesRecycler;
    RecyclerView frameworksRecycler;
    RecyclerView mobilesRecycler;
    RecyclerView userExpectedLanguagesRecycler;
    RecyclerView userExpectedDatabasesRecycler;
    RecyclerView userExpectedFrameworksRecycler;
    RecyclerView userExpectedPreferenceRecycler;

    private RecyclerView filesRecycler;
    private ProfessionFilesAdapter filesAdapter;

    private String testTaskUrl;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((requestCode == TAKE_USER_BRIEF_FILE_REQUEST) && resultCode == Activity.RESULT_OK) {
            String duplicateFiles = updateFileListOnIntentResult(data, filesAdapter);
            showDuplicatedFilesIfExist(duplicateFiles, this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_profession);

        createToolbar(R.id.actionbar_profession_developer, 0, getString(R.string.profession_dev), true);

        showLoadingDialog();

        new Initializer(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

//        languagesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeLanguageList(this));
//        databasesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeDatabaseLanguageList(this));
//        frameworkAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeFrameworksList(this));
//        mobilesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeMobileTechnologiesList(this));
//
//        expectedLanguagesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeLanguageList(this));
//        expectedDatabasesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeDatabaseLanguageList(this));
//        expectedFrameworksAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeFrameworksList(this));
//        expectedMobilesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeMobileTechnologiesList(this));


        contactsFormYesButton = findViewById(R.id.radiobutton_profession_developer_yes);
        contactsFormNoButton = findViewById(R.id.radiobutton_profession_developer_no);

        TextView webTestTaskLink = findViewById(R.id.button_profession_developer_task_download_web);
        TextView sendTaskOnEmailWeb = findViewById(R.id.button_profession_developer_task_send_web);

        TextView androidTestTaskLink = findViewById(R.id.button_profession_developer_task_download_android);
        TextView sendTaskOnEmailAndroid = findViewById(R.id.button_profession_developer_task_send_android);

        TextView agreement = findViewById(R.id.text_profession_developer_agreement);
        Button sendData = findViewById(R.id.button_profession_developer_send);
        LinearLayout codeFilesLayout = findViewById(R.id.layout_professions_developer_files);

        webTestTaskLink.setOnClickListener(this);
        sendTaskOnEmailWeb.setOnClickListener(this);

        androidTestTaskLink.setOnClickListener(this);
        sendTaskOnEmailAndroid.setOnClickListener(this);

        sendData.setOnClickListener(this);
        codeFilesLayout.setOnClickListener(this);

        agreement.setText(getAgreementString(this));
        agreement.setMovementMethod(LinkMovementMethod.getInstance());
        agreement.setHighlightColor(Color.BLUE);


        comment = findViewById(R.id.edit_profession_developer_contacts_comment);
        comment.setOnTouchListener(new scrollEditTextHelper(comment));

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadPreferences();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 8000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        boolean[] languagesSelectedCheckboxData = languagesAdapter.getSelectedCheckboxArray();
        boolean[] databasesSelectedCheckboxData = databasesAdapter.getSelectedCheckboxArray();
        boolean[] frameworksSelectedCheckboxData = frameworkAdapter.getSelectedCheckboxArray();
        boolean[] mobilesSelectedCheckboxData = mobilesAdapter.getSelectedCheckboxArray();

        boolean[] userExpectedLanguagesSelectedCheckboxData = expectedLanguagesAdapter.getSelectedCheckboxArray();
        boolean[] userExpectedDatabasesSelectedCheckboxData = expectedDatabasesAdapter.getSelectedCheckboxArray();
        boolean[] userExpectedFrameworksSelectedCheckboxData = expectedFrameworksAdapter.getSelectedCheckboxArray();
        boolean[] userExpectedMobilesSelectedCheckboxData = expectedMobilesAdapter.getSelectedCheckboxArray();

        outState.putBooleanArray("languagesCheckboxes", languagesSelectedCheckboxData);
        outState.putBooleanArray("databasesCheckboxes", databasesSelectedCheckboxData);
        outState.putBooleanArray("frameworksCheckboxes", frameworksSelectedCheckboxData);
        outState.putBooleanArray("mobilesCheckboxes", mobilesSelectedCheckboxData);

        outState.putBooleanArray("expectedLanguagesCheckboxes", userExpectedLanguagesSelectedCheckboxData);
        outState.putBooleanArray("expectedDatabasesCheckboxes", userExpectedDatabasesSelectedCheckboxData);
        outState.putBooleanArray("expectedFrameworksCheckboxes", userExpectedFrameworksSelectedCheckboxData);
        outState.putBooleanArray("expectedMobilesCheckboxes", userExpectedMobilesSelectedCheckboxData);

        ArrayList<File> filesList = (ArrayList<File>) filesAdapter.getFileList();
        if (filesList != null && filesList.size() > 0) {
            outState.putSerializable("files", filesList);
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

        expectedLanguagesAdapter.setSelectedCheckboxArray(savedInstanceState.getBooleanArray("expectedLanguagesCheckboxes"));
        expectedDatabasesAdapter.setSelectedCheckboxArray(savedInstanceState.getBooleanArray("expectedDatabasesCheckboxes"));
        expectedFrameworksAdapter.setSelectedCheckboxArray(savedInstanceState.getBooleanArray("expectedFrameworksCheckboxes"));
        expectedMobilesAdapter.setSelectedCheckboxArray(savedInstanceState.getBooleanArray("expectedMobilesCheckboxes"));

        ArrayList<File> filesList;

        if (savedInstanceState.getSerializable("files") != null) {
            filesList = (ArrayList<File>) savedInstanceState.getSerializable("files");
            filesAdapter.setFileList(filesList);
            filesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        int elementId = v.getId();

        if (elementId == R.id.layout_professions_developer_files) {
            Intent intent = new Intent(this, FilePickerActivity.class);
            intent.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
            intent.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
            intent.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
            intent.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

            startActivityForResult(intent, TAKE_USER_BRIEF_FILE_REQUEST);
        }

        if (elementId == R.id.button_profession_developer_task_download_web || elementId == R.id.button_profession_developer_task_download_android) {
            if (elementId == R.id.button_profession_developer_task_download_web) {
                getTestTaskUrl(1);
            } else {
                getTestTaskUrl(2);
            }
        }

        if (elementId == R.id.button_profession_developer_task_send_web || elementId == R.id.button_profession_developer_task_send_android) {
            if (elementId == R.id.button_profession_developer_task_send_web) {
                showTestTaskDialog(this, getMeteorService("send-test-task/"), 1);
            } else {
                showTestTaskDialog(this, getMeteorService("send-test-task/"), 2);
            }
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
                developerData.setGitHub(gitHub.getText().toString());
                developerData.setLanguages(languagesAdapter.getSelectedTechnologiesArray());
                developerData.setDatabases(databasesAdapter.getSelectedTechnologiesArray());
                developerData.setFrameworks(frameworkAdapter.getSelectedTechnologiesArray());
                developerData.setMobiles(mobilesAdapter.getSelectedTechnologiesArray());
                developerData.setExpectedLanguages(expectedLanguagesAdapter.getSelectedTechnologiesArray());
                developerData.setExpectedDatabases(expectedDatabasesAdapter.getSelectedTechnologiesArray());
                developerData.setExpectedFrameworks(expectedFrameworksAdapter.getSelectedTechnologiesArray());
                developerData.setExpectedMobiles(expectedMobilesAdapter.getSelectedTechnologiesArray());

                sendData(developerData);

            } else {
                StyleableToast.makeText(this, getString(R.string.error_validation), Toast.LENGTH_LONG, R.style.ToastValidationError).show();
            }
        }
    }

    public boolean validationSuccess() {
        boolean isSuccess = true;
        name = findViewById(R.id.edit_profession_developer_contacts_name);
        phone = findViewById(R.id.edit_profession_developer_contacts_phone);
        email = findViewById(R.id.edit_profession_developer_contacts_email);
        gitHub = findViewById(R.id.edit_profession_developer_github);

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

        if (comment.getText() == null || comment.getText().length() == 0) {
            comment.setError(getString(R.string.error_validation_comment));
            isSuccess = false;
        }
        return isSuccess;
    }

    public void sendData(DeveloperData developerData) {
        String json = getJsonFromDeveloperObject(developerData);
        RequestBody jsonBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), json);

        List<MultipartBody.Part> filesList = createMultipartBodyList((ArrayList<File>) filesAdapter.getFileList());

        Call<ResultJson> call = getMeteorService("vacancy/").postDeveloperData(jsonBody, filesList);

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

    @Override
    public void savePreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("developerName", String.valueOf(name.getText()));
        editor.putString("developerPhone", String.valueOf(phone.getText()));
        editor.putString("developerEmail", String.valueOf(email.getText()));
        editor.putString("developerComment", String.valueOf(comment.getText()));
        editor.putBoolean("developerExperience", contactsFormYesButton.isChecked());

        saveDeveloperFlags(editor, "developerLanguages", languagesAdapter);
        saveDeveloperFlags(editor, "developerDatabases", databasesAdapter);
        saveDeveloperFlags(editor, "developerFrameworks", frameworkAdapter);
        saveDeveloperFlags(editor, "developerMobiles", mobilesAdapter);

        saveDeveloperFlags(editor, "expectedDeveloperLanguages", expectedLanguagesAdapter);
        saveDeveloperFlags(editor, "expectedDeveloperDatabases", expectedDatabasesAdapter);
        saveDeveloperFlags(editor, "expectedDeveloperFrameworks", expectedFrameworksAdapter);
        saveDeveloperFlags(editor, "expectedDeveloperMobiles", expectedMobilesAdapter);

        ArrayList<File> filesList = (ArrayList<File>) filesAdapter.getFileList();

        if (filesList != null) {
            putArrayListOnSharedPreference(getFilesNamesList(filesList), editor, "filesNames");
        }
        editor.apply();
    }

    public void loadPreferences() {
        name = findViewById(R.id.edit_profession_developer_contacts_name);
        phone = findViewById(R.id.edit_profession_developer_contacts_phone);
        email = findViewById(R.id.edit_profession_developer_contacts_email);
        restoreValues();
    }

    public void restoreValues() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        restoreEditText(sharedPreferences, "developerName", name);
        restoreEditText(sharedPreferences, "developerPhone", phone);
        restoreEditText(sharedPreferences, "developerEmail", email);
        restoreEditText(sharedPreferences, "developerComment", comment);

        restoreDeveloperFlags(sharedPreferences, "developerLanguages", languagesAdapter);
        restoreDeveloperFlags(sharedPreferences, "developerDatabases", databasesAdapter);
        restoreDeveloperFlags(sharedPreferences, "developerFrameworks", frameworkAdapter);
        restoreDeveloperFlags(sharedPreferences, "developerMobiles", mobilesAdapter);

        restoreDeveloperFlags(sharedPreferences, "expectedDeveloperLanguages", expectedLanguagesAdapter);
        restoreDeveloperFlags(sharedPreferences, "expectedDeveloperDatabases", expectedDatabasesAdapter);
        restoreDeveloperFlags(sharedPreferences, "expectedDeveloperFrameworks", expectedFrameworksAdapter);
        restoreDeveloperFlags(sharedPreferences, "expectedDeveloperMobiles", expectedMobilesAdapter);

        restoreFilesClips(sharedPreferences, "developerCodeFilesNames", filesAdapter);

        restoreRadioButtons(sharedPreferences, "developerExperience", contactsFormYesButton, contactsFormNoButton, null);
    }


    private class Initializer extends AsyncTask<Void, Void, Void> {

        public Initializer(Context context) {
            this.context = context;
        }

        Context context;

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("OkHttpTAG", "INSIDE");
            languagesRecycler = findViewById(R.id.recycler_profession_developer_languages);
            databasesRecycler = findViewById(R.id.recycler_profession_developer_databases);
            frameworksRecycler = findViewById(R.id.recycler_profession_developer_frameworks);
            mobilesRecycler = findViewById(R.id.recycler_profession_developer_mobile);
            userExpectedLanguagesRecycler = findViewById(R.id.recycler_profession_developer_expected_languages);
            userExpectedDatabasesRecycler = findViewById(R.id.recycler_profession_developer_expected_databases);
            userExpectedFrameworksRecycler = findViewById(R.id.recycler_profession_developer_expected_frameworks);
            userExpectedPreferenceRecycler = findViewById(R.id.recycler_profession_developer_expected_mobile);

            languagesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeLanguageList(context));
            databasesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeDatabaseLanguageList(context));
            frameworkAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeFrameworksList(context));
            mobilesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeMobileTechnologiesList(context));
            expectedLanguagesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeLanguageList(context));
            expectedDatabasesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeDatabaseLanguageList(context));
            expectedFrameworksAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeFrameworksList(context));
            expectedMobilesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeMobileTechnologiesList(context));

            RecyclerView.LayoutManager layoutManagerForLanguages = new GridLayoutManager(context, 3);
            RecyclerView.LayoutManager layoutManagerForDatabases = new GridLayoutManager(context, 3);
            RecyclerView.LayoutManager layoutManagerForFrameworks = new GridLayoutManager(context, 3);
            RecyclerView.LayoutManager layoutManagerForMobile = new GridLayoutManager(context, 3);

            RecyclerView.LayoutManager layoutManagerForUserExpectedLanguages = new GridLayoutManager(context, 3);
            RecyclerView.LayoutManager layoutManagerForUserExpectedDatabases = new GridLayoutManager(context, 3);
            RecyclerView.LayoutManager layoutManagerForUserExpectedFrameworks = new GridLayoutManager(context, 3);
            RecyclerView.LayoutManager layoutManagerForUserExpectedMobile = new GridLayoutManager(context, 3);

            languagesRecycler.setLayoutManager(layoutManagerForLanguages);
            databasesRecycler.setLayoutManager(layoutManagerForDatabases);
            frameworksRecycler.setLayoutManager(layoutManagerForFrameworks);
            mobilesRecycler.setLayoutManager(layoutManagerForMobile);

            userExpectedLanguagesRecycler.setLayoutManager(layoutManagerForUserExpectedLanguages);
            userExpectedDatabasesRecycler.setLayoutManager(layoutManagerForUserExpectedDatabases);
            userExpectedFrameworksRecycler.setLayoutManager(layoutManagerForUserExpectedFrameworks);
            userExpectedPreferenceRecycler.setLayoutManager(layoutManagerForUserExpectedMobile);

            languagesRecycler.setNestedScrollingEnabled(false);
            databasesRecycler.setNestedScrollingEnabled(false);
            frameworksRecycler.setNestedScrollingEnabled(false);
            mobilesRecycler.setNestedScrollingEnabled(false);

            userExpectedLanguagesRecycler.setNestedScrollingEnabled(false);
            userExpectedDatabasesRecycler.setNestedScrollingEnabled(false);
            userExpectedFrameworksRecycler.setNestedScrollingEnabled(false);
            userExpectedPreferenceRecycler.setNestedScrollingEnabled(false);

            RelativeLayout expand1 = findViewById(R.id.layout_profession_developer_language_expander_wrapper);
            RelativeLayout expand2 = findViewById(R.id.layout_profession_developer_databases_expander_wrapper);
            RelativeLayout expand3 = findViewById(R.id.layout_profession_developer_framework_expander_wrapper);
            RelativeLayout expand4 = findViewById(R.id.layout_profession_developer_mobile_expander_wrapper);

            RelativeLayout expand5 = findViewById(R.id.layout_profession_developer_expected_language_expander_wrapper);
            RelativeLayout expand6 = findViewById(R.id.layout_profession_developer_expected_databases_expander_wrapper);
            RelativeLayout expand7 = findViewById(R.id.layout_profession_developer_expected_framework_expander_wrapper);
            RelativeLayout expand8 = findViewById(R.id.layout_profession_developer_expected_mobile_expander_wrapper);

            expand1.setOnClickListener(new ExpanderLayoutHelper(R.id.layout_profession_developer_language_expander_wrapper));
            expand2.setOnClickListener(new ExpanderLayoutHelper(R.id.layout_profession_developer_databases_expander_wrapper));
            expand3.setOnClickListener(new ExpanderLayoutHelper(R.id.layout_profession_developer_framework_expander_wrapper));
            expand4.setOnClickListener(new ExpanderLayoutHelper(R.id.layout_profession_developer_mobile_expander_wrapper));

            expand5.setOnClickListener(new ExpanderLayoutHelper(R.id.layout_profession_developer_expected_language_expander_wrapper));
            expand6.setOnClickListener(new ExpanderLayoutHelper(R.id.layout_profession_developer_expected_databases_expander_wrapper));
            expand7.setOnClickListener(new ExpanderLayoutHelper(R.id.layout_profession_developer_expected_framework_expander_wrapper));
            expand8.setOnClickListener(new ExpanderLayoutHelper(R.id.layout_profession_developer_expected_mobile_expander_wrapper));

            filesRecycler = findViewById(R.id.recycler_profession_developer_code_files);
            filesRecycler.setItemAnimator(new FadeInRightAnimator());
            filesRecycler.setLayoutManager(new GridLayoutManager(context, 2));
            filesAdapter = new ProfessionFilesAdapter(context, new ArrayList<File>(), filesRecycler);
            filesRecycler.setAdapter(filesAdapter);
            filesRecycler.setNestedScrollingEnabled(false);
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            languagesRecycler.setAdapter(languagesAdapter);
            databasesRecycler.setAdapter(databasesAdapter);
            frameworksRecycler.setAdapter(frameworkAdapter);
            mobilesRecycler.setAdapter(mobilesAdapter);
            userExpectedLanguagesRecycler.setAdapter(expectedLanguagesAdapter);
            userExpectedDatabasesRecycler.setAdapter(expectedDatabasesAdapter);
            userExpectedFrameworksRecycler.setAdapter(expectedFrameworksAdapter);
            userExpectedPreferenceRecycler.setAdapter(expectedMobilesAdapter);
            Log.d("OkHttpTAG", "OUTSIDE");
            hideLoadingDialog();
        }
    }

    public class ExpanderLayoutHelper implements View.OnClickListener {
        RelativeLayout expandWrapper;
        ImageView arrow;
        ExpandableLayout expandableLayout;

        ExpanderLayoutHelper(int relativeLayoutId) {
            this.expandWrapper = findViewById(relativeLayoutId);
            this.arrow = (ImageView) expandWrapper.getChildAt(1);
            this.expandableLayout = (ExpandableLayout) expandWrapper.getChildAt(2);
        }

        @Override
        public void onClick(View v) {
            if (expandableLayout.isCollapsed()) {
                expandableLayout.expand();
                expandWrapper.getChildAt(1).animate().rotation(90f).setDuration(1000);
            } else {
                expandableLayout.collapse();
                arrow.animate().rotation(0).setDuration(1000);
            }
        }

    }

    private void initializeLists(final Context context) {
        runOnUiThread(new Runnable() {
            public void run() {
                languagesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeLanguageList(context));
                databasesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeDatabaseLanguageList(context));
                frameworkAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeFrameworksList(context));
                mobilesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeMobileTechnologiesList(context));

                expectedLanguagesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeLanguageList(context));
                expectedDatabasesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeDatabaseLanguageList(context));
                expectedFrameworksAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeFrameworksList(context));
                expectedMobilesAdapter = new DeveloperTechnologiesAdapter(context, MeteorUtility.initializeMobileTechnologiesList(context));
            }
        });
    }

    public void getTestTaskUrl(final int taskId) {
        showLoadingDialog();
        Call<ResultJson<TestTasksRetrofit>> call = getMeteorService("").getTestTasks();

        call.enqueue(new Callback<ResultJson<TestTasksRetrofit>>() {
            @Override
            public void onResponse(Call<ResultJson<TestTasksRetrofit>> call,
                                   Response<ResultJson<TestTasksRetrofit>> response) {
                hideLoadingDialog();

                if (response.isSuccessful() && response.body() != null) {
                    if (taskId == 1) {
                        testTaskUrl = response.body().getContent().getWebTaskUrl();
                    } else {
                        testTaskUrl = response.body().getContent().getAndroidTaskUrl();
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(testTaskUrl));
                    startActivity(intent);
                } else

                {
//                    String responseFromServer = responseArray.get("Error");
//                    String fileFormat = responseFromServer.substring(responseFromServer.lastIndexOf('/') + 1);
//                    String errorText = String.format(getString(R.string.error_file_format), fileFormat);
                    DialogUtility.showErrorDialog(DeveloperProfessionActivity.this, "Error((", false);
                }
            }

            @Override
            public void onFailure(Call<ResultJson<TestTasksRetrofit>> call, Throwable t) {
                hideLoadingDialog();
                DialogUtility.showErrorDialog(DeveloperProfessionActivity.this, getString(R.string.error_loading_data), false);
            }
        });
    }
}

