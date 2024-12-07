package com.example.evilerhangman;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.io.InputStream;

public class MultiplayerViewModel extends AndroidViewModel {
    public EvilHangman game;
    private int word_length, lives;
    public MultiplayerViewModel(@NonNull Application application, int word_length, int lives) {
        super(application);
        this.word_length = word_length;
        this.lives = lives;
        initializeGame();
    }

    private void initializeGame() {
        try {
            InputStream is = getApplication().getAssets().open("words_alpha.txt");
            game = new EvilHangman(is, word_length, lives, Mode.NORMAL);
        } catch(IOException e) {
            Log.d("HANGMAN", e.getMessage());
        }
    }

    public void reset(int word_length, int lives) {
        Log.d("HANGMAN", "Resetting MultiplayerViewModel");
        this.word_length = word_length;
        this.lives = lives;
        initializeGame();
    }
}