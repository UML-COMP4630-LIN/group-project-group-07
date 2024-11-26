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

public class ResultScreenFragment extends Fragment {

    private ResultScreenViewModel mViewModel;

    public static ResultScreenFragment newInstance() {
        return new ResultScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_screen, container, false);

        Button playAgainButton = view.findViewById(R.id.playAgainButton);
        Button mainMenuButton = view.findViewById(R.id.mainMenuButton);

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            // TODO: Implement play again functionality
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_resultScreenFragment_to_gameScreenFragment);
            }
        });

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            // TODO: Implement main menu functionality
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_resultScreenFragment_to_mainMenuFragment);
            }
        });

        return view;
    }

}