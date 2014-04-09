package com.rc.devkit.utilities;

import android.content.Context;
import android.util.DisplayMetrics;

public class DeviceScreenProperties
{
    private Integer lastSnapshotOrientation;
    private Context applicationContext;

    private int screenWidth;
    private int screenHeight;
    private int densityDependentScreenHeight;
    private int densityDependentScreenWidth;
    private float modelScaleFactor;

    public DeviceScreenProperties(Context applicationContext)
    {
        this.applicationContext = applicationContext;
        refreshDeviceScreenProperties();
    }

    public void refreshDeviceScreenProperties()
    {
        DisplayMetrics metrics = applicationContext.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        densityDependentScreenHeight = (int)(metrics.heightPixels / metrics.density);
        densityDependentScreenWidth = (int)(metrics.widthPixels / metrics.density);
        modelScaleFactor = scaleFactor();
        lastSnapshotOrientation = currentOrientation();
    }

    public int currentOrientation()
    {
//        public static final int ORIENTATION_UNDEFINED = 0;
//        public static final int ORIENTATION_PORTRAIT = 1;
//        public static final int ORIENTATION_LANDSCAPE = 2;
        int orientation = applicationContext.getResources().getConfiguration().orientation;
        return orientation;
    }

    private boolean isCurrentOrientationValid()
    {
        if (lastSnapshotOrientation == null || (lastSnapshotOrientation != currentOrientation())) {
            return false;
        }

        return true;
    }

    public void checkOrientationValidation()
    {
        if (!isCurrentOrientationValid()) {
            refreshDeviceScreenProperties();
        }
    }

    public int getScreenWidth()
    {
        checkOrientationValidation();
        return screenWidth;
    }

    public int getScreenHeight()
    {
        checkOrientationValidation();
        return screenHeight;
    }

    public int getDensityDependentScreenHeight()
    {
        checkOrientationValidation();
        return densityDependentScreenHeight;
    }

    public int getDensityDependentScreenWidth()
    {
        checkOrientationValidation();
        return densityDependentScreenWidth;
    }

    public Float getModelScaleFactor()
    {
        checkOrientationValidation();
        return modelScaleFactor;
    }

    public float scaleFactor()
    {
        // Tab 7'' port: 1.0f;
        if (densityDependentScreenHeight > 800) {
            return 1.0f;
        }

        // Tab land, Galaxy S2, Galaxy S3: 0.67f;
        return .67f;
    }
}
