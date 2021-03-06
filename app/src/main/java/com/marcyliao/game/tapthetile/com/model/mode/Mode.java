package com.marcyliao.game.tapthetile.com.model.mode;

import com.marcyliao.game.tapthetile.com.model.tile.Tile;

/**
 * Created by marcy on 2014-09-12.
 */
public interface Mode {
    public final static int MIXED = 0;
    public final static int COLOR = 1;
    public final static int CHAR = 2;
    public final static int ALL = 4;

    public final static int NUM_MODES = 3;

    public Tile getCorrectTile();
    public Tile getWrongTile();
    public void setCurrentLevel(int level);
    public int getRecoverTime(int level);



}
