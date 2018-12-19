package hr.meteor.ru.meteorjob.ui.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.Patterns;
import android.util.TypedValue;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.beans.DeveloperData;
import hr.meteor.ru.meteorjob.ui.beans.ManagerData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.content.Context.MODE_PRIVATE;

public class MeteorUtility {
    public static int dpToPx(int dp, Context context) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }

    public static Spanned getUnderlineSpanned(String string) {
        return Html.fromHtml("<u>" + string + "</u>");
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String getJsonFromManagerObject(ManagerData managerData) {
        JSONObject json = new JSONObject();
        try {
            json.put("Name", managerData.getName());
            json.put("Phone", managerData.getPhone());
            json.put("Email", managerData.getEmail());
            json.put("HasExperience", managerData.isSkilled());
            json.put("Comment", managerData.getComment());
            if (managerData.getAnswer() != null) {
                json.put("Answer", managerData.getAnswer());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static String getJsonFromDeveloperObject(DeveloperData developerData) {
        JSONObject json = new JSONObject();
        try {
            json.put("Name", developerData.getName());
            json.put("Phone", developerData.getPhone());
            json.put("Email", developerData.getEmail());
            json.put("HasExperience", developerData.isSkilled());
            json.put("Comment", developerData.getComment());
            json.put("Languages", Arrays.toString(developerData.getLanguages()));
            json.put("Databases", Arrays.toString(developerData.getDatabases()));
            json.put("Framework", Arrays.toString(developerData.getFrameworks()));
            json.put("Mobiles", Arrays.toString(developerData.getMobiles()));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static boolean isFileExternalCorrect(File file) {
        return file.getName().endsWith(".doc") || file.getName().endsWith(".pdf") || file.getName().endsWith(".ppt");
    }

    public static String getFileExternal(File file) {
        return file.toString().substring(file.toString().lastIndexOf('.') + 1);
    }

    public static MultipartBody.Part createMultipartBodyPartFromFile(File file) {
        if (file != null) {
            String filePath = file.toString();
            String external = getFileExternal(file);
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(external);
            RequestBody requestBody = RequestBody.create(MediaType.parse(mimeTypeFromExtension), file);
            return MultipartBody.Part.createFormData("resume_file", file.toString().substring(filePath.lastIndexOf('/') + 1), requestBody);
        } else {
            return null;
        }
    }

    public static ArrayList<MultipartBody.Part> createMultipartBodyList(ArrayList<File> fileList) {
        if (fileList != null) {
            ArrayList<MultipartBody.Part> resultList = new ArrayList<>();
            Log.d("OkHttpTAG", String.valueOf(fileList.size()));
            Log.d("OkHttpTAG", String.valueOf(fileList.toString()));
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);

                String filePath = file.toString();
                String external = getFileExternal(file);
                Log.d("OkHttpTAG", external);
                if (external.length() == 0 || external.contains("/")) {
                    continue;
                }
                String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(external);
                RequestBody requestBody = RequestBody.create(MediaType.parse(mimeTypeFromExtension), file);
                resultList.add(MultipartBody.Part.createFormData("resume_file " + i, file.toString().substring(filePath.lastIndexOf('/') + 1), requestBody));
            }
            return resultList;
        } else {
            return null;
        }
    }

    public static void setLinearLayoutParam(LinearLayout linearLayout, int weight, int height, int visibility) {
        linearLayout.setVisibility(visibility);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        params.weight = weight;
        params.height = height;
        linearLayout.setLayoutParams(params);
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

    public static void putArrayListOnSharedPreference(ArrayList<String> arrayList, SharedPreferences.Editor editor, String key) {
        Gson gson = new Gson();
        String json = gson.toJson(arrayList);
        editor.putString(key, json);
    }

    public static ArrayList<String> putArrayListOutFeomSharedPreference(SharedPreferences sharedPreferences, String key) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();

        String json = sharedPreferences.getString(key, null);
        return gson.fromJson(json, type);
    }

    public static boolean[] getArrayFromArrayList(ArrayList<String> arrayList) {
        boolean[] restoredCheckers = new boolean[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            restoredCheckers[i] = Boolean.valueOf(arrayList.get(i));
        }
        return restoredCheckers;
    }
}
