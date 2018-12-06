package hr.meteor.ru.meteorjob.ui.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hr.meteor.ru.meteorjob.R;

public class DialogUtility {
    public static void showErrorDialog(final Context context, int textRes, final boolean isActivityClose) {
        final Activity activity = (Activity) context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final Dialog dialog = new Dialog(context, R.style.TransparentCustomDialog);
        View popupView = inflater.inflate(R.layout.popup_error, null);

        dialog.setContentView(popupView);
        dialog.show();

        TextView errorText = popupView.findViewById(R.id.error_text);
        errorText.setText(textRes);

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
}