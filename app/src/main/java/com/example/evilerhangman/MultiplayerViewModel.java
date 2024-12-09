/*
MultiplayerViewModel.java
This file contains the code for the ViewModel used in multiplayer mode.
*/

package com.example.evilerhangman;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import java.io.IOException;
import java.io.InputStream;

public class MultiplayerViewModel extends AndroidViewModel {
    public EvilHangman game;
    private double difficulty;
    private int wordLength;
    /*
    MultiplayerViewModel constructor
    Creates an instance of the MultiplayerViewModel.
    Parameters:
    - application: The application context. Needed so it can read from a file.
    - wordLength: The length of the word player 1 will have to guess.
    - difficulty: A modifier for the amount of lives player 1 will have.
    */
    public MultiplayerViewModel(@NonNull Application application, int wordLength, double difficulty) {
        super(application);
        this.wordLength = wordLength;
        this.difficulty = difficulty;
        initializeGame();
    }

    /*
    initializeGame
    Initializes the game when the ViewModel is created for the 1st time. Needed so it can be reset.
    Parameters:
    None.
    Returns:
    Nothing.
    */
    private void initializeGame() {
        try {
            InputStream is = getApplication().getAssets().open("words_alpha.txt");
            game = new EvilHangman(is, wordLength, difficulty, Mode.NORMAL);
        } catch(IOException e) {
            Log.d("HANGMAN", e.getMessage());
        }
    }

    /*
    reset
    Resets the game after someone wins. Needed as otherwise the game state would persist even after returning to the main menu.
    Parameters:
    - wordLength: The length of the word player 1 will have to guess.
    - difficulty: A modifier for the amount of lives player 1 will have.
    Returns:
    Nothing.
    */
    public void reset(int wordLength, double difficulty) {
        Log.d("HANGMAN", "Resetting MultiplayerViewModel");
        this.wordLength = wordLength;
        this.difficulty = difficulty;
        initializeGame();
    }
}