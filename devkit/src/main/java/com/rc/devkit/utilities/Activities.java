package com.rc.devkit.utilities;

import java.util.List;

import com.rc.devkit.general.PermissionChecker;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

public class Activities
{
    public static void startActivity(Activity root, Class<?> activityClass)
    {
        Intent intent = new Intent(root, activityClass);
        root.startActivity(intent);
    }

    public static void startActivity(Activity root, Class<?> activityClass, Bundle extras)
    {
        if (extras == null) {
            throw new RuntimeException("Bundle is null, use method without bundle.");
        }

        Intent intent = new Intent(root, activityClass);
        intent.putExtras(extras);
        root.startActivity(intent);
    }

    public static void startActivityAndCloseCurrent(Activity root, Class<?> activityClass)
    {
        Intent intent = new Intent(root, activityClass);
        root.startActivity(intent);
        root.finish();
    }

    public static void startActivityForResult(Activity root, Class<?> activityClass, int requestCode)
    {
        Intent intent = new Intent(root, activityClass);
        root.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Activity root, Class<?> activityClass, int requestCode, Bundle extras)
    {
        Intent intent = new Intent(root, activityClass);
        
        if (extras != null) {
            intent.putExtras(extras);
        }

        root.startActivityForResult(intent, requestCode);
    }
    
    public static void openInBrowser(Activity source, String siteHttp)
    {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(siteHttp));
        source.startActivity(myIntent);
    }

    public static int lockActivityOrientation(Activity activity)
    {
        int oldOrientation = activity.getRequestedOrientation();

        int currentOrientation = activity.getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        return oldOrientation;
    }

    public static void unlockActivityOrientation(Activity activity, int orientation)
    {
        activity.setRequestedOrientation(orientation);
    }
    
    public static int countOfRunningActivities(Context context)
    {
		PermissionChecker.create(context).checkPermissions(new CountStackPermisionCheck());
        ActivityManager mngr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

//        if(taskList.get(0).numActivities == 1 && taskList.get(0).topActivity.getClassName().equals(context.getClass().getName())) {
//            Penny.instance().presentMessage("This is last activity in the stack");
//        }

        return taskList.get(0).numActivities;
    }

    // TODO: Could add class name comparison from top method
    public static boolean isLastActivityOnStack(Context context)
    {
        boolean isLast = (countOfRunningActivities(context) == 1);
        return isLast;
    }
}
