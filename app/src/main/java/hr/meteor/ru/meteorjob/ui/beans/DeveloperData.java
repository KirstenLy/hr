package hr.meteor.ru.meteorjob.ui.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeveloperData {
    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Phone")
    @Expose
    private String phone;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("IsSkilled")
    @Expose
    private String isSkilled;

    @SerializedName("GitHub")
    @Expose
    private String gitHub;

    @SerializedName("Comment")
    @Expose
    private String comment;

    @SerializedName("Languages")
    @Expose
    private String[] languages;

    @SerializedName("Databases")
    @Expose
    private String[] databases;

    @SerializedName("Frameworks")
    @Expose
    private String[] frameworks;

    @SerializedName("Mobiles")
    @Expose
    private String[] mobiles;

    @SerializedName("ExpectedLanguages")
    @Expose
    private String[] expectedLanguages;

    @SerializedName("ExpectedDatabases")
    @Expose
    private String[] expectedDatabases;

    @SerializedName("ExpectedFrameworks")
    @Expose
    private String[] expectedFrameworks;

    @SerializedName("ExpectedMobiles")
    @Expose
    private String[] expectedMobiles;

    public String isSkilled() {
        return isSkilled;
    }

    public void setSkilled(String skilled) {
        isSkilled = skilled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGitHub() {
        return gitHub;
    }

    public void setGitHub(String gitHub) {
        this.gitHub = gitHub;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String[] getDatabases() {
        return databases;
    }

    public void setDatabases(String[] databases) {
        this.databases = databases;
    }

    public String[] getFrameworks() {
        return frameworks;
    }

    public void setFrameworks(String[] frameworks) {
        this.frameworks = frameworks;
    }

    public String[] getMobiles() {
        return mobiles;
    }

    public String getIsSkilled() {
        return isSkilled;
    }

    public void setIsSkilled(String isSkilled) {
        this.isSkilled = isSkilled;
    }

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }

    public String[] getExpectedLanguages() {
        return expectedLanguages;
    }

    public void setExpectedLanguages(String[] expectedLanguages) {
        this.expectedLanguages = expectedLanguages;
    }

    public String[] getExpectedDatabases() {
        return expectedDatabases;
    }

    public void setExpectedDatabases(String[] expectedDatabases) {
        this.expectedDatabases = expectedDatabases;
    }

    public String[] getExpectedFrameworks() {
        return expectedFrameworks;
    }

    public void setExpectedFrameworks(String[] expectedFrameworks) {
        this.expectedFrameworks = expectedFrameworks;
    }

    public String[] getExpectedMobiles() {
        return expectedMobiles;
    }

    public void setExpectedMobiles(String[] expectedMobiles) {
        this.expectedMobiles = expectedMobiles;
    }
}
