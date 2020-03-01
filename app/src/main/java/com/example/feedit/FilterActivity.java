package com.example.feedit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FilterActivity extends AppCompatActivity {

    private ImageView save_button;
    private List<String> projects_list = Arrays.asList();
    private List<String> teams_list = Arrays.asList();
    private List<String> projects_list_first_stage= new ArrayList<>();
    private List<String> teams_list_first_stage = new ArrayList<>();
    private FeedItFBInterface fb_interface;
    private String project_string, team_string;
    private RecyclerView project_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        findViewById(R.id.sign_out_button);
        fb_interface = FeedItFBInterface.getInstance();
        save_button = (ImageView) findViewById(R.id.save);
        save_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                saveUpdatedQuery();
                Intent my_intent = new Intent(getBaseContext(), Feed.class);
                startActivity(my_intent);

            }


        });

        project_recycler = findViewById(R.id.project_filter_recycler);
        project_recycler.setHasFixedSize(true);
        project_recycler.setLayoutManager(new LinearLayoutManager(this));
        fb_interface.setUpRecyclerViewForProjectFilter(project_recycler);

    }

    @Override
    protected void onStart() {
        super.onStart();
        fb_interface.startProjectFilter();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    public void saveUpdatedQuery(){

        teams_list=teams_list_first_stage;
        projects_list=projects_list_first_stage;

        fb_interface.setQueryForFeed(projects_list, teams_list);
    }


    public void onCheckboxClicked(View view) {

        CheckBox checkbox;
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.main_office_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.main_office_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                break;

            case R.id.production_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.production_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                break;

            case R.id.pr_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.pr_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                break;

            case R.id.executive_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.executive_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                break;

            case R.id.r_and_d_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.r_and_d_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                break;

            case R.id.marketing_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.marketing_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                break;

        }
    }

    public void signOut(View view) {
        if (view.getId()==R.id.sign_out_button)
        {
            FirebaseAuth.getInstance().signOut();
            Intent myIntent = new Intent(getBaseContext(), SignInActivity.class);
            startActivity(myIntent);
        }

    }
}
