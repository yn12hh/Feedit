package com.example.feedit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
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
import android.widget.Toast;

import com.example.feedit.Post;
import com.example.feedit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Feed extends AppCompatActivity  {

    private ImageView add_button;
    private Dialog  new_post_dialog;
    private ImageView close_dialog;
    private Button post_button_clicked;
    private FeedItFBInterface FBI;
    private EditText title_et, projects_et, teams_et, post_content_et;
    private TextView title_tv,teams_tv, projects_tv, post_content_tv, author_tv; //time?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);



        //missing author_tv = (TextView) findViewById(), the part that creates the text


        FBI = new FeedItFBInterface();

        add_button = (ImageView) findViewById(R.id.add_icon);
        add_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                showAddPost();
            }


        });




    }



   public void showAddPost(){


       final AlertDialog.Builder mbuilder = new AlertDialog.Builder(Feed.this);
       View mview = getLayoutInflater().inflate(R.layout.add_post_dialog, null);

       mbuilder.setView(mview);
       new_post_dialog = mbuilder.create();
       new_post_dialog.show();

       title_et = (EditText) mview.findViewById(R.id.title_input);
       teams_et = (EditText) mview.findViewById(R.id.teams_name_input);
       projects_et = (EditText) mview.findViewById(R.id.projects_name_input);
       post_content_et = (EditText) mview.findViewById(R.id.posts_content_input);
       post_button_clicked = (Button) mview.findViewById(R.id.post_button);
       post_button_clicked.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String title = title_et.getText().toString();
               String teams = teams_et.getText().toString();
               String projects = projects_et.getText().toString();
               String post_content = post_content_et.getText().toString();
               if(!title.isEmpty() && !teams.isEmpty() && !projects.isEmpty() && !post_content.isEmpty()) {

                   sendDetailsToFB(title, teams, projects, post_content);
                   new_post_dialog.dismiss();
               }
                else {

                   Toast.makeText(getApplicationContext(),"You left one of the fields empty, please try again", Toast.LENGTH_LONG).show();
               }
           }
       });

       close_dialog = (ImageView) mview.findViewById(R.id.close_dialog_imag);
       close_dialog.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new_post_dialog.dismiss();
           }
       });


    }





    public void sendDetailsToFB(String title, String teams, String projects, String post_content) {
        Post new_post = new Post(title,teams,post_content, projects);
        FBI.uploadPost(new_post);
        new_post_dialog.dismiss();
    }


}
