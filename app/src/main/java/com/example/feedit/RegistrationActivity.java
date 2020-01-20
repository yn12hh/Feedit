package com.example.feedit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edittext_email, edittext_password;
    private FirebaseAuth mAuth;
    ProgressBar reg_progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        edittext_email = (EditText) findViewById(R.id.email_edit);
        edittext_password = (EditText) findViewById(R.id.password_edit);
        reg_progressbar = (ProgressBar)findViewById(R.id.reg_progressbar);
        findViewById(R.id.registration_button).setOnClickListener(this);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registration_button:
                registerUser();

        }
    }

    private void registerUser() {
        String username = edittext_email.getText().toString().trim();
        String password = edittext_password.getText().toString().trim();

        if (username.isEmpty()) {
            edittext_email.setError("Username is required");
            edittext_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            edittext_email.setError("Please enter a valid email");
            edittext_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edittext_password.setError("Password is required");
            edittext_password.requestFocus();
            return;
        }

        if (password.length() < 6) {
            edittext_password.setError("Minimum lenght of password should be 6");
            edittext_password.requestFocus();
            return;
        }

        reg_progressbar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                reg_progressbar.setVisibility(View.GONE);
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"User Registered Successfull", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent (getBaseContext(),Feed.class);
                    startActivity(myIntent);
                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
        }});
    }
}
