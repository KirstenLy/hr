package hr.meteor.ru.meteorjob.ui.utility;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;

public class MeteorUtility {
    public static int dpToPx(int dp, Context context) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static Spanned getUnderlineSpanned(String string) {
        return Html.fromHtml("<u>" + string + "</u>");
    }
}
