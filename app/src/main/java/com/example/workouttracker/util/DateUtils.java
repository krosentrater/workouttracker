package com.example.workouttracker.util;

import android.widget.DatePicker;

import java.util.Calendar;

public class DateUtils {

    public static long datePickerToEpoch(DatePicker picker) {
        Calendar cal = Calendar.getInstance();
        cal.set(
                picker.getYear(),
                picker.getMonth(),
                picker.getDayOfMonth(),
                0,0,0
        );
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }
}
