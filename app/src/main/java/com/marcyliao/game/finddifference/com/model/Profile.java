package com.marcyliao.game.finddifference.com.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.marcyliao.game.finddifference.R;
import com.marcyliao.game.finddifference.com.model.mode.Mode;

/**
 * Created by marcy on 2014-09-12.
 */
public class Profile {
    private static String PROFILE = "PROFILE";
    private static String BEST_MIXED_MODE = "BEST_MIXED_MODE";
    private static String BEST_COLOR_MODE = "BEST_COLOR_MODE";
    private static String BEST_CHAR_MODE = "BEST_CHAR_MODE";
    private static String CURRENT_MODE = "CURRENT_MODE";

    private int bestOfMixedMode;
    private int bestOfColorMode;
    private int bestOfCharMode;
    private int currentMode;

    public static Profile loadProfile(Context context) {
        return new Profile().load(context);
    }

    public Profile load(Context context) {
        SharedPreferences profilePreference;
        profilePreference = context.getSharedPreferences(PROFILE, 0);

        bestOfMixedMode = profilePreference.getInt(BEST_MIXED_MODE,0);
        bestOfColorMode = profilePreference.getInt(BEST_COLOR_MODE,0);
        bestOfCharMode = profilePreference.getInt(BEST_CHAR_MODE,0);
        currentMode = profilePreference.getInt(CURRENT_MODE, Mode.COLOR);

        return this;

    }

    public void save(Context context) {
        SharedPreferences profilePreference;
        profilePreference = context.getSharedPreferences(PROFILE, 0);

        profilePreference.edit().putInt(BEST_MIXED_MODE, bestOfMixedMode)
                .putInt(BEST_COLOR_MODE, bestOfColorMode)
                .putInt(BEST_CHAR_MODE, bestOfCharMode)
                .putInt(CURRENT_MODE, currentMode)
                .commit();
    }

    public int getBestOfMixedMode() {
        return bestOfMixedMode;
    }

    public void setBestOfMixedMode(int bestOfMixedMode) {
        this.bestOfMixedMode = bestOfMixedMode;
    }

    public int getBestOfColorMode() {
        return bestOfColorMode;
    }

    public void setBestOfColorMode(int bestOfColorMode) {
        this.bestOfColorMode = bestOfColorMode;
    }

    public int getBestOfCharMode() {
        return bestOfCharMode;
    }

    public void setBestOfCharMode(int bestOfCharMode) {
        this.bestOfCharMode = bestOfCharMode;
    }

    public int getCurrentMode() {
        return currentMode;
    }
    public String getCurrentModeName(Context context) {
        return getModeName(getCurrentMode(),context);
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }

    public int getBestOfCurrentMode(){
        switch (currentMode) {
            case Mode.COLOR:
                return bestOfColorMode;
            case Mode.CHAR:
                return bestOfCharMode;
            case Mode.MIXED:
                return bestOfMixedMode;
        }

        return Mode.MIXED;
    }

    public void setBestOfCurrentMode(int newBest) {
        switch (currentMode) {
            case Mode.COLOR:
                setBestOfColorMode(newBest);
                break;
            case Mode.CHAR:
                setBestOfCharMode(newBest);
                break;
            case Mode.MIXED:
                setBestOfMixedMode(newBest);
                break;
        }
    }

    public static String getModeName(int mode, Context context) {
        if(mode == Mode.MIXED)
            return context.getString(R.string.mixed);
        else if (mode == Mode.COLOR)
            return context.getString(R.string.color);
        else if (mode == Mode.CHAR)
            return context.getString(R.string.character);
        else
            return null;
    }
}
