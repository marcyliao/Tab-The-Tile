package com.marcyliao.game.tapthetile.utility;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by mac on 2014-09-15.
 */
public class SizeHelper {
    public static class ScreenSize {
       public int height;
       public int width;
    }

    public static ScreenSize getScreenSizePx(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        ScreenSize screenSize = new ScreenSize();
        screenSize.height = displaymetrics.heightPixels;
        screenSize.width = displaymetrics.widthPixels;

        return screenSize;
    }

    public static ScreenSize getScreenSize(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        ScreenSize screenSize = new ScreenSize();
        screenSize.height = displaymetrics.heightPixels;
        screenSize.width = displaymetrics.widthPixels;

        return screenSize;
    }

    public static int convertDpToPixel(int dp, Context context) {
        Resources r = context.getResources();
        int px = (int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));

        return px;
    }
}
