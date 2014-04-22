package com.rc.devkit.penny;

/**
 * Created by Softhis on 16.04.2014.
 */
public interface PennyMessagePresenterDelegate
{
    public void handleCallback(String title, String message, MessageFeel messageFeel);
}
