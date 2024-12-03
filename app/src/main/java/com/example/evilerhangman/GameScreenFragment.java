package com.example.evilerhangman;

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

import com.example.evilerhangman.databinding.FragmentGameScreenBinding;

import java.util.ArrayList;

public class GameScreenFragment extends Fragment {

    private GameScreenViewModel mViewModel;
    private FragmentGameScreenBinding binding;
    public static GameScreenFragment newInstance() {
        return new GameScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentGameScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        GameScreenViewModelFactory viewModelFactory = new GameScreenViewModelFactory(getActivity().getApplication(), 7, 6);
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
                if(integer != 6) {
                    binding.gallowsImage.setImageResource(mViewModel.game.imageArray[integer]);
                }
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