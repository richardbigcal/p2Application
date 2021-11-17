package com.jose.p2system.fields;

import com.google.gson.annotations.SerializedName;

public class UserProfileInfoField {


    @SerializedName("id")
    public String user_id;

    @SerializedName("fullname")
    public  String user_fullname;

    @SerializedName("email")
    public String user_email;

    @SerializedName("mobileno")
    public String user_mobileno;

    @SerializedName("address")
    public String user_address;


    public String getUser_id() { return user_id; }
    public void setUser_id(String user_id) { this.user_id = user_id; }

    public String getUser_fullname() { return user_fullname; }
    public void setUser_fullname(String user_fullname) { this.user_fullname = user_fullname; }

    public String getUser_email() { return user_email; }
    public void setUser_email(String user_email) { this.user_email = user_email; }

    public String getUser_mobileno() { return user_mobileno; }
    public void setUser_mobileno(String user_mobileno) { this.user_mobileno = user_mobileno; }

    public String getUser_address() { return user_address; }
    public void setUser_address(String user_address) { this.user_address = user_address; }


}
