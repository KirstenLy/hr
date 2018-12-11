package hr.meteor.ru.meteorjob.ui.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.DeveloperTechnologiesAdapter;
import hr.meteor.ru.meteorjob.ui.utility.MeteorUtility;

import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.setFileNameOnTextView;

public class DeveloperProfessionActivity extends AbstractActivity implements View.OnClickListener {
    TextView userTaskOrCodeFile;
    TextView userBriefFile;

    DeveloperTechnologiesAdapter languagesAdapter;
    DeveloperTechnologiesAdapter databasesAdapter;
    DeveloperTechnologiesAdapter frameworkAdapter;
    DeveloperTechnologiesAdapter mobilesAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            if ((requestCode == TAKE_USER_BRIEF_FILE_REQUEST || requestCode == TAKE_USER_TASK_OR_CODE_FILE_REQUEST) && data.getData() != null) {
                if (requestCode == TAKE_USER_TASK_OR_CODE_FILE_REQUEST) {
                    setFileNameOnTextView(data.getData().getPath(), userTaskOrCodeFile);
                } else {
                    setFileNameOnTextView(data.getData().getPath(), userBriefFile);
                }
            }
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
        RecyclerView mobileRecycler = findViewById(R.id.recycler_profession_developer_mobile);

        RecyclerView.LayoutManager layoutManagerForLanguages = new GridLayoutManager(this, 3);
        RecyclerView.LayoutManager layoutManagerForDatabases = new GridLayoutManager(this, 3);
        RecyclerView.LayoutManager layoutManagerForFrameworks = new GridLayoutManager(this, 3);
        RecyclerView.LayoutManager layoutManagerForMobile = new GridLayoutManager(this, 3);

        languagesRecycler.setLayoutManager(layoutManagerForLanguages);
        databasesRecycler.setLayoutManager(layoutManagerForDatabases);
        frameworksRecycler.setLayoutManager(layoutManagerForFrameworks);
        mobileRecycler.setLayoutManager(layoutManagerForMobile);

        languagesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeLanguageList(this));
        databasesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeDatabaseLanguageList(this));
        frameworkAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeFrameworksList(this));
        mobilesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeMobileTechnologiesList(this));

        languagesRecycler.setAdapter(languagesAdapter);
        databasesRecycler.setAdapter(databasesAdapter);
        frameworksRecycler.setAdapter(frameworkAdapter);
        mobileRecycler.setAdapter(mobilesAdapter);

        languagesRecycler.setNestedScrollingEnabled(false);
        databasesRecycler.setNestedScrollingEnabled(false);
        frameworksRecycler.setNestedScrollingEnabled(false);
        mobileRecycler.setNestedScrollingEnabled(false);

        userTaskOrCodeFile = findViewById(R.id.text_profession_developer_get_task);
        userTaskOrCodeFile.setOnClickListener(this);

        ImageView clearUserTaskOrCodeFile = findViewById(R.id.image_profession_developer_task_clear);
        clearUserTaskOrCodeFile.setOnClickListener(this);

        TextView webTestTaskLink = findViewById(R.id.text_profession_developer_web_task_link);
        webTestTaskLink.setOnClickListener(this);

        TextView androidTestTaskLink = findViewById(R.id.text_profession_developer_android_task_link);
        androidTestTaskLink.setOnClickListener(this);

        userBriefFile = findViewById(R.id.text_profession_developer_get_brief);
        userBriefFile.setOnClickListener(this);

        ImageView clearUserBriefFile = findViewById(R.id.image_profession_developer_brief_clear);
        clearUserBriefFile.setOnClickListener(this);

        Button sendData = findViewById(R.id.button_profession_developer_send);
        sendData.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        boolean[] languagesSelectedCheckboxData = languagesAdapter.getSelectedCheckboxData();
        boolean[] databasesSelectedCheckboxData = databasesAdapter.getSelectedCheckboxData();
        boolean[] frameworksSelectedCheckboxData = frameworkAdapter.getSelectedCheckboxData();
        boolean[] mobilesSelectedCheckboxData = mobilesAdapter.getSelectedCheckboxData();

        outState.putBooleanArray("languagesCheckboxes", languagesSelectedCheckboxData);
        outState.putBooleanArray("databasesCheckboxes", databasesSelectedCheckboxData);
        outState.putBooleanArray("frameworksCheckboxes", frameworksSelectedCheckboxData);
        outState.putBooleanArray("mobilesCheckboxes", mobilesSelectedCheckboxData);

        if (userBriefFile != null) {
            outState.putString("userBriefFileKey", String.valueOf(userBriefFile.getText()));
        }
        if (userTaskOrCodeFile != null) {
            outState.putString("userTaskOrCodeFileKey", String.valueOf(userTaskOrCodeFile.getText()));
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        languagesAdapter.setSelectedCheckboxData(savedInstanceState.getBooleanArray("languagesCheckboxes"));
        databasesAdapter.setSelectedCheckboxData(savedInstanceState.getBooleanArray("databasesCheckboxes"));
        frameworkAdapter.setSelectedCheckboxData(savedInstanceState.getBooleanArray("frameworksCheckboxes"));
        mobilesAdapter.setSelectedCheckboxData(savedInstanceState.getBooleanArray("mobilesCheckboxes"));

        if (userBriefFile != null && savedInstanceState.getString("userBriefFileKey") != null) {
            userBriefFile.setText(savedInstanceState.getString("userBriefFileKey"));
        }
        if (userTaskOrCodeFile != null && savedInstanceState.getString("userTaskOrCodeFileKey") != null)
            userTaskOrCodeFile.setText(savedInstanceState.getString("userTaskOrCodeFileKey"));
    }

    @Override
    public void onClick(View v) {
        int elementId = v.getId();

        if (elementId == R.id.text_profession_developer_get_task || elementId == R.id.text_profession_developer_get_brief) {
            Intent intent = new Intent();
            intent.setType("file/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            if (elementId == R.id.text_profession_developer_get_task) {
                startActivityForResult(Intent.createChooser(intent, getString(R.string.default_choose_file)), TAKE_USER_TASK_OR_CODE_FILE_REQUEST);
            } else {
                startActivityForResult(Intent.createChooser(intent, getString(R.string.default_choose_file)), TAKE_USER_BRIEF_FILE_REQUEST);
            }
        }

        if (elementId == R.id.image_profession_developer_task_clear || elementId == R.id.image_profession_developer_brief_clear) {
            if (elementId == R.id.image_profession_developer_task_clear) {
                userTaskOrCodeFile.setText(MeteorUtility.getUnderlineSpanned(getString(R.string.developer_sent_file)));
            } else {
                userBriefFile.setText(MeteorUtility.getUnderlineSpanned(getString(R.string.developer_sent_file)));
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
                MeteorUtility.sendData();
            } else {
                Toast.makeText(this, R.string.error_validation, Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean validationSuccess() {
        boolean isSuccess = true;
        EditText name = findViewById(R.id.edit_profession_developer_contacts_name);
        EditText phone = findViewById(R.id.edit_profession_developer_contacts_phone);
        EditText email = findViewById(R.id.edit_profession_developer_contacts_email);

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
}

