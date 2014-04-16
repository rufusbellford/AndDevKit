package com.rc.devkit.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class FirstRunCheck
{
    private static SharedPreferences preferences(Context context)
    {
        return context.getSharedPreferences("FIRST:RUN", Context.MODE_PRIVATE);
    }

    public static boolean isFirstUse(Context context){
        return preferences(context).getBoolean("FIRST_USE", true);
    }

    public static void saveFirstUse(Context context){
        SharedPreferences.Editor prefEditor = preferences(context).edit();
        prefEditor.putBoolean("FIRST_USE", false);
        prefEditor.commit();
    }

    public static void clearFirstUse(Context context) {
        SharedPreferences.Editor prefEditor = preferences(context).edit();
        prefEditor.remove("FIRST_USE");
        prefEditor.commit();
    }
}
