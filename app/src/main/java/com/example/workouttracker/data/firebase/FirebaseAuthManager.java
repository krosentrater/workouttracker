package com.example.workouttracker.data.firebase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class FirebaseAuthManager {

    // Initialize Firebase Auth
    private final FirebaseAuth auth = FirebaseAuth.getInstance();

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
