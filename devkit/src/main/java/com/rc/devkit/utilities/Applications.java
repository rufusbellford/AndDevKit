package com.rc.devkit.utilities;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.security.Signature;

public class Applications
{
    // from: http://izvornikod.com/Blog/tabid/82/EntryId/13/How-to-check-if-your-android-application-is-running-in-debug-or-release-mode.aspx
    public static boolean isDebuggable(Context ctx)
    {
        boolean debuggable = false;

        PackageManager pm = ctx.getPackageManager();
        try
        {
            ApplicationInfo appinfo = pm.getApplicationInfo(ctx.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags &= ApplicationInfo.FLAG_DEBUGGABLE));
        }
        catch(PackageManager.NameNotFoundException e)
        {
            /*debuggable variable will remain false*/
        }

        return debuggable;
    }

    public static final String DEFAULT_ANDROID_DEBUG_KEY = "CN=Android Debug,O=Android,C=US";

    public static boolean isSignedWithSignature(Context ctx, String debugKey)
    {
        String TAG = "isDebuggable";

        boolean debuggable = false;

        try
        {
            PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(),PackageManager.GET_SIGNATURES);
            android.content.pm.Signature[] signatures = pinfo.signatures;

            for ( int i = 0; i < signatures.length;i++)
            {
                Log.d(TAG, signatures[i].toString());
            }

            if (debugKey.equals(signatures[0].toString()))
            {
                debuggable = true;
            }

        }
        catch (PackageManager.NameNotFoundException e)
        {
            //debuggable variable will remain false
        }

        return debuggable;
    }
}
