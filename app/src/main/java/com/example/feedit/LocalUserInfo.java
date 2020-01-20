package com.example.feedit;

import android.widget.EditText;

public class LocalUserInfo {
    private static LocalUserInfo  localUserInfo_instance = null;
    private String last_used_user_name;
    private String last_used_password;

    private LocalUserInfo(){
        last_used_user_name = "";
        last_used_password = "";
    }
    public static LocalUserInfo getInstance(EditText edittext_username, EditText edittext_password) {
        if(localUserInfo_instance == null)
            localUserInfo_instance = new LocalUserInfo();

        localUserInfo_instance.autoFill(edittext_username, edittext_password);

        return localUserInfo_instance;
    }

    public void autoFill(EditText edittext_username, EditText edittext_password) {
        edittext_password.setText(last_used_password);
        edittext_username.setText(last_used_user_name);
    }
}
