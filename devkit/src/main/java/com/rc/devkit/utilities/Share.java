package com.rc.devkit.utilities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Softhis on 11.04.2014.
 */
public class Share
{
    public static void share(Activity activity, String title, String content)
    {
        share(activity, title, content, null);
    }

    public static void share(Activity activity, String title, String content, String chooserScreenName)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, title);

        sendIntent.setType("text/plain");

        if (Strings.isEmpty(chooserScreenName))
        {
            activity.startActivity(sendIntent);
        }
        else
        {
            activity.startActivity(Intent.createChooser(sendIntent , chooserScreenName));
        }
    }

//    private static void shareWithImage(Activity activity, String drawableFileName)
//    {
//        Uri imageUri = Uri.parse("android.resource://com.pasujemi.app/drawable/" + drawableFileName);
//        Intent sendIntent = new Intent(Intent.ACTION_SEND);
//        sendIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//        sendIntent.setType("image/png");
//        activity.startActivity(Intent.createChooser(sendIntent , "Share"));
//    }
}
