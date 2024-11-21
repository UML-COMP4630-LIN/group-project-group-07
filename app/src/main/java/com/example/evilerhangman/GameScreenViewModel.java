package com.example.evilerhangman;

import androidx.lifecycle.ViewModel;

public class GameScreenViewModel extends ViewModel {
    private EvilHangman game;
    public GameScreenViewModel() {
        game = new EvilHangman(); // need to get args in here, need to make factory
    }
}