package com.marcyliao.game.tapthetile.com.model.tile;

import com.marcyliao.game.tapthetile.com.model.component.Color;

/**
 * Created by marcy on 2014-09-14.
 */
public class ColorTile implements Tile {
    private Color color;

    public ColorTile() {}

    public ColorTile(Color color){
        setColor(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Type getType() {
        return Type.COLOR;
    }
}
