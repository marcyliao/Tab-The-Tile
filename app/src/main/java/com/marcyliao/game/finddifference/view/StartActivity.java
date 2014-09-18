package com.marcyliao.game.finddifference.view;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.marcyliao.game.finddifference.R;
import com.marcyliao.game.finddifference.com.model.Profile;
import com.marcyliao.game.finddifference.utility.SoundHelper;
import com.marcyliao.game.finddifference.utility.ViewHelper;


public class StartActivity extends Activity {

    private View buttonStart;
    private View buttonShare;
    private View buttonSetting;
    private View buttonBest;
    private TextView textViewBest;

    //sound
    private MediaPlayer clickSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initWidgets();
        initSounds();

    }

    private void initSounds() {
        clickSound = MediaPlayer.create(this, R.raw.click);
    }

    private void initWidgets() {
        buttonStart = ViewHelper.getButtonWithEffect(this, R.id.buttonStart);
        buttonSetting = ViewHelper.getButtonWithEffect(this,R.id.buttonSetting);
        buttonBest = ViewHelper.getButtonWithEffect(this,R.id.buttonBest);
        buttonShare = ViewHelper.getButtonWithEffect(this, R.id.buttonShare);

        textViewBest = (TextView) findViewById(R.id.textViewBestNumber);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, GameActivity.class);
                SoundHelper.sound(clickSound);
                startActivity(intent);
            }
        });

        updateWidgets();
    }

    private void updateWidgets() {
        Profile profile = Profile.loadProfile(this);
        textViewBest.setText(String.valueOf(profile.getBestOfCurrentMode()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateWidgets();
    }
}
