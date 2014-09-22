package com.marcyliao.game.finddifference.utility;

import android.content.Context;
import android.content.Intent;

import com.marcyliao.game.finddifference.R;


/**
 * Created by mac on 2014-09-21.
 */
public class ShareUtility {
    public static void shareApp(Context context) {
        share(context.getString(R.string.share),new StringBuilder().append("#").append(context.getString(R.string.app_name)).append(context.getString(R.string.share_text)).append(context.getString(R.string.app_store_link)).toString(),context);
    }

    public static void shareResult(Context context, int level) {

    }

    public static void share(String subject, String text, Context context) {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);

        context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share)));
    }
}
