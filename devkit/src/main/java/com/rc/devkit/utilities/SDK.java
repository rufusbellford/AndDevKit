package com.rc.devkit.utilities;

import android.os.Build;

public class SDK
{
    public static boolean hasHoneycomb()
    {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean isSDKEquealOrAbove(int sdkVersion)
    {
        return Build.VERSION.SDK_INT >= sdkVersion;
    }
}
