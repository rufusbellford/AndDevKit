package com.rc.devkit.debug;

import android.support.v4.app.Fragment;

import java.io.Serializable;

public interface DebugRunnable extends Serializable
{
    public void runOnFragment(Fragment fragment);
}
