package com.example.feedit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

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
    private FeedItFBInterface fb_interface;
    private String main_office_string = "", production_string = "", pr_string = "", executive_string = "", r_and_d_string = "", marketing_string = "";
    private String project1 = "", project2 = "", project3 = "", project4 = "", project5 = "", project6 = "", project7 = "", project8 = "", project9 = "", project10 = "", project11 = "", project12 = "", project13 = "", project14 = "", project15 = "", project16 = "", project17 = "", project18 = "", project19 = "", project20 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        findViewById(R.id.sign_out_button);
       // main_office_string = ""; production_string = ""; pr_string = ""; executive_string = ""; r_and_d_string = ""; marketing_string = "";
        //project1 = ""; project2 = ""; project3 = ""; project4 = ""; project5 = ""; project6 = ""; project7 = ""; project8 = ""; project9 = ""; project10 = ""; project11 = ""; project12 = ""; project13 = ""; project14 = "";  project15 = ""; project16 = ""; project17 = ""; project18 = ""; project19 = ""; project20 = "";

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
        teams_list = Arrays.asList(executive_string, main_office_string, marketing_string, pr_string, production_string, r_and_d_string);
        projects_list = Arrays.asList(project1, project2, project3, project4, project5, project6, project7, project8, project9, project10);
        //, project15, project16, project17, project18, project19 ,project20);
        fb_interface.setQueryForFeed(projects_list, teams_list);
    }


    public void onCheckboxClicked(View view) {

        CheckBox checkbox;
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.main_office_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.main_office_team);
                    main_office_string = checkbox.getText().toString();
                }
                break;

            case R.id.production_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.production_team);
                    production_string = checkbox.getText().toString();
                }
                break;

            case R.id.pr_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.pr_team);
                    pr_string = checkbox.getText().toString();
                }
                break;

            case R.id.executive_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.executive_team);
                    executive_string = checkbox.getText().toString();
                }
                break;

            case R.id.r_and_d_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.r_and_d_team);
                    r_and_d_string = checkbox.getText().toString();
                }
                break;

            case R.id.marketing_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.marketing_team);
                    marketing_string = checkbox.getText().toString();
                }
                break;

//            case R.id.project_1:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_1);
//                    project1 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_2:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_2);
//                    project2 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_3:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_3);
//                    project3 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_4:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_4);
//                    project4 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_5:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_5);
//                    project5 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_6:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_6);
//                    project6 = checkbox.getText().toString();
//                }
//                break;
//            case R.id.project_7:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_7);
//                    project7 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_8:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_8);
//                    project8 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_9:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_9);
//                    project9 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_10:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_10);
//                    project10 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_11:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_11);
//                    project11 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_12:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_12);
//                    project12 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_13:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_13);
//                    project13 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_14:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_14);
//                    project14 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_15:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_15);
//                    project15 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_16:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_16);
//                    project16 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_17:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_17);
//                    project17 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_18:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_18);
//                    project18 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_19:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_19);
//                    project19 = checkbox.getText().toString();
//                }
//                break;
//
//            case R.id.project_20:
//                if (checked) {
//                    checkbox = (CheckBox) view.findViewById(R.id.project_20);
//                    project20 = checkbox.getText().toString();
//                }
//                break;
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
