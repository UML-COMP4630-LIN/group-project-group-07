/*
ResultScreenFragment.java
This file contains the code for the result screen Fragment. Arguments from the game screen are received and displayed on screen.
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

import com.example.evilerhangman.databinding.FragmentGameScreenBinding;
import com.example.evilerhangman.databinding.FragmentResultScreenBinding;

public class ResultScreenFragment extends Fragment {
    private FragmentResultScreenBinding binding;

    public static ResultScreenFragment newInstance() {
        return new ResultScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentResultScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        boolean won = ResultScreenFragmentArgs.fromBundle(requireArguments()).getWon();
        String word = ResultScreenFragmentArgs.fromBundle(requireArguments()).getWord();

        if(won) {
            binding.resultMessage.setText(R.string.you_won);
            binding.resultImage.setImageResource(R.drawable.you_win);
        } else {
            binding.resultMessage.setText(R.string.you_lost);
            binding.resultImage.setImageResource(R.drawable.you_lose);
        }
        binding.revealedWord.setText(word);

        binding.playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_resultScreenFragment_to_gameScreenFragment);
            }
        });

        binding.mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_resultScreenFragment_to_mainMenuFragment);
            }
        });

        return view;
    }

}