package com.example.evilerhangman;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.evilerhangman.databinding.FragmentPlayerOneScreenBinding;

import java.util.ArrayList;

public class PlayerOneScreenFragment extends Fragment {

    private FragmentPlayerOneScreenBinding binding;

    private MultiplayerViewModel mViewModel;
    private int[] imageArray = new int[]{
            R.drawable.right_leg,
            R.drawable.left_leg,
            R.drawable.left_arm,
            R.drawable.right_arm,
            R.drawable.torso,
            R.drawable.head,
            R.drawable.gallows
    };

    public static PlayerOneScreenFragment newInstance() {
        return new PlayerOneScreenFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayerOneScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        MultiplayerViewModelFactory viewModelFactory = new MultiplayerViewModelFactory(getActivity().getApplication(), 7, 6);
        mViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(MultiplayerViewModel.class);

        mViewModel.game.revealedWord.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.p1wordDisplay.setText(s);
            }
        });

        mViewModel.game.livesLeft.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.p1remainingAttemptsNumber.setText(integer.toString());
                binding.p1gallowsImage.setImageResource(imageArray[integer]);
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
                binding.p1guessedLetters.setText(sb.toString());
            }
        });

        binding.p1submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.p1guessInput.getText().toString().length() != 1) {
                    return;
                }
                String letter = binding.p1guessInput.getText().toString();
                PlayerOneScreenFragmentDirections.ActionPlayerOneScreenFragmentToPlayerTwoScreenFragment action = PlayerOneScreenFragmentDirections.actionPlayerOneScreenFragmentToPlayerTwoScreenFragment(letter);
                Navigation.findNavController(view).navigate(action);
//                Navigation.findNavController(view).navigate(R.id.action_playerOneScreenFragment_to_playerTwoScreenFragment);
                binding.p1guessInput.setText("");
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