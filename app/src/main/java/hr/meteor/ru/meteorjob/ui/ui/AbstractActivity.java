package hr.meteor.ru.meteorjob.ui.ui;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.utility.MeteorUtility;

public abstract class AbstractActivity extends AppCompatActivity {
    Dialog loadingDialog;

    public boolean isInternetActive() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public void showLoadingDialog() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View loadingView = inflater.inflate(R.layout.loading_process, null);

        loadingDialog = new Dialog(this, R.style.TransparentCustomDialog);
        loadingDialog.setContentView(loadingView);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(MeteorUtility.dpToPx(250, getApplicationContext()), RelativeLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
    }

    public Toolbar createToolbar(int id, int iconId, int titleId, boolean isHomeActive) {
        Toolbar toolbar = findViewById(id);
        if (titleId != 0) {
            toolbar.setTitle(titleId);
        }
        if (iconId != 0) {
            toolbar.setNavigationIcon(iconId);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeActive);
        return toolbar;
    }
}
