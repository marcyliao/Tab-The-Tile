package com.marcyliao.game.finddifference.core.controller;

import android.content.Context;
import android.os.Handler;

import com.marcyliao.game.finddifference.com.model.Profile;
import com.marcyliao.game.finddifference.com.model.mode.CharMode;
import com.marcyliao.game.finddifference.com.model.mode.ColorMode;
import com.marcyliao.game.finddifference.com.model.mode.MixedMode;
import com.marcyliao.game.finddifference.com.model.mode.Mode;
import com.marcyliao.game.finddifference.core.controller.listener.GameListener;

/**
 * Created by marcy on 2014-09-14.
 */
public class GameController {
    private int remainMilliseconds;
    private int level;
    private int tempBestLevel;
    private Mode mode;

    public static final int MAX_TIME = 10000000;
    private static final int INIT_REMAIN_TIME = 5000000;

    // status
    private GameStatus currentStatus;

    // listener
    private GameListener gameListener;

    // context
    private Context context;

    public GameController(Context context) {
        Profile profile = Profile.loadProfile(context);

        int currentMode = profile.getCurrentMode();
        if(currentMode == Mode.CHAR)
            mode = new CharMode();
        else if(currentMode == Mode.COLOR)
            mode = new ColorMode();
        else
            mode = new MixedMode();

        this.context = context;
    }

    public void chooseCorrect(){
        nextLevel();
    }

    public void chooseWrong() {
        remainMilliseconds -= 1000000;
        gameListener.onProgressUpdated(remainMilliseconds);
    }

    private void nextLevel() {
        level++;
        gameListener.onLevelUpdated(level);

        mode.setCurrentLevel(level);
        if(level > tempBestLevel) {
            tempBestLevel = level;
            gameListener.onBestLevelUpdated(tempBestLevel);
        }

        remainMilliseconds += getRecoverTime(level);
        if(remainMilliseconds >= 10000000)
            remainMilliseconds = 10000000;

        startLevel();
    }

    public void restartGame() {
        startGame();
    }

    private void startLevel() {
        mode.setCurrentLevel(level);
        int column = getColumnCount(level);
        int size = column*column;
        int trueItemIndex = (int) Math.round((size-1)*Math.random());

        gameListener.onStartLevel(level, column, trueItemIndex, mode.getCorrectTile(), mode.getWrongTile());
        gameListener.onColumnCountUpdated(getColumnCount(level));

    }

    public void startGame() {
        currentStatus = GameStatus.GOING;

        level = 1;
        gameListener.onLevelUpdated(level);

        remainMilliseconds = INIT_REMAIN_TIME;

        Profile profile = Profile.loadProfile(context);
        tempBestLevel = profile.getBestOfCurrentMode();
        gameListener.onBestLevelUpdated(tempBestLevel);

        startLevel();
        customHandler.postDelayed(updateTimerThread, 40);
        gameListener.onGameStart();

    }

    public void pauseGame() {
        currentStatus = GameStatus.PAUSED;
        gameListener.onPaused();

    }

    public void resumeGame() {
        restartLevel();
        currentStatus = GameStatus.GOING;
        customHandler.postDelayed(updateTimerThread, 40);
        gameListener.onResume();
    }

    private Handler customHandler = new Handler();
    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            if(currentStatus == GameStatus.GOING) {
                remainMilliseconds -= 40000;
                customHandler.postDelayed(this, 40);
                gameListener.onProgressUpdated(remainMilliseconds);
            }

            //game over
            if(remainMilliseconds < 0) {

                //stop and reset timer
                remainMilliseconds = 0;
                currentStatus = GameStatus.STOPPED;
                remainMilliseconds = INIT_REMAIN_TIME;

                // update best
                Profile profile = Profile.loadProfile(context);
                int bestLevel = profile.getBestOfCurrentMode();
                if(level > bestLevel) {
                    profile.setBestOfCurrentMode(level);
                    profile.save(context);
                    gameListener.onBestLevelUpdated(level);
                }

                gameListener.onGameOver();
            }
        }

    };

    private void restartLevel() {
        startLevel();
    }


    private int getRecoverTime(int level) {
        int recoverTIme = (int)((level/15.0+1)*1000000);
        if(recoverTIme > 4000000)
            recoverTIme = 4000000;
        return recoverTIme;
    }

    private int getColumnCount(int level) {
        int columns = 4 + level/10;
        if(columns > 8)
            columns = 8;
        return columns;
    }

    public void setGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }

    public enum GameStatus {
        GOING, PAUSED, STOPPED;
    }

    public GameStatus getCurrentStatus(){
        return currentStatus;
    }

}