package com.example.workouttracker.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.workouttracker.data.model.User;
import com.example.workouttracker.data.repository.UserRepository;
import com.google.firebase.auth.FirebaseUser;

public class AccountViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public AccountViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<User> getUser() {
        return userLiveData;
    }

    public LiveData<String> getError() {
        return errorLiveData;
    }

    public void loadUserProfile() {
        FirebaseUser current = userRepository.getCurrentUser();

        if (current == null) {
            errorLiveData.setValue("No user logged in.");
            return;
        }

        userRepository.getUserProfile(current.getUid())
                .addOnSuccessListener(userLiveData::setValue)
                .addOnFailureListener(e -> errorLiveData.setValue(e.getMessage()));
    }

}
