package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.CalendarView;

import com.example.myapplication.Calander.CalendarActivity;
import com.example.myapplication.Exercise.ExerciseActivity;
import com.example.myapplication.Routine.RoutineActivity;
import com.example.myapplication.ScheduleSearch.ScheduleSearchActivity;
import com.example.myapplication.Schedule.Schedule;
import com.example.myapplication.Schedule.ScheduleActivity;

import java.time.LocalTime;

public class MainActivity extends AppCompatActivity {
    MyDBHelper myDBHelper;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDBHelper = new MyDBHelper(this);
        repository = new Repository();

        Button exerciseBtn = (Button)findViewById(R.id.exerciseBtn);
        exerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivityForResult(intent,1001);
            }
        });

        Button routineBtn = (Button)findViewById(R.id.routineBtn);
        routineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RoutineActivity.class);
                startActivityForResult(intent,1001);
            }
        });

        Button initBtn = (Button)findViewById(R.id.initBtn);
        initBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.init(myDBHelper);
            }
        });

        Button printbtn = (Button)findViewById(R.id.exerciseSearchActivityBtn);
        printbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.insert_schedule(myDBHelper, new Schedule("start", LocalTime.of(1,31),LocalTime.of(4,30)));
                repository.select_schedule(myDBHelper);
            }
        });

        Button addBtn = (Button)findViewById(R.id.scheduleSearchActivityBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleSearchActivity.class);
                startActivityForResult(intent,1001);
            }
        });

        Button scheduleBtn = (Button) findViewById(R.id.scheduleBtn);
        scheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScheduleActivity.class);
                startActivityForResult(intent,1001);
            }
        });

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month + 1) + "-" + dayOfMonth;
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                intent.putExtra("year",year);
                intent.putExtra("month",month);
                intent.putExtra("dayOfMonth",dayOfMonth);
                startActivityForResult(intent,1001);
            }
        });



    }

}