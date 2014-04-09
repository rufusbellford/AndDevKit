package com.rc.devkit.debug;

import android.os.Bundle;

import java.io.Serializable;

public class DebugRunOption
{
    private Class runningClass = null;
    private Options options = null;
    private String title = null;
    private DebugRunnable runnable;

    public DebugRunOption(Class runningClass, String title)
    {
        this.runningClass = runningClass;
        this.title = title;
    }

    public DebugRunOption(Class runningClass, Options options, String title)
    {
        this.runningClass = runningClass;
        this.options = options;
        this.title = title;
    }

    public DebugRunOption(Class classToRun, Options runOtions)
    {
        this.runningClass = classToRun;
        this.options = runOtions;
    }

    public DebugRunOption(Class runningClass)
    {
        this.runningClass = runningClass;
    }

    public Class getRunningClass()
    {
        return runningClass;
    }

    public void setRunningClass(Class runningClass)
    {
        this.runningClass = runningClass;
    }

    public Options getOptions()
    {
        return options;
    }

    public void setOptions(Options options)
    {
        this.options = options;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Bundle getExtras()
    {
        if (getOptions() != null) {
            return getOptions().runBundle;
        }

        return null;
    }

    public DebugRunnable getRunnable()
    {
        return runnable;
    }

    public void setRunnable(DebugRunnable runnable)
    {
        this.runnable = runnable;
    }

    public static class Options
    {
        private Bundle runBundle;

        public Options(Bundle runBundle)
        {
            this.runBundle = runBundle;
        }

        public Options()
        {
        }
    }

    /**
     * Runs for Integer, Long and Serializable. For further compability - add other statements.
     * @param extraTag
     * @param extra
     */
    public void putExtra(String extraTag, Object extra)
    {
        if (options == null) {
            options = new Options();
        }

        if (options.runBundle == null) {
            options.runBundle = new Bundle();
        }

        if (extra instanceof Long){
            Long longExtra = (Long) extra;
            options.runBundle.putLong(extraTag, longExtra);
        }
        else if (extra instanceof Integer){
            Integer intExtra = (Integer) extra;
            options.runBundle.putInt(extraTag, intExtra);
        }
        else if (extra instanceof Serializable){
            Serializable serializableExtra = (Serializable) extra;
            options.runBundle.putSerializable(extraTag, serializableExtra);
        }
    }
}
