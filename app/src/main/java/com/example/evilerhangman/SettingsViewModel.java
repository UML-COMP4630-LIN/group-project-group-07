/*
SettingsViewModel.java
This file contains the code for the settings screen's ViewModel.
*/

package com.example.evilerhangman;

import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    public double difficulty = 1.0;
    public Mode mode = Mode.EVIL;
    public int length = 7;
}