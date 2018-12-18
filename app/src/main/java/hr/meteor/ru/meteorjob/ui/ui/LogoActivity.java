package hr.meteor.ru.meteorjob.ui.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.utility.DialogUtility;

public class LogoActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        if (!isInternetActive()) {
            DialogUtility.showErrorDialog(this, getString(R.string.error_internet_connection), true);
        } else {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            };
            Handler handler = new Handler();
            handler.postDelayed(runnable, 2000);
        }
    }
}
