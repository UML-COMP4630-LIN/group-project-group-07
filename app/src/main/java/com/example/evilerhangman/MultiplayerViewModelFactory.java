package com.example.evilerhangman;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MultiplayerViewModelFactory implements ViewModelProvider.Factory {
    private int word_length;
    private double difficulty;
    private Application application;

    MultiplayerViewModelFactory(@NonNull Application application, int word_length, double difficulty) {
        this.application = application;
        this.word_length = word_length;
        this.difficulty = difficulty;
    }
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == MultiplayerViewModel.class) {
            Log.d("HANGMAN", "CREATING NEW MULTIPLAYERVIEWMODEL");
            return (T)new MultiplayerViewModel(this.application, this.word_length, this.difficulty);
        }
        return null;
    }

}
