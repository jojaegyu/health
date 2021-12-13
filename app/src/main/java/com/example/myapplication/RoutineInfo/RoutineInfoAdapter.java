package com.example.myapplication.RoutineInfo;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.Repository;


import java.util.ArrayList;

public class RoutineInfoAdapter extends RecyclerView.Adapter<RoutineInfoAdapter.RoutineInfoViewHolder>{
    private ArrayList<RoutineInfo> mList;
    private Context mcontext;
    Repository repository;
    MyDBHelper myDBHelper;

    public class RoutineInfoViewHolder extends RecyclerView.ViewHolder {
        protected TextView exercise;
        protected TextView weight;
        protected TextView set;
        protected TextView number;

        public RoutineInfoViewHolder(@NonNull View view) {
            super(view);
            this.exercise = (TextView) view.findViewById(R.id.scheduleName);
            this.weight = (TextView) view.findViewById(R.id.routineInfo_Weight);
            this.set = (TextView) view.findViewById(R.id.routineInfo_Set);
            this.number = (TextView) view.findViewById(R.id.routineInfo_Number);
        }
    }

    public RoutineInfoAdapter(Context context,ArrayList<RoutineInfo> list){
        this.mList = list;
        mcontext = context;
        repository = new Repository();
        myDBHelper = new MyDBHelper(context);
    }

    @NonNull
    @Override
    public RoutineInfoAdapter.RoutineInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_routine,parent,false);

        RoutineInfoAdapter.RoutineInfoViewHolder viewHolder = new RoutineInfoAdapter.RoutineInfoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineInfoAdapter.RoutineInfoViewHolder holder, int position) {

        holder.exercise.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        holder.weight.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        holder.set.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        holder.number.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);

        holder.exercise.setGravity(Gravity.CENTER);
        holder.weight.setGravity(Gravity.CENTER);
        holder.set.setGravity(Gravity.CENTER);
        holder.number.setGravity(Gravity.CENTER);

        RoutineInfo routineInfo = mList.get(position);
        routineInfo.print();
        holder.exercise.setText(routineInfo.getExerciseName().toString());
        holder.weight.setText(Integer.toString(routineInfo.getWeight()));
        holder.set.setText(Integer.toString(routineInfo.getSet()));
        holder.number.setText(Integer.toString(routineInfo.getNumber()));
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size():0);
    }
}

