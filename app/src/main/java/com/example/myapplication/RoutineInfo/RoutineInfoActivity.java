package com.example.myapplication.RoutineInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.TaskStackBuilder;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Exercise.Exercise;
import com.example.myapplication.MyDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.Repository;
import com.example.myapplication.Routine.Routine;
import com.example.myapplication.Routine.RoutineActivity;
import com.example.myapplication.Routine.RoutineAdapter;

import java.util.ArrayList;

public class RoutineInfoActivity extends AppCompatActivity {
    Context context;
    Repository repository;
    MyDBHelper myDBHelper;
    ArrayList<RoutineInfo> mArrayList;
    private RoutineInfoAdapter routineInfoAdapter;
    private int count = -1;
    Intent intent;
    String routineName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routineinfo);


        myDBHelper = new MyDBHelper(this);
        repository = new Repository();
        intent = getIntent();
        routineName = intent.getStringExtra("routineName");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.routineInfo_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mArrayList = repository.select_routineInfo(myDBHelper,routineName);
        routineInfoAdapter = new RoutineInfoAdapter(this,mArrayList);
        recyclerView.setAdapter(routineInfoAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);



        Button add_routineInfo = (Button) findViewById(R.id.add_routineInfo);
        add_routineInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RoutineInfoActivity.this);

                View view = LayoutInflater.from(RoutineInfoActivity.this)
                        .inflate(R.layout.editbox_routineinfo, null, false);
                builder.setView(view);
                Spinner spinner = (Spinner)view.findViewById(R.id.routineInfo_spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(RoutineInfoActivity.this,R.layout.support_simple_spinner_dropdown_item);
                ArrayList<Exercise> exerciseArrayList = repository.select_exercise(myDBHelper);
                for(int i = 0; i < exerciseArrayList.size(); i++){
                    adapter.add(exerciseArrayList.get(i).getName());
                }
                spinner.setAdapter(adapter);

                final AlertDialog dialog = builder.create();
                Button ButtonSubmit = (Button)view.findViewById(R.id.routineInfo_submitBtn);
                TextView weight_textView = (TextView)view.findViewById(R.id.weight_edittext);
                TextView set_textView = (TextView)view.findViewById(R.id.set_edittext);
                TextView number_textView = (TextView)view.findViewById(R.id.number_edittext);

                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RoutineInfo new_routineInfo = new RoutineInfo(routineName
                                ,spinner.getSelectedItem().toString()
                                ,Integer.parseInt(weight_textView.getText().toString())
                                ,Integer.parseInt(set_textView.getText().toString())
                                ,Integer.parseInt(number_textView.getText().toString())
                        );
                        repository.insert_routineInfo(myDBHelper,new_routineInfo);
                        mArrayList.add(new_routineInfo);
                        routineInfoAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    public RoutineInfoActivity() {
        this.context = this;
    }
}
