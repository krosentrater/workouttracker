package com.example.workouttracker.data.repository;

import com.example.workouttracker.data.firebase.FirebaseAuthManager;
import com.example.workouttracker.data.firebase.FirebaseUserDataSource;
import com.example.workouttracker.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.function.Consumer;

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

    // Login with username associated with email
    public Task<AuthResult> loginWithUsername(String username, String password) {
        TaskCompletionSource<AuthResult> tcs = new TaskCompletionSource<>();

        userDataSource.getUserByUsername(username)
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()) {
                        String email = query.getDocuments().get(0).getString("email");

                        assert email != null;
                        authManager.getAuth()
                                .signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(tcs::setResult)
                                .addOnFailureListener(tcs::setException);

                    } else {
                        tcs.setException(new Exception("Username not found"));
                    }
                })
                .addOnFailureListener(tcs::setException);

        return tcs.getTask();
    }


    // Checks if username is available in Firestore
    public void isUsernameAvailable(String username, Consumer<Boolean> callback) {
        userDataSource.getUserByUsername(username)
                .addOnSuccessListener(query -> callback.accept(query.isEmpty()))
                .addOnFailureListener(e -> callback.accept(false));
    }


}
