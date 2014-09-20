package com.marcyliao.game.finddifference.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.marcyliao.game.finddifference.R;
import com.marcyliao.game.finddifference.com.model.Profile;
import com.marcyliao.game.finddifference.com.model.Setting;
import com.marcyliao.game.finddifference.com.model.mode.Mode;
import com.marcyliao.game.finddifference.utility.SoundManager;
import com.marcyliao.game.finddifference.utility.ViewHelper;


public class StartActivity extends Activity {

    private View buttonStart;
    private View buttonMode;
    private View buttonShare;
    private View buttonSound;
    private View buttonBest;

    private TextView textViewBest;
    private TextView textViewMode;

    //sound
    private SoundManager soundManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initWidgets();
        initSounds();

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

        updateWidgets();
    }

    private void updateWidgets() {
        Profile profile = Profile.loadProfile(this);
        textViewBest.setText(String.valueOf(profile.getBestOfCurrentMode()));
        textViewMode.setText(profile.getCurrentModeName());

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
        updateWidgets();
    }
}
