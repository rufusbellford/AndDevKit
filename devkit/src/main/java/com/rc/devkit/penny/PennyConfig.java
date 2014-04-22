package com.rc.devkit.penny;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

import com.rc.devkit.utilities.Registry;

import java.io.File;

public class PennyConfig 
{	
	protected String LOG_TAG;
	protected HandleType defaultHandleType;
	protected String logFileName;
	protected int toastLength;
	protected File logFile;

    public PennyConfig()
    {
        logFileName = "ERROR_HANDLE_LOG_FILE.log";
        toastLength = Toast.LENGTH_SHORT;
        logFile = null;
        defaultHandleType = HandleType.LOG;
    }

    public PennyConfig(String logTag, HandleType defaultHandleType, String logFileName, int toastLength)
    {
        this.LOG_TAG = logTag;
        this.defaultHandleType = defaultHandleType;
        this.logFileName = logFileName;
        this.toastLength = toastLength;
    }

    public void displayPackage(boolean displayPackage)
    {
        Context applicationContext = Registry.getInstance().getApplicationContext();
        
        if (displayPackage)
        {
        	LOG_TAG = applicationContext.getPackageName();
        }
        else
        {
            PackageManager packageManager = Registry.getInstance().getApplicationContext().getPackageManager();
            ApplicationInfo ai;
            try
            {
                ai = packageManager.getApplicationInfo( applicationContext.getPackageName(), 0);
            }
            catch (final PackageManager.NameNotFoundException e) {
                ai = null;
            }

            final String applicationName = (String) (ai != null ? packageManager.getApplicationLabel(ai) : "(unknown)");
            LOG_TAG = applicationName;
        }
    }
    
    public void createLogFile(String logFileName, boolean shouldBeExternal)
    {
        String absoluteFilePath;

        if (shouldBeExternal)
        {
            absoluteFilePath = Environment.getExternalStorageDirectory() + "/" + logFileName;
        }
        else
        {
            ContextWrapper contextWrapper = new ContextWrapper(Registry.getInstance().getApplicationContext());
            absoluteFilePath = contextWrapper.getFilesDir().getAbsolutePath() + "/" + logFileName;
        }

        logFile = new File(absoluteFilePath);
    }
}
