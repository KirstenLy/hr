package hr.meteor.ru.meteorjob.ui.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.retrofit.services.MeteorService;
import hr.meteor.ru.meteorjob.ui.retrofit.services.ResultJson;
import hr.meteor.ru.meteorjob.ui.ui.AbstractActivity;
import hr.meteor.ru.meteorjob.ui.ui.DeveloperProfessionActivity;
import hr.meteor.ru.meteorjob.ui.ui.MainActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.createMultipartBodyList;
import static hr.meteor.ru.meteorjob.ui.utility.MeteorUtility.getJsonFromDeveloperObject;

public class DialogUtility {

    public static void showErrorDialog(final Context context, String error, final boolean isActivityClose) {
        final Activity activity = (Activity) context;
        final Dialog dialog = new Dialog(context, R.style.TransparentCustomDialog);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_error, null);
        dialog.setContentView(popupView);
        dialog.show();

        TextView errorText = popupView.findViewById(R.id.error_text);
        errorText.setText(error);

        Button finishButton = popupView.findViewById(R.id.close_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isActivityClose) {
                    activity.finish();
                } else {
                    dialog.cancel();
                }
            }
        });
    }

    public static void showTestResult(final Context context, int correctAnswersCount) {
        final Dialog dialog = new Dialog(context, R.style.TransparentCustomDialog);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_error, null);
        dialog.setContentView(popupView);
        dialog.setCancelable(false);
        dialog.show();

        TextView errorText = popupView.findViewById(R.id.error_text);
        String testResult = context.getString(R.string.test_finish)
                + '\n'
                + String.format(context.getString(R.string.test_finish_show_count), correctAnswersCount);
        errorText.setText(testResult);

        Button finishButton = popupView.findViewById(R.id.close_button);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public static void showTestTaskDialog(final Context context, final MeteorService meteorService, final int taskKey) {
        final Dialog dialog = new Dialog(context, R.style.TransparentCustomDialog);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_send_file, null);
        dialog.setContentView(popupView);
        dialog.show();

        final EditText email = popupView.findViewById(R.id.edit_popup_send_email);

        Button finishButton = popupView.findViewById(R.id.close_button);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MeteorUtility.isValidEmail(String.valueOf(email.getText()))) {
                    email.setError(context.getString(R.string.error_validation_mail));
                } else {
                    ((DeveloperProfessionActivity) context).showLoadingDialog();
                    Call<ResultJson> call;

                    if (taskKey == 1) {
                        call = meteorService.postWebTestTask(email.getText().toString());
                    } else {
                        call = meteorService.postAndroidTestTask(email.getText().toString());
                    }

                    call.enqueue(new Callback<ResultJson>() {
                        @Override
                        public void onResponse(Call<ResultJson> call,
                                               Response<ResultJson> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                ((DeveloperProfessionActivity) context).hideLoadingDialog();
                                showErrorDialog(context, "Тестовое успешно отослано на:\n" + email.getText() + "!", false);
                                dialog.cancel();
                            } else {
                                ((DeveloperProfessionActivity) context).hideLoadingDialog();
                                showErrorDialog(context, context.getString(R.string.error_email_send), false);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResultJson> call, Throwable t) {
                        }
                    });
                }
            }
        });
    }
}