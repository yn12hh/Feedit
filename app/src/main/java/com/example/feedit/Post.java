package com.example.feedit;


import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Post {
    private String title;
    private String team;
    private Date time_stamp =  new Date();
    private final Calendar c = Calendar.getInstance();
    private String poster_name;
    private String post_text;
    private String project;

    public Post(String title, String team, Date time_stamp, String poster_name, String post_text, String project) {
        this.title = title;
        this.team = team;
        this.time_stamp = time_stamp;
        this.poster_name = poster_name;
        this.post_text = post_text;
        this.project = project;
    }

    //getters
    public String getTitle(){
       return title;
    }

    public String getTeam(){
        return team;
    }

    public String getPoster_name(){
        return poster_name;
    }

    public String getPost_text(){
        return post_text;
    }

    public String getProject(){
        return project;
    }

    public Date getTime_stamp() { return time_stamp; }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
    }

    public void setPoster_name(String poster_name) {
        this.poster_name = poster_name;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
