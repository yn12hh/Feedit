package com.example.feedit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 123 ;
    private static final String TAG = "GoogleAuth";
    EditText edittext_username, edittext_password;
    FirebaseAuth mAuth;
    ProgressBar signin_progressbar;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edittext_username = (EditText) findViewById(R.id.sign_in_username);
        edittext_password = (EditText) findViewById(R.id.sign_in_password);
        signin_progressbar = (ProgressBar) findViewById(R.id.signin_progressbar);
        findViewById(R.id.Resigstration_signin_textView).setOnClickListener(this);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_in_google_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Resigstration_signin_textView:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
            case R.id.sign_in_button:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String username = edittext_username.getText().toString().trim();
        String password = edittext_password.getText().toString().trim();

        if (username.isEmpty()) {
            edittext_username.setError("Username is required");
            edittext_username.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            edittext_username.setError("Please enter a valid email");
            edittext_username.requestFocus();
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

        signin_progressbar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    signin_progressbar.setVisibility(View.GONE);
                    Intent myIntent = new Intent (getBaseContext(),Feed.class);
                    startActivity(myIntent);
                }
                else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
               Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(SignInActivity.this, "User signed in successfully with Google acount", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                          //  updateUI(user);
                            Intent myIntent = new Intent (getBaseContext(),Feed.class);
                            startActivity(myIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this,getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
                            //  Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                          //  updateUI(null);
                        }

                        // ...
                    }
                });
    }
}
