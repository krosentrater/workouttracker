package com.example.workouttracker.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workouttracker.R;
import com.example.workouttracker.ui.auth.AuthActivity;
import com.example.workouttracker.util.CalculateAge;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;

    public AccountFragment() {
        super(R.layout.fragment_account);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountViewModel = new ViewModelProvider(this, new AccountViewModelFactory()).get(AccountViewModel.class);

        ImageButton back = view.findViewById(R.id.backBtn);
        back.setOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());

        TextView accountName = view.findViewById(R.id.accountName);
        ImageView profilePicture = view.findViewById(R.id.accountAvatar);

        // Observe user data
        accountViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                String fullName = getString(R.string.full_name, user.firstName, user.lastName);
                accountName.setText(fullName);

                // Profile Picture
                profilePicture.setImageResource(R.drawable.ic_account_circle);
            }
        });

        accountViewModel.getError().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });

        // Load Profile
        accountViewModel.loadUserProfile();

        // TODO: Add actual edit feature for profile
        view.findViewById(R.id.btnEditProfile).setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), AuthActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        view.findViewById(R.id.btnLogout).setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), AuthActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
    }
}