package com.example.evilerhangman;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import java.io.IOException;

public class GameScreenViewModel extends ViewModel {
    public EvilHangman game;
    public GameScreenViewModel(int word_length, int lives) {
        try {
            game = new EvilHangman(word_length, lives);
        } catch(IOException e) {
            Log.d("HANGMAN", e.getMessage());
        }
    }
}