package hr.meteor.ru.meteorjob.ui.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ManagerData {
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

    @SerializedName("Answer")
    @Expose
    private String answer;

    @SerializedName("Comment")
    @Expose
    private String comment;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
