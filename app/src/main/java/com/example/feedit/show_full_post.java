package com.example.feedit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class show_full_post extends AppCompatActivity {

    private String title_string, time_stamp_string, teams_string, projects_string, content_string;
    TextView title, time_stamp, teams, projects, content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_post);
        title_string = getIntent().getStringExtra("title");
        time_stamp_string = getIntent().getStringExtra("time_stamp");
        teams_string = getIntent().getStringExtra("teams");
        projects_string = getIntent().getStringExtra("projects");
        content_string = getIntent().getStringExtra("content");

        title = (TextView) findViewById(R.id.title);
        time_stamp = (TextView) findViewById(R.id.time_stamp);
        teams = (TextView) findViewById(R.id.teams);
        projects = (TextView) findViewById(R.id.projects);
        content = (TextView) findViewById(R.id.content);

        title.setText(title_string);
        time_stamp.setText(time_stamp_string);
        teams.setText(teams_string);
        projects.setText(projects_string);
        content.setText(content_string);
    }

    public void deletePost(View view){

    }

    public void goToFeed(View view){
        Intent my_intent = new Intent(getBaseContext(), Feed.class);
        startActivity(my_intent);
    }
}
