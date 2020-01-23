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

import com.example.feedit.Post;
import com.example.feedit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Feed extends AppCompatActivity implements dialog.dialogListener {

    private ImageView add_button;
    private Dialog  new_post_dialog;
    private ImageView close_dialog;
    private Button post_button_clicked;
    private FeedItFBInterface FBI;

    private TextView title_tv,teams_tv, projects_tv, post_content_tv, author_tv; //time?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FeedFragment()).commit();

        //missing author_tv = (TextView) findViewById(), the part that creates the text
        new_post_dialog = new Dialog(this);

        FBI = new FeedItFBInterface();

        add_button = (ImageView) findViewById(R.id.add_icon);
        add_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                openDialog();
            }
        });




    }

    public void openDialog () {
        dialog dialog = new dialog();
        dialog.show(getSupportFragmentManager(), "exampke dialog");

    }

   /* public void showAddPost(){
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
                *//*EditText title = (EditText) findViewById(R.id.title_input);
                String title_string =   title.getText().toString();
                EditText teams_name = (EditText) findViewById(R.id.teams_name_input);
                String teams_name_string =  teams_name.getText().toString();
                //EditText user_ID = (EditText) findViewById(R.id.txtInput_userID);
                String user_ID_string =  "1234";
                EditText posts_content = (EditText) findViewById(R.id.posts_content_input);
                String posts_content_string =  posts_content.getText().toString();
                EditText projects_name = (EditText) findViewById(R.id.projects_name_input);
                String projects_name_string =  projects_name.getText().toString();*//*
                //Post new_post_object = new Post(name_string ,teams_name_string, user_ID_string, posts_content_string, projects_name_string);
                //pass to firebase!;

                *//*Post post = new Post("come see the new board!", "marketing", "naftali", "it's ready!!!", "luach_bogrim");
                FBI.uploadPost(post);*//*
                new_post_dialog.dismiss();

            }
        });
    }*/

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


    @Override
    public void applyTexts(String title, String teams, String projects, String post_content) {
        Post new_post = new Post(title,teams,post_content, projects);
        FBI.uploadPost(new_post);
    }
}
