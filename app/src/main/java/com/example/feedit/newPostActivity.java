package com.example.feedit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class newPostActivity extends AppCompatActivity {

    private FeedItFBInterface fb_interface;
    private EditText title_et, post_content_et, new_project_name; //et stands for Edit Text, written in acronym to short name length
    private Spinner teams_sp, projects_sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        title_et = (EditText) findViewById(R.id.title_input);
        teams_sp = (Spinner) findViewById(R.id.teams_name_input);
        projects_sp = (Spinner) findViewById(R.id.projects_name_input);
        post_content_et = (EditText) findViewById(R.id.posts_content_input);
        fb_interface = FeedItFBInterface.getInstance();

        String[] items = new String[] {"Tap to choose", "Main Office", "Production", "PR", "Executive","R & D", "Marketing"};
        ArrayAdapter<String> teams_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        teams_sp.setAdapter(teams_adapter);
        setUpProjSpinner();

    }

    private void setUpProjSpinner() {
        final List<String> projects_names = new ArrayList<>();
        final ArrayAdapter<String> proj_spinner_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, projects_names);
        proj_spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projects_sp.setAdapter(proj_spinner_adapter);
        fb_interface.populateProjSpinner(proj_spinner_adapter, projects_names);
        projects_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if(item.equals("Add new project")) {
                    parent.setSelection(0);
                    addProject();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onPostButtonClicked(View view){
        String title = title_et.getText().toString();
        String teams = teams_sp.getSelectedItem().toString();
        String projects = projects_sp.getSelectedItem().toString();
        String post_content = post_content_et.getText().toString();
        if (!title.isEmpty() && (!teams.isEmpty() && teams != "Tap to choose") && (!projects.isEmpty() && projects != "Tap to choose") && !post_content.isEmpty()) {

            sendDetailsToFB(title, teams, projects, post_content);
            Intent my_intent = new Intent(getBaseContext(), Feed.class);
            startActivity(my_intent);
        }
        else {

            Toast.makeText(getApplicationContext(),"You left one of the fields empty, please try again", Toast.LENGTH_LONG).show();
        }
    }


    public void sendDetailsToFB(String title, String teams, String projects, String post_content) {
        Post new_post = new Post(title,teams,post_content, projects);
        fb_interface.uploadPost(new_post);
    }

    public void addProject() {
        Button add_project_button;
        ImageView close_dialog;
        final Dialog  new_project_dialog;

        final AlertDialog.Builder mbuilder = new AlertDialog.Builder(newPostActivity.this);
        View mview = getLayoutInflater().inflate(R.layout.add_project_dialog, null);

        mbuilder.setView(mview);
        new_project_dialog = mbuilder.create();
        new_project_dialog.show();

        new_project_name = (EditText) mview.findViewById(R.id.project_input);

        add_project_button = (Button) mview.findViewById(R.id.add_project_button);
        add_project_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String new_project_name_string = new_project_name.getText().toString();
                if(!new_project_name_string.isEmpty()) {

                    fb_interface.newProjectName(new_project_name_string);
                    setUpProjSpinner();
                    new_project_dialog.dismiss();

                }
                else {

                    Toast.makeText(getApplicationContext(),"You left the field empty, please try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        close_dialog = (ImageView) mview.findViewById(R.id.close_dialog_imag);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_project_dialog.dismiss();
            }
        });



    }



    public void onClickCancelNewPost(View view) {/*called when the x button pressed in newpost.xml*/
        Intent cencel_post = new Intent(getBaseContext(), Feed.class);
        startActivity(cencel_post);    }
}