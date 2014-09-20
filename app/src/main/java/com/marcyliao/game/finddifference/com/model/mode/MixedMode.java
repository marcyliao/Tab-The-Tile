package com.marcyliao.game.finddifference.com.model.mode;

import com.marcyliao.game.finddifference.com.model.tile.Tile;

/**
 * Created by mac on 2014-09-14.
 */
public class MixedMode implements Mode{
    private CharMode charMode;
    private ColorMode colorMode;
    private Mode currentMode;

    public MixedMode() {
        charMode = new CharMode();
        colorMode = new ColorMode();
    }

    @Override
    public Tile getCorrectTile() {
        return currentMode.getCorrectTile();
    }

    @Override
    public Tile getWrongTile() {
        return currentMode.getWrongTile();
    }

    @Override
    public void setCurrentLevel(int level) {
        if(Math.random() > 0.5) {
            currentMode = charMode;
        }
        else {
            currentMode = colorMode;
        }

        currentMode.setCurrentLevel(level);
    }

    @Override
    public int getRecoverTime(int level) {
        return currentMode.getRecoverTime(level);
    }
}
