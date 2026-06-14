package com.example.workouttracker.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.workouttracker.R;
import com.example.workouttracker.auth.AuthActivity;

public class AccountFragment extends Fragment {

    public AccountFragment() {
        super(R.layout.fragment_account);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton back = view.findViewById(R.id.backBtn);
        back.setOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());

        view.findViewById(R.id.btnEditProfile).setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Edit Profile Clicked", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {
            // TODO: Add real logout logic
            Intent intent = new Intent(requireActivity(), AuthActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}