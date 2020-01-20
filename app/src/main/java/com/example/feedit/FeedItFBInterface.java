package com.example.feedit;


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
        entries_collection = db.collection("sampleData");
    }

    public boolean uploadPost(Post post) {

        Map<String, Object> uploadable_post = new HashMap<>();
        uploadable_post.put("title", post.getTitle());
        uploadable_post.put("author", post.getAuthor());
        uploadable_post.put("timestamp", post.getTime_stamp());
        uploadable_post.put("contants", post.getPost_text());
        uploadable_post.put("team", post.getTeam());
        uploadable_post.put("project", post.getProject());

        return entries_collection.add(uploadable_post).isSuccessful();
    }


}
