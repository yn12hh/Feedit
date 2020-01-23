package com.example.feedit;


import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Post {
    private final String title;
    private final String team;
    private final Date time_stamp;
    private final Calendar c = Calendar.getInstance();
    private String author;
    private final String post_text;
    private final String project;
    private SimpleDateFormat timestamp_format = new SimpleDateFormat("HH:mm dd/mm/yyyy", Locale.getDefault());

    public Post(String title, String team, String post_text, String project) {
        this.title = title;
        this.team = team;
        if((author = FirebaseAuth.getInstance().getCurrentUser().getEmail()) == null)
            author = "Anonymous";
        else
            author = author.substring(0, author.indexOf('@'));
        this.post_text = post_text;
        this.project = project;
        this.time_stamp = new Date();
    }

    //getters
    public String getTitle(){
       return title;
    }

    public String getTeam(){
        return team;
    }

    public String getAuthor(){
        return author;
    }

    public String getPost_text(){
        return post_text;
    }

    public String getProject(){
        return project;
    }

    public Date getTime_stamp() { return time_stamp; }

}
