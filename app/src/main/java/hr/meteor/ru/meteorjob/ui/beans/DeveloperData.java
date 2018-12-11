package hr.meteor.ru.meteorjob.ui.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeveloperData {
    @SerializedName("IsSkilled")
    @Expose
    private boolean isSkilled;

    @SerializedName("Name")
    @Expose
    private String name;

    @SerializedName("Phone")
    @Expose
    private String phone;

    @SerializedName("Email")
    @Expose
    private String email;

    @SerializedName("Comment")
    @Expose
    private String comment;

    @SerializedName("Languages")
    @Expose
    private String[] languages;

    @SerializedName("Databases")
    @Expose
    private String[] Databases;

    @SerializedName("Frameworks")
    @Expose
    private String[] frameworks;

    @SerializedName("Mobiles")
    @Expose
    private String[] mobiles;

    public DeveloperData(boolean isSkilled, String name, String phone, String email, String comment, String[] languages, String[] databases, String[] frameworks, String[] mobiles) {
        this.isSkilled = isSkilled;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.comment = comment;
        this.languages = languages;
        Databases = databases;
        this.frameworks = frameworks;
        this.mobiles = mobiles;
    }

    public boolean isSkilled() {
        return isSkilled;
    }

    public void setSkilled(boolean skilled) {
        isSkilled = skilled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return Databases;
    }

    public void setDatabases(String[] databases) {
        Databases = databases;
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

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }
}
