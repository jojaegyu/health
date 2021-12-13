package com.example.myapplication.Routine;

import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Calander.CalendarActivity;
import com.example.myapplication.MyDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.Repository;
import com.example.myapplication.RoutineInfo.RoutineInfoActivity;
//import com.example.myapplication.RoutineInfo.RoutineInfoActivity;

import java.util.ArrayList;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>{
    private ArrayList<Routine> mList;
    private Context mcontext;
    private AppCompatActivity activity;

    Repository repository;
    MyDBHelper myDBHelper;

    public class RoutineViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected TextView routine_name;


        public RoutineViewHolder(View view) {
            super(view);
            this.routine_name = (TextView) view.findViewById(R.id.exercise_name);
            view.setOnCreateContextMenuListener(this);
        }



        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1001, 1, "수정");
            MenuItem Delete = menu.add(Menu.NONE, 1002, 2, "삭제");
            MenuItem RoutineInfo = menu.add(Menu.NONE,1003,3,"루틴 관리");
            Edit.setOnMenuItemClickListener(onEditMenu);
            Delete.setOnMenuItemClickListener(onEditMenu);
            RoutineInfo.setOnMenuItemClickListener(onEditMenu);
        }

        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 1001:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);

                        View view = LayoutInflater.from(mcontext)
                                .inflate(R.layout.editbox, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                        final EditText editTextName = (EditText) view.findViewById(R.id.name_edittext);

                        Routine old_routine = new Routine(mList.get(getAdapterPosition()).getName());
                        editTextName.setText(mList.get(getAdapterPosition()).getName());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String strName = editTextName.getText().toString();

                                Routine new_routine = new Routine(strName);

                                repository.update_routine(myDBHelper,old_routine,new_routine);
                                mList.set(getAdapterPosition(), new_routine);
                                notifyItemChanged(getAdapterPosition());
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        break;

                    case 1002:
                        repository.delete_routine(myDBHelper, mList.get(getAdapterPosition()));
                        mList.remove(getAdapterPosition());

                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(),mList.size());

                        break;

                    case 1003:
                        Intent intent = new Intent(mcontext, RoutineInfoActivity.class);
                        intent.putExtra("routineName",routine_name.getText());
                        activity.startActivityForResult(intent,1002);
                        break;

                }
                return true;
            }

        };
    }

    public RoutineAdapter(Context context,ArrayList<Routine> list,AppCompatActivity activity){
        this.mList = list;
        mcontext = context;
        this.activity = activity;
        repository = new Repository();
        myDBHelper = new MyDBHelper(context);
    }


    @NonNull
    @Override
    public RoutineAdapter.RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise,parent,false);

        RoutineAdapter.RoutineViewHolder viewHolder = new RoutineAdapter.RoutineViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull RoutineAdapter.RoutineViewHolder holder, int position) {
        holder.routine_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);

        holder.routine_name.setGravity(Gravity.CENTER);

        holder.routine_name.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size():0);
    }
}
