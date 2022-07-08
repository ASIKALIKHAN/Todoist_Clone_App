package com.bawp.todoister.util;

import androidx.room.TypeConverter;

import com.bawp.todoister.model.Priority;

import java.util.Date;

public class Converters {
    //todo note this for future use

    @TypeConverter
    public static Date fromTimeStamp(Long value){
        return value  ==null ? null :new Date(value);
    }
    @TypeConverter
    public static Long dateToTimeStamp(Date date){
        return date  ==null ? null : date.getTime();
    }
    @TypeConverter
    public static String fromPriority(Priority priority){
        return priority  == null ? null : priority.name();
    }
    @TypeConverter
    public static Priority toPriority(String name){
        return name  == null ? null : Priority.valueOf(name);
    }


}
