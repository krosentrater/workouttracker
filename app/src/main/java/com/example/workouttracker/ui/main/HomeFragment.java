package com.example.workouttracker.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workouttracker.R;
import com.example.workouttracker.util.CalculateAge;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(this, new HomeViewModelFactory()).get(HomeViewModel.class);

        TextView textWelcome = view.findViewById(R.id.textWelcome);
        TextView textUserName = view.findViewById(R.id.textUserName);
        TextView textWeight = view.findViewById(R.id.textWeight);
        TextView textHeight = view.findViewById(R.id.textHeight);
        TextView textAge = view.findViewById(R.id.textAge);
        TextView textBMI = view.findViewById(R.id.textBmi);

        ImageButton accountBtn = view.findViewById(R.id.btnAccount);
        accountBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_homeFragment_to_accountFragment);
        });

        homeViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {

                textWelcome.setText(getString(R.string.welcome_back));
                textUserName.setText(user.firstName);
                textWeight.setText(getString(R.string.home_weight_label, user.weight));

                float feet = user.height / 12f;
                float inches = user.height % 12f;
                textHeight.setText(getString(R.string.home_height_label, feet, inches));

                int age = CalculateAge.calculateAge(user.age);
                textAge.setText(getString(R.string.home_age_label, age));

                float heightInches = user.height;
                float bmi = (user.weight / (heightInches * heightInches)) * 703f;
                textBMI.setText(getString(R.string.home_bmi_label, bmi));
            }
        });

        homeViewModel.getError().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        homeViewModel.loadUserProfile();
    }
}