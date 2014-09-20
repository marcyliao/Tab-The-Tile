package com.marcyliao.game.finddifference.com.model.mode;

import com.marcyliao.game.finddifference.com.model.component.Color;
import com.marcyliao.game.finddifference.com.model.tile.CharTile;
import com.marcyliao.game.finddifference.com.model.tile.ColorTile;
import com.marcyliao.game.finddifference.com.model.tile.Tile;

/**
 * Created by marcy on 2014-09-14.
 */
public class ColorMode implements Mode{

    private ColorTile wrongTile;
    private ColorTile correctTile;

    @Override
    public Tile getCorrectTile() {
        return correctTile;
    }

    @Override
    public Tile getWrongTile() {
        return wrongTile;
    }

    @Override
    public void setCurrentLevel(int level) {
        correctTile = new ColorTile();
        wrongTile = new ColorTile();

        int r = rand10To250();
        int g = rand10To250();
        int b = rand10To250();
        Color wrongColor = new Color(255, r, g, b);

        int diff = 12 - level/10;
        if(diff < 5){
            diff = 5;
        }
        boolean dir = Math.random() > 0.5;
        Color correctColor = new Color(255, randDiff255(r, diff, dir), randDiff255(g, diff, dir), randDiff255(b, diff, dir));

        wrongTile.setColor(wrongColor);
        correctTile.setColor(correctColor);
    }

    @Override
    public int getRecoverTime(int level) {
        return 400000+10000*level;
    }


    private int rand10To250() {
        return (int) Math.round(10+240 * Math.random());
    }

    private int randDiff255(int c, int diff, boolean dir) {
        int newC;
        if(dir) {
            newC = c+diff;
            newC = (newC >= 255? 255: newC);
        }
        else {
            newC = c-diff;
            newC = (newC <= 0? 0: newC);
        }

        return newC;
    }
}
