package com.example.feedit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.reg_button);
        findViewById(R.id.sign_in_button);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_button:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
            case R.id.signin_button:
                startActivity(new Intent(this, SignInActivity.class));
                break;

        }
    }
}
