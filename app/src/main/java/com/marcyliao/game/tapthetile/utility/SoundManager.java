package com.marcyliao.game.tapthetile.utility;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.marcyliao.game.tapthetile.R;

import java.util.HashMap;

public class SoundManager {

    static private SoundManager _instance;
    private static SoundPool mSoundPool;
    private static HashMap<Integer, Integer> mSoundPoolMap;
    private static AudioManager mAudioManager;
    private static Context mContext;

    private static boolean soundSwitch = true;

    private SoundManager()
    {
    }

    /**
     * Requests the instance of the Sound Manager and creates it
     * if it does not exist.
     *
     * @return Returns the single instance of the SoundManager
     */
    static synchronized public SoundManager getInstance()
    {
        if (_instance == null)
            _instance = new SoundManager();
        return _instance;
    }

    /**
     * Initialises the storage for the sounds
     *
     * @param theContext The Application context
     */
    public static  void initSounds(Context theContext)
    {
        mContext = theContext;
        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        mSoundPoolMap = new HashMap<Integer, Integer>();
        mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * Add a new Sound to the SoundPool
     *
     * @param Index - The Sound Index for Retrieval
     * @param SoundID - The Android ID for the Sound asset.
     */
    public static void addSound(int Index,int SoundID)
    {
        mSoundPoolMap.put(Index, mSoundPool.load(mContext, SoundID, 1));
    }

    /**
     * Loads the various sound assets
     * Currently hardcoded but could easily be changed to be flexible.
     */
    public static void loadSounds()
    {
        mSoundPoolMap.put(R.raw.click, mSoundPool.load(mContext, R.raw.click, 1));
        mSoundPoolMap.put(R.raw.correct, mSoundPool.load(mContext, R.raw.correct, 1));
        mSoundPoolMap.put(R.raw.incorrect, mSoundPool.load(mContext, R.raw.incorrect, 1));
        mSoundPoolMap.put(R.raw.gameover, mSoundPool.load(mContext, R.raw.gameover, 1));
    }

    /**
     * Plays a Sound
     *
     * @param res - The id of the resource
     * @param speed - The Speed to play not, not currently used but included for compatibility
     */
    public static void playSound(int res,float speed)
    {
        if(soundSwitch) {
            float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            mSoundPool.play(mSoundPoolMap.get(res), streamVolume, streamVolume, 1, 0, speed);
        }
    }

    /**
     * Plays a Sound
     *
     * @param res - The id of the resource
     */
    public static void playSound(int res)
    {
        playSound(res,1.0f);
    }

    /**
     * Stop a Sound
     * @param index - index of the sound to be stopped
     */
    public static void stopSound(int index)
    {
        mSoundPool.stop(mSoundPoolMap.get(index));
    }

    /**
     * Deallocates the resources and Instance of SoundManager
     */
    public static void cleanup()
    {
        mSoundPool.release();
        mSoundPool = null;
        mSoundPoolMap.clear();
        mAudioManager.unloadSoundEffects();
        _instance = null;

    }

    public static boolean isSoundSwitch() {
        return soundSwitch;
    }

    public static void setSoundSwitch(boolean soundSwitch) {
        SoundManager.soundSwitch = soundSwitch;
    }
}
