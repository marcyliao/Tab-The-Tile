package com.marcyliao.game.tapthetile.core.controller.listener;

import com.marcyliao.game.tapthetile.com.model.tile.Tile;

/**
 * Created by mac on 2014-09-14.
 */
public interface GameListener {
    public void onBestLevelUpdated(int tempBestLevel);
    public void onPaused();
    public void  onProgressUpdated(int remainMilliseconds);
    public void onGameOver();
    public void onGameStart();
    public void onRefreshLevel(int level, int column, int trueItemIndex, Tile correctTile, Tile wrongTile);
    public void onColumnCountUpdated(int columnCount);
    public void onLevelUpdated(int level);
    public void onResume();
}
