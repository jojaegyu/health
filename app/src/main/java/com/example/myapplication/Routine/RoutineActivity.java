package com.example.myapplication.Routine;

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

public class RoutineActivity extends AppCompatActivity {
    MyDBHelper myDBHelper;
    Repository repository;
    private ArrayList<Routine> mArrayList;
    private RoutineAdapter routineAdapter;
    private int count = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        myDBHelper = new MyDBHelper(this);
        repository = new Repository();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.routine_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mArrayList = repository.select_routine(myDBHelper);
        routineAdapter = new RoutineAdapter(this,mArrayList,RoutineActivity.this);
        recyclerView.setAdapter(routineAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        Button add = (Button) findViewById(R.id.add_routineBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(RoutineActivity.this);

                View view = LayoutInflater.from(RoutineActivity.this)
                        .inflate(R.layout.editbox, null, false);
                builder.setView(view);


                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                final EditText editTextID = (EditText) view.findViewById(R.id.name_edittext);

                editTextID.setHint("루틴을 입력하세요.");
                ButtonSubmit.setText("삽입");

                final AlertDialog dialog = builder.create();

                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String routine_name = editTextID.getText().toString();

                        Routine routine = new Routine(routine_name);
                        repository.insert_routine(myDBHelper,routine);
                        mArrayList.add(routine);
                        routineAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

    }
}
