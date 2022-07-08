package com.bawp.todoister.adapter;

import com.bawp.todoister.model.Task;

public interface OnTodoClickListener {
    void onTodoClick(Task task);
    void onTodoLongClick(Task task,int position);
    void onTodoRadioButtonClick(Task task,int position);
}
