package com.example.myapplication.Schedule;

import android.content.Context;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.Repository;

import java.time.LocalTime;
import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>{
    private ArrayList<Schedule> mList;
    private Context mcontext;
    Repository repository;
    MyDBHelper myDBHelper;

    public class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected TextView scheduleName;
        protected TextView scheduleStartTime;
        protected TextView scheduleEndTime;


        public ScheduleViewHolder(View view) {
            super(view);
            this.scheduleName = (TextView) view.findViewById(R.id.scheduleName);
            this.scheduleStartTime = (TextView) view.findViewById(R.id.scheduleStartTime);
            this.scheduleEndTime = (TextView) view.findViewById(R.id.scheduleEndTime);

            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "수정");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1001:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);

                        View view = LayoutInflater.from(mcontext)
                                .inflate(R.layout.editbox_schedule, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_schedule_dialog_submit);
                        final EditText editTextName = (EditText) view.findViewById(R.id.name_schedule);
                        final TimePicker startTimePicker = (TimePicker) view.findViewById(R.id.schedule_startTimePicker);
                        final TimePicker endTimePicker = (TimePicker) view.findViewById(R.id.schedule_endTimePicker);

                        Schedule schedule = new Schedule(mList.get(getAdapterPosition()).getScheduleName()
                                ,mList.get(getAdapterPosition()).getStartTime()
                                ,mList.get(getAdapterPosition()).getEndTime());

                        editTextName.setText(schedule.getScheduleName());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String strName = editTextName.getText().toString();

                                LocalTime startLocalTime = LocalTime.of(startTimePicker.getHour(),startTimePicker.getMinute());
                                LocalTime endLocalTime = LocalTime.of(endTimePicker.getHour(),endTimePicker.getMinute());

                                Schedule newSchedule = new Schedule(strName, startLocalTime, endLocalTime);

                                repository.update_Schedule(myDBHelper,schedule,newSchedule);
                                mList.set(getAdapterPosition(), newSchedule);
                                notifyItemChanged(getAdapterPosition());
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        break;
                    case 1002:
                        repository.delete_Schedule(myDBHelper, mList.get(getAdapterPosition()));
                        mList.remove(getAdapterPosition());

                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(),mList.size());

                        break;
                }
                return true;
            }

        };
    }

    public ScheduleAdapter(Context context,ArrayList<Schedule> list){
        this.mList = list;
        mcontext = context;
        repository = new Repository();
        myDBHelper = new MyDBHelper(context);
    }

    @NonNull
    @Override
    public ScheduleAdapter.ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule,parent,false);

        ScheduleAdapter.ScheduleViewHolder viewHolder = new ScheduleAdapter.ScheduleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ScheduleViewHolder holder, int position) {
        holder.scheduleName.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        holder.scheduleStartTime.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);
        holder.scheduleEndTime.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);

        holder.scheduleName.setGravity(Gravity.CENTER);
        holder.scheduleStartTime.setGravity(Gravity.CENTER);
        holder.scheduleEndTime.setGravity(Gravity.CENTER);

        holder.scheduleName.setText(mList.get(position).getScheduleName());
        holder.scheduleStartTime.setText(mList.get(position).getStartTime().toString());
        holder.scheduleEndTime.setText(mList.get(position).getEndTime().toString());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size():0);
    }
}

