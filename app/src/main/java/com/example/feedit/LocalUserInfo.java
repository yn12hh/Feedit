package com.example.feedit;

public class LocalUserInfo {
    private static LocalUserInfo  localUserInfo_instance = null;
    private String last_used_user_name;
    private String last_used_password;

    private LocalUserInfo(){
        last_used_user_name = "";
        last_used_password = "";
    }
    public static LocalUserInfo getInstance() {
        if(localUserInfo_instance == null)
            localUserInfo_instance = new LocalUserInfo();
        return localUserInfo_instance;
    }


    public String getLast_used_user_name() {
        return last_used_user_name;
    }

    public void setLast_used_user_name(String last_used_user_name) {
        this.last_used_user_name = last_used_user_name;
    }

    public String getLast_used_password() {
        return last_used_password;
    }

    public void setLast_used_password(String last_used_password) {
        this.last_used_password = last_used_password;
    }
}
