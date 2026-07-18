package com.example.workouttracker.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.workouttracker.data.model.User;
import com.example.workouttracker.data.repository.UserRepository;

public class HomeViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(new UserRepository());
        }
        throw new IllegalArgumentException("Unknown viewmodel class: " + modelClass.getName());
    }
}
