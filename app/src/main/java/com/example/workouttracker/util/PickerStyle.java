package com.example.workouttracker.util;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;

public class PickerStyle {

    public static void styleNumberPicker(NumberPicker picker, int textColor, int dividerColor) {
        setNumberPickerTextColor(picker, textColor);
        setNumberPickerDividerColor(picker, dividerColor);

        // Force text color to stay white even after scrolling
        picker.setFormatter(value -> {
            setNumberPickerTextColor(picker, textColor);
            return String.valueOf(value);
        });

        picker.setOnValueChangedListener((np, oldVal, newVal) ->
                setNumberPickerTextColor(np, textColor)
        );
    }

    private static void setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                } catch (Exception ignored) {}
            }
        }
    }

    private static void setNumberPickerDividerColor(NumberPicker picker, int color) {
        try {
            java.lang.reflect.Field[] fields = NumberPicker.class.getDeclaredFields();
            for (java.lang.reflect.Field field : fields) {
                if (field.getName().equals("mSelectionDivider")) {
                    field.setAccessible(true);
                    field.set(picker, new ColorDrawable(color));
                    picker.invalidate();
                    break;
                }
            }
        } catch (Exception ignored) {}
    }

    public static void styleDatePicker(DatePicker datePicker, int textColor, int dividerColor) {
        int count = ((ViewGroup) datePicker).getChildCount();

        for (int i = 0; i < count; i++) {
            View child = ((ViewGroup) datePicker).getChildAt(i);
            if (child instanceof NumberPicker) {
                styleNumberPicker((NumberPicker) child, textColor, dividerColor);
            }
        }
    }
}
