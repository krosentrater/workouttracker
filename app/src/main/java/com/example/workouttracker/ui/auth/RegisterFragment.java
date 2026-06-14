package com.example.workouttracker.ui.auth;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workouttracker.R;
import com.example.workouttracker.util.DateUtils;
import com.example.workouttracker.util.PickerStyle;

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Back Button
        ImageButton backArrow = view.findViewById(R.id.backButtonLogin);
        backArrow.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_registerFragment_to_loginFragment);
        });


        EditText firstName = view.findViewById(R.id.editFirstName);
        EditText lastName = view.findViewById(R.id.editLastName);
        EditText username = view.findViewById(R.id.editUsername);
        EditText password = view.findViewById(R.id.editPassword);
        EditText weight = view.findViewById(R.id.editWeight);

        NumberPicker feetPicker = view.findViewById(R.id.pickerFeet);
        NumberPicker inchPicker = view.findViewById(R.id.pickerInches);
        DatePicker birthdayPicker = view.findViewById(R.id.datePickerBirthday);


        // Default values and min/max values for pickers
        feetPicker.setMinValue(3);
        feetPicker.setMaxValue(7);
        feetPicker.setValue(5);

        inchPicker.setMinValue(0);
        inchPicker.setMaxValue(11);
        inchPicker.setValue(8);

        view.post(() -> {
            int white = Color.WHITE;
            int orange = Color.parseColor("#FF9800");

            PickerStyle.styleNumberPicker(feetPicker, white, orange);
            PickerStyle.styleNumberPicker(inchPicker, white, orange);
            PickerStyle.styleDatePicker(birthdayPicker, white, orange);

        });

        Log.d("DEBUG", "BirthdayPicker children = " + birthdayPicker.getChildCount());



        Button createAccount = view.findViewById(R.id.btnCreateAccount);

        //Convert to single height
        int feet = feetPicker.getValue();
        int inches = inchPicker.getValue();

        int totalHeight = (feet * 12) + inches;

        createAccount.setOnClickListener(v -> {

            // Convert birthday to epoch millis format
            long birthdayMillis = DateUtils.datePickerToEpoch(birthdayPicker);
            Toast.makeText(requireContext(), "Account Created", Toast.LENGTH_SHORT).show();

            // TODO: Save to Room database
            // User user = new User(
            //      firstName.getText().toString(),
            //      lastName.getText().toString(),
            //      username.getText().toString(),
            //      birthdayMillis,
            //      Float.parseFloat(weight.getText().toString()),
            //      Float.parseFloat(height.getText().toString())
            // );
        });
    }
}