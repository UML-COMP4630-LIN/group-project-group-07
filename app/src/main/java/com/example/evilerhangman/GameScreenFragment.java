package com.example.evilerhangman;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
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
        GameScreenViewModelFactory viewModelFactory = new GameScreenViewModelFactory(getActivity().getApplication(), 7, 5);
        mViewModel = new ViewModelProvider(this, viewModelFactory).get(GameScreenViewModel.class);
        mViewModel.game.revealed_word.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.wordDisplay.setText(s);
            }
        });
        mViewModel.game.lives_left.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.remainingAttemptsNumber.setText(integer.toString());
            }
        });
        mViewModel.game.guessed_letters.observe(getViewLifecycleOwner(), new Observer<ArrayList<Character>>() {
            @Override
            public void onChanged(ArrayList<Character> characters) {
                StringBuilder sb = new StringBuilder();
                for(Character ch: characters) {
                    sb.append(ch).append(", ");
                }
                binding.guessedLetters.setText(sb.toString());
            }
        });
        Button submitButton = binding.submitButton;

        submitButton.setOnClickListener(new View.OnClickListener() {
            // TODO: Fix this with proper implementation!

            @Override
            public void onClick(View v) {
                mViewModel.game.guess(binding.guessInput.getText().toString().charAt(0));
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