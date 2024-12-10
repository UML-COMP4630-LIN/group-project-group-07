/*
Mode.java
This file contains the enum for the Evil Hangman modes.
EVIL: The word family changes to the biggest possible one.
GOOD: The word family changes to the smallest possible one.
NORMAL: A random word is picked at the beginning of the game, and whatever family it is in is chosen.
*/

package com.example.evilerhangman;

public enum Mode {
    EVIL,
    GOOD,
    NORMAL
}
