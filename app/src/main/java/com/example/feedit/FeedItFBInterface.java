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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class FeedItFBInterface {
    private FirebaseFirestore db;
    private CollectionReference entries_collection;

    FeedItFBInterface(){
        db =  FirebaseFirestore.getInstance();
        entries_collection = db.collection("entries");
    }


    public boolean uploadPost(Post post) {

        Map<String, Object> uploadable_post = new HashMap<>();
        uploadable_post.put("title", post.getTitle());
        uploadable_post.put("author", post.getAuthor());
        uploadable_post.put("time_stamp", post.getTimeStamp());

        uploadable_post.put("post_text", post.getPost_text());
        uploadable_post.put("team", post.getTeam());
        uploadable_post.put("project", post.getProject());

        return entries_collection.add(uploadable_post).isSuccessful();
    }


}

