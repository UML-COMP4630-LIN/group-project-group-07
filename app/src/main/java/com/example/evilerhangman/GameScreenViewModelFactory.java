package com.example.evilerhangman;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GameScreenViewModelFactory implements ViewModelProvider.Factory {
    private int word_length, lives;
    private Application application;
    GameScreenViewModelFactory(@NonNull Application application, int word_length, int lives) {
        this.application = application;
        this.word_length = word_length;
        this.lives = lives;
    }
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == GameScreenViewModel.class) {
            return (T)new GameScreenViewModel(this.application, this.word_length, this.lives);
        }
        return null;
    }
}