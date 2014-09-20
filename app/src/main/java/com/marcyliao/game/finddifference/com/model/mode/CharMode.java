package com.marcyliao.game.finddifference.com.model.mode;

import com.marcyliao.game.finddifference.com.model.component.Color;
import com.marcyliao.game.finddifference.com.model.tile.CharTile;
import com.marcyliao.game.finddifference.com.model.tile.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marcy on 2014-09-14.
 */
public class CharMode implements Mode{

    private CharTile wrongTile;
    private CharTile correctTile;

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
        wrongTile = new CharTile();
        correctTile = new CharTile();

        // decide text of correct and wrong tiles
        decideText(level);

        // decide color of correct and wrong tiles
        decideTextColorAndBackgroundColor();

    }

    private void decideTextColorAndBackgroundColor() {
        Color textColor;
        Color backgroundColor;

        int index = (int)((goodColors.length-1)*Math.random()/2)*2;
        double seed = Math.random();
        if(seed>0.5){
            textColor = goodColors[index];
            backgroundColor = goodColors[index+1];
        }
        else {
            textColor =  goodColors[index+1];
            backgroundColor = goodColors[index];
        }

        wrongTile.setBackgroundColor(backgroundColor);
        correctTile.setBackgroundColor(backgroundColor);
        wrongTile.setTextColor(textColor);
        correctTile.setTextColor(textColor);
    }

    private void decideText(int level) {

        int begin;
        int end;
        int numCharParis = charSet.length/2;
        if(level < 20) {
            begin = 0;
            end = numCharParis/4 - 1;
        }
        else if (level < 40) {
            begin = numCharParis/4 - 1;
            end = 3*numCharParis/4 - 1;
        }
        else if (level < 100) {
            begin = 2 * numCharParis/4 - 1;
            end = numCharParis - 1;
        }
        else {
            begin = 0;
            end = numCharParis - 1;
        }


        int index = (begin + (int)Math.round(Math.random()*(end-begin)))*2;
        if(index < 0)
            index = 0;
        if(index >= charSet.length)
            index = charSet.length - 2;

        if(Math.random() > 0.5) {
            correctTile.setText(charSet[index]);
            wrongTile.setText(charSet[index + 1]);
        }
        else {
            correctTile.setText(charSet[index + 1]);
            wrongTile.setText(charSet[index]);
        }
    }

    public int getRecoverTime(int level) {
        int recoverTIme = (int)((level/12.0+1)*1000000);
        if(recoverTIme > 4500000)
            recoverTIme = 4500000;
        return recoverTIme;
    }

    private String[] charSet = {
            "h","b",
            "c","d",
            "0","O",
            "3","B",
            "9","g",
            "8","B",
            "6","b",
            "A","4",
            "Ф","ф",
            "ы","ю",
            "Б","б",
            "0","O",
            "k","K",
            "P","R",
            "モ","そ",
            "グ","ゾ",
            "キ","テ",
            "ま","き",
            "ぜ","ゼ",
            "の","り",
            "た","な",
            "ば","は",
            "亼","大",
            "尴","尬",
            "灿","烂",
            "う","え",
            "ク","ケ",
            "井","开",
            "上","下",
            "芉","芏",
            "召","叧",
            "吞","吴",
            "凸","凹",
            "辉","煌",
            "お","あ",
            "圷","圵",
            "夫","矢",
            "孒","予",
            "鸟","乌",
            "左","右",
            "自","目",
            "日","目",
            "考","老",
            "甲","由",
            "o","O",
            "乒","乓",
            "冈","凶",
            "麒","麟",
            "雷","霆",
            "ـس","ـش",
            "ـک","ـگ",
            "ـژ","ـز",
            "ـظ","ـط",
            "人","入",
            "ش","س",
            "ك","ق",
            "ي","ى",
            "己","已",
            "子","孓",
            "q","p",
            "卍","卐",
            "ـب","ـپ",
            "ـت","ـث",
            "ﺞ","ﭻ",
            "ـغ","ـع",
            "ـو","ـه",
            "Ã","Ä",
            "Ć","Ĉ",
            "孑","孓",
            "Β","Б",
            "Â","Ã",
            "버","바",
            "브","보",
            "다","더",
            "후","호",
            "히","허",
            "사","서",
            "룹","몹",
            "몰","솔",
            "卍","卐",
            "릿","릇",
            "받","벋",
            "氷","永",
            "下","卞",
            "万","方",
            "夭","天",
            "六","穴",
            "犬","太",
            "駄","䭾",
            "鞎","珢",
            "ぬ","ね",
            "饕","餮",
            "耄","耋",
            "未","末",
            "亾","兦",
            "魍","魉",
            "魑","魅",
            "幣","弊",
            "土","士",
            "ビ","ピ",
            "ぽ","ぱ"
    };

    private Color[] goodColors = {
            new Color(255,84,115,135),new Color(255,248,232,157),
            new Color(255,90,13,67),new Color(255,244,222,41),
            new Color(255,87,96,105),new Color(255,247,68,97),
            new Color(255,23,50,7),new Color(255,230,180,80),
            new Color(255,255,94,72),new Color(255,0,34,40),
            new Color(255,53,44,10),new Color(255,255,255,255),
            new Color(255,82,75,46),new Color(255,171,92,37),
            new Color(255,56,13,49),new Color(255,114,111,128),
            new Color(255,170,138,87),new Color(255,113,175,164),
            new Color(255,101,147,74),new Color(255,160,191,124),
            new Color(255,217,116,43),new Color(255,230,180,80),
            new Color(255,18,53,85),new Color(255,209,73,78),
            new Color(255,230,155,3),new Color(255,219,208,167)
    };
}
