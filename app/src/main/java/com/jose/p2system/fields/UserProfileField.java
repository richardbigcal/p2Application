package com.jose.p2system.fields;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserProfileField {


    @SerializedName("status_code")
    public  String status_code;

    public String getStatus_code() {
        return status_code;
    }
    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    @SerializedName("status_message")
    public String status_message;

    public String getStatus_message() {
        return status_message;
    }
    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    @SerializedName("profile")
    private UserProfileInfoField userProfileInfoField;

    public UserProfileInfoField getUserProfileInfoField() {
        return userProfileInfoField;
    }

    public void setUserProfileInfoField(UserProfileInfoField userProfileInfoField) {
        this.userProfileInfoField = userProfileInfoField;
    }




}
