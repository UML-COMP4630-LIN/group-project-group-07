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
    private double difficulty;
    private int word_length;
    public MultiplayerViewModel(@NonNull Application application, int word_length, double difficulty) {
        super(application);
        this.word_length = word_length;
        this.difficulty = difficulty;
        initializeGame();
    }

    private void initializeGame() {
        try {
            InputStream is = getApplication().getAssets().open("words_alpha.txt");
            game = new EvilHangman(is, word_length, difficulty, Mode.NORMAL);
        } catch(IOException e) {
            Log.d("HANGMAN", e.getMessage());
        }
    }

    public void reset(int word_length, double difficulty) {
        Log.d("HANGMAN", "Resetting MultiplayerViewModel");
        this.word_length = word_length;
        this.difficulty = difficulty;
        initializeGame();
    }
}