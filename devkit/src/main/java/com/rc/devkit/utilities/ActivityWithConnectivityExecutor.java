package com.rc.devkit.utilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.rc.devkit.connectivity.Connectivity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActivityWithConnectivityExecutor extends ActivityExecutor
{
    private boolean internetNeeded = false;
    private boolean wifiNeeded = false;

    private OnActivityNotStartListener onActivityNotStartListener;

    public ActivityWithConnectivityExecutor setOnActivityNotStartListener(OnActivityNotStartListener onActivityNotStartListener)
    {
        this.onActivityNotStartListener = onActivityNotStartListener;
        return this;
    }

    private ActivityWithConnectivityExecutor(Activity activity)
    {
        super(activity);
    }

    public static ActivityWithConnectivityExecutor create(Activity activity)
    {
        return new ActivityWithConnectivityExecutor(activity);
    }

    public ActivityWithConnectivityExecutor internetNeeded()
    {
        this.internetNeeded = true;
        return this;
    }

    public ActivityWithConnectivityExecutor wifiNeeded()
    {
        this.internetNeeded = true;
        this.wifiNeeded = true;
        return this;
    }

    @Override
    public void execute()
    {
        if (internetNeeded)
        {
            Connectivity connectivity = Connectivity.create(activityToRunFrom);

            if (wifiNeeded)
            {
                if (!connectivity.isWifiConnected()) {
                    if (onActivityNotStartListener != null) {
                        onActivityNotStartListener.onActivityNotStarted();
                    }

                    return ;
                }
            }

            if (!connectivity.isOnline()) {
                if (onActivityNotStartListener != null) {
                    onActivityNotStartListener.onActivityNotStarted();
                }

                return ;
            }
        }

        if (onActivityNotStartListener != null) {
            onActivityNotStartListener.onActivityWillStart();
        }

        super.execute();
    }

    public static interface OnActivityNotStartListener
    {
        public void onActivityNotStarted();
        public void onActivityWillStart();
    }
}
