package com.xino.todoister.adapter;


import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.xino.todoister.R;
import com.xino.todoister.model.Task;
import com.xino.todoister.util.Utils;
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


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = taskList.get(position);
        String formatted = Utils.formatDate(task.getDueDate());
        String[] items1 = formatted.split("-");
        String date = items1[0];
        String time = items1[1];

        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[] {-android.R.attr.state_enabled},
                new int[] {android.R.attr.state_enabled}
        },
                new int[]{
                        Color.LTGRAY, //disabled
                        Utils.priorityColor(task)
                });
        if (taskList.size()==0){
            listener.onNoTodoEvent(true);
        }
       else { listener.onNoTodoEvent(false);}


        holder.taskTV.setText(task.getTask());

        if (holder.cardViewDescription.getVisibility()==View.VISIBLE){
            if (task.getTaskDescription().compareTo("")==0){
                holder.textViewDescription.setText(R.string.no_description);
            }
            else {
                holder.textViewDescription.setText(task.getTaskDescription());
            }
        }
        else
        {
            holder.textViewDescription.setText(task.getTaskDescription());
        }

        holder.dueDateChip.setText(date);
        holder.dueDateChip.setTextColor(Utils.priorityColor(task));
        holder.dueDateChip.setChipIconTint(colorStateList);
        holder.dueTimeChip.setText(time);
        holder.dueTimeChip.setTextColor(Utils.priorityColor(task));
        holder.dueTimeChip.setChipIconTint(colorStateList);
        holder.imageButtonExpandCollapse.setBackgroundTintList(colorStateList);
        holder.appCompatRadioButton.setButtonTintList(colorStateList);
        holder.appCompatRadioButton.setChecked(task.isRadioSelected);

    }

    @Override
    public int getItemCount() {

        return taskList.size();

    }

    public class TaskHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener{
        private AppCompatRadioButton appCompatRadioButton;
        private AppCompatTextView taskTV;
        private Chip dueDateChip;
        private Chip dueTimeChip;
        private ImageButton imageButtonExpandCollapse;
        private ConstraintLayout expandableConstraintLayout;

        private CardView cardViewDescription;
        private TextView textViewDescription;
        public TaskHolder(@NonNull View itemView) {
            super(itemView);
            appCompatRadioButton = itemView.findViewById(R.id.todo_radio_button);
            taskTV = itemView.findViewById(R.id.todo_row_todo);
            dueDateChip = itemView.findViewById(R.id.todo_row_due_date_chip);
            dueTimeChip = itemView.findViewById(R.id.todo_row_due_time_chip);

            imageButtonExpandCollapse = itemView.findViewById(R.id.imageViewExpandCollapse);
            expandableConstraintLayout = itemView.findViewById(R.id.expandableConstraintLayout);

            cardViewDescription = itemView.findViewById(R.id.cardViewDescripton);
            textViewDescription = itemView.findViewById(R.id.textViewDescripton);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            appCompatRadioButton.setOnClickListener(this);
            imageButtonExpandCollapse.setOnClickListener(this);

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
            else if (id == R.id.imageViewExpandCollapse ) {
                listener.onTodoExpand(currTask,getAdapterPosition());
                if (cardViewDescription.getVisibility()==View.GONE) {
                    cardViewDescription.setVisibility(View.VISIBLE);
                    imageButtonExpandCollapse.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
                else {
                    cardViewDescription.setVisibility(View.GONE);
                    imageButtonExpandCollapse.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }
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
