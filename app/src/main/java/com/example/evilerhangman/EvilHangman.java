package com.example.evilerhangman;

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
    ArrayList<String> words;
    public String word;

    public EvilHangman(InputStream stream, int wordLength, int lives) throws IOException {
        livesLeft = new MutableLiveData<>(lives);
        this.wordLength = wordLength;
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
        this.revealedWord = new MutableLiveData<>(sb.toString());
    }
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
//            Log.d("HANGMAN", word + ": " + sb.toString());
        }
        String biggestFamily = "";
        int biggestFamilySize = -1;
        for(Map.Entry<String, ArrayList<String>> entry: wordFamilies.entrySet()) {
            if(entry.getValue().size() > biggestFamilySize) {
                biggestFamily = entry.getKey();
                biggestFamilySize = entry.getValue().size();
            }
        }
        if(biggestFamily.indexOf(letter) == -1) {
            if(livesLeft != null) {
                livesLeft.setValue(livesLeft.getValue() - 1);
            }
        }
        revealedWord.setValue(biggestFamily);
        words = wordFamilies.get(biggestFamily);
//        Log.d("HANGMAN", "New family: " + biggestFamily + " (" + biggestFamilySize + ")");
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
