package hr.meteor.ru.meteorjob.ui.utility;

import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.Spanned;
import android.util.TypedValue;

import java.util.ArrayList;

import hr.meteor.ru.meteorjob.R;

public class MeteorUtility {
    public static int dpToPx(int dp, Context context) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static Spanned getUnderlineSpanned(String string) {
        return Html.fromHtml("<u>" + string + "</u>");
    }

    public static ArrayList<String> initializeLanguageList(Context context) {
        ArrayList<String> languageArrayList = new ArrayList<>();
        languageArrayList.add(context.getString(R.string.developer_technology_languages_1));
        languageArrayList.add(context.getString(R.string.developer_technology_languages_2));
        languageArrayList.add(context.getString(R.string.developer_technology_languages_3));
        languageArrayList.add(context.getString(R.string.developer_technology_languages_4));
        languageArrayList.add(context.getString(R.string.developer_technology_languages_5));
        languageArrayList.add(context.getString(R.string.developer_technology_languages_6));
        languageArrayList.add(context.getString(R.string.developer_technology_languages_7));
        return languageArrayList;
    }

    public static ArrayList<String> initializeDatabaseLanguageList(Context context) {
        ArrayList<String> languageArrayList = new ArrayList<>();
        languageArrayList.add(context.getString(R.string.developer_technology_sql_1));
        languageArrayList.add(context.getString(R.string.developer_technology_sql_2));
        languageArrayList.add(context.getString(R.string.developer_technology_sql_3));
        languageArrayList.add(context.getString(R.string.developer_technology_sql_4));
        languageArrayList.add(context.getString(R.string.developer_technology_sql_5));
        languageArrayList.add(context.getString(R.string.developer_technology_sql_6));
        return languageArrayList;
    }

    public static ArrayList<String> initializeFrameworksList(Context context) {
        ArrayList<String> languageArrayList = new ArrayList<>();
        languageArrayList.add(context.getString(R.string.developer_technology_framework_1));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_2));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_3));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_4));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_5));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_6));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_7));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_8));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_9));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_10));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_11));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_12));
        languageArrayList.add(context.getString(R.string.developer_technology_framework_13));
        return languageArrayList;
    }

    public static ArrayList<String> initializeMobileTechnologiesList(Context context) {
        ArrayList<String> languageArrayList = new ArrayList<>();
        languageArrayList.add(context.getString(R.string.developer_technology_mobile_1));
        languageArrayList.add(context.getString(R.string.developer_technology_mobile_2));
        languageArrayList.add(context.getString(R.string.developer_technology_mobile_3));
        languageArrayList.add(context.getString(R.string.developer_technology_mobile_4));
        languageArrayList.add(context.getString(R.string.developer_technology_mobile_5));
        languageArrayList.add(context.getString(R.string.developer_technology_mobile_6));
        languageArrayList.add(context.getString(R.string.developer_technology_mobile_7));
        languageArrayList.add(context.getString(R.string.developer_technology_mobile_8));
        languageArrayList.add(context.getString(R.string.developer_technology_mobile_9));
        return languageArrayList;
    }
}
