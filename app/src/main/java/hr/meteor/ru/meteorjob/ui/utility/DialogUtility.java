package hr.meteor.ru.meteorjob.ui.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hr.meteor.ru.meteorjob.R;

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

    public static void showSendTaskDialog(final Context context, int position) {
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
                showErrorDialog(context, "Sended!\n" + email.getText(), false);
                dialog.cancel();
            }
        });
    }
}