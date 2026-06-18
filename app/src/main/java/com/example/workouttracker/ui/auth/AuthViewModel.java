package com.example.workouttracker.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workouttracker.data.model.User;
import com.example.workouttracker.data.repository.UserRepository;

public class AuthViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    public LiveData<Boolean> getRegisterSuccess() {
        return registerSuccess;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public AuthViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(String email, String password, User user) {
        userRepository.registerUser(email, password)
                .addOnSuccessListener(firebaseUser -> {
                    user.uid = firebaseUser.getUid();

                    userRepository.saveUserProfile(user)
                            .addOnSuccessListener(unused -> registerSuccess.setValue(true))
                            .addOnFailureListener(e -> errorMessage.setValue(e.getMessage()));
                })
                .addOnFailureListener(e -> errorMessage.setValue(e.getMessage()));
    }
}
