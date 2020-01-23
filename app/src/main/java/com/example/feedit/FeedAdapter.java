package com.example.feedit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class FeedAdapter extends FirestoreRecyclerAdapter<Post, FeedAdapter.FeedPostHolder> {

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
        holder.hour.setText(String.valueOf(model.getTime_stamp()));
    }

    @NonNull
    @Override
    public FeedPostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_post, parent, false);
        return new FeedPostHolder(view);
    }

    class FeedPostHolder extends RecyclerView.ViewHolder {
        TextView title, team, name, project, text, hour;
        public FeedPostHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.post_title);
            team = itemView.findViewById(R.id.post_team);
            name = itemView.findViewById(R.id.post_author);
            project = itemView.findViewById(R.id.post_project);
            text = itemView.findViewById(R.id.post_text);
            hour = itemView.findViewById(R.id.post_time_stamp);

        }
    }

}
