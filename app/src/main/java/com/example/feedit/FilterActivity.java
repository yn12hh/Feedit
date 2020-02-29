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

                passQueryInfoToRecyclerView(view);//missing
                Intent my_intent = new Intent(getBaseContext(), Feed.class);
                startActivity(my_intent);

            }


        });



    }

    public void passQueryInfoToRecyclerView(View view) {//we need to implement the passing part


//            switch(view.getId()) {
//                case R.id.project_1_projects:
//                    if (checked) {
//                        checkbox = (CheckBox) view.findViewById(R.id.project_1_projects);
//                        projects_list.add(checkbox.getText().toString());
//                    }
//                    break;
//
//                case R.id.project_2_projects:
//                    if (checked) {
//                        checkbox = (CheckBox) view.findViewById(R.id.project_2_projects);
//                        projects_list.add(checkbox.getText().toString());
//                    }
//                    break;
//
//                case R.id.project_3_projects:
//                    if (checked) {
//                        checkbox = (CheckBox) view.findViewById(R.id.project_3_projects);
//                        projects_list.add(checkbox.getText().toString());
//                    }
//                    break;
//
//                case R.id.project_4_projects:
//                    if (checked) {
//                        checkbox = (CheckBox) view.findViewById(R.id.project_4_projects);
//                        projects_list.add(checkbox.getText().toString());
//                    }
//                    break;
//
//                case R.id.project_5_projects:
//                    if (checked) {
//                        checkbox = (CheckBox) view.findViewById(R.id.project_5_projects);
//                        projects_list.add(checkbox.getText().toString());
//                    }
//                    break;
//
//
//            }
    }

    public void onCheckboxClicked(View view) {

        CheckBox checkbox;
        String main_office_string = "", production_string = "", pr_string = "", executive_string = "", r_and_d_string = "", marketing_string = "";
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
        }
        teams_list = Arrays.asList(executive_string, main_office_string, marketing_string, pr_string, production_string, r_and_d_string);

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
