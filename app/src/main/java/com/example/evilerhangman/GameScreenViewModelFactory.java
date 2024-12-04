/*
GameScreenViewModelFactory.java
This file contains the GameScreenViewModel factory, which creates instances of the GameScreenViewModel. This is necessary so that arguments from the user (settings) can be passed to the view model.
*/

package com.example.evilerhangman;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class GameScreenViewModelFactory implements ViewModelProvider.Factory {
    private int word_length, lives;
    private Application application;
    /*
    GameScreenViewModelFactory constructor
    Parameters:
    - application: The application context. Needed so it can read from a file.
    - wordLength: The length of the word the user will have to guess.
    - lives: The amount of lives the user will have.
    */
    GameScreenViewModelFactory(@NonNull Application application, int word_length, int lives) {
        this.application = application;
        this.word_length = word_length;
        this.lives = lives;
    }
    /*
    create
    Creates the GameScreenViewModel.
    Parameters:
    - modelClass: Should be equal to the GameScreenViewModel class.
    Returns:
    An instance of the GameScreenViewModel.
    */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == GameScreenViewModel.class) {
            return (T)new GameScreenViewModel(this.application, this.word_length, this.lives);
        }
        return null;
    }
}
