package com.example.evilerhangman;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.evilerhangman.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private FragmentSettingsBinding binding;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        // Change this line to use the activity scope
        mViewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);

        // Set the correct radio button based on the current difficulty value
        if (mViewModel.difficulty == 0.5) {
            binding.easyDifficulty.setChecked(true);
        } else if (mViewModel.difficulty == 1.0) {
            binding.mediumDifficulty.setChecked(true);
        } else if (mViewModel.difficulty == 2.0) {
            binding.hardDifficulty.setChecked(true);
        }

        binding.difficultyGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == binding.easyDifficulty.getId()) {
                mViewModel.difficulty = 0.5;
            }
            else if(checkedId == binding.mediumDifficulty.getId()) {
                mViewModel.difficulty = 1.0;
            }
            else if(checkedId == binding.hardDifficulty.getId()) {
                mViewModel.difficulty = 2.0;
            }
        });

        return view;
    }

}