package com.bawp.todoister.adapter;


import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.todoister.R;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.util.Utils;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewTaskAdapter extends RecyclerView.Adapter<RecyclerViewTaskAdapter.TaskHolder> {
   private List<Task> taskList = new ArrayList<>();
   private  OnTodoClickListener listener;



    public void setOnTaskClickListener(OnTodoClickListener onTodoClickListener){
        this.listener = onTodoClickListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setUpdatedTaskList(List<Task> tasks){
        this.taskList = tasks;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_row,parent,false);
        return new TaskHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = taskList.get(position);
        String formatted = Utils.formatDate(task.getDueDate());

        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[] {-android.R.attr.state_enabled},
                new int[] {android.R.attr.state_enabled}
        },
                new int[]{
                        Color.LTGRAY, //disabled
                        Utils.priorityColor(task)
                });

        holder.taskTV.setText(task.getTask());
        holder.todayChip.setText(formatted);
        holder.todayChip.setTextColor(Utils.priorityColor(task));
        holder.todayChip.setChipIconTint(colorStateList);
        holder.appCompatRadioButton.setButtonTintList(colorStateList);
        holder.appCompatRadioButton.setChecked(task.isRadioSelected);

    }

    @Override
    public int getItemCount() {
        return taskList.size();

    }



    public class TaskHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener{
        public AppCompatRadioButton appCompatRadioButton;
        public AppCompatTextView taskTV;
        public Chip todayChip;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            appCompatRadioButton = itemView.findViewById(R.id.todo_radio_button);
            taskTV = itemView.findViewById(R.id.todo_row_todo);
            todayChip = itemView.findViewById(R.id.todo_row_chip);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            appCompatRadioButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Task currTask = taskList.get(getAdapterPosition());

            int id = view.getId();
            if (id == R.id.todo_row_layout) {
                listener.onTodoClick(currTask);
            }
            else if (id == R.id.todo_radio_button) {
                listener.onTodoRadioButtonClick(currTask,getAdapterPosition());
                appCompatRadioButton.setChecked(currTask.isRadioSelected);

            }
        }


        @Override
        public boolean onLongClick(View view) {
            Task currTask = taskList.get(getAdapterPosition());

            int id = view.getId();
            if (id == R.id.todo_row_layout) {

                    listener.onTodoLongClick(currTask,getAdapterPosition());
                return true;
            }

            return false;
        }
    }
}
