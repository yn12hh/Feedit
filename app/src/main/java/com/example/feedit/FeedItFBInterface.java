package com.example.feedit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedItFBInterface {
    private static FeedItFBInterface instance = null;

    private CollectionReference entries_collection;
    private FeedAdapter feed_adapter;
    private RecyclerView feed_rv;
    private Query feed_query;

    private FeedItFBInterface(){
        entries_collection = FirebaseFirestore.getInstance().collection("entries");
        feed_query = entries_collection.orderBy("timestamp", Query.Direction.DESCENDING);
    }

    public static FeedItFBInterface getInstance() {
        if(instance == null) {
            instance = new FeedItFBInterface();
        }
        return instance;
    }


    public boolean uploadPost(Post post) {

        Map<String, Object> uploadable_post = new HashMap<>();
        uploadable_post.put("title", post.getTitle());
        uploadable_post.put("author", post.getAuthor());
        uploadable_post.put("timestamp", post.getTimeStamp());
        uploadable_post.put("post_text", post.getPost_text());
        uploadable_post.put("team", post.getTeam());
        uploadable_post.put("project", post.getProject());

        return entries_collection.add(uploadable_post).isSuccessful();
    }

    public void setUpRecyclerViewForFeed(RecyclerView view){
        feed_rv = view;
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>().setQuery(feed_query, Post.class).build();
        feed_adapter =  new FeedAdapter(options);
        view.setAdapter(feed_adapter);
    }

    public void setQueryForFeed(List<String> projects_for_query, List<String> teams_for_query) {
        if(projects_for_query.isEmpty() && teams_for_query.isEmpty()) {
            feed_query = entries_collection.orderBy("timestamp", Query.Direction.DESCENDING);
        } else if (projects_for_query.isEmpty()) {
            feed_query = entries_collection.whereIn("team", teams_for_query).orderBy("timestamp", Query.Direction.DESCENDING);
        } else if (teams_for_query.isEmpty()) {
            feed_query = entries_collection.whereIn("project", projects_for_query).orderBy("timestamp", Query.Direction.DESCENDING);
        } else if(projects_for_query.size() > 1){
            feed_query = entries_collection.whereIn("project", projects_for_query).whereEqualTo("team", teams_for_query.get(0)).orderBy("timestamp", Query.Direction.DESCENDING);
        } else {
            feed_query = entries_collection.whereIn("team", teams_for_query).whereEqualTo("project", projects_for_query.get(0)).orderBy("timestamp", Query.Direction.DESCENDING);
        }
    }

    public void startFeedListening() {
        feed_adapter.startListening();
    }

    public void stopFeedListening() {
        feed_adapter.stopListening();
    }

    public void feedRefresh() {
        feed_adapter.stopListening();
        feed_adapter.startListening();
    }

    private class FeedAdapter extends FirestoreRecyclerAdapter<Post, FeedAdapter.FeedPostHolder> {

        public FeedAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull FeedPostHolder holder, int position, @NonNull Post model) {
            holder.title.setText(model.getTitle());
            holder.team.setText(model.getTeam());
            holder.name.setText(model.getAuthor());
            holder.project.setText(model.getProject());
            holder.text.setText(model.getPost_text());
            holder.time_stamp.setText(String.valueOf(model.getTimeStampString()));
        }

        @NonNull
        @Override
        public FeedPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_post, parent, false);
            return new FeedPostHolder(view);
        }

        class FeedPostHolder extends RecyclerView.ViewHolder {
            TextView title, team, name, project, text, time_stamp;
            public FeedPostHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.post_title);
                team = itemView.findViewById(R.id.post_team);
                name = itemView.findViewById(R.id.post_author);
                project = itemView.findViewById(R.id.post_project);
                text = itemView.findViewById(R.id.post_text);
                time_stamp = itemView.findViewById(R.id.post_time_stamp);

            }
        }

    }

}

