package com.example.workouttracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


public class ProgressFragment extends Fragment {

    public ProgressFragment() {
        super(R.layout.fragment_progress);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Placeholder for future chart logic
        Toast.makeText(requireContext(), "Progress screen loaded", Toast.LENGTH_SHORT).show();
    }
}