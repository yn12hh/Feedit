package com.example.feedit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.SetOptions;

import com.google.firebase.firestore.SetOptions;
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
    private Boolean query_chnged_flag;
    private CollectionReference projects_names_collection;

    private FeedItFBInterface(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        entries_collection = db.collection("entries");
        projects_names_collection = db.collection("projects_names");
        feed_query = entries_collection.orderBy("timestamp", Query.Direction.DESCENDING);
        query_chnged_flag = false;

    }

    public static FeedItFBInterface getInstance() {
        if(instance == null) {
            instance = new FeedItFBInterface();
        }
        return instance;
    }


public boolean uploadPost(final Post post) {

    Map<String, Object> uploadable_post = new HashMap<>();
    uploadable_post.put("title", post.getTitle());
    uploadable_post.put("author", post.getAuthor());
    uploadable_post.put("timestamp", post.getTimeStamp());
    uploadable_post.put("post_text", post.getPost_text());
    uploadable_post.put("team", post.getTeam());
    uploadable_post.put("project", post.getProject());


    Map<String, Object> proj_changed = new HashMap<>();
    proj_changed.put("project_name", post.getProject());
    proj_changed.put("last_changed", post.getTimeStamp());
    projects_names_collection.document(post.getProject()).set(proj_changed, SetOptions.merge());

    return entries_collection.add(uploadable_post).isSuccessful();
}

    public void setUpRecyclerViewForFeed(RecyclerView view){
        feed_rv = view;
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>().setQuery(feed_query, Post.class).build();
        feed_adapter =  new FeedAdapter(options);
        feed_rv.setAdapter(feed_adapter);

        //I need to change here something --Ari
        feed_adapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(feed_rv.getContext(), "Position: " +feed_adapter.getItem(position).getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
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
        query_chnged_flag = true;
    }

    public void startFeedListening() {
        if(query_chnged_flag) {
            feed_adapter.stopListening();
            setUpRecyclerViewForFeed(feed_rv);
            query_chnged_flag = false;
        }
        feed_adapter.startListening();
    }

    public void stopFeedListening() {
        feed_adapter.stopListening();
    }

    public void feedRefresh() {
        feed_adapter.stopListening();
        feed_adapter.startListening();
    }

    //changed FeedAdapter from private to public static
    public static class FeedAdapter extends FirestoreRecyclerAdapter<Post, FeedAdapter.FeedPostHolder> {

        private OnItemClickListener click_listener;

        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            click_listener = listener;
        }

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
            return new FeedPostHolder(view, click_listener);
        }

        //added public static here
        public static class FeedPostHolder extends RecyclerView.ViewHolder {
            TextView title, team, name, project, text, time_stamp;
            public FeedPostHolder(@NonNull View itemView, final OnItemClickListener listener) {
                super(itemView);
                title = itemView.findViewById(R.id.post_title);
                team = itemView.findViewById(R.id.post_team);
                name = itemView.findViewById(R.id.post_author);
                project = itemView.findViewById(R.id.post_project);
                text = itemView.findViewById(R.id.post_text);
                time_stamp = itemView.findViewById(R.id.post_time_stamp);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listener != null) {
                            int position = getAdapterPosition();
                            if(position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(position);
                            }
                        }
                    }
                });
            }
        }

    }

}

