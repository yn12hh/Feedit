package com.example.feedit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Feed extends AppCompatActivity {

    private ImageView add_button;
    private Dialog  new_post_dialog;
    private ImageView close_dialog;
    private Button post_button_clicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FeedFragment()).commit();

        new_post_dialog = new Dialog(this);

        add_button = (ImageView) findViewById(R.id.add_icon);
        add_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showAddPost();
            }
        });




    }

    public void showAddPost(){
        new_post_dialog.setContentView(R.layout.add_post_dialog);
        close_dialog = (ImageView) new_post_dialog.findViewById(R.id.close_dialog_imag);
        post_button_clicked = (Button) new_post_dialog.findViewById(R.id.post_button);

        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_post_dialog.dismiss();
            }
        });

        new_post_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        new_post_dialog.show();

        post_button_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText name = (EditText) findViewById(R.id.title_input);
                String name_string =  name.getText().toString();
                Post new_post_object = new Post(name_string ,R.id.teams_name_input, R.id.txtInput_userID, R.id.posts_content_input, R.id.projects_name_input);
                //pass to firebase!;
                new_post_dialog.dismiss();

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.nav_feed:
                    selectedFragment = new FeedFragment();
                    break;
                case R.id.nav_team:
                    selectedFragment = new TeamsFragment();
                    break;
                case R.id.nav_projects:
                    selectedFragment = new ProjectsFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };


    }


