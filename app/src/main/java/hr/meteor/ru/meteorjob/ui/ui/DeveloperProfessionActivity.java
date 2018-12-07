package hr.meteor.ru.meteorjob.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.DeveloperTechnologiesAdapter;
import hr.meteor.ru.meteorjob.ui.utility.MeteorUtility;

public class DeveloperProfessionActivity extends AbstractActivity implements View.OnClickListener {
    final int TAKE_USER_QUEST_OR_CODE_FILE_REQUEST = 101;
    final int TAKE_USER_RESUME_FILE_REQUEST = 102;
    TextView userTestQuestOrCodeFile;
    TextView userResumeFile;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == TAKE_USER_QUEST_OR_CODE_FILE_REQUEST && data != null) {
            File file = new File(data.getData().getPath());
            userTestQuestOrCodeFile.setText(file.getName());
        } else {
            Toast.makeText(this, R.string.error_loading_file_resume, Toast.LENGTH_LONG).show();
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

        DeveloperTechnologiesAdapter languagesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeLanguageList(this));
        DeveloperTechnologiesAdapter databasesAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeDatabaseLanguageList(this));
        DeveloperTechnologiesAdapter frameworkAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeFrameworksList(this));
        DeveloperTechnologiesAdapter mobileAdapter = new DeveloperTechnologiesAdapter(this, MeteorUtility.initializeMobileTechnologiesList(this));

        languagesRecycler.setAdapter(languagesAdapter);
        databasesRecycler.setAdapter(databasesAdapter);
        frameworksRecycler.setAdapter(frameworkAdapter);
        mobileRecycler.setAdapter(mobileAdapter);

        userTestQuestOrCodeFile = findViewById(R.id.link_profession_developer_take_file);
        userTestQuestOrCodeFile.setOnClickListener(this);

        userResumeFile = findViewById(R.id.link_profession_developer_take_resume);
        userResumeFile.setOnClickListener(this);

        ImageView clearUserFileImg = findViewById(R.id.image_profession_developer_clear_img);
        clearUserFileImg.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.link_profession_developer_take_file):

                Intent intent = new Intent();
                intent.setType("file/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select File"), TAKE_USER_QUEST_OR_CODE_FILE_REQUEST);
                break;
            case (R.id.image_profession_developer_clear_img): {
                if (userTestQuestOrCodeFile != null && userTestQuestOrCodeFile.getText() != null) {
                    Log.d("UserFileTag", String.valueOf(userTestQuestOrCodeFile.getText()));
                    userTestQuestOrCodeFile.setText(getString(R.string.manager_sent_put_resume));
                }
            }
        }
    }
}
