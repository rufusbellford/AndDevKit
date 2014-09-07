package com.rc.devkit.foundation.interfaces;

/**
 * Created by radek on 26.08.2014.
 */
public interface Progressable
{
    public void progressIndeterminateWithMessage(String message);
    public void progressWithMessage(Double progress, String message);
    public void progressDone();
}
