package com.example.workouttracker.util;

import java.util.Calendar;

public class CalculateAge {

    public static int calculateAge(long birthdayMillis) {
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(birthdayMillis);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }
}
