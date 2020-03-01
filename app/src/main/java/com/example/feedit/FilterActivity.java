package com.example.feedit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FilterActivity extends AppCompatActivity {

    private ImageView save_button;
    private List<String> projects_list = Arrays.asList();
    private List<String> teams_list = Arrays.asList();
    private List<String> projects_list_first_stage= new ArrayList<>();
    private List<String> teams_list_first_stage = new ArrayList<>();
    private FeedItFBInterface fb_interface;
    private CheckBox main_office_cb, production_cb, pr_cb, executive_cb, rd_cb, marketing_cb;
    private Switch all_teams_sw, all_projects_sw;
    private String project_string, team_string;
    private RecyclerView project_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        findViewById(R.id.sign_out_button);
        main_office_cb = findViewById(R.id.main_office_team);
        production_cb = findViewById(R.id.production_team);
        pr_cb = findViewById(R.id.pr_team);
        rd_cb = findViewById(R.id.r_and_d_team);
        executive_cb = findViewById(R.id.executive_team);
        marketing_cb = findViewById(R.id.marketing_team);
        all_projects_sw = findViewById(R.id.all_projects_switch);
        all_teams_sw = findViewById(R.id.all_teams_switch);

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


    public void onCheckboxClickedTeams(View view) {
        colorAllTeamsBlack();
        all_teams_sw.setChecked(false);
        CheckBox checkbox;
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.main_office_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.main_office_team);
                    team_string = checkbox.getText().toString();
                    if(teams_list_first_stage.size()>0 && projects_list_first_stage.size()>0) {
                        checkbox.setChecked(false);
                        Toast.makeText(getApplicationContext(), "The projects list is bigger than 1, you can check up to one team", Toast.LENGTH_LONG).show();
                    }
                    else  teams_list_first_stage.add(team_string);
                }
                break;

            case R.id.production_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.production_team);
                    team_string = checkbox.getText().toString();
                    if(teams_list_first_stage.size()>0 && projects_list_first_stage.size()>0) {
                        checkbox.setChecked(false);
                        Toast.makeText(getApplicationContext(), "The projects list is bigger than 1, you can check up to one team", Toast.LENGTH_LONG).show();
                    }
                    else  teams_list_first_stage.add(team_string);
                }
                break;

            case R.id.pr_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.pr_team);
                    team_string = checkbox.getText().toString();
                    if(teams_list_first_stage.size()>0 && projects_list_first_stage.size()>0) {
                        checkbox.setChecked(false);
                        Toast.makeText(getApplicationContext(), "The projects list is bigger than 1, you can check up to one team", Toast.LENGTH_LONG).show();
                    }
                    else  teams_list_first_stage.add(team_string);
                }
                break;

            case R.id.executive_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.executive_team);
                    team_string = checkbox.getText().toString();
                    if(teams_list_first_stage.size()>0 && projects_list_first_stage.size()>0) {
                        checkbox.setChecked(false);
                        Toast.makeText(getApplicationContext(), "The projects list is bigger than 1, you can check up to one team", Toast.LENGTH_LONG).show();
                    }
                    else  teams_list_first_stage.add(team_string);
                }
                break;

            case R.id.r_and_d_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.r_and_d_team);
                    team_string = checkbox.getText().toString();
                    if(teams_list_first_stage.size()>0 && projects_list_first_stage.size()>0) {
                        checkbox.setChecked(false);
                        Toast.makeText(getApplicationContext(), "The projects list is bigger than 1, you can check up to one team", Toast.LENGTH_LONG).show();
                    }
                    else  teams_list_first_stage.add(team_string);
                }
                break;

            case R.id.marketing_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.marketing_team);
                    team_string = checkbox.getText().toString();
                    if(teams_list_first_stage.size()>0 && projects_list_first_stage.size()>0) {
                        checkbox.setChecked(false);
                        Toast.makeText(getApplicationContext(), "The projects list is bigger than 1, you can check up to one team", Toast.LENGTH_LONG).show();
                    }
                    else  teams_list_first_stage.add(team_string);
                }
                break;
        }

    }

    public void onCheckboxClickedProjects(View view) {


        CheckBox checkbox;
        boolean checked = ((CheckBox) view).isChecked();
//
//                CheckBox checkbox;
//        boolean checked = ((CheckBox) view).isChecked();
//
//        switch(view.getId()) {
//            case R.id.project_1:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_1);
//                    project1_string = checkbox.getText().toString();
//                    projects_list_first_stage.add(project1_string);
//                }
//                break;
//
//            case R.id.project_2:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_2);
//                    project2_string = checkbox.getText().toString();
//                    projects_list_first_stage.add(project2_string);
//                }
//                break;
//
//            case R.id.project_3:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_3);
//                    project3_string = checkbox.getText().toString();
//                    projects_list_first_stage.add(project3_string);
//                }
//                break;
//
//        }
    }

    public void signOut(View view) {
        if (view.getId()==R.id.sign_out_button)
        {
            FirebaseAuth.getInstance().signOut();
            Intent myIntent = new Intent(getBaseContext(), SignInActivity.class);
            startActivity(myIntent);
        }

    }

    public void allTeams(View view) {
        if (all_teams_sw.isChecked())
        {
            unceckAllTeams();
            colorAllTeamsGrey();
        }
        else
            colorAllTeamsBlack();
    }

    public void colorAllTeamsGrey()
    {
        main_office_cb.setTextColor(Color.GRAY);
        production_cb.setTextColor(Color.GRAY);
        pr_cb.setTextColor(Color.GRAY);
        rd_cb.setTextColor(Color.GRAY);
        marketing_cb.setTextColor(Color.GRAY);
        executive_cb.setTextColor(Color.GRAY);
    }

    public void colorAllTeamsBlack()
    {
        main_office_cb.setTextColor(Color.BLACK);
        production_cb.setTextColor(Color.BLACK);
        pr_cb.setTextColor(Color.BLACK);
        rd_cb.setTextColor(Color.BLACK);
        marketing_cb.setTextColor(Color.BLACK);
        executive_cb.setTextColor(Color.BLACK);
    }

    public void unceckAllTeams()
    {
        main_office_cb.setChecked(false);
        production_cb.setChecked(false);
        pr_cb.setChecked(false);
        rd_cb.setChecked(false);
        marketing_cb.setChecked(false);
        executive_cb.setChecked(false);
    }
    public void allProjects(View view) {
    }


    public void clearTeams(View view) {
        unceckAllTeams();
    }
}
