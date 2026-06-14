package com.example.workouttracker.data.repository;

import com.example.workouttracker.data.firebase.FirebaseAuthManager;
import com.example.workouttracker.data.firebase.FirebaseUserDataSource;
import com.example.workouttracker.data.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class UserRepository {

    private final FirebaseAuthManager authManager;
    private final FirebaseUserDataSource userDataSource;

    public UserRepository() {
        this.authManager = new FirebaseAuthManager();
        this.userDataSource = new FirebaseUserDataSource();
    }

    public Task<FirebaseUser> registerUser(String email, String password) {
        return authManager.register(email, password);
    }

    public Task<Void> saveUserProfile(User user) {
        return userDataSource.saveUser(user);
    }

    public Task<FirebaseUser> loginUser(String email, String password) {
        return authManager.login(email, password);
    }

    public Task<User> getUserProfile(String uid) {
        return userDataSource.getUser(uid);
    }

    public FirebaseUser getCurrentUser() {
        return authManager.getCurrentUser();
    }

    public void logout() {
        authManager.logout();
    }
}
