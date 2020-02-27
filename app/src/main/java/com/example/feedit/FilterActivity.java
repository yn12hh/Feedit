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

public class FilterActivity extends AppCompatActivity {

    private ImageView save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        save_button = (ImageView) findViewById(R.id.save);
        save_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                passQueryInfoToRecyclerView(view);//missing
                Intent my_intent = new Intent(getBaseContext(), Feed.class);
                startActivity(my_intent);

            }


        });


    }

    public void passQueryInfoToRecyclerView(View view) {//we need to implement the passing part
        List<String> projects_list = Arrays.asList();
        List<String> teams_list = Arrays.asList();

        CheckBox checkbox;

        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.main_office_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.main_office_team);
                    teams_list.add(checkbox.getText().toString());
                }
                break;

            case R.id.production_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.production_team);
                    teams_list.add(checkbox.getText().toString());
                }
                break;

            case R.id.pr_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.pr_team);
                    teams_list.add(checkbox.getText().toString());
                }
                break;

            case R.id.executive_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.executive_team);
                    teams_list.add(checkbox.getText().toString());
                }
                break;

            case R.id.r_and_d_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.r_and_d_team);
                    teams_list.add(checkbox.getText().toString());
                }
                break;

            case R.id.marketing_team:
                if (checked) {
                    checkbox = (CheckBox) view.findViewById(R.id.marketing_team);
                    teams_list.add(checkbox.getText().toString());
                }
                break;
        }



            switch(view.getId()) {
                case R.id.project_1_projects:
                    if (checked) {
                        checkbox = (CheckBox) view.findViewById(R.id.project_1_projects);
                        projects_list.add(checkbox.getText().toString());
                    }
                    break;

                case R.id.project_2_projects:
                    if (checked) {
                        checkbox = (CheckBox) view.findViewById(R.id.project_2_projects);
                        projects_list.add(checkbox.getText().toString());
                    }
                    break;

                case R.id.project_3_projects:
                    if (checked) {
                        checkbox = (CheckBox) view.findViewById(R.id.project_3_projects);
                        projects_list.add(checkbox.getText().toString());
                    }
                    break;

                case R.id.project_4_projects:
                    if (checked) {
                        checkbox = (CheckBox) view.findViewById(R.id.project_4_projects);
                        projects_list.add(checkbox.getText().toString());
                    }
                    break;

                case R.id.project_5_projects:
                    if (checked) {
                        checkbox = (CheckBox) view.findViewById(R.id.project_5_projects);
                        projects_list.add(checkbox.getText().toString());
                    }
                    break;

                    
            }
    }
}
