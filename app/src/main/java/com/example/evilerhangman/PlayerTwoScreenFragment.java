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

import com.example.evilerhangman.databinding.FragmentPlayerTwoScreenBinding;

public class PlayerTwoScreenFragment extends Fragment {

    private FragmentPlayerTwoScreenBinding binding;

    private MultiplayerViewModel mViewModel;

    public static PlayerTwoScreenFragment newInstance() {
        return new PlayerTwoScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentPlayerTwoScreenBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        MultiplayerViewModelFactory viewModelFactory = new MultiplayerViewModelFactory(getActivity().getApplication(), 7, 6);
        mViewModel = new ViewModelProvider(requireActivity(), viewModelFactory).get(MultiplayerViewModel.class);

        mViewModel.game.revealedWord.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.p2wordDisplay.setText(s);
            }
        });

        mViewModel.game.livesLeft.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != 6) {
                    binding.p2gallowsImage.setImageResource(mViewModel.game.imageArray[integer]);
                }
            }
        });

        binding.p2submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_playerTwoScreenFragment_to_playerOneScreenFragment);
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