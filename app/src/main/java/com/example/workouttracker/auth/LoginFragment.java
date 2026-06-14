package com.example.workouttracker.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workouttracker.MainActivity;
import com.example.workouttracker.R;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    // Add filler login for now
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnLogin = view.findViewById(R.id.btnLogin);
        TextView textRegister = view.findViewById(R.id.registerLink);

        textRegister.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        btnLogin.setOnClickListener(v -> {
            // TODO: Replace with actual login authentication later!
            Toast.makeText(requireContext(), "Login clicked!", Toast.LENGTH_SHORT).show();

            // Launch MainActivity
            Intent intent = new Intent(requireActivity(), MainActivity.class);
            startActivity(intent);

            // Close AuthActivity
            requireActivity().finish();
        });
    }
}
