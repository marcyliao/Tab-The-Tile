package com.marcyliao.game.finddifference.com.model.mode;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import android.os.Handler;
import android.widget.Toast;

import com.marcyliao.game.finddifference.R;

public class MainGameActivity extends Activity {
    /*
    public static final int BOARD_MARGIN = 50;
    public static final int BOARD_PADDING = 10;
    public static final int CELL_MARGIN = 1;
    public static final int INIT_REMAIN_TIME = 5000000;

    //Game Properties
    private int remainMilliseconds;
    private int level;
    private int tempBestLevel;

    // Menus
    private RelativeLayout pauseMenuRelativeLayout;
    private RelativeLayout startMenuRelativeLayout;
    private RelativeLayout resultMenuRelativeLayout;
    private RelativeLayout gameMainLayout;

    // Game View
    private GridLayout gameBoardGridLayout;

    // TextView
    private TextView levelTextView;
    private TextView resultTextView;
    private TextView bestLevelTextView;
    private TextView tempBestLevelTextView;

    // Buttons
    private Button quickStartButton;
    private Button restartButton;
    private Button pauseButton;
    private Button resumeButton;
    private Button exitButton;
    private Button backToMainMenuButton;

    // Progress Bar
    private ProgressBar remainedTimeProgressBar;

    // settings
    private String SETTINGS = "SETTINGS";
    private String HIGHEST_LEVEL = "HIGHEST_LEVEL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);

        // init menus
        pauseMenuRelativeLayout = (RelativeLayout) findViewById(R.id.pauseMenuRelativeLayout);
        startMenuRelativeLayout = (RelativeLayout) findViewById(R.id.startMenuRelativeLayout);
        resultMenuRelativeLayout = (RelativeLayout) findViewById(R.id.resultMenuRelativeLayout);

        // other components
        levelTextView = (TextView) findViewById(R.id.levelTextView);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        bestLevelTextView = (TextView) findViewById(R.id.bestLevelTextView);
        tempBestLevelTextView = (TextView) findViewById(R.id.tempBestLevelTextView);

        remainedTimeProgressBar = (ProgressBar) findViewById(R.id.remainedTimeProgressBar);
        remainedTimeProgressBar.setMax(10000000);
        remainedTimeProgressBar.setProgress(remainMilliseconds);

        // init buttons
        quickStartButton = (Button) findViewById(R.id.quickStartButton);
        quickStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invisibleAllViews();
                gameMainLayout.setVisibility(View.VISIBLE);
                // start game
                startGame();
            }
        });

        restartButton = (Button) findViewById(R.id.restartButton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invisibleAllViews();
                gameMainLayout.setVisibility(View.VISIBLE);
                // restart game
                restartGame();
            }
        });

        pauseButton = (Button) findViewById(R.id.pauseButton);
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invisibleAllViews();
                pauseMenuRelativeLayout.setVisibility(View.VISIBLE);
                // pause game
                pauseGame();
            }
        });

        resumeButton = (Button) findViewById(R.id.resumeButton);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                invisibleAllViews();
                gameMainLayout.setVisibility(View.VISIBLE);
                // pause game
                resumeGame();
            }
        });

        exitButton = (Button) findViewById(R.id.exitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartMenuView();
                // pause game
                stopGame();
            }
        });

        backToMainMenuButton = (Button) findViewById(R.id.backToMainMenuButton);
        backToMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStartMenuView();
            }
        });

        // get the layout contains the game board
        gameMainLayout = (RelativeLayout) findViewById(R.id.gameMainRelativeLayout);

        // build game board
        gameBoardGridLayout = buildGameBoard();
        gameMainLayout.addView(gameBoardGridLayout);

        // init start menu
        showStartMenuView();
    }

    private void showStartMenuView() {

        invisibleAllViews();
        startMenuRelativeLayout.setVisibility(View.VISIBLE);

        SharedPreferences settings = getSharedPreferences(SETTINGS, 0);
        int bestLevel = settings.getInt(HIGHEST_LEVEL, 0);
        bestLevelTextView.setText("Best:"+bestLevel);

    }

    boolean start = false;
    private void stopGame() {
        start = false;
        remainMilliseconds = INIT_REMAIN_TIME;
        remainedTimeProgressBar.setProgress(remainMilliseconds);
    }

    private void restartGame() {
        startGame();
    }

    private void invisibleAllViews(){
        pauseMenuRelativeLayout.setVisibility(View.INVISIBLE);
        startMenuRelativeLayout.setVisibility(View.INVISIBLE);
        resultMenuRelativeLayout.setVisibility(View.INVISIBLE);
        gameMainLayout.setVisibility(View.INVISIBLE);
    }

    private void startGame() {
        start = true;
        pause = false;
        level = 1;
        remainMilliseconds = INIT_REMAIN_TIME;
        remainedTimeProgressBar.setProgress(remainMilliseconds);

        SharedPreferences settings = getSharedPreferences(SETTINGS, 0);
        tempBestLevel = settings.getInt(HIGHEST_LEVEL, 0);
        tempBestLevelTextView.setText(String.valueOf(tempBestLevel));

        startLevel();
        customHandler.postDelayed(updateTimerThread, 40);

    }

    private boolean pause = false;
    private void pauseGame() {
        pause = true;
    }

    private void resumeGame() {
        restartLevel();
        pause = false;
        customHandler.postDelayed(updateTimerThread, 40);
    }

    private Handler customHandler = new Handler();
    private Runnable updateTimerThread = new Runnable() {

        public void run() {
            if(!pause && start) {
                remainMilliseconds -= 40000;
                remainedTimeProgressBar.setProgress(remainMilliseconds);
                customHandler.postDelayed(this, 40);
            }

            //game over
            if(remainMilliseconds < 0) {
                remainMilliseconds = 0;
                stopGame();
                invisibleAllViews();

                SharedPreferences settings = getSharedPreferences(SETTINGS, 0);
                int bestLevel = settings.getInt(HIGHEST_LEVEL, 0);
                if(level > bestLevel) {
                    settings.edit().putInt(HIGHEST_LEVEL,level).commit();
                }

                resultMenuRelativeLayout.setVisibility(View.VISIBLE);
                resultTextView.setText("You passed "+level+" levels.\nRanked #1 in all the plays.");
            }
        }

    };

    private void restartLevel() {
        startLevel();
    }

    private void startLevel() {
        levelTextView.setText(String.valueOf(level));
        if(gameBoardGridLayout.getChildCount() > 0)
            gameBoardGridLayout.removeAllViews();
        gameBoardGridLayout.setRowCount(getColumnCount(level));
        gameBoardGridLayout.setColumnCount(getColumnCount(level));

        int size = getColumnCount(level)*getColumnCount(level);
        int trueItemIndex = (int) Math.round((size-1)*Math.random());

        double seed = Math.random();
        if(seed > 0.5)
            startColorLevel(size, trueItemIndex);
        else
            startCharLevel(size, trueItemIndex);

    }
    private void startCharLevel(int size, int trueItemIndex) {
        Map<String, Integer> colors = getCharLevelColors();
        Map<String, String> chars = getChars(level);
        for(int i=0; i < size; i++) {
            Button button = new Button(this);
            GradientDrawable shape =  new GradientDrawable();
            shape.setCornerRadius(8);
            shape.setColor(colors.get("background"));
            button.setBackgroundDrawable(shape);
            button.setTextColor(colors.get("char"));

            // level specif
            if(i==trueItemIndex) {
                button.setText(chars.get("right"));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startNextLevel();
                    }
                });
            }
            else {
                button.setText(chars.get("wrong"));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        remainMilliseconds -= 1000000;
                    }
                });
            }

            //common
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMargins(CELL_MARGIN, CELL_MARGIN, CELL_MARGIN, CELL_MARGIN);
            button.setLayoutParams(params);
            button.setHeight(getCellLength());
            button.setWidth(getCellLength());
            button.setPadding(0,0,0,0);
            button.setTextSize(getTextSize(level));


            gameBoardGridLayout.addView(button);
        }
    }

    private void startColorLevel(int size, int trueItemIndex) {
        Map<String, Integer> colors = getColorLevelColors(level);
        for(int i=0; i < size; i++) {
            Button button = new Button(this);

            // level specific
            if(i==trueItemIndex) {
                GradientDrawable shape =  new GradientDrawable();
                shape.setCornerRadius(8);
                shape.setColor(colors.get("right"));
                button.setBackgroundDrawable(shape);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startNextLevel();
                    }
                });
            }
            else {
                GradientDrawable shape =  new GradientDrawable();
                shape.setCornerRadius(8);
                shape.setColor(colors.get("other"));
                button.setBackgroundDrawable(shape);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        remainMilliseconds -= 1000000;
                    }
                });
            }

            //common
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMargins(CELL_MARGIN, CELL_MARGIN, CELL_MARGIN, CELL_MARGIN);
            button.setLayoutParams(params);
            button.setHeight(getCellLength());
            button.setWidth(getCellLength());
            button.setPadding(0,0,0,0);
            button.setTextSize(getTextSize(level));


            gameBoardGridLayout.addView(button);
        }
    }

    private void startNextLevel() {
        level++;
        if(level > tempBestLevel) {
            tempBestLevel = level;
            tempBestLevelTextView.setText(String.valueOf(tempBestLevel));
        }

        remainMilliseconds += getRecoverTime(level);
        if(remainMilliseconds >= 10000000)
            remainMilliseconds = 10000000;
        startLevel();
    }

    private int getRecoverTime(int level) {
        int recoverTIme = (int)((level/15.0+1)*1000000);
        if(recoverTIme > 4000000)
            recoverTIme = 4000000;
        return recoverTIme;
    }



    private GridLayout buildGameBoard() {
        GridLayout gameBoardGridLayout = new GridLayout(this);
        int boardLength = getBoardLength();
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = boardLength;
        params.height = boardLength;
        gameBoardGridLayout.setLayoutParams(params);

        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(boardLength,
                boardLength);
        relativeLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        gameBoardGridLayout.setLayoutParams(relativeLayoutParams);

        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(8);
        shape.setColor(Color.GRAY);
        gameBoardGridLayout.setBackgroundDrawable(shape);
        gameBoardGridLayout.setPadding(BOARD_PADDING,BOARD_PADDING,BOARD_PADDING,BOARD_PADDING);

        return gameBoardGridLayout;
    }

    private float getTextSize(int level) {
        float textSize = 21 + (8-level/6);
        if(textSize < 21)
            textSize = 21;

        return textSize;
    }

    private Map<String, Integer> getColorLevelColors(int level) {
        Map<String, Integer> colors = new HashMap<String, Integer>();
        int r = rand10To250();
        int g = rand10To250();
        int b = rand10To250();
        Integer other = Color.argb(255, r, g, b);
        int diff = 12 - level/12;
        if(diff < 8){
            diff = 8;
        }
        Integer right = Color.argb(255, randDiff255(r, diff), randDiff255(g, diff), randDiff255(b, diff));

        colors.put("other", other);
        colors.put("right", right);

        return colors;
    }

    private Map<String, Integer> getCharLevelColors(int level) {
        Map<String, Integer> colors = new HashMap<String, Integer>();
        int r = rand50To210();
        int g = rand50To210();
        int b = rand50To210();
        Integer backgroundColor = Color.argb(255, r, g, b);
        int diff = 150 - 2*level;
        if(diff < 13){
            diff = 13;
        }
        Integer charColor = Color.argb(255, randDiff255(r, diff), randDiff255(g, diff), randDiff255(b, diff));

        colors.put("char", charColor);
        colors.put("background", backgroundColor);

        return colors;
    }

    private Map<String,String> getChars(int level) {
        Map<String, String> chars = new HashMap<String, String>();
        int index = (level-10)/2;
        if(index < 0)
            index = 0;
        int span = charSet.length >> 1;
        if(index+span >= charSet.length)
            index = charSet.length-span-1;
        index = (index + ((int)Math.round(Math.random()*span)))/2*2;

        String charRight;
        String charOther;
        if(Math.random() > 0.5) {
            charRight = charSet[index];
            charOther = charSet[index + 1];
        }
        else {
            charRight = charSet[index + 1];
            charOther = charSet[index];
        }

        chars.put("right",charRight);
        chars.put("wrong",charOther);

        return chars;
    }

    private int rand10To250() {
        return (int) Math.round(10+240 * Math.random());
    }

    private int rand50To210() {
        return (int) Math.round(55+205 * Math.random());
    }

    private int randDiff255(int c, int diff) {
        int newC;
        if(Math.random() > 0.5f) {
            newC = c+diff;
            newC = (newC >= 255? 255: newC);
        }
        else {
            newC = c-diff;
            newC = (newC <= 0? 0: newC);
        }

        return newC;
    }

    private int getColumnCount(int level) {
        int columns = 4 + level/10;
        if(columns > 8)
            columns = 8;
        return columns;
    }

    private int getBoardLength() {
        return getScreenWidth() - 2*BOARD_MARGIN;
    }

    private int getScreenWidth() {
        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);
        int width = dimension.widthPixels;
        int height = dimension.heightPixels;
    }

    private int getCellLength() {
        float length = ((float)(getBoardLength()-2*BOARD_PADDING))/getColumnCount(level);
        length = length- 2*CELL_MARGIN;
        return (int)length;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private Integer[] goodColors = {
            Color.argb(255,84,115,135),Color.argb(255,248,232,157),
            Color.argb(255,90,13,67),Color.argb(255,244,222,41),
            Color.argb(255,87,96,105),Color.argb(255,247,68,97),
            Color.argb(255,23,50,7),Color.argb(255,230,180,80),
            Color.argb(255,153,80,84),Color.argb(255,130,179,61),
            Color.argb(255,0,90,171),Color.argb(255,255,222,0),
            Color.argb(255,255,94,72),Color.argb(255,0,34,40),
            Color.argb(255,53,44,10),Color.argb(255,255,255,255),
            Color.argb(255,82,75,46),Color.argb(255,171,92,37),
            Color.argb(255,56,13,49),Color.argb(255,114,111,128),
            Color.argb(255,170,138,87),Color.argb(255,113,175,164),
            Color.argb(255,101,147,74),Color.argb(255,160,191,124),
            Color.argb(255,217,116,43),Color.argb(255,230,180,80),
            Color.argb(255,230,155,3),Color.argb(255,209,73,78),
            Color.argb(255,18,53,85),Color.argb(255,209,73,78),
            Color.argb(255,230,155,3),Color.argb(255,219,208,167)
    };

    private Map<String, Integer> getCharLevelColors(){
        Map<String, Integer> pair = new HashMap<String, Integer>();
        int index = (int)((goodColors.length-1)*Math.random()/2)*2;
        double seed = Math.random();
        if(seed>0.5){
            pair.put("char", goodColors[index]);
            pair.put("background", goodColors[index+1]);
        }
        else {
            pair.put("char", goodColors[index+1]);
            pair.put("background", goodColors[index]);
        }

        Toast.makeText(this, index+"", Toast.LENGTH_SHORT).show();
        return pair;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(start) {
            invisibleAllViews();
            pauseMenuRelativeLayout.setVisibility(View.VISIBLE);
            pauseGame();
        }
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
            "卍","卐",
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
            "孑","孓",
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
            "亾","兦",
            "ش","س",
            "ك","ق",
            "ي","ى",
            "土","士",
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
            "魍","魉",
            "魑","魅",
            "幣","弊",
            "ビ","ピ",
            "ぽ","ぱ"
    };
    */
}
