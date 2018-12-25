package hr.meteor.ru.meteorjob.ui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.beans.Profession;
import hr.meteor.ru.meteorjob.ui.utility.DialogUtility;

public class MainActivity extends AbstractActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isInternetActive()) {
            DialogUtility.showErrorDialog(this, getString(R.string.error_internet_connection), true);
        }

        createToolbar(R.id.actionbar_main, 0, R.string.actionbar_title, false);

        ArrayList<Profession> professionList = new ArrayList<>();
        professionList.add(new Profession(1, R.drawable.ic_profession_developer, getString(R.string.profession_dev)));
        professionList.add(new Profession(2, R.drawable.ic_profession_manager, getString(R.string.profession_manager)));

        Button managerTestButton = findViewById(R.id.button_profession_manager_test);
        Button developerTestButton = findViewById(R.id.button_profession_developer_test);
        Button managerReviewButton = findViewById(R.id.button_profession_manager_review);
        Button developerReviewButton = findViewById(R.id.button_profession_developer_review);

        managerTestButton.setOnClickListener(this);
        developerTestButton.setOnClickListener(this);
        managerReviewButton.setOnClickListener(this);
        developerReviewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.button_profession_manager_test:
                intent = new Intent(getApplicationContext(), TestListActivity.class);
                intent.putExtra("profession_ID", 1);
                break;
            case R.id.button_profession_developer_test:
                intent = new Intent(getApplicationContext(), TestListActivity.class);
                intent.putExtra("profession_ID", 2);
                break;
            case R.id.button_profession_developer_review:
                intent = new Intent(getApplicationContext(), DeveloperProfessionActivity.class);
                break;
            case R.id.button_profession_manager_review:
                intent = new Intent(getApplicationContext(), ManagerProfessionActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }
}
