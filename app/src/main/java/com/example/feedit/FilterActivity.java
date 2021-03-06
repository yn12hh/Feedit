package com.example.feedit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class FilterActivity extends AppCompatActivity {

    private List<String> projects_list = Arrays.asList();
    private List<String> teams_list = Arrays.asList();
    private List<String> projects_list_first_stage= new ArrayList<>();
    private List<String> teams_list_first_stage = new ArrayList<>();
    private FeedItFBInterface fb_interface;
    private CheckBox main_office_cb, production_cb, pr_cb, executive_cb, rd_cb, marketing_cb;
    private Switch all_teams_sw, all_projects_sw;
    private String team_string;
    private RecyclerView project_recycler;
    private ProgressBar proj_prog_bar;

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
        all_projects_sw = findViewById(R.id.all_projects_switch);

        proj_prog_bar = findViewById(R.id.project_progress_bar);

        fb_interface = FeedItFBInterface.getInstance();

        project_recycler = findViewById(R.id.project_filter_recycler);
        project_recycler.setHasFixedSize(true);
        project_recycler.setLayoutManager(new LinearLayoutManager(this));
        fb_interface.setUpRecyclerViewForProjectFilter(project_recycler, all_projects_sw);
        fb_interface.startProjectFilter();

        all_projects_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                fb_interface.changeProjColor(isChecked);
            }
        });

        all_teams_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    unceckAllTeams();
                    colorAllTeamsGrey();
                }
                else {
                    colorAllTeamsBlack();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        proj_prog_bar.setVisibility(View.VISIBLE);
        fb_interface.startProjectFilter();
        proj_prog_bar.setVisibility(View.INVISIBLE);
    }


    public void saveUpdatedQuery(){

        teams_list=teams_list_first_stage; /*this is essential because the setQueryForFeed can get only lists with definite size.*/
        fb_interface.setQueryForFeed(projects_list, teams_list);
    }

    public void onClickedSave(View view) {

        projects_list = fb_interface.sendCheckedInfo();
        if(projects_list.size()>1 && teams_list_first_stage.size()>1)
            Toast.makeText(getApplicationContext(), "You can't choose more than 1 filter query in both projects and teams, please try again", Toast.LENGTH_LONG).show();
        else {
            saveUpdatedQuery();
            Intent my_intent = new Intent(getBaseContext(), FeedActivity.class);
            startActivity(my_intent);
        }

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
                    teams_list_first_stage.add(team_string);
                }
                else teams_list_first_stage.remove(team_string);
                break;

            case R.id.production_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.production_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                else teams_list_first_stage.remove(team_string);
                break;

            case R.id.pr_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.pr_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                else teams_list_first_stage.remove(team_string);
                break;

            case R.id.executive_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.executive_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                else teams_list_first_stage.remove(team_string);
                break;

            case R.id.r_and_d_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.r_and_d_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                else teams_list_first_stage.remove(team_string);
                break;

            case R.id.marketing_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.marketing_team);
                    team_string = checkbox.getText().toString();
                    teams_list_first_stage.add(team_string);
                }
                else teams_list_first_stage.remove(team_string);
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
        teams_list_first_stage.clear();
    }
    public void allProjects(View view) {
        if (all_projects_sw.isChecked())
        {
            unceckAllProjects();
            colorAllProjectsGrey();
        }
        else
            colorAllProjectsBlack();
    }


    public void colorAllProjectsGrey()
    {
        main_office_cb.setTextColor(Color.GRAY);
        production_cb.setTextColor(Color.GRAY);
        pr_cb.setTextColor(Color.GRAY);
        rd_cb.setTextColor(Color.GRAY);
        marketing_cb.setTextColor(Color.GRAY);
        executive_cb.setTextColor(Color.GRAY);
    }

    public void colorAllProjectsBlack()
    {
        main_office_cb.setTextColor(Color.BLACK);
        production_cb.setTextColor(Color.BLACK);
        pr_cb.setTextColor(Color.BLACK);
        rd_cb.setTextColor(Color.BLACK);
        marketing_cb.setTextColor(Color.BLACK);
        executive_cb.setTextColor(Color.BLACK);
    }

    public void unceckAllProjects()
    {
        main_office_cb.setChecked(false);
        production_cb.setChecked(false);
        pr_cb.setChecked(false);
        rd_cb.setChecked(false);
        marketing_cb.setChecked(false);
        executive_cb.setChecked(false);
    }



    public void clearTeams(View view) {
        unceckAllTeams();
    }
}
