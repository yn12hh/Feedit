package com.example.feedit;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FeedActivity extends AppCompatActivity  {

    private ImageView add_post_button;
    private FeedItFBInterface fb_interface;
    private SwipeRefreshLayout feed_swipe_refresh_layout;


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

                Intent my_intent = new Intent(getBaseContext(), NewPostActivity.class);
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

        public void options(View view) {
        if (view.getId()==R.id.options_button)
        {
            Intent myIntent = new Intent(getBaseContext(), FilterActivity.class);
            startActivity(myIntent);
        }

    }





}
