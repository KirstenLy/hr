package hr.meteor.ru.meteorjob.ui.ui;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.concurrent.TimeUnit;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.retrofit.services.MeteorService;
import hr.meteor.ru.meteorjob.ui.utility.MeteorUtility;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class AbstractActivity extends AppCompatActivity {
    final int TAKE_USER_TASK_OR_CODE_FILE_REQUEST = 101;
    final int TAKE_USER_BRIEF_FILE_REQUEST = 102;
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

    public void hideLoadingDialog() {
        loadingDialog.hide();
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

    public MeteorService getMeteorService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("OkHttpTAG", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(60, TimeUnit.SECONDS).
                readTimeout(60, TimeUnit.SECONDS).
                writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://hr.fokgroup.com/vacancy/").addConverterFactory(GsonConverterFactory.create()).client(client).build();
        return retrofit.create(MeteorService.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
