package hr.meteor.ru.meteorjob.ui.ui;

import android.os.Bundle;
import android.view.MenuItem;

import hr.meteor.ru.meteorjob.R;

public class DeveloperProfessionActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_profession);

        createToolbar(R.id.actionbar_profession_developer, 0, R.string.profession_dev, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
