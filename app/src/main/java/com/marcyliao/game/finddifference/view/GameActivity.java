package com.marcyliao.game.finddifference.view;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.marcyliao.game.finddifference.R;
import com.marcyliao.game.finddifference.com.model.tile.CharTile;
import com.marcyliao.game.finddifference.com.model.tile.ColorTile;
import com.marcyliao.game.finddifference.com.model.tile.Tile;
import com.marcyliao.game.finddifference.core.controller.GameController;
import com.marcyliao.game.finddifference.core.controller.listener.GameListener;
import com.marcyliao.game.finddifference.utility.SizeHelper;
import com.marcyliao.game.finddifference.utility.SoundManager;
import com.marcyliao.game.finddifference.utility.ViewHelper;

public class GameActivity extends Activity {
    //UI properties
    public static final int BOARD_MARGIN = 50;
    public static final int BOARD_PADDING = 10;
    public static final int CELL_MARGIN = 1;
    public static final int SCREEN_HEIGHT_REMAIN = 240;

    //widgets
    private ProgressBar progressBarTimer;
    private TextView textViewNumberOfColumns;
    private TextView textViewLevel;
    private TextView textViewBestNumber;

    private View buttonGroupGameOver;
    private View buttonRestart;
    private View buttonShare;
    private View buttonBack;

    private View buttonPause;

    private View buttonGroupPause;
    private View buttonStart;
    private View buttonGiveUp;

    private View buttonGiveUpYes;
    private View buttonGiveUpNo;

    //panels
    private RelativeLayout relativeLayoutGameOver;
    private RelativeLayout relativeLayoutPause;
    private RelativeLayout relativeLayoutGiveUp;
    private GridLayout gameBoard;

    //controller
    private GameController gameController;

    //other helper params
    private int gameBoardLength;

    //sounds
    private SoundManager soundManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initWidgets();
        initPanels();
        initGameController();
        initSounds();
        setHighScreenBrightness();
        
        gameController.startGame();

    }

    private void setHighScreenBrightness() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if(lp.screenBrightness < 0.7f)
            lp.screenBrightness = 0.7f;
        getWindow().setAttributes(lp);
    }

    private void initSounds() {
        SoundManager.getInstance();
        SoundManager.initSounds(this);
        SoundManager.loadSounds();
    }

    private void initGameController() {
        gameController = new GameController(this);
        gameController.setGameListener(new GameListener() {
            @Override
            public void onBestLevelUpdated(int tempBestLevel) {
                textViewBestNumber.setText(String.valueOf(tempBestLevel));
            }

            @Override
            public void onPaused() {
                hideAll();
                relativeLayoutPause.setVisibility(View.VISIBLE);
                buttonGroupPause.setVisibility(View.VISIBLE);
            }

            @Override
            public void onProgressUpdated(int remainMilliseconds) {
                progressBarTimer.setProgress(remainMilliseconds);
            }

            @Override
            public void onGameOver() {
                hideAll();
                relativeLayoutGameOver.setVisibility(View.VISIBLE);
                buttonGroupGameOver.setVisibility(View.VISIBLE);
                SoundManager.playSound(R.raw.gameover);
            }

            @Override
            public void onGameStart() {
                hideAll();
                buttonPause.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRefreshLevel(int level, int column, int trueItemIndex, final Tile correctTile, Tile wrongTile) {
                int size = column * column;
                if(gameBoard.getChildCount() > 0)
                    gameBoard.removeAllViews();
                gameBoard.setRowCount(column);
                gameBoard.setColumnCount(column);
                for (int i = 0; i < size; i++) {
                    Button button = null;

                    if (i == trueItemIndex) {
                        button = getButtonFromTile(correctTile);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(gameController.getCurrentStatus() == GameController.GameStatus.GOING) {
                                    gameController.chooseCorrect();
                                }
                                SoundManager.playSound(R.raw.correct);

                            }
                        });
                        ViewHelper.addClickRightEffect(button);
                    } else {
                        button = getButtonFromTile(wrongTile);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(gameController.getCurrentStatus() == GameController.GameStatus.GOING) {
                                    gameController.chooseWrong();
                                }
                                SoundManager.playSound(R.raw.incorrect);
                            }
                        });
                        ViewHelper.addClickWrongEffect(button);
                    }

                    //common
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.setMargins(CELL_MARGIN, CELL_MARGIN, CELL_MARGIN, CELL_MARGIN);
                    button.setLayoutParams(params);
                    button.setHeight(getCellLength(column));
                    button.setWidth(getCellLength(column));
                    button.setPadding(0, 0, 0, 0);
                    button.setTextSize(getTextSize(column));


                    gameBoard.addView(button);
                }
            }

            @Override
            public void onColumnCountUpdated(int columnCount) {
                textViewNumberOfColumns.setText(String.valueOf(columnCount) + "X");
            }

            @Override
            public void onLevelUpdated(int level) {
                textViewLevel.setText(String.valueOf(level));
            }

            @Override
            public void onResume() {
                hideAll();
                buttonPause.setVisibility(View.VISIBLE);
            }
        });
    }

    private Button getButtonFromTile(Tile tile) {
        Button button = new Button(this);
        if(tile.getType().equals(Tile.Type.CHAR)) {
            CharTile charTile = (CharTile)tile;
            GradientDrawable shape =  new GradientDrawable();
            shape.setCornerRadius(8);
            shape.setColor(convertTileColorToAndroidColor(charTile.getBackgroundColor()));
            button.setBackgroundDrawable(shape);
            button.setTextColor(convertTileColorToAndroidColor(charTile.getTextColor()));
            button.setText(charTile.getText());
        }
        else if(tile.getType().equals(Tile.Type.COLOR)){
            ColorTile colorTile = (ColorTile)tile;
            GradientDrawable shape =  new GradientDrawable();
            shape.setCornerRadius(8);
            shape.setColor(convertTileColorToAndroidColor(colorTile.getColor()));
            button.setBackgroundDrawable(shape);
        }

        return button;
    }

    private int convertTileColorToAndroidColor(com.marcyliao.game.finddifference.com.model.component.Color color) {
        int andColor = Color.argb(color.a, color.r, color.g, color.b);
        return andColor;
    }

    private void hideAll() {
        buttonGroupGameOver.setVisibility(View.INVISIBLE);
        buttonGroupPause.setVisibility(View.INVISIBLE);
        buttonPause.setVisibility(View.INVISIBLE);

        relativeLayoutGameOver.setVisibility(View.INVISIBLE);
        relativeLayoutPause.setVisibility(View.INVISIBLE);
        relativeLayoutGiveUp.setVisibility(View.INVISIBLE);
    }

    private void initPanels() {
        relativeLayoutPause = (RelativeLayout)findViewById(R.id.relativeLayoutPause);
        relativeLayoutGameOver = (RelativeLayout)findViewById(R.id.relativeLayoutGameOver);
        relativeLayoutGameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayoutGameOver.setVisibility(View.INVISIBLE);
            }
        });

        relativeLayoutGiveUp = (RelativeLayout)findViewById(R.id.relativeLayoutGiveUp);


        buildGameBoard();
    }

    private void buildGameBoard() {
        SizeHelper.ScreenSize screenSize = SizeHelper.getScreenSize(this);
        int panelHeightPx = screenSize.height - SizeHelper.convertDpToPixel(SCREEN_HEIGHT_REMAIN,this);
        int panelWidthPx = screenSize.width;

        // select the smaller one as the length
        int boardLengthPx = (panelHeightPx < panelWidthPx? panelHeightPx: panelWidthPx) - 2*BOARD_MARGIN;

        RelativeLayout gameBoardWrapperLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(boardLengthPx,boardLengthPx);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        gameBoardWrapperLayout.setLayoutParams(params);
        GradientDrawable wrapperShape =  new GradientDrawable();
        wrapperShape.setCornerRadius(13);
        wrapperShape.setColor(Color.argb(255,102,115,123));
        gameBoardWrapperLayout.setBackgroundDrawable(wrapperShape);
        RelativeLayout relativeLayoutGameBoardPanel = (RelativeLayout)findViewById(R.id.relativeLayoutGameBoardPanel);
        relativeLayoutGameBoardPanel.addView(gameBoardWrapperLayout);

        gameBoard = new GridLayout(this);
        gameBoardLength = boardLengthPx - 2*BOARD_PADDING;
        RelativeLayout.LayoutParams gameBordParams = new RelativeLayout.LayoutParams(gameBoardLength,gameBoardLength);
        gameBordParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        gameBoard.setLayoutParams(gameBordParams);
        gameBoardWrapperLayout.addView(gameBoard);
        GradientDrawable shape =  new GradientDrawable();
        shape.setCornerRadius(10);
        shape.setColor(Color.GRAY);
        gameBoard.setBackgroundDrawable(shape);
    }

    private void initWidgets() {
        initTextViews();
        initButtons();
        initProgressBar();
    }

    private void initProgressBar() {
        progressBarTimer = (ProgressBar)findViewById(R.id.progressBarTimer);
        progressBarTimer.setMax(GameController.MAX_TIME);
    }

    private void initTextViews() {
        textViewBestNumber = (TextView)findViewById(R.id.textViewBestNumber);
        textViewLevel = (TextView)findViewById(R.id.textViewLevel);
        textViewNumberOfColumns = (TextView)findViewById(R.id.textViewNumberOfColumns);
    }

    private void initButtons() {
        buttonRestart = ViewHelper.getButtonWithEffect(this,R.id.buttonRestart);
        buttonBack = ViewHelper.getButtonWithEffect(this,R.id.buttonBack);
        buttonPause = ViewHelper.getButtonWithEffect(this,R.id.buttonPause);
        buttonShare = ViewHelper.getButtonWithEffect(this,R.id.buttonShare);
        buttonStart = ViewHelper.getButtonWithEffect(this,R.id.buttonStart);
        buttonGiveUp =  ViewHelper.getButtonWithEffect(this, R.id.buttonPauseBack);
        buttonGiveUpYes = ViewHelper.getButtonWithEffect(this, R.id.buttonGiveUpYes);
        buttonGiveUpNo = ViewHelper.getButtonWithEffect(this, R.id.buttonGiveUpNo);

        buttonGroupGameOver = findViewById(R.id.buttonGroupGameOver);
        buttonGroupPause = findViewById(R.id.buttonGroupPause);

        initButtonListeners();
    }

    private void initButtonListeners() {
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundManager.playSound(R.raw.click);
                gameController.restartGame();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SoundManager.playSound(R.raw.click);
                finish();
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                SoundManager.playSound(R.raw.click);
                gameController.pauseGame();
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                SoundManager.playSound(R.raw.click);
                //TODO: Share
            }
        });

        buttonStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                SoundManager.playSound(R.raw.click);
                gameController.resumeGame();
            }
        });

        buttonGiveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundManager.playSound(R.raw.click);
                relativeLayoutGiveUp.setVisibility(View.VISIBLE);
            }
        });

        buttonGiveUpYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundManager.playSound(R.raw.click);
                finish();
            }
        });

        buttonGiveUpNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SoundManager.playSound(R.raw.click);
                relativeLayoutGiveUp.setVisibility(View.INVISIBLE);
            }
        });
    }

    private int getCellLength(int column) {
        float length = ((float)(gameBoardLength))/column;
        length = length- 2*CELL_MARGIN;
        return (int)length;
    }

    private float getTextSize(int column) {
        float textSize = 21 + (8-column);
        if(textSize < 21)
            textSize = 21;

        return textSize;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(gameController.getCurrentStatus() == GameController.GameStatus.GOING) {
            gameController.pauseGame();
        }
    }
}
