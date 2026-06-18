package com.example.workouttracker.ui.auth;

import android.content.Intent;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workouttracker.MainActivity;
import com.example.workouttracker.R;
import com.example.workouttracker.data.model.User;
import com.example.workouttracker.util.CalculateAge;
import com.example.workouttracker.util.DateUtils;
import com.example.workouttracker.util.PickerStyle;

public class RegisterFragment extends Fragment {

    private AuthViewModel authViewModel;

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

        authViewModel = new ViewModelProvider((ViewModelStoreOwner) this, new AuthViewModelFactory()).get(AuthViewModel.class);


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
        createAccount.setOnClickListener(v -> {

            String emailStr = username.getText().toString().trim() + "@wt.com"; // Temp workaround email field needed
            String passwordStr = password.getText().toString().trim();

            //Convert to single height
            int feet = feetPicker.getValue();
            int inches = inchPicker.getValue();
            int totalHeight = (feet * 12) + inches;

            // Convert birthday to epoch millis format
            long birthdayMillis = DateUtils.datePickerToEpoch(birthdayPicker);

            User user = new User();
            user.email = emailStr;
            user.username = username.getText().toString().trim();
            user.weight = Float.parseFloat(weight.getText().toString());
            user.height = totalHeight;
            user.age = CalculateAge.calculateAge(birthdayMillis);

            authViewModel.register(emailStr, passwordStr, user);

        });

        observeViewModel();
    }

    private void observeViewModel() {
        authViewModel.getRegisterSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(requireContext(), "Account Created!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(requireActivity(), MainActivity.class));
                requireActivity().finish();
            }
        });

        authViewModel.getErrorMessage().observe(getViewLifecycleOwner(), error ->
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
        );
    }
}