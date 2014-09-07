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

    protected void saveString(String tag, String value)
    {
        String newTag = tag + getSuffix();
        SharedPreferences.Editor editor = preferences().edit();
        editor.putString(newTag, value);
        editor.commit();
    }

    protected String readString(String tag, String defaultValue)
    {
        String newTag = tag + getSuffix();
        return preferences().getString(newTag, defaultValue);
    }

    protected void saveInteger(String tag, Integer value)
    {
        String newTag = tag + getSuffix();
        SharedPreferences.Editor editor = preferences().edit();
        editor.putInt(newTag, value);
        editor.commit();
    }

    protected Integer readInteger(String tag, Integer defaultValue)
    {
        String newTag = tag + getSuffix();
        return preferences().getInt(newTag, defaultValue);
    }

    protected void saveLong(String tag, Long value)
    {
        String newTag = tag + getSuffix();
        SharedPreferences.Editor editor = preferences().edit();
        editor.putLong(newTag, value);
        editor.commit();
    }

    protected Long readLong(String tag, Long defaultValue)
    {
        String newTag = tag + getSuffix();
        return preferences().getLong(newTag, defaultValue);
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
