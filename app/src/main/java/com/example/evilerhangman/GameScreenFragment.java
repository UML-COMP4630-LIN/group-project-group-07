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

public class GameScreenFragment extends Fragment {

    private GameScreenViewModel mViewModel;

    public static GameScreenFragment newInstance() {
        return new GameScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_screen, container, false);

        Button submitButton = view.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            // TODO: Fix this with proper implementation!

            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_gameScreenFragment_to_resultScreenFragment);
            }
        });

        return view;
    }

}