package com.marcyliao.game.finddifference.utility;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by mac on 2014-09-16.
 */
public class SoundHelper {

    public static void sound(MediaPlayer mp) {
        mp.seekTo(0);
        mp.start();
    }
}
