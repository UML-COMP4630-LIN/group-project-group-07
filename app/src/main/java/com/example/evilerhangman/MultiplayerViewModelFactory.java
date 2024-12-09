/*
MultiplayerViewModelFactory.java
This file contains the code for the factory that produces the ViewModel used in multiplayer mode.
*/

package com.example.evilerhangman;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MultiplayerViewModelFactory implements ViewModelProvider.Factory {
    private int wordLength;
    private double difficulty;
    private Application application;

    /*
    MultiplayerViewModelFactory constructor
    Parameters:
    - application: The application context. Needed so it can read from a file.
    - wordLength: The length of the word player 1 will have to guess.
    - difficulty: A modifier for the amount of lives player 1 will have.
    */
    MultiplayerViewModelFactory(@NonNull Application application, int wordLength, double difficulty) {
        this.application = application;
        this.wordLength = wordLength;
        this.difficulty = difficulty;
    }

    /*
    create
    Creates the MultiplayerViewModelFactory.
    Parameters:
    - modelClass: Should be equal to the MultiplayerViewModelFactory class.
    Returns:
    An instance of the MultiplayerViewModelFactory.
    */
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == MultiplayerViewModel.class) {
            Log.d("HANGMAN", "CREATING NEW MULTIPLAYERVIEWMODEL");
            return (T)new MultiplayerViewModel(this.application, this.wordLength, this.difficulty);
        }
        return null;
    }

}
