package com.marcyliao.game.tapthetile.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;
import com.marcyliao.game.tapthetile.R;
import com.marcyliao.game.tapthetile.com.model.Profile;
import com.marcyliao.game.tapthetile.com.model.Setting;
import com.marcyliao.game.tapthetile.com.model.mode.Mode;
import com.marcyliao.game.tapthetile.utility.ShareUtility;
import com.marcyliao.game.tapthetile.utility.SoundManager;
import com.marcyliao.game.tapthetile.utility.ViewHelper;


public class StartActivity extends BaseGameActivity {

    private View buttonStart;
    private View buttonMode;
    private View buttonShare;
    private View buttonSound;
    private View buttonBest;
    private View buttonAchievement;
    private View buttonLeaderBoard;

    private TextView textViewBest;
    private TextView textViewMode;

    private final static int REQUEST_ACHIEVEMENTS = 1011;
    private final static int REQUEST_LEADERBOARD = 1012;

    //layouts
    private RelativeLayout relativeLayoutBottomPanel;
    private RelativeLayout relativeLayoutLogo;

    //sound
    private SoundManager soundManager;

    //anim
    private Animation animSlideUp;
    private Animation fadeIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initMovingBackground();
        initWidgets();
        initPanels();
        initSounds();
        initAnimation();

        initAds();

    }

    private void initAds() {
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private void initMovingBackground() {
        View imageViewBg1 = findViewById(R.id.imageViewBg1);
        View imageViewBg2 = findViewById(R.id.imageViewBg2);
        imageViewBg1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_from_right));
        imageViewBg2.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_out_fo_left));
    }

    private void initPanels() {
        relativeLayoutBottomPanel = (RelativeLayout)findViewById(R.id.relativeLayoutBottomPanel);
        relativeLayoutLogo = (RelativeLayout)findViewById(R.id.relativeLayoutLogo);
    }

    private void initAnimation() {
        animSlideUp = AnimationUtils.loadAnimation(this,R.anim.slide_up);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        startAnimation();


    }

    private void startAnimation() {
        relativeLayoutLogo.startAnimation(fadeIn);
        relativeLayoutBottomPanel.startAnimation(animSlideUp);
    }

    private void initSounds() {
        Setting setting = Setting.loadSetting(this);

        SoundManager.getInstance();
        SoundManager.setSoundSwitch(setting.isSoundSwitch());
        SoundManager.initSounds(this);
        SoundManager.loadSounds();
    }

    private void initWidgets() {
        buttonStart = ViewHelper.getButtonWithEffect(this, R.id.buttonStart);
        buttonSound = ViewHelper.getButtonWithEffect(this,R.id.buttonSound);
        buttonBest = ViewHelper.getButtonWithEffect(this,R.id.buttonBest);
        buttonShare = ViewHelper.getButtonWithEffect(this, R.id.buttonShare);
        buttonMode = ViewHelper.getButtonWithEffect(this,R.id.buttonMode);
        buttonAchievement = ViewHelper.getButtonWithEffect(this,R.id.buttonAchievement);
        buttonLeaderBoard = ViewHelper.getButtonWithEffect(this,R.id.buttonLeaderBoard);

        textViewBest = (TextView) findViewById(R.id.textViewBestNumber);
        textViewMode = (TextView) findViewById(R.id.textViewMode);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, GameActivity.class);
                startActivity(intent);

                SoundManager.playSound(R.raw.click);
            }
        });

        buttonMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Profile profile = Profile.loadProfile(StartActivity.this);
                int currentMode = profile.getCurrentMode();
                int nextMode = currentMode+1;
                if(nextMode >= Mode.NUM_MODES) {
                    nextMode = 0;
                }

                profile.setCurrentMode(nextMode);
                profile.save(StartActivity.this);

                updateWidgets();

                SoundManager.playSound(R.raw.click);
            }
        });

        buttonSound.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Setting setting = Setting.loadSetting(StartActivity.this);

                if(SoundManager.isSoundSwitch()) {
                    setting.setSoundSwitch(false);
                    SoundManager.setSoundSwitch(false);
                }
                else {
                    setting.setSoundSwitch(true);
                    SoundManager.setSoundSwitch(true);
                }

                setting.save(StartActivity.this);

                updateWidgets();

                SoundManager.playSound(R.raw.click);
            }
        });

        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtility.shareApp(StartActivity.this);
            }
        });

        buttonAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSignedIn()) {
                    startActivityForResult(Games.Achievements.getAchievementsIntent(getApiClient()), REQUEST_ACHIEVEMENTS);
                }
                else {
                    beginUserInitiatedSignIn();
                }

                SoundManager.playSound(R.raw.click);
            }
        });

        buttonLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSignedIn()) {
                    startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(getApiClient()), REQUEST_LEADERBOARD);
                }
                else {
                    beginUserInitiatedSignIn();
                }

                SoundManager.playSound(R.raw.click);
            }
        });

        updateWidgets();
    }

    private void updateWidgets() {
        Profile profile = Profile.loadProfile(this);
        textViewBest.setText(String.valueOf(profile.getBestOfCurrentMode()));
        textViewMode.setText(profile.getCurrentModeName(this));

        Setting setting = Setting.loadSetting(this);
        if(setting.isSoundSwitch()) {
            ((ImageButton)buttonSound).setImageResource(R.drawable.sound_on);
        }
        else {
            ((ImageButton)buttonSound).setImageResource(R.drawable.sound_off);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAnimation();
        updateWidgets();
    }

    @Override
    protected void onDestroy() {
        SoundManager.cleanup();
        super.onDestroy();
    }

    @Override
    public void onSignInFailed() {
    }

    @Override
    public void onSignInSucceeded() {
        Profile profile = Profile.loadProfile(this);
        profile.synBest(this,getApiClient());
    }
}
