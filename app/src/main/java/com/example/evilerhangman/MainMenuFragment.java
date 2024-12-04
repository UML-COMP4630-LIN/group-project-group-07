/*
MainMenuFragment.java
This file contains the code for the main menu Fragment. The menu buttons are set up and linked to their navigation destinations.
*/

package com.example.evilerhangman;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenuFragment extends Fragment {

    private MainMenuViewModel mViewModel;

    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        Button singlePlayerButton = view.findViewById(R.id.btnSingleplayer);
        Button multiplayerButton = view.findViewById(R.id.btnMultiplayer);
        Button settingsButton = view.findViewById(R.id.btnSettings);
        Button aboutButton = view.findViewById(R.id.btnAbout);
        Button helpButton = view.findViewById(R.id.btnHelp);

        singlePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_gameScreenFragment);
            }
        });

        multiplayerButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // TODO: Implement multiplayer functionality
           }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_settingsFragment);
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_aboutFragment);
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_helpFragment);
            }
        });

        return view;
    }

}