package com.example.feedit;

public class LocalUserInfo {
    private LocalUserInfo  localUserInfo_instance = null;
    private String last_used_user_name;
    private String last_used_password;

    private LocalUserInfo(){
        last_used_user_name = "";
        last_used_password = "";
    }
    public LocalUserInfo getInstance() {
        if(localUserInfo_instance == null)
            localUserInfo_instance = new LocalUserInfo();
        return localUserInfo_instance;
    }
}
