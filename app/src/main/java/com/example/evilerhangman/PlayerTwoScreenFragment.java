package com.example.evilerhangman;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.evilerhangman.databinding.FragmentPlayerTwoScreenBinding;

public class PlayerTwoScreenFragment extends Fragment {

    private FragmentPlayerTwoScreenBinding binding;

    private MultiplayerViewModel mViewModel;

    private SettingsViewModel settingsViewModel;
    private int[] imageArray = new int[]{
        R.drawable.right_leg,
        R.drawable.left_leg,
        R.drawable.left_arm,
        R.drawable.right_arm,
        R.drawable.torso,
        R.drawable.head,
        R.drawable.gallows
    };

    public static PlayerTwoScreenFragment newInstance() {
        return new PlayerTwoScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPlayerTwoScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Character letter = PlayerTwoScreenFragmentArgs.fromBundle(requireArguments()).getLetter().charAt(0);
        binding.p1guessed.setText(getString(R.string.p1guessed, letter));

        settingsViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        MultiplayerViewModelFactory viewModelFactory = new MultiplayerViewModelFactory(getActivity().getApplication(), settingsViewModel.length, settingsViewModel.difficulty);
        mViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(MultiplayerViewModel.class);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mViewModel.game.words);
        binding.p2wordChanger.setAdapter(adapter);

        mViewModel.game.revealedWord.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.p2wordDisplay.setText(s);
            }
        });

        mViewModel.game.livesLeft.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.p2gallowsImage.setImageResource(imageArray[(int)Math.ceil(integer / settingsViewModel.difficulty)]);
            }
        });

        binding.p2submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.game.word = binding.p2wordChanger.getSelectedItem().toString();
                boolean gameOver = mViewModel.game.guess(letter);
                if(gameOver) {
                    boolean won = mViewModel.game.hasWon();
                    PlayerTwoScreenFragmentDirections.ActionPlayerTwoScreenFragmentToResultScreenFragment action = PlayerTwoScreenFragmentDirections.actionPlayerTwoScreenFragmentToResultScreenFragment(won, binding.p2wordChanger.getSelectedItem().toString());
                    mViewModel.reset(settingsViewModel.length, settingsViewModel.difficulty);
                    Navigation.findNavController(view).navigate(action);
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_playerTwoScreenFragment_to_playerOneScreenFragment);
                }
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