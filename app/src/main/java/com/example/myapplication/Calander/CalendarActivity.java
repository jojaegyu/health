package com.example.myapplication.Calander;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.myapplication.Routine.Routine;
import com.example.myapplication.Routine.RoutineActivity;
import com.example.myapplication.Routine.RoutineAdapter;
import com.example.myapplication.RoutineInfo.RoutineInfo;
import com.example.myapplication.RoutineInfo.RoutineInfoAdapter;
import com.example.myapplication.Schedule.Schedule;
import com.example.myapplication.Schedule.ScheduleActivity;
import com.example.myapplication.Schedule.ScheduleAdapter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {
    Repository repository;
    MyDBHelper myDBHelper;
    ArrayList<Routine> routineArrayList;
    ArrayList<Schedule> scheduleArrayList;
    private ScheduleAdapter scheduleAdapter;
    private RoutineAdapter routineAdapter;
    private int count = -1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        repository = new Repository();
        myDBHelper = new MyDBHelper(this);

        Intent intent = getIntent();
        int year = intent.getIntExtra("year",2021);
        int month = intent.getIntExtra("month",12);
        int dayOfMonth = intent.getIntExtra("dayOfMonth",25);
        LocalDate localDate = LocalDate.of(year,month,dayOfMonth);

        TextView dateTextView = (TextView) findViewById(R.id.calendar_dateTextView);
        dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        dateTextView.setGravity(Gravity.CENTER);
        dateTextView.setText(localDate.toString());

        RecyclerView routine_recyclerView = (RecyclerView) findViewById(R.id.calendar_routine_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        routine_recyclerView.setLayoutManager(linearLayoutManager);

        routineArrayList = repository.select_calendar_routine(myDBHelper, localDate);
        routineAdapter = new RoutineAdapter(this,routineArrayList,CalendarActivity.this);
        routine_recyclerView.setAdapter(routineAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(routine_recyclerView.getContext(),linearLayoutManager.getOrientation());
        routine_recyclerView.addItemDecoration(dividerItemDecoration);


        Button addRoutineBtn = findViewById(R.id.calendar_add_routineBtn);
        addRoutineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);

                View view = LayoutInflater.from(CalendarActivity.this)
                        .inflate(R.layout.editbox_calendar_routine, null, false);
                builder.setView(view);

                final Spinner spinner = (Spinner) view.findViewById(R.id.calendar_routine_spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CalendarActivity.this,R.layout.support_simple_spinner_dropdown_item);
                ArrayList<Routine> ArrayList = repository.select_routine(myDBHelper);
                for(int i = 0;i< ArrayList.size();i++){
                    adapter.add(ArrayList.get(i).getName());
                }
                spinner.setAdapter(adapter);

                final AlertDialog dialog = builder.create();
                dialog.show();

                Button submitBtn = (Button)view.findViewById(R.id.calendar_routineAddBtn);
                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Routine selected_Routine = new Routine(spinner.getSelectedItem().toString());
                        repository.insert_calendar_routine(myDBHelper,selected_Routine,localDate);
                        routineArrayList.add(selected_Routine);
                        for(int i = 0 ;i < routineArrayList.size(); i++){
                            System.out.println(routineArrayList.get(i));
                        }
                        routineAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

            }
        });

        RecyclerView schedule_recyclerView = (RecyclerView) findViewById(R.id.calendar_schedule_recyclerView);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        schedule_recyclerView.setLayoutManager(linearLayoutManager2);

        scheduleArrayList = repository.select_calendar_schedule(myDBHelper,localDate);
        scheduleAdapter = new ScheduleAdapter(this,scheduleArrayList);
        schedule_recyclerView.setAdapter(scheduleAdapter);

        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(schedule_recyclerView.getContext(),linearLayoutManager.getOrientation());
        schedule_recyclerView.addItemDecoration(dividerItemDecoration);

        Button addScheduleBtn = (Button) findViewById(R.id.calendar_addscheduleBtn);
        addScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);

                View view = LayoutInflater.from(CalendarActivity.this)
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
                        repository.insert_calendar_schedule(myDBHelper,schedule,localDate);
                        scheduleArrayList.add(schedule);
                        scheduleAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
    }
}
