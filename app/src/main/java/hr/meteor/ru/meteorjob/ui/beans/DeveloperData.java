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

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }
}
