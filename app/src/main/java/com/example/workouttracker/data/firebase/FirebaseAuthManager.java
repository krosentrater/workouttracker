package com.example.workouttracker.data.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class FirebaseAuthManager {

    // Initialize Firebase Auth
    private final FirebaseAuth auth;

    // Emulator Config for testing Firebase logic *firebase emulators:start*
    public FirebaseAuthManager() {
        auth = FirebaseAuth.getInstance();
        if (BuildConfig.DEBUG) {
            auth.useEmulator("10.0.2.2", 9099);
        }
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    // Create new user with email/password
    public Task<FirebaseUser> register(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password).continueWith(task -> {
            if (!task.isSuccessful()) throw Objects.requireNonNull(task.getException());
            return auth.getCurrentUser();
        });
    }

    public Task<FirebaseUser> login(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password)
                .continueWith(task -> {
                    if (!task.isSuccessful()) throw Objects.requireNonNull(task.getException());
                    return auth.getCurrentUser();
                });
    }

    // Get current user logged in
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    // Sign out user
    public void logout() {
        auth.signOut();
    }
}
