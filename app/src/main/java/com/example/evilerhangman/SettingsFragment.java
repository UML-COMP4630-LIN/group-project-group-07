/*
SettingsFragment.java
This file contains the code for the settings screen Fragment.
*/

package com.example.evilerhangman;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.evilerhangman.databinding.FragmentSettingsBinding;

import java.util.Objects;

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

        // Set the correct radio button based on the current mode value
        if (mViewModel.mode == Mode.GOOD) {
            binding.goodMode.setChecked(true);
        } else if (mViewModel.mode == Mode.NORMAL) {
            binding.normalMode.setChecked(true);
        } else if (mViewModel.mode == Mode.EVIL) {
            binding.evilMode.setChecked(true);
        }

        // Set the correct radio button based on the current difficulty value
        if (mViewModel.difficulty == 2.0) {
            binding.easyDifficulty.setChecked(true);
        } else if (mViewModel.difficulty == 1.0) {
            binding.mediumDifficulty.setChecked(true);
        } else if (mViewModel.difficulty == 0.5) {
            binding.hardDifficulty.setChecked(true);
        }

        binding.gameModeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == binding.normalMode.getId()) {
                mViewModel.mode = Mode.NORMAL;
            } else if (checkedId == binding.evilMode.getId()) {
                mViewModel.mode = Mode.EVIL;
            } else if (checkedId == binding.goodMode.getId()) {
                mViewModel.mode = Mode.GOOD;
            }
        });

        binding.difficultyGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == binding.easyDifficulty.getId()) {
                mViewModel.difficulty = 2.0;
            }
            else if(checkedId == binding.mediumDifficulty.getId()) {
                mViewModel.difficulty = 1.0;
            }
            else if(checkedId == binding.hardDifficulty.getId()) {
                mViewModel.difficulty = 0.5;
            }
        });

        binding.length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int length = Integer.parseInt(charSequence.toString());
                if (length >= 1 && length <= 20) {
                    mViewModel.length = length;
                } else {
                    Toast.makeText(requireContext(), "Please enter a valid number between 1 and 20!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return view;
    }

}