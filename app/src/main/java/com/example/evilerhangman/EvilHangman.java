package com.example.evilerhangman;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EvilHangman {
    public MutableLiveData<Integer> livesLeft;
    private int wordLength;
    private double partialLives = 0;
    public MutableLiveData<String> revealedWord;
    public MutableLiveData<ArrayList<Character>> guessedLetters;
    public ArrayList<String> words;
    public String word;
    Mode mode;

    public EvilHangman(InputStream stream, int wordLength, int lives, Mode mode) throws IOException {
        livesLeft = new MutableLiveData<>(lives);
        this.wordLength = wordLength;
        this.mode = mode;
        guessedLetters = new MutableLiveData<>(new ArrayList<>());
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        words = new ArrayList<>();
        String line = br.readLine();
        while(line != null) {
            if(line.length() == wordLength) {
                words.add(line);
            }
            line = br.readLine();
        }
        br.close();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            sb.append('_');
        }
        if(this.mode == Mode.NORMAL) {
            word = words.get(new Random().nextInt(words.size()));
        }
        this.revealedWord = new MutableLiveData<>(sb.toString());
    }
    public boolean guess(Character letter, Double difficulty) {
        if(guessedLetters.getValue().contains(letter)) { // should be checking for nulls
            return false;
        }
        ArrayList<Character> newGuessedLetters = guessedLetters.getValue();
        newGuessedLetters.add(letter);
        guessedLetters.setValue(newGuessedLetters);
        HashMap<String, ArrayList<String>> wordFamilies = new HashMap<String, ArrayList<String>>();
        for(String word: words) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < wordLength; i++) {
                sb.append('_');
            }
            for (Character guessedLetter : guessedLetters.getValue()) {
                int index = word.indexOf(guessedLetter);
                while(index >= 0) {
                    sb.setCharAt(index, guessedLetter);
                    index = word.indexOf(guessedLetter, index + 1);
                }
            }
            if(!wordFamilies.containsKey(sb.toString())) {
                wordFamilies.put(sb.toString(), new ArrayList<>());
            }
            wordFamilies.get(sb.toString()).add(word);
            Log.d("HANGMAN", word + ": " + sb.toString());
        }
        String newFamily = "";
        int biggestFamilySize = -1;
        int smallestFamilySize = words.size();
        for(Map.Entry<String, ArrayList<String>> entry: wordFamilies.entrySet()) {
            switch(mode) {
                case EVIL:
                    if (entry.getValue().size() > biggestFamilySize) {
                        newFamily = entry.getKey();
                        biggestFamilySize = entry.getValue().size();
                    }
                    break;
                case GOOD:
                    if (entry.getValue().size() < smallestFamilySize) {
                        newFamily = entry.getKey();
                        smallestFamilySize = entry.getValue().size();
                    }
                    break;
                case NORMAL:
                    if (entry.getValue().contains(this.word)) {
                        newFamily = entry.getKey();
                    }
                    break;
            }
        }
        if(newFamily.indexOf(letter) == -1) {
            if(livesLeft != null) {
                partialLives += difficulty;

                if(partialLives >= 1.0) {
                    int livesToLose = (int) partialLives;
                    partialLives -= livesToLose;
                    livesLeft.setValue(livesLeft.getValue() - livesToLose);
                }
            }
        }
        revealedWord.setValue(newFamily);
        words = wordFamilies.get(newFamily);
        Log.d("HANGMAN", "New family: " + newFamily + " (" + biggestFamilySize + ")");
        word = words.get(new Random().nextInt(words.size()));
        return livesLeft.getValue() == 0 || revealedWord.getValue().indexOf('_') == -1;
    }
    public boolean hasWon() {
        if(livesLeft.getValue() == 0) {
            return false;
        }
        if(revealedWord.getValue().indexOf('_') == -1) {
            return true;
        }
        throw new RuntimeException("Game hasn't finished yet");
    }
}
