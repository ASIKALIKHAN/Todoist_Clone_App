package com.bawp.todoister.util;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bawp.todoister.model.Priority;
import com.bawp.todoister.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {
    public static String formatDate(Date date) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
//        simpleDateFormat.applyPattern("EEE, MMM d"); h:mm a
 //       simpleDateFormat.applyPattern("EEE, MMM d, yyyy");
        simpleDateFormat.applyPattern("EEE, MMM d, yyyy-h:mm a"); //happy to be added smiley Face


        return  simpleDateFormat.format(date);

    }

    public static void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public static int priorityColor(Task task) {
        int color;
        if (task.getPriority() == Priority.HIGH) {
            color = Color.argb(150,201, 21, 23);
        }else if (task.getPriority() == Priority.MEDIUM) {
            color = Color.argb(150,255, 150,0);
        }else {
            color = Color.argb(150, 70, 181, 255);
        }
        return color;
    }
}

