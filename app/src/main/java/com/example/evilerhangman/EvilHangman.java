package com.example.evilerhangman;

import androidx.lifecycle.MutableLiveData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EvilHangman {
    public MutableLiveData<Integer> lives_left;
    private int word_length;
    public MutableLiveData<String> revealed_word;
    public MutableLiveData<ArrayList<Character>> guessed_letters;
    ArrayList<String> words;

    public EvilHangman(int word_length, int lives) throws IOException {
        lives_left = new MutableLiveData<>(lives);
        this.word_length = word_length;
        guessed_letters = new MutableLiveData<>(new ArrayList<>());
        BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("words_alpha.txt"), "UTF-8")); // fails to read file
        ArrayList<String> words = new ArrayList<>();
        String line = br.readLine();
        while(line != null) {
            if(line.length() == word_length) {
                words.add(line);
            }
            line = br.readLine();
        }
        br.close();
    }
    public boolean guess(Character letter) {
        if(guessed_letters.getValue().contains(letter)) { // should be checking for nulls
            return false;
        }
        HashMap<String, ArrayList<String>> word_families = new HashMap<String, ArrayList<String>>();
        for(String word: words) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < word_length; i++) {
                sb.append('_');
            }
            for (Character guessed_letter : guessed_letters.getValue()) {
                int index = word.indexOf(guessed_letter);
                while(index >= 0) {
                    sb.setCharAt(index, guessed_letter);
                    index = word.indexOf(letter, index + 1);
                }
            }
            word_families.get(sb.toString()).add(word);
        }
        String biggest_family = "";
        int biggest_family_size = -1;
        for(Map.Entry<String, ArrayList<String>> entry: word_families.entrySet()) {
            if(entry.getValue().size() > biggest_family_size) {
                biggest_family = entry.getKey();
                biggest_family_size = entry.getValue().size();
            }
        }
        if(biggest_family.indexOf(letter) == -1) {
            if(lives_left != null) {
                lives_left.setValue(lives_left.getValue() - 1);
            }
        }
        revealed_word.setValue(biggest_family);
        return lives_left.getValue() == 0 || biggest_family_size == 1;
    }
    public boolean hasWon() {
        if(lives_left.getValue() == 0) {
            return false;
        }
        if(revealed_word.getValue().indexOf('_') == -1) {
            return true;
        }
        throw new RuntimeException("Game hasn't finished yet");
    }
}
