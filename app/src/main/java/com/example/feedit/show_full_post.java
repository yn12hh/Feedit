package com.example.feedit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;

import android.app.AlertDialog;

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
        areYouSureDialog();
    }

    public void areYouSureDialog(){
        Button delete_post_button;
        ImageView close_dialog;
        final Dialog delete_post_dialog;

        final AlertDialog.Builder mbuilder = new AlertDialog.Builder(show_full_post.this);
        View mview = getLayoutInflater().inflate(R.layout.delete_post_dialog, null);

        mbuilder.setView(mview);
        delete_post_dialog = mbuilder.create();
        delete_post_dialog.show();

        delete_post_button = (Button) mview.findViewById(R.id.delete_post_button);
        delete_post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_doc_ref.delete();
                Intent my_intent = new Intent(getBaseContext(), Feed.class);
                startActivity(my_intent);

            }
        });

        close_dialog = (ImageView) mview.findViewById(R.id.close_dialog_imag);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_post_dialog.dismiss();
            }
        });


    }

    public void goToFeed(View view){
        Intent my_intent = new Intent(getBaseContext(), Feed.class);
        startActivity(my_intent);
    }
}
