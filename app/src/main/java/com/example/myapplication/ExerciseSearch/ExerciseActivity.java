package com.example.myapplication.ExerciseSearch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Exercise.Exercise;
import com.example.myapplication.Exercise.ExerciseAdapter;
import com.example.myapplication.MyDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.Repository;

import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {
    MyDBHelper myDBHelper;
    Repository repository;
    ArrayList<Exercise> mArrayList;
    private ExerciseAdapter exerciseAdapter;
    private int count = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercisesearch);

        myDBHelper = new MyDBHelper(this);
        repository = new Repository();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.exercise_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mArrayList = new ArrayList<Exercise>();
        exerciseAdapter = new ExerciseAdapter(this,mArrayList);
        recyclerView.setAdapter(exerciseAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        Button ScheduleSearchBtn = (Button) findViewById(R.id.exerciseSearchBtn);
        TextView exerciseKeyword = (TextView) findViewById(R.id.exerciseSearchEditText);

        ScheduleSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.removeAll(mArrayList);
                String keyword = exerciseKeyword.getText().toString();
                ArrayList<Exercise> tmp = repository.search_exercise(myDBHelper,keyword);
                mArrayList.addAll(tmp);
                exerciseAdapter.notifyDataSetChanged();

            }
        });
    }
}
