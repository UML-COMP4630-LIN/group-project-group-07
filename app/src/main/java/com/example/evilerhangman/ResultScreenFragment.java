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
    private ResultScreenViewModel mViewModel;

    public static ResultScreenFragment newInstance() {
        return new ResultScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentResultScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Button playAgainButton = binding.playAgainButton;
        Button mainMenuButton = binding.mainMenuButton;

        boolean won = ResultScreenFragmentArgs.fromBundle(requireArguments()).getWon();
        String word = ResultScreenFragmentArgs.fromBundle(requireArguments()).getWord();

        if(won) {
            binding.resultMessage.setText(R.string.you_won);
        } else {
            binding.resultMessage.setText(R.string.you_lost);
        }
        binding.revealedWord.setText(word);

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