/*
MainMenuFragment.java
This file contains the code for the main menu Fragment. The menu buttons are set up and linked to their navigation destinations.
*/

package com.example.evilerhangman;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.evilerhangman.databinding.FragmentMainMenuBinding;

public class MainMenuFragment extends Fragment {

    private FragmentMainMenuBinding binding;

    private MultiplayerViewModel mViewModel;
    private SettingsViewModel settingsViewModel;

    public static MainMenuFragment newInstance() {
        return new MainMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        MultiplayerViewModelFactory viewModelFactory = new MultiplayerViewModelFactory(getActivity().getApplication(), settingsViewModel.length, settingsViewModel.difficulty);
        mViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(MultiplayerViewModel.class);

        binding.btnSingleplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_gameScreenFragment);
            }
        });

        binding.btnMultiplayer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mViewModel.reset(settingsViewModel.length, settingsViewModel.difficulty);
               Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_playerOneScreenFragment);
           }
        });

        binding.btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_settingsFragment);
            }
        });

        binding.btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_aboutFragment);
            }
        });

        binding.btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainMenuFragment_to_helpFragment);
            }
        });

        return view;
    }

}