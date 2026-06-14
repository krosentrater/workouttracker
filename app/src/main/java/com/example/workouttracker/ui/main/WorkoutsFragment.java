package com.example.workouttracker.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Toast;

import com.example.workouttracker.R;

public class WorkoutsFragment extends Fragment {


    public WorkoutsFragment() {
        super(R.layout.fragment_workouts);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnAddWorkout).setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Add Workout clicked", Toast.LENGTH_SHORT).show();
        });
    }
}