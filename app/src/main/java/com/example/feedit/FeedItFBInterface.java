package com.example.feedit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.firebase.firestore.SetOptions;

public class FeedItFBInterface {
    private static FeedItFBInterface instance = null;

    private CollectionReference entries_collection;
    private FeedAdapter feed_adapter;
    private RecyclerView feed_rv;
    private Query feed_query;
    private Boolean query_changed_flag;
    private CollectionReference projects_names_collection;

    private final String project_names[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
    private String actual_project_names[];
    private ProjRecyclerAdapter proj_recycler_adapter;
    private RecyclerView project_rv;

    private FeedItFBInterface() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        entries_collection = db.collection("entries");
        projects_names_collection = db.collection("projects_names");
        feed_query = entries_collection.orderBy("timestamp", Query.Direction.DESCENDING);
        query_changed_flag = false;
        updateProjectsNames();
    }

    public static FeedItFBInterface getInstance() {
        if (instance == null) {
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

        updateProjectTime(post.getProject());

        return entries_collection.add(uploadable_post).isSuccessful();
    }

    public void setUpRecyclerViewForFeed(RecyclerView view) {
        feed_rv = view;
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>().setQuery(feed_query, Post.class).build();
        feed_adapter = new FeedAdapter(options);
        feed_rv.setAdapter(feed_adapter);

        feed_adapter.setOnItemClickListener(new FeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(feed_rv.getContext(), show_full_post.class);
                intent.putExtra("post_title", feed_adapter.getItem(position).getTitle());
                intent.putExtra("post_time_stamp", feed_adapter.getItem(position).getTimeStamp());
                intent.putExtra("post_team", feed_adapter.getItem(position).getTeam());
                intent.putExtra("post_project", feed_adapter.getItem(position).getProject());
                intent.putExtra("post_text", feed_adapter.getItem(position).getPost_text());
                intent.putExtra("post_author", feed_adapter.getItem(position).getAuthor());
                intent.putExtra("postion", position);
                feed_rv.getContext().startActivity(intent);
            }
        });
    }

    public void setUpRecyclerViewForProjectFilter(RecyclerView view) {
        project_rv = view;
    }

    public void startProjectFilter() {
        updateProjectsNames();
        proj_recycler_adapter = new ProjRecyclerAdapter(project_names, project_rv.getContext());
        project_rv.setAdapter(proj_recycler_adapter);
    }

    public void setQueryForFeed(List<String> projects_for_query, List<String> teams_for_query) {
        if (projects_for_query.isEmpty() && teams_for_query.isEmpty()) {
            feed_query = entries_collection.orderBy("timestamp", Query.Direction.DESCENDING);
        } else if (projects_for_query.isEmpty()) {
            feed_query = entries_collection.whereIn("team", teams_for_query).orderBy("timestamp", Query.Direction.DESCENDING);
        } else if (teams_for_query.isEmpty()) {
            feed_query = entries_collection.whereIn("project", projects_for_query).orderBy("timestamp", Query.Direction.DESCENDING);
        } else if (projects_for_query.size() > 1) {
            feed_query = entries_collection.whereIn("project", projects_for_query).whereEqualTo("team", teams_for_query.get(0)).orderBy("timestamp", Query.Direction.DESCENDING);
        } else {
            feed_query = entries_collection.whereIn("team", teams_for_query).whereEqualTo("project", projects_for_query.get(0)).orderBy("timestamp", Query.Direction.DESCENDING);
        }
        query_changed_flag = true;
    }

    public void startFeedListening() {
        if (query_changed_flag) {
            feed_adapter.stopListening();
            setUpRecyclerViewForFeed(feed_rv);
            query_changed_flag = false;
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

    public DocumentReference getPostDocRef(int position) {
        return feed_adapter.getSnapshots().getSnapshot(position).getReference();
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
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(position);
                            }
                        }
                    }
                });
            }
        }

    }

    //I've made this static for OnItemClickListener --Ari
    public static class ProjRecyclerAdapter extends RecyclerView.Adapter<ProjRecyclerAdapter.ProjectViewHolder> {

        private String project_names[];
        private Context context;
        private List<String> checked_projects_first_stage = new ArrayList<>();


        public ProjRecyclerAdapter(String[] project_names, Context context) {
            this.project_names = project_names;
            this.context = context;
        }

        @NonNull
        @Override
        public ProjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.project_filter_checkbox, parent, false);
            return new ProjectViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProjectViewHolder holder, int position) {
            holder.name.setText(project_names[position]);
            if (project_names[position].equals("")) {
                holder.name.setVisibility(View.GONE);
            }
            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(View v, int position) {
                    CheckBox checkbox = (CheckBox) v;

                    if (checkbox.isChecked()) {
                        {
                            checked_projects_first_stage.add(project_names[position]);
                        }
                    } else if (!checkbox.isChecked()) {
                        checked_projects_first_stage.remove(project_names[position]);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return project_names.length;
        }

        public class ProjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            CheckBox name;
            ItemClickListener item_click_listener;

            public ProjectViewHolder(@NonNull View itemView) {
                super(itemView);
                name = (CheckBox) itemView.findViewById(R.id.project_name);

                name.setOnClickListener(this);
            }

            public void setItemClickListener(ItemClickListener ic) {
                this.item_click_listener = ic;
            }

            @Override
            public void onClick(View v) {
                this.item_click_listener.onItemClick(v, getLayoutPosition());
            }
        }

        //this may need to be outside of the adapter class
        public interface ItemClickListener {
            void onItemClick(View v, int position);
        }
    }

    public void updateProjectsNames() {
        projects_names_collection.orderBy("last_changed", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    int i = 0;
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String curr_proj_name = document.getString("project_name");
                        if (!Arrays.asList(project_names).contains(curr_proj_name)) {
                            project_names[i++] = curr_proj_name;
                            if (i == project_names.length) break;
                        }
                    }
                    Arrays.sort(project_names);
                }
            }
        });
    }

    private void updateProjectTime(String name) {
        Map<String, Object> proj_changed = new HashMap<>();
        proj_changed.put("project_name", name);
        proj_changed.put("last_changed", new Date());
        projects_names_collection.document(name).set(proj_changed, SetOptions.merge());
    }

    public void newProjectName(String name) {
        name = name.toLowerCase();
        updateProjectTime(name);
        updateProjectsNames();
    }

    public String[] getProject_names() {
        int counter = 0;
        for (int i = 0; i < project_names.length; i++) {
            if (!project_names[i].equals("")) {
                counter++;
            }
        }
        String[] ret_arr = new String[counter + 1];
        ret_arr[0] = "Tap to choose";
        counter = 1;
        for (int i = 0; i < project_names.length; i++) {
            if (!project_names[i].equals("")) {
                ret_arr[counter++] = project_names[i];
            }
        }
        return ret_arr;
    }

    public class ProjectCheckBox {
        String name;

        public ProjectCheckBox() {
        }

        public ProjectCheckBox(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public List<String> sendCheckedInfo() {
//        StringBuffer sb = null;
//        List<String> checked_projects = Arrays.asList();
//        checked_projects = proj_recycler_adapter.checked_projects_first_stage;
        return proj_recycler_adapter.checked_projects_first_stage;
    }
}

