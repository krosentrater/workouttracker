package com.example.workouttracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find navController from NavHostFragment
        NavController navController = ((NavHostFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.mainNavHost))).getNavController();


        // Find the BottomNavView
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment,
                R.id.workoutsFragment,
                R.id.progressFragment,
                R.id.accountFragment
        ).build();

        // Connect the bottom nav to nav Controller
        NavigationUI.setupWithNavController(bottomNav, navController);

        // Navigate from Account page back to home
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.homeFragment) {
                if (navController.getCurrentDestination() != null && navController.getCurrentDestination().getId() == R.id.accountFragment) {
                    navController.popBackStack(R.id.homeFragment, false);
                }
            }

            return NavigationUI.onNavDestinationSelected(item, navController);
        });
    }
}