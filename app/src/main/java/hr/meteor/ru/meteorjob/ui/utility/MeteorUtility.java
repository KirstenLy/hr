package hr.meteor.ru.meteorjob.ui.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.muddzdev.styleabletoast.StyleableToast;
import com.nononsenseapps.filepicker.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.meteor.ru.meteorjob.R;
import hr.meteor.ru.meteorjob.ui.adapters.DeveloperTechnologiesAdapter;
import hr.meteor.ru.meteorjob.ui.adapters.ProfessionFilesAdapter;
import hr.meteor.ru.meteorjob.ui.beans.DeveloperData;
import hr.meteor.ru.meteorjob.ui.beans.ManagerData;
import hr.meteor.ru.meteorjob.ui.beans.TestAnswer;
import hr.meteor.ru.meteorjob.ui.beans.TestQuestion;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MeteorUtility {

    public static int dpToPx(int dp, Context context) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
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
            json.put("GitHub", developerData.getGitHub());
            json.put("Languages", Arrays.toString(developerData.getLanguages()));
            json.put("Databases", Arrays.toString(developerData.getDatabases()));
            json.put("Framework", Arrays.toString(developerData.getFrameworks()));
            json.put("Mobiles", Arrays.toString(developerData.getMobiles()));
            json.put("ExpectedLanguages", Arrays.toString(developerData.getExpectedLanguages()));
            json.put("ExpectedDatabases", Arrays.toString(developerData.getExpectedDatabases()));
            json.put("ExpectedFrameworks", Arrays.toString(developerData.getExpectedFrameworks()));
            json.put("ExpectedMobiles", Arrays.toString(developerData.getExpectedMobiles()));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static String getFileExternal(File file) {
        return file.toString().substring(file.toString().lastIndexOf('.') + 1);
    }

    public static int getCheckedPosition(RadioGroup radioGroup) {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        View radioButton = radioGroup.findViewById(radioButtonID);
        return radioGroup.indexOfChild(radioButton);
    }

    public static ArrayList<MultipartBody.Part> createMultipartBodyList(ArrayList<File> fileList) {
        if (fileList != null) {
            ArrayList<MultipartBody.Part> resultList = new ArrayList<>();
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                String filePath = file.toString();
                String external = getFileExternal(file);
                if (external.length() == 0 || external.contains("/")) {
                    continue;
                }
                String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(external);
                RequestBody requestBody = RequestBody.create(MediaType.parse(mimeTypeFromExtension), file);
                resultList.add(MultipartBody.Part.createFormData("resume_file " + file.getName(), file.toString().substring(filePath.lastIndexOf('/') + 1), requestBody));
            }
            return resultList;
        } else {
            return null;
        }
    }

    public static void setLinearLayoutParam(RelativeLayout linearLayout, int weight, int height, int visibility) {
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

    public static ArrayList<TestQuestion> initializeTestQuestionList() {
        ArrayList<TestQuestion> testQuestionList = new ArrayList<>();

        testQuestionList.add(new TestQuestion(
                "Question 1 title",
                new TestAnswer[]{
                        new TestAnswer("Activity", false),
                        new TestAnswer("Fragment", false),
                        new TestAnswer("Layout", true),
                        new TestAnswer("Class", false)}
        ));


        testQuestionList.add(new TestQuestion(
                "Question 2 title",
                new TestAnswer[]{
                        new TestAnswer("ArrayList", false),
                        new TestAnswer("HashMap", false),
                        new TestAnswer("LinkedList", true),
                        new TestAnswer("LinkedHashMap", false)}
        ));

        testQuestionList.add(new TestQuestion(
                "Question 3 title",
                new TestAnswer[]{
                        new TestAnswer("Integer", false),
                        new TestAnswer("Boolean", false),
                        new TestAnswer("String", true),
                        new TestAnswer("Object", false)}));

        return testQuestionList;
    }

    public static void putArrayListOnSharedPreference(ArrayList<String> arrayList, SharedPreferences.Editor editor, String key) {
        if (arrayList == null) {
            editor.putString(key, null);
        } else {
            Gson gson = new Gson();
            String json = gson.toJson(arrayList);
            editor.putString(key, json);
        }
    }

    public static String updateFileListOnIntentResult(Intent data, ProfessionFilesAdapter adapter) {
        List<Uri> filesUtiList = Utils.getSelectedFilesFromResult(data);
        ArrayList<File> filesList = (ArrayList<File>) adapter.getFileList();

        StringBuilder duplicateNamesBuilder = new StringBuilder();

        for (Uri uri : filesUtiList) {
            File file = Utils.getFileForUri(uri);
            if (!filesList.contains(file)) {
                filesList.add(file);
            } else {
                duplicateNamesBuilder.append("\n").append(file.getName());
            }
        }

        adapter.notifyDataSetChanged();
        String result = duplicateNamesBuilder.toString();
        if (result.equals("")) {
            return null;
        } else {
            return result;
        }
    }

    public static void restoreRadioButtons(SharedPreferences sharedPreferences, String key, RadioButton yesButton, RadioButton noButton, RelativeLayout answerLayout) {
        if (sharedPreferences.getBoolean(key, false)) {
            yesButton.setChecked(true);
        } else {
            noButton.setChecked(true);
            if (answerLayout != null) {
                setLinearLayoutParam(answerLayout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, View.VISIBLE);
            }
        }
    }

    public static void restoreEditText(SharedPreferences sharedPreferences, String
            key, EditText editText) {
        if (sharedPreferences.contains(key)) {
            editText.setText(sharedPreferences.getString(key, ""));
        }
    }

    public static void restoreFilesClips(SharedPreferences sharedPreferences, String
            key, ProfessionFilesAdapter adapter) {
        if (sharedPreferences.contains(key)) {
            ArrayList<String> filePaths = putArrayListOutFromSharedPreference(sharedPreferences, key);
            ArrayList<File> filesList = (ArrayList<File>) adapter.getFileList();
            for (int i = 0; i < filePaths.size(); i++) {
                filesList.add(new File(filePaths.get(i)));
            }
            adapter.setFileList(filesList);
            adapter.notifyDataSetChanged();
        }
    }

    public static void saveDeveloperFlags(SharedPreferences.Editor editor, String key, DeveloperTechnologiesAdapter adapter) {
        boolean[] checkedFlags = adapter.getSelectedCheckboxArray();
        ArrayList<String> checkedFlagsArrayList = new ArrayList<>();

        for (boolean checkedFlag : checkedFlags) {
            checkedFlagsArrayList.add(String.valueOf(checkedFlag));
        }

        putArrayListOnSharedPreference(checkedFlagsArrayList, editor, key);
    }

    public static void restoreDeveloperFlags(SharedPreferences sharedPreferences, String key, DeveloperTechnologiesAdapter adapter) {
        if (sharedPreferences.contains(key)) {
            ArrayList<String> checkedFrameworks = putArrayListOutFromSharedPreference(sharedPreferences, key);
            boolean[] restoredCheckers = getArrayFromArrayList(checkedFrameworks);
            adapter.setSelectedCheckboxArray(restoredCheckers);
            adapter.notifyDataSetChanged();
        }
    }

    public static ArrayList<String> getFilesNamesList(ArrayList<File> filesList) {
        if (filesList != null && filesList.size() > 0) {
            ArrayList<String> fileNamesPaths = new ArrayList<>();

            for (int i = 0; i < filesList.size(); i++) {
                fileNamesPaths.add(filesList.get(i).toString());
            }
            return fileNamesPaths;
        }
        return null;
    }

    public static ArrayList<String> putArrayListOutFromSharedPreference(SharedPreferences
                                                                                sharedPreferences, String key) {
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

    public static void showDuplicatedFilesIfExist(String fileNames, Context context) {
        if (!(fileNames == null || fileNames.length() == 0)) {
            String filesNames = String.format(context.getString(R.string.error_repeat_files), fileNames);
            StyleableToast.makeText(context, filesNames, Toast.LENGTH_LONG, R.style.ToastValidationError).show();
        }
    }

    public static SpannableString getAgreementString(final Context context) {
        SpannableString agreementText = new SpannableString(context.getString(R.string.default_accept_agreement));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                String url = context.getString(R.string.url_agreement);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);
            }
        };

        agreementText.setSpan(clickableSpan, 48, 76, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return agreementText;
    }

    public static void correctMultiplyTextSize(EditText editText, Context context) {
        if (editText.getText().toString().equals(context.getString(R.string.manager_hint_question))) {
            editText.setTextSize(12);
        } else {
            editText.setTextSize(18);
        }
    }
}
