package com.example.myapplication.Schedule;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.Repository;

import java.time.LocalTime;
import java.util.ArrayList;

public class ScheduleActivity extends AppCompatActivity {
    MyDBHelper myDBHelper;
    Repository repository;
    ArrayList<Schedule> mArrayList;
    private ScheduleAdapter scheduleAdapter;
    private int count = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        myDBHelper = new MyDBHelper(this);
        repository = new Repository();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.schedule_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mArrayList = repository.select_schedule(myDBHelper);
        scheduleAdapter = new ScheduleAdapter(this,mArrayList);
        recyclerView.setAdapter(scheduleAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        Button add = (Button) findViewById(R.id.add_schedule);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleActivity.this);

                View view = LayoutInflater.from(ScheduleActivity.this)
                        .inflate(R.layout.editbox_schedule, null, false);
                builder.setView(view);


                final Button ButtonSubmit = (Button) view.findViewById(R.id.button_schedule_dialog_submit);
                final EditText editTextID = (EditText) view.findViewById(R.id.name_schedule);
                final TimePicker startTimePicker = (TimePicker) view.findViewById(R.id.schedule_startTimePicker);
                final TimePicker endTImePicker = (TimePicker) view.findViewById(R.id.schedule_endTimePicker);

                ButtonSubmit.setText("삽입");

                final AlertDialog dialog = builder.create();

                ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String scheduleName = editTextID.getText().toString();
                        LocalTime startTime = LocalTime.of(startTimePicker.getHour(),startTimePicker.getMinute());
                        LocalTime endTIme = LocalTime.of(endTImePicker.getHour(),endTImePicker.getMinute());
                        Schedule schedule = new Schedule(scheduleName,startTime,endTIme);
                        repository.insert_schedule(myDBHelper,schedule);
                        mArrayList.add(schedule);
                        scheduleAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
    }
}
