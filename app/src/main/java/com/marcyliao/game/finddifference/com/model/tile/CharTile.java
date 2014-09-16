package com.marcyliao.game.finddifference.com.model.tile;

import com.marcyliao.game.finddifference.com.model.component.Color;

/**
 * Created by marcy on 2014-09-14.
 */
public class CharTile implements Tile {
    private String text;
    private Color textColor;
    private Color backgroundColor;

    public CharTile() {}

    public CharTile(String text, Color textColor, Color backgroundColor) {
        setText(text);
        setTextColor(textColor);
        setBackgroundColor(backgroundColor);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public Type getType() {
        return Type.CHAR;
    }
}
