package com.example.feedit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
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
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Feed extends AppCompatActivity  {

    private ImageView add_post_button;
    private Dialog  new_post_dialog;
    private ImageView close_dialog;
    private Button post_button_clicked;
    private FeedItFBInterface fb_interface;
    private EditText title_et, projects_et, teams_et, post_content_et; //et stands for Edit Text, written in acronym to save name length
    private TextView title_tv,teams_tv, projects_tv, post_content_tv, author_tv; //tv stands for Text View, written in acronym to save name length


    private FirebaseFirestore firestore_database = FirebaseFirestore.getInstance();
    private CollectionReference feed_it_posts_ref = firestore_database.collection("entries");

    private RecyclerView feed_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        fb_interface = new FeedItFBInterface();

        feed_recycler = findViewById(R.id.feed_recycler_view);
        feed_recycler.setHasFixedSize(true);
        feed_recycler.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.sign_out_button);
        fb_interface.setUpRecyclerView(feed_recycler, null);

        add_post_button = (ImageView) findViewById(R.id.add_icon);
        add_post_ button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                showAddPostDialog();
            }


        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        fb_interface.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fb_interface.stopListening();
    }



   public void showAddPostDialog(){

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
        fb_interface.uploadPost(new_post);
        new_post_dialog.dismiss();
    }


    public void signout(View view) {
        if (view.getId()==R.id.sign_out_button)
        {
            FirebaseAuth.getInstance().signOut();
            Intent myIntent = new Intent(getBaseContext(), SignInActivity.class);
            startActivity(myIntent);
        }

    }
}
