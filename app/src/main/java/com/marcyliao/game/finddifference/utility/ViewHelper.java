package com.marcyliao.game.finddifference.utility;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mliao on 03/09/2014.
 */
public class ViewHelper {
    public static void addClickEffect(View view){
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0x88FFFFFF, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        v.performClick();
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public static void addClickRightEffect(View view){
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0x55FFFF00, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        v.performClick();
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public static void addClickWrongEffect(View view){
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0x55FF0000, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        v.performClick();
                        return false;
                    }
                }
                return false;
            }
        });
    }

    public static View getButtonWithEffect(Activity activity, int id) {
        View button = activity.findViewById(id);
        ViewHelper.addClickEffect(button);
        return button;
    }

    public static void hideView(View v) {
        v.clearAnimation();
        v.setVisibility(View.INVISIBLE);
    }
}
