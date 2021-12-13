package com.example.myapplication.Exercise;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.Repository;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {
    MyDBHelper myDBHelper;
    Repository repository;
    private ArrayList<Exercise> mArrayList;
    private ExerciseAdapter exerciseAdapter;
    private int count = -1;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        myDBHelper = new MyDBHelper(this);
        repository = new Repository();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.exercise_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mArrayList = repository.select_exercise(myDBHelper);
        exerciseAdapter = new ExerciseAdapter(this,mArrayList);
        recyclerView.setAdapter(exerciseAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        Button add = (Button) findViewById(R.id.add_exerciseBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ExerciseActivity.this);

                View view = LayoutInflater.from(ExerciseActivity.this)
                        .inflate(R.layout.editbox, null, false);
                builder.setView(view);


                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                final EditText editTextID = (EditText) view.findViewById(R.id.name_edittext);


                ButtonSubmit.setText("삽입");

                final AlertDialog dialog = builder.create();

                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String exercise_name = editTextID.getText().toString();

                        Exercise exercise = new Exercise(exercise_name);
                        repository.insert_exercise(myDBHelper,exercise);
                        mArrayList.add(exercise);
                        exerciseAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });




    }


}
