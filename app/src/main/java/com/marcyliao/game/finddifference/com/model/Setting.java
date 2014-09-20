package com.marcyliao.game.finddifference.com.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by marcy on 2014-09-20.
 */
public class Setting {
    private static String SETTING = "SETTING";
    private static String SOUND = "SOUND";

    private boolean soundSwitch;

    public static Setting loadSetting(Context context) {
        return new Setting().load(context);
    }

    public Setting load(Context context) {
        SharedPreferences profilePreference;
        profilePreference = context.getSharedPreferences(SETTING, 0);

        soundSwitch = profilePreference.getBoolean(SOUND, true);

        return this;

    }

    public void save(Context context) {
        SharedPreferences profilePreference;
        profilePreference = context.getSharedPreferences(SETTING, 0);

        profilePreference.edit().putBoolean(SOUND, soundSwitch).commit();
    }

    public boolean isSoundSwitch() {
        return soundSwitch;
    }

    public void setSoundSwitch(boolean soundSwitch) {
        this.soundSwitch = soundSwitch;
    }
}
