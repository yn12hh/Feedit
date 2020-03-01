package com.example.feedit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    private String main_office_string = "", production_string = "", pr_string = "", executive_string = "", r_and_d_string = "", marketing_string = "";
    private String project1_string = "", project2_string = "", project3_string = "", project4_string = "", project5_string = "", project6_string = "", project7_string = "", project8_string = "", project9_string = "", project10_string = "", project11_string = "", project12_string = "", project13_string = "", project14_string = "", project15_string = "", project16_string = "", project17_string = "", project18_string = "", project19_string = "", project20_string = "";
    private CheckBox main_office_cb, production_cb, pr_cb, executive_cb, rd_cb, marketing_cb;
    private Switch all_teams_sw, all_projects_sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        findViewById(R.id.sign_out_button);
        main_office_string = ""; production_string = ""; pr_string = ""; executive_string = ""; r_and_d_string = ""; marketing_string = "";
        project1_string = ""; project2_string = ""; project3_string = ""; project4_string = ""; project5_string = ""; project6_string = ""; project7_string = ""; project8_string = ""; project9_string = ""; project10_string = "";  project11_string = ""; project12_string = ""; project13_string = ""; project14_string = ""; project15_string = ""; project16_string = ""; project17_string = ""; project18_string = ""; project19_string = ""; project20_string = "";
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

        switch(view.getId()) {
            case R.id.main_office_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.main_office_team);
                    main_office_string = checkbox.getText().toString();
                    teams_list_first_stage.add(main_office_string);
                }
                break;

            case R.id.production_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.production_team);
                    production_string = checkbox.getText().toString();
                    teams_list_first_stage.add(production_string);
                }
                break;

            case R.id.pr_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.pr_team);
                    pr_string = checkbox.getText().toString();
                    teams_list_first_stage.add(pr_string);
                }
                break;

            case R.id.executive_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.executive_team);
                    executive_string = checkbox.getText().toString();
                    teams_list_first_stage.add(executive_string);
                }
                break;

            case R.id.r_and_d_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.r_and_d_team);
                    r_and_d_string = checkbox.getText().toString();
                    teams_list_first_stage.add(r_and_d_string);
                }
                break;

            case R.id.marketing_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.marketing_team);
                    marketing_string = checkbox.getText().toString();
                    teams_list_first_stage.add(marketing_string);
                }
                break;
        }

    }

    public void onCheckboxClickedProjects(View view) {

        CheckBox checkbox;
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.project_1:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_1);
                    project1_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project1_string);
                }
                break;

            case R.id.project_2:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_2);
                    project2_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project2_string);
                }
                break;

            case R.id.project_3:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_3);
                    project3_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project3_string);
                }
                break;

            case R.id.project_4:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_4);
                    project4_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project4_string);
                }
                break;

            case R.id.project_5:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_5);
                    project5_string = checkbox.getText().toString();

                    projects_list_first_stage.add(project5_string);
                }
                break;

            case R.id.project_6:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_6);
                    project6_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project6_string);
                }
                break;
            case R.id.project_7:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_7);
                    project7_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project7_string);
                }
                break;

            case R.id.project_8:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_8);
                    project8_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project8_string);
                }
                break;

            case R.id.project_9:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_9);
                    project9_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project9_string);
                }
                break;

            case R.id.project_10:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_10);
                    project10_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project10_string);
                }
                break;

            case R.id.project_11:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_11);
                    project11_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project11_string);
                }
                break;

            case R.id.project_12:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_12);
                    project12_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project12_string);
                }
                break;

            case R.id.project_13:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_13);
                    project13_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project13_string);
                }
                break;

            case R.id.project_14:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_14);
                    project14_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project14_string);
                }
                break;

            case R.id.project_15:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_15);
                    project15_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project15_string);
                }
                break;

            case R.id.project_16:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_16);
                    project16_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project16_string);
                }
                break;

            case R.id.project_17:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_17);
                    project17_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project17_string);
                }
                break;

            case R.id.project_18:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_18);
                    project18_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project18_string);
                }
                break;

            case R.id.project_19:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_19);
                    project19_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project19_string);
                }
                break;

            case R.id.project_20:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.project_20);
                    project20_string = checkbox.getText().toString();
                    projects_list_first_stage.add(project20_string);
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
