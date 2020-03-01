package com.example.feedit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import org.w3c.dom.Text;

public class show_full_post extends AppCompatActivity {

    private String title_string, time_stamp_string, team_string, project_string, text_string, author_string;
    TextView title, time_stamp, team, project, text, author;

    private int position;
    private FeedItFBInterface fb_interface;
    private DocumentReference post_doc_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_post);
        fb_interface = FeedItFBInterface.getInstance();

        //we must implement these methods in a seperate function called "intializeView"
        title_string = getIntent().getStringExtra("post_title");
        time_stamp_string = getIntent().getStringExtra("post_time_stamp");
        team_string = getIntent().getStringExtra("post_team");
        project_string = getIntent().getStringExtra("post_project");
        text_string = getIntent().getStringExtra("post_text");
        author_string = getIntent().getStringExtra("post_author");
        position = getIntent().getIntExtra("position", 0);

        title = (TextView) findViewById(R.id.post_title);
        time_stamp = (TextView) findViewById(R.id.post_time_stamp);
        team = (TextView) findViewById(R.id.post_team);
        project = (TextView) findViewById(R.id.post_project);
        text = (TextView) findViewById(R.id.post_text);
        author = (TextView) findViewById(R.id.post_author);

        title.setText(title_string);
        time_stamp.setText(time_stamp_string);
        team.setText(team_string);
        project.setText(project_string);
        text.setText(text_string);
        author.setText(author_string);

        post_doc_ref = fb_interface.getPostDocRef(position);
    }

    public void deletePost(View view){

    }

    public void goToFeed(View view){
        Intent my_intent = new Intent(getBaseContext(), Feed.class);
        startActivity(my_intent);
    }
}
