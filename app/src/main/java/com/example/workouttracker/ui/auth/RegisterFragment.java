package com.example.workouttracker.ui.auth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

        // View Binds
        EditText firstName = view.findViewById(R.id.editFirstName);
        EditText lastName = view.findViewById(R.id.editLastName);
        EditText username = view.findViewById(R.id.editUsername);
        EditText password = view.findViewById(R.id.editPassword);
        EditText weight = view.findViewById(R.id.editWeight);
        EditText userEmail = view.findViewById(R.id.editEmail);
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

        // Back Button
        ImageButton backArrow = view.findViewById(R.id.backButtonLogin);
        backArrow.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_registerFragment_to_loginFragment);
        });

        // Create Account Button
        Button createAccount = view.findViewById(R.id.btnCreateAccount);
        createAccount.setOnClickListener(v -> {

            // Read Inputs
            String emailStr = userEmail.getText().toString().trim();
            String passwordStr = password.getText().toString().trim();
            String fNameStr = firstName.getText().toString().trim();
            String lNameStr = lastName.getText().toString().trim();
            String usernameStr = username.getText().toString().trim();
            String weightStr = weight.getText().toString().trim();

            //Convert to single height
            int feet = feetPicker.getValue();
            int inches = inchPicker.getValue();
            int totalHeight = (feet * 12) + inches;

            // Convert birthday to epoch millis format
            long birthdayMillis = DateUtils.datePickerToEpoch(birthdayPicker);

            // ----------------- //
            // Validation
            // ----------------- //

            // First Name
            if (fNameStr.isEmpty()) {
                firstName.setError("First name is required.");
                return;
            }
            // Last Name
            if (lNameStr.isEmpty()) {
                lastName.setError("Last name is required.");
                return;
            }
            // Username
            if (usernameStr.isEmpty()) {
                username.setError("Username is required.");
                return;
            }
            if (usernameStr.length() < 3) {
                username.setError("Username must be at least 3 characters.");
                return;
            }
            if (!usernameStr.matches("^[a-zA-Z0-9_]+$")) {
                username.setError("Only letters, numbers, and underscores allowed");
                return;
            }
            // Email
            if (emailStr.isEmpty()) {
                userEmail.setError("Email is required.");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                userEmail.setError("Invalid email format.");
                return;
            }
            // Password
            if (passwordStr.length() < 6) {
                password.setError("Password must be at least 6 characters.");
                return;
            }
            // Weight
            float weightValue;
            try {
                weightValue = Float.parseFloat(weightStr);
                if (weightValue < 50 || weightValue > 700) {
                    weight.setError("Enter a realistic weight");
                    return;
                }
            } catch (NumberFormatException e) {
                weight.setError("Invalid weight");
                return;
            }
            // Height
            if (totalHeight < 36 || totalHeight > 96) { // 3ft to 8ft
                Toast.makeText(requireContext(), "Enter a realistic height", Toast.LENGTH_SHORT).show();
                return;
            }
            // Birthday
            int age = CalculateAge.calculateAge(birthdayMillis);
            if (age < 13) {
                Toast.makeText(requireContext(), "You must be at least 13 years old", Toast.LENGTH_SHORT).show();
                return;
            }

            // Build User
            User user = new User();
            user.email = emailStr;
            user.username = usernameStr;
            user.firstName = fNameStr;
            user.lastName = lNameStr;
            user.weight = weightValue;
            user.height = totalHeight;
            user.age = birthdayMillis;

            // Firestore Username Uniqueness
            authViewModel.checkUsernameAvailable(usernameStr, available -> {
                if (!available) {
                    username.setError("Username already taken.");
                    return;
                }
                else {
                    // Register new user
                    authViewModel.register(emailStr, passwordStr, user);
                }
            });

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