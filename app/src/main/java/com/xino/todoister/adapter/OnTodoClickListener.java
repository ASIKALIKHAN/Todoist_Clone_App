package com.xino.todoister.adapter;

import com.xino.todoister.model.Task;

public interface OnTodoClickListener {
    void onTodoClick(Task task);
    void onTodoLongClick(Task task,int position);
    void onTodoRadioButtonClick(Task task,int position);
    void onNoTodoEvent(boolean emptyTodo);
    void onTodoExpand(Task task,int position);
}
