/*
EvilHangman.java
This file contains the EvilHangman class, which handles the game logic. An instance is created in the GameScreenViewModel.
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
    - stream: Contains the list of words to be used (all of them, not just ones of a particular length).
    - wordLength: The word length to be used.
    - lives: The amount of lives the user will have.
    - mode: EVIL, NORMAL, GOOD, or MULTIPLAYER. Changes how the next word family is determined: EVIL picks the smallest one, GOOD picks the largest one, NORMAL picks a word at the beginning and chooses whatever family has that word in it, and MULTIPLAYER picks whatever family the other player's chosen word is.
    */
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
    /*
    guess
    Contains the logic to guess a letter in the word.
    Parameters:
    - letter: The letter the user is guessing.
    Returns:
    A boolean saying whether or not the game is over (but not whether they have won or lost, just if either of them are true).
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
                livesLeft.setValue(livesLeft.getValue() - 1);
            }
        }
        revealedWord.setValue(newFamily);
        words = wordFamilies.get(newFamily);
        Log.d("HANGMAN", "New family: " + newFamily + " (" + biggestFamilySize + ")");
        word = words.get(new Random().nextInt(words.size()));
        return livesLeft.getValue() == 0 || revealedWord.getValue().indexOf('_') == -1;
    }
    /*
    hasWon
    Determines whether or not the user has won. Should only be called when the game is over (i.e. when guess returns true).
    Parameters:
    None
    Returns:
    Whether the user has won (true) or lost (false).
    */
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
