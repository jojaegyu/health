package com.example.myapplication.ScheduleSearch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.Repository;
import com.example.myapplication.Schedule.Schedule;
import com.example.myapplication.Schedule.ScheduleAdapter;

import java.util.ArrayList;

public class ScheduleSearchActivity extends AppCompatActivity {
    MyDBHelper myDBHelper;
    Repository repository;
    ArrayList<Schedule> mArrayList;
    private ScheduleAdapter scheduleAdapter;
    private int count = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulesearch);

        myDBHelper = new MyDBHelper(this);
        repository = new Repository();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scheduleSearch_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        mArrayList = new ArrayList<Schedule>();
        scheduleAdapter = new ScheduleAdapter(this,mArrayList);
        recyclerView.setAdapter(scheduleAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        Button ScheduleSearchBtn = (Button) findViewById(R.id.scheduleSearchBtn);
        TextView SearchKeyword = (TextView) findViewById(R.id.scheduleSearchEditText);

        ScheduleSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mArrayList.removeAll(mArrayList);
                String keyword = SearchKeyword.getText().toString();
                ArrayList<Schedule> tmp = repository.search_Schedule(myDBHelper,keyword);
                mArrayList.addAll(tmp);
                scheduleAdapter.notifyDataSetChanged();

            }
        });


    }
}
