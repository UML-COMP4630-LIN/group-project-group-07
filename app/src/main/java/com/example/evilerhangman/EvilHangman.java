package com.example.evilerhangman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class EvilHangman {
    public int lives_left, word_length;
    public String word_family, revealed_word;
    public ArrayList<Character> guessed_letters;
    ArrayList<String> words;

    public EvilHangman(int word_length, int lives) throws IOException {
        lives_left = lives;
        this.word_length = word_length;
        guessed_letters = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("words_alpha.txt"));
        ArrayList<String> words = new ArrayList<>();
        try {
            String line = br.readLine();
            while(line != null) {
                if(line.length() == word_length) {
                    words.add(line);
                }
                line = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            br.close();
        }
    }
    public boolean guess(Character letter) {
        if(guessed_letters.contains(letter)) {
            return false;
        }
        HashMap<String, ArrayList<String>> word_families = new HashMap<String, ArrayList<String>>();
        for(String word: words) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < word_length; i++) {
                sb.append('_');
            }
            for (Character guessed_letter : guessed_letters) {
                int index = word.indexOf(guessed_letter);
                while(index >= 0) {
                    sb.setCharAt(index, guessed_letter);
                    index = word.indexOf(letter, index + 1);
                }
            }
            word_families.get(word_family).add(word);
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
            lives_left--;
        }
        revealed_word = biggest_family;
        return lives_left == 0 || biggest_family_size == 1;
    }
    public boolean hasWon() {
        if(lives_left == 0) {
            return false;
        }
        if(revealed_word.indexOf('_') == -1) {
            return true;
        }
        throw new RuntimeException("Game hasn't finished yet");
    }
}
