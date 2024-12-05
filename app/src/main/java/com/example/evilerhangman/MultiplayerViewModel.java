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
    public MultiplayerViewModel(@NonNull Application application, int word_length, int lives) {
        super(application);
        try {
            InputStream is = getApplication().getAssets().open("words_alpha.txt");
            game = new EvilHangman(is, word_length, lives);
        } catch(IOException e) {
            Log.d("HANGMAN", e.getMessage());
        }
    }
}