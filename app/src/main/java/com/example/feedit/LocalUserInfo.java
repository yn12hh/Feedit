package com.example.feedit;

import android.content.SharedPreferences;
import android.widget.EditText;
import android.content.SharedPreferences;

public class LocalUserInfo {
    private static LocalUserInfo  localUserInfo_instance = null;
    private String last_used_user_name;
    private String last_used_password;
    private SharedPreferences user_info_sharedpreferences;

    private LocalUserInfo(SharedPreferences user_info){
        user_info_sharedpreferences = user_info;

        last_used_user_name = user_info.getString("user_name", "");
        last_used_password = user_info.getString("password", "");
    }
    public static LocalUserInfo getInstance(SharedPreferences user_info) {
        if(localUserInfo_instance == null)
            localUserInfo_instance = new LocalUserInfo(user_info);

        return localUserInfo_instance;
    }

    public void autoFill(EditText edittext_username, EditText edittext_password) {
        edittext_password.setText(last_used_password);
        edittext_username.setText(last_used_user_name);
    }

    public void updateUserInfo(String user_name, String password){
        SharedPreferences.Editor info_edit = user_info_sharedpreferences.edit();
        info_edit.putString("user_name", user_name);
        info_edit.putString("password", password);
        last_used_password = password;
        last_used_user_name = user_name;
    }
}
