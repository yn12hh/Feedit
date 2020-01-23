package com.example.feedit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

public class dialog extends AppCompatDialogFragment {
    private EditText title_et, projects_et, teams_et, post_content_et;
    private  dialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_post_dialog, null);
        builder.setView(view).setTitle("what?").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("post", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = title_et.getText().toString();
                String teams = teams_et.getText().toString();
                String projects = projects_et.getText().toString();
                String post_content = post_content_et.getText().toString();
                listener.applyTexts(title, teams,projects,post_content);
            }
        });

        title_et = view.findViewById(R.id.title_input);
        teams_et = view.findViewById(R.id.teams_name_input);
        projects_et = view.findViewById(R.id.projects_name_input);
        post_content_et = view.findViewById(R.id.posts_content_input);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (dialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "must implement dialogListener");
        }

    }

    public interface dialogListener{
        void applyTexts(String title, String teams, String projects, String post_content);


        }
    }

