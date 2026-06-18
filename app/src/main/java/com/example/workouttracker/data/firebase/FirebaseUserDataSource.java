package com.example.workouttracker.data.firebase;

import com.example.workouttracker.data.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.BuildConfig;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class FirebaseUserDataSource {

    // Initialize FirebaseFirestore database
    private final FirebaseFirestore db;

    // Add Firebase emulator for testing logic *firebase emulators:start*
    public FirebaseUserDataSource() {
        db = FirebaseFirestore.getInstance();
        if (BuildConfig.DEBUG) {
            db.useEmulator("10.0.2.2", 8080);
        }
    }

    public FirebaseFirestore getDb() {
        return db;
    }


    // Stores user profile
    public Task<Void> saveUser(User user) {
        return db.collection("users")
                .document(user.uid)
                .set(user);
    }


    // Gets user profile
    public Task<User> getUser(String uid) {
        return db.collection("users")
                .document(uid)
                .get()
                .continueWith(task -> {
                    if (!task.isSuccessful()) throw Objects.requireNonNull(task.getException());
                    DocumentSnapshot doc = task.getResult();
                    return doc.toObject(User.class);
                });
    }


    // Updates user profile
    public Task<Void> updateUser(User user) {
        return db.collection("users")
                .document(user.uid)
                .set(user);
    }
}
