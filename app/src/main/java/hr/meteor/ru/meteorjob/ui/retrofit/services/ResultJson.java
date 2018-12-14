package hr.meteor.ru.meteorjob.ui.retrofit.services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Base json response
 * @param <T> Content class type
 */
public class ResultJson<T> {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("Code")
    @Expose
    private int code;

    @SerializedName("ErrorList")
    @Expose
    private ArrayList<String> errorList;

    @SerializedName("MessageList")
    @Expose
    private ArrayList<String> messageList;

    @SerializedName("Data")
    @Expose
    private T content;

    /**
     * Get status in json response
     * @return Status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set new status
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get content from the Content field
     * @return
     */
    public T getContent() {
        return content;
    }

    /**
     * Set new content
     * @param content
     */
    public void setContent(T content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ArrayList<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(ArrayList<String> errorList) {
        this.errorList = errorList;
    }

    public ArrayList<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(ArrayList<String> messageList) {
        this.messageList = messageList;
    }

    public boolean isSuccess() {
        return status != null && (status.equals("success") || status.equals("ok"));
    }
}
