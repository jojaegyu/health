package com.example.myapplication.Exercise;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MyDBHelper;
import com.example.myapplication.R;
import com.example.myapplication.Repository;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>{
    private ArrayList<Exercise> mList;
    private Context mcontext;
    Repository repository;
    MyDBHelper myDBHelper;

    public class ExerciseViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        protected TextView exercise_name;


        public ExerciseViewHolder(View view) {
            super(view);
            this.exercise_name = (TextView) view.findViewById(R.id.exercise_name);

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
                                .inflate(R.layout.editbox, null, false);
                        builder.setView(view);
                        final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                        final EditText editTextName = (EditText) view.findViewById(R.id.name_edittext);

                        Exercise old_exercise = new Exercise(mList.get(getAdapterPosition()).getName());
                        editTextName.setText(mList.get(getAdapterPosition()).getName());

                        final AlertDialog dialog = builder.create();
                        ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String strName = editTextName.getText().toString();

                                Exercise new_exercise = new Exercise(strName);

                                repository.update_exercise(myDBHelper,old_exercise,new_exercise);
                                mList.set(getAdapterPosition(), new_exercise);
                                notifyItemChanged(getAdapterPosition());
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                        break;
                    case 1002:
                        repository.delete_exercise(myDBHelper, mList.get(getAdapterPosition()));
                        mList.remove(getAdapterPosition());

                        notifyItemRemoved(getAdapterPosition());
                        notifyItemRangeChanged(getAdapterPosition(),mList.size());

                        break;
                }
                return true;
            }

        };
    }

    public ExerciseAdapter(Context context,ArrayList<Exercise> list){
        this.mList = list;
        mcontext = context;
        repository = new Repository();
        myDBHelper = new MyDBHelper(context);
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise,parent,false);

        ExerciseViewHolder viewHolder = new ExerciseViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        holder.exercise_name.setTextSize(TypedValue.COMPLEX_UNIT_SP,25);

        holder.exercise_name.setGravity(Gravity.CENTER);

        holder.exercise_name.setText(mList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size():0);
    }
}

