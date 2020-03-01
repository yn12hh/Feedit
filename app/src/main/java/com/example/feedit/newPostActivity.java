package com.example.feedit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;


public class newPostActivity extends AppCompatActivity {

    private Button post_button_clicked;
    private FeedItFBInterface fb_interface;
    private EditText title_et, projects_et, teams_et, post_content_et; //et stands for Edit Text, written in acronym to save name length



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        title_et = (EditText) findViewById(R.id.title_input);
        teams_et = (EditText) findViewById(R.id.teams_name_input);
        projects_et = (EditText) findViewById(R.id.projects_name_input);
        post_content_et = (EditText) findViewById(R.id.posts_content_input);
        fb_interface = FeedItFBInterface.getInstance();

        post_button_clicked = (Button) findViewById(R.id.post_button);
        post_button_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = title_et.getText().toString();
                String teams = teams_et.getText().toString();
                String projects = projects_et.getText().toString();
                String post_content = post_content_et.getText().toString();
                if(!title.isEmpty() && !teams.isEmpty() && !projects.isEmpty() && !post_content.isEmpty()) {

                    sendDetailsToFB(title, teams, projects, post_content);
                    Intent my_intent = new Intent(getBaseContext(), Feed.class);
                    startActivity(my_intent);
                }
                else {

                    Toast.makeText(getApplicationContext(),"You left one of the fields empty, please try again", Toast.LENGTH_LONG).show();
                }
            }
        });



    }





    public void sendDetailsToFB(String title, String teams, String projects, String post_content) {
        Post new_post = new Post(title,teams,post_content, projects);
        fb_interface.uploadPost(new_post);
    }



    public void cancelNewPost(View view) {
        Intent cencel_post = new Intent(getBaseContext(), Feed.class);
        startActivity(cencel_post);    }
}