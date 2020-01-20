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
    private String title;
    private String team;
    private Date time_stamp;
    private final Calendar c = Calendar.getInstance();
    private String author;
    private String post_text;
    private String project;
    private SimpleDateFormat timestamp_format = new SimpleDateFormat("HH:mm dd/mm/yyyy", Locale.getDefault());

    public Post(String title, String team, String post_text, String project) {
        this.title = title;
        this.team = team;
        if((this.author = FirebaseAuth.getInstance().getCurrentUser().getEmail()) == null)
            this.author = "Anonymous";
        else
            this.author = this.author.substring(0, this.author.indexOf('@'));
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

    public String getTime_stampAsString() { return timestamp_format.format(time_stamp);}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
