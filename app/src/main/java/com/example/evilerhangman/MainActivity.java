/***********************************************
 Authors: Tristan McDermott, Michael Marchand, Mathew Langlois, and Bardh Tafarshiku
 Date: 12/12/24
 Purpose: To create a Hangman game that changes the word after every guess.
 What Learned: We have learned more indepth knowledge of github, how to properly co-operate
                how to indepth debug, improving user experience, and learning about viewmodel scope.
 Sources of Help: We asked the professor for help in dealing with ViewModels not resetting.
 Time Spent (Hours): 40
 ***********************************************/

/*
Mobile App Development I -- COMP.4630 Honor Statement
The practice of good ethical behavior is essential for maintaining good order
in the classroom, providing an enriching learning experience for students,
and training as a practicing computing professional upon graduation. This
practice is manifested in the University's Academic Integrity policy.
Students are expected to strictly avoid academic dishonesty and adhere to the
Academic Integrity policy as outlined in the course catalog. Violations will
be dealt with as outlined therein. All programming assignments in this class
are to be done by the student alone unless otherwise specified. No outside
help is permitted except the instructor and approved tutors.
I certify that the work submitted with this assignment is mine and was
generated in a manner consistent with this document, the course academic
policy on the course website on Blackboard, and the UMass Lowell academic
code.
Date: 12/12/24
Names: Tristan McDermott, Michael Marchand, Mathew Langlois, and Bardh Tafarshiku
*/

/*
MainActivity.java
This file contains the code for the main Activity.
*/

package com.example.evilerhangman;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.evilerhangman.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        EdgeToEdge.enable(this);
        setContentView(view);

        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(
                R.id.nav_host_fragment );
        NavController navController = navHostFragment.getNavController();

        AppBarConfiguration.Builder builder = new
                AppBarConfiguration.Builder(R.id.mainMenuFragment);
        AppBarConfiguration appBarConfiguration = builder.build();

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        ViewCompat.setOnApplyWindowInsetsListener(binding.navHostFragment, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar.setNavigationOnClickListener(v -> {
            navController.navigate(R.id.mainMenuFragment);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if(item.getItemId() == android.R.id.home) {
            navController.navigate(R.id.mainMenuFragment);
            return true;
        }
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }
}