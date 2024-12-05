/*
GameScreenViewModel.java
This file contains the code for the game screen view model, which handles the logic of the game screen. It contains an instance of the EvilHangman class and is constructed by the GameScreenViewModelFactory.
*/

package com.example.evilerhangman;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.io.InputStream;

public class GameScreenViewModel extends AndroidViewModel {
    public EvilHangman game;
    /*
    GameScreenViewModel constructor
    Creates an EvilHangman instance based on arguments it received from the factory.
    Parameters:
    - application: The application context. Needed so it can read from a file.
    - wordLength: The length of the word the user will have to guess.
    - lives: The amount of lives the user will have.
    */
    public GameScreenViewModel(@NonNull Application application, int wordLength, int lives) {
        super(application);
        try {
            InputStream is = getApplication().getAssets().open("words_alpha.txt");
            game = new EvilHangman(is, wordLength, lives);
        } catch(IOException e) {
            Log.d("HANGMAN", e.getMessage());
        }
    }
}