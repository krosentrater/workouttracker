package com.example.workouttracker.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.workouttracker.MainActivity;
import com.example.workouttracker.R;

public class LoginFragment extends Fragment {

    private AuthViewModel authViewModel;
    private FrameLayout loadingOverlay;
    private Button btnLogin;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    // Add filler login for now
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authViewModel = new ViewModelProvider(this, new AuthViewModelFactory()).get(AuthViewModel.class);

        EditText usernameInput = view.findViewById(R.id.editUsername);
        EditText passwordInput = view.findViewById(R.id.editPassword);
        btnLogin = view.findViewById(R.id.btnLogin);
        TextView textRegister = view.findViewById(R.id.registerLink);
        loadingOverlay = view.findViewById(R.id.loadingOverlay);


        textRegister.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        btnLogin.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty()) {
                usernameInput.setError("Username is required.");
                return;
            }

            if (password.isEmpty()) {
                passwordInput.setError("Password is required.");
                return;
            }

            loadingOverlay.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);

            authViewModel.loginWithUsername(username, password);
        });
        observeViewModel();
    }

    private void observeViewModel() {
        authViewModel.getLoginSuccess().observe(getViewLifecycleOwner(), success -> {
            loadingOverlay.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
            if (success != null && success) {
                Intent intent = new Intent(requireActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        authViewModel.getErrorMessage().observe(getViewLifecycleOwner(), msg -> {
            loadingOverlay.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
            if (msg != null) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
