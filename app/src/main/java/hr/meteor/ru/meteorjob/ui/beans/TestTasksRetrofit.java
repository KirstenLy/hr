package hr.meteor.ru.meteorjob.ui.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestTasksRetrofit {
    @SerializedName("TestTaskWebURL")
    @Expose
    private String webTaskUrl;

    @SerializedName("TestTaskAndroidURL")
    @Expose
    private String androidTaskUrl;

    public String getWebTaskUrl() {
        return webTaskUrl;
    }

    public void setWebTaskUrl(String webTaskUrl) {
        this.webTaskUrl = webTaskUrl;
    }

    public String getAndroidTaskUrl() {
        return androidTaskUrl;
    }

    public void setAndroidTaskUrl(String androidTaskUrl) {
        this.androidTaskUrl = androidTaskUrl;
    }
}
