package com.example.workouttracker;

import android.app.Application;
import android.util.Log;

import com.google.firebase.BuildConfig;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class WorkoutTrackerApp extends Application {

    // Created for using emulator for debugging and testing firebase logic
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("APP", "WorkoutTrackerApp started");

        // Force Firebase to initialize HERE, not earlier
        FirebaseApp.initializeApp(this);
        Log.e("APP", "Firebase initialized");

        // Now bind the emulator BEFORE anything else touches Firebase
        FirebaseAuth auth = FirebaseAuth.getInstance();
        Log.e("APP", "Auth instance created");
        auth.useEmulator("10.0.2.2", 9099);
        Log.e("APP", "Auth emulator bound");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.e("APP", "Firestore instance created");
        db.useEmulator("10.0.2.2", 8080);
        Log.e("APP", "Firestore emulator bound");
    }
}
