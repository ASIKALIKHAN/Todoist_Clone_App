package com.bawp.todoister;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.bawp.todoister.model.Priority;
import com.bawp.todoister.model.SharedViewModel;
import com.bawp.todoister.model.Task;
import com.bawp.todoister.model.TaskViewModel;
import com.bawp.todoister.util.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

public class BottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    Calendar calendar = Calendar.getInstance();
    private EditText enterTodo;
    private ImageButton calendarButton;
    private ImageButton timeButton;
    private ImageButton priorityButton;
    private RadioGroup priorityRadioGroup;
    private RadioButton selectedRadioButton;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private TimePicker timePicker;
    private TimePickerDialog timePickerDialog;
    private Group calendarGroup;
    private Date dueDate;
    private Date dueTime;
    private Date setDateNTime;
    private SharedViewModel sharedViewModel;
    private boolean isEdit;
    private Priority priority;
    private int hour ;
    private int minute;
    private  final Calendar calenderForTimeAux = Calendar.getInstance();

    public BottomSheetFragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.bottom_sheet, container, false);

        calendarGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calendarButton = view.findViewById(R.id.today_calendar_button);
        timeButton = view.findViewById(R.id.today_timepicker_button);
        enterTodo = view.findViewById(R.id.enter_todo_et);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        priorityRadioGroup = view.findViewById(R.id.radioGroup_priority);

        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sharedViewModel.getSelectedItem().getValue() != null) {
            isEdit = sharedViewModel.getIsEdit();
            Task task = sharedViewModel.getSelectedItem().getValue();
            enterTodo.setText(task.getTask());
            Log.d("MY", "onViewCreated: " + isEdit + " " + task.getTask());
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel = new ViewModelProvider(requireActivity())
                .get(SharedViewModel.class);

        calendarButton.setOnClickListener(view12 -> {
            Utils.hideSoftKeyboard(view12);
            calendarGroup.setVisibility(calendarGroup.getVisibility() == View.GONE ?
                    View.VISIBLE : View.GONE);


        });
        timeButton.setOnClickListener(view14 -> {
//                timePicker.setVisibility(timePicker.getVisibility() == View.GONE ?
//                        View.VISIBLE : View.GONE);
           // final Calendar calenderForTimeAux = Calendar.getInstance();
                 hour = calenderForTimeAux.get(Calendar.HOUR_OF_DAY);
                 minute = calenderForTimeAux.get(Calendar.MINUTE);
                 timePickerDialog = new TimePickerDialog(BottomSheetFragment.this.getActivity(), new TimePickerDialog.OnTimeSetListener() {
                     @Override
                     public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                         calenderForTimeAux.set(Calendar.HOUR_OF_DAY, hourOfDay);
                         calenderForTimeAux.set(Calendar.MINUTE, minute);
                         calenderForTimeAux.set(Calendar.SECOND, 0);
                         calenderForTimeAux.set(Calendar.MILLISECOND, 0);
                         dueTime = calenderForTimeAux.getTime();
                        timePickerDialog.dismiss();
                     }
                 },hour ,minute ,false);
                 timePickerDialog.show();

        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMoth) {
                calendar.clear();
                calendar.set(year, month, dayOfMoth);
                dueDate = calendar.getTime();
                Log.d("TIME", "CALENDER: " + dueDate.toString());

            }
        });
        priorityButton.setOnClickListener(view13 -> {
            Utils.hideSoftKeyboard(view13);
            priorityRadioGroup.setVisibility(
                    priorityRadioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );
            priorityRadioGroup.setOnCheckedChangeListener((radioGroup, checkedId) -> {
                if (priorityRadioGroup.getVisibility() == View.VISIBLE) {
                    selectedButtonId = checkedId;
                    selectedRadioButton = view.findViewById(selectedButtonId);
                    if (selectedRadioButton.getId() == R.id.radioButton_high) {
                        priority = Priority.HIGH;
                    } else if (selectedRadioButton.getId() == R.id.radioButton_med) {
                        priority = Priority.MEDIUM;
                    }else if (selectedRadioButton.getId() == R.id.radioButton_low) {
                        priority = Priority.LOW;
                    }else {
                        priority = Priority.LOW;
                    }
                }else{
                    priority = Priority.LOW;
                }
            });
        });

        saveButton.setOnClickListener(view1 -> {
            String task = enterTodo.getText().toString().trim();

            if (!TextUtils.isEmpty(task) && dueDate != null && priority != null && dueTime != null) {
                calenderForTimeAux.set(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                setDateNTime = calenderForTimeAux.getTime();
                Task myTask = new Task(task, priority,
                        setDateNTime, Calendar.getInstance().getTime(),
                        false);
//                Task myTask = new Task(task, priority,
//                        dueDate, Calendar.getInstance().getTime(),
//                        false);
                if (isEdit) {
                    Task updateTask = sharedViewModel.getSelectedItem().getValue();

                    assert updateTask != null;
                    updateTask.setTask(task);
                    updateTask.setDateCreated(Calendar.getInstance().getTime());
                    updateTask.setPriority(priority);
                    updateTask.setDueDate(dueDate);
                    updateTask.setRadioSelected(false);
                    TaskViewModel.update(updateTask);
                    sharedViewModel.setIsEdit(false);


                } else {
                    TaskViewModel.insert(myTask);
                }
                enterTodo.setText("");
                if (this.isVisible()) {
                    this.dismiss();
                }

            }else {
                Snackbar.make(saveButton, R.string.empty_field, Snackbar.LENGTH_LONG)
                        .show();
            }
        });


    }

    @Override
    public void onClick(View view) {
        Calendar setCal = Calendar.getInstance(); // for getting the required values properly ..issue solved - day was getting added up in the set date with every button click
        int id = view.getId();
        if (id == R.id.today_chip) {
            calendar.clear();
            calendar.set(setCal.get(Calendar.YEAR), setCal.get(Calendar.MONTH), setCal.get(Calendar.DAY_OF_MONTH));
            //set data for today
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            dueDate = calendar.getTime();
            Log.d("TIME", "onClick: " + dueDate.toString());


        } else if (id == R.id.tomorrow_chip) {
            calendar.clear();
            calendar.set(setCal.get(Calendar.YEAR), setCal.get(Calendar.MONTH), setCal.get(Calendar.DAY_OF_MONTH));
            //set data for tomorrow
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dueDate = calendar.getTime();
            Log.d("TIME", "TOMORROW: " + dueDate.toString());


        } else if (id == R.id.next_week_chip) {
            calendar.clear();
            calendar.set(setCal.get(Calendar.YEAR), setCal.get(Calendar.MONTH), setCal.get(Calendar.DAY_OF_MONTH));
            //set data for next week
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            dueDate = calendar.getTime();
            Log.d("TIME", "NEXT WEEK: " + dueDate.toString());

        }

    }
}