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

public class Feed extends AppCompatActivity  {

    private ImageView add_post_button;
    private Dialog  new_post_dialog;
    private ImageView close_dialog;
    private Button post_button_clicked;
    private FeedItFBInterface fb_interface;
    private EditText title_et, projects_et, teams_et, post_content_et; //et stands for Edit Text, written in acronym to save name length
    private TextView title_tv,teams_tv, projects_tv, post_content_tv, author_tv; //tv stands for Text View, written in acronym to save name length

    private SwipeRefreshLayout feed_swipe_refresh_layout;

    private FirebaseFirestore firestore_database = FirebaseFirestore.getInstance();
    private CollectionReference feed_it_posts_ref = firestore_database.collection("entries");

    private RecyclerView feed_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        fb_interface = FeedItFBInterface.getInstance();
        feed_recycler = findViewById(R.id.feed_recycler_view);
        feed_recycler.setHasFixedSize(true);
        feed_recycler.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.options_button);

        fb_interface.setUpRecyclerViewForFeed(feed_recycler);

        add_post_button = (ImageView) findViewById(R.id.add_icon);
        add_post_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent my_intent = new Intent(getBaseContext(), newPostActivity.class);
                startActivity(my_intent);
            }


        });



        feed_swipe_refresh_layout = findViewById(R.id.feed_swipe_refresh);
        feed_swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fb_interface.feedRefresh();
                feed_swipe_refresh_layout.setRefreshing(false);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        fb_interface.startFeedListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fb_interface.stopFeedListening();
    }






}
