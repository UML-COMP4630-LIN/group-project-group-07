/*
GameScreenFragment.java
This file contains the code for the game screen Fragment and handles the UI logic relating to it.
*/

package com.example.evilerhangman;

import static com.example.evilerhangman.Mode.GOOD;

import androidx.lifecycle.Observer;
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
import android.widget.EditText;

import com.example.evilerhangman.databinding.FragmentGameScreenBinding;

import java.util.ArrayList;

public class GameScreenFragment extends Fragment {

    private GameScreenViewModel mViewModel;
    private SettingsViewModel settingsViewModel;
    private FragmentGameScreenBinding binding;
    public static GameScreenFragment newInstance() {
        return new GameScreenFragment();
    }
    private int[] imageArray = new int[]{
        R.drawable.right_leg,
        R.drawable.left_leg,
        R.drawable.left_arm,
        R.drawable.right_arm,
        R.drawable.torso,
        R.drawable.head,
        R.drawable.gallows
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGameScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        GameScreenViewModelFactory viewModelFactory = new GameScreenViewModelFactory(getActivity().getApplication(), 7, settingsViewModel.difficulty, settingsViewModel.mode);
        mViewModel = new ViewModelProvider(this, viewModelFactory).get(GameScreenViewModel.class);
        mViewModel.game.revealedWord.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.wordDisplay.setText(s);
            }
        });
        mViewModel.game.livesLeft.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.remainingAttemptsNumber.setText(integer.toString());
                binding.gallowsImage.setImageResource(imageArray[(int)Math.ceil(integer / settingsViewModel.difficulty)]);
            }
        });
        mViewModel.game.guessedLetters.observe(getViewLifecycleOwner(), new Observer<ArrayList<Character>>() {
            @Override
            public void onChanged(ArrayList<Character> characters) {
                StringBuilder sb = new StringBuilder();
                for(Character ch: characters) {
                    sb = sb.append(ch).append(", ");
                }
                if(sb.length() > 0) {
                    sb.setLength(sb.length() - 2);
                }
                binding.guessedLetters.setText(sb.toString());
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.guessInput.getText().toString().length() != 1) {
                    return;
                }
                boolean gameOver = mViewModel.game.guess(binding.guessInput.getText().toString().charAt(0));
                if(gameOver) {
                    boolean won = mViewModel.game.hasWon();
                    GameScreenFragmentDirections.ActionGameScreenFragmentToResultScreenFragment action = GameScreenFragmentDirections.actionGameScreenFragmentToResultScreenFragment(won, mViewModel.game.word);
                    Navigation.findNavController(view).navigate(action);
                }
                binding.guessInput.setText("");
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}