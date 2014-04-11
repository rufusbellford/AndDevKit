package com.rc.devkit.foundation;

import android.content.SharedPreferences;

import com.rc.devkit.utilities.Registry;

import java.io.Serializable;

public abstract class SettingsParameter implements Serializable
{
    private static final String SETTINGS_TAG = "SETTINGS:TAG:";
    protected abstract String getSuffix();
    public abstract void save();

    protected void saveBool(String tag, Boolean value)
    {
        String newTag = tag + getSuffix();
        SharedPreferences.Editor editor = preferences().edit();
        editor.putBoolean(newTag, value);
        editor.commit();
    }

    protected Boolean readBool(String tag, Boolean defaultValue)
    {
        String newTag = tag + getSuffix();
        return preferences().getBoolean(newTag, defaultValue);
    }

    private static SharedPreferences preferences()
    {
        SharedPreferences preferences = Registry.getInstance().getApplicationContext().getSharedPreferences(SETTINGS_TAG, 0);
        return preferences;
    }
}
