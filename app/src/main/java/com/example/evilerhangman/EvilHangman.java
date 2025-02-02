/*
EvilHangman.java
This file contains the EvilHangman class, which contains the state and logic of the Evil Hangman game.
*/

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
    public MutableLiveData<String> revealedWord;
    public MutableLiveData<ArrayList<Character>> guessedLetters;
    public ArrayList<String> words;
    public String word;
    Mode mode;

    /*
    EvilHangman constructor
    Parameters:
    - stream: a stream containing all of the possible words to guess.
    - wordLength: the length of the word to be guessed.
    - difficulty: a multiplier of the user's number of lives.
    - mode: EVIL, NORMAL, or GOOD. See Mode.java.
    */
    public EvilHangman(InputStream stream, int wordLength, double difficulty, Mode mode) throws IOException {
        livesLeft = new MutableLiveData<>((int)(6 * difficulty));
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
            sb.append('-');
        }
        if(this.mode == Mode.NORMAL) {
            word = words.get(new Random().nextInt(words.size()));
        }
        this.revealedWord = new MutableLiveData<>(sb.toString());
    }
    /*
    guess
    The method for guessing a letter of the word.
    Parameters:
    - letter: the letter to be guessed.
    Returns:
    A boolean saying whether or not the game is over (but not whether the user has won or lost).
    */
    public boolean guess(Character letter) {
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
                sb.append('-');
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
                    if (entry.getValue().size() <= smallestFamilySize) {
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
                livesLeft.setValue(livesLeft.getValue() - 1);
            }
        }
        revealedWord.setValue(newFamily);
        words = wordFamilies.get(newFamily);
        Log.d("HANGMAN", "New family: " + newFamily + " (" + words.size() + ")");
        word = words.get(new Random().nextInt(words.size()));
        return livesLeft.getValue() == 0 || revealedWord.getValue().indexOf('-') == -1;
    }
    /*
    hasWon
    Determines whether the user won or lost.
    Parameters:
    None.
    Returns:
    True if the user won the game, false if they lost, and throws an error if the game isn't over yet.
    */
    public boolean hasWon() {
        if(livesLeft.getValue() == 0) {
            return false;
        }
        if(revealedWord.getValue().indexOf('-') == -1) {
            return true;
        }
        throw new RuntimeException("Game hasn't finished yet");
    }
}
