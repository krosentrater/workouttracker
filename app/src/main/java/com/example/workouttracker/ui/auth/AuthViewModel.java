package com.example.workouttracker.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.workouttracker.data.model.User;
import com.example.workouttracker.data.repository.UserRepository;

import java.util.function.Consumer;

public class AuthViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> registerSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

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

    public void loginWithUsername(String username, String password) {
        userRepository.loginWithUsername(username, password)
                .addOnSuccessListener(authResult -> loginSuccess.postValue(true))
                .addOnFailureListener(e -> errorMessage.postValue(e.getMessage()));
    }


    public void checkUsernameAvailable(String username, Consumer<Boolean> callback) {
        userRepository.isUsernameAvailable(username, callback);
    }


}
