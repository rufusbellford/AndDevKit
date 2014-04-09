package com.rc.devkit.foundation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public abstract class BaseSplashScreenActivity extends Activity
{
    //================================================================================
    // Private variables
    //================================================================================
    private long splashStartTimestamp = 0l;
    private volatile boolean cancelOpeningAnotherIntent = false;

    //================================================================================
    // Activity Events
    //================================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        splashStartTimestamp = System.currentTimeMillis();

        setupView();
        if (!willPerformBackgroundTasks()) {
        	splashScreenShouldEndAfterSplashTime();
		}
    }

    /**
     * Set your layout here
     */
    public abstract void setupView();

    /**
     * Your implementation of background job. 
     */
    public abstract boolean willPerformBackgroundTasks();

    /**
     * Here implement your actions for
     */
    public abstract int timeOfSplashInMilis();
    
    public abstract Intent intentToRun();

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        cancelOpeningAnotherIntent = true;
    }

    //================================================================================
    // Private methods
    //================================================================================
    // postSplashScreenAndWait
    // time to wait
    // postSpashScreenImmiedatly
    // no time to wait
    
    protected void splashScreenShouldEndAfterSplashTime()
    {
        long timeToWait = countLeftTime();
        keepSplashAndStartActivity(timeToWait, intentToRun());
    }
    
    protected void splashScreenShouldEndImmediately()
    {
        keepSplashAndStartActivity(0, intentToRun());
    }

    private long countLeftTime()
    {
        return splashStartTimestamp + timeOfSplashInMilis() - System.currentTimeMillis();
    }

    private void keepSplashAndStartActivity(final long timeToWait, final Intent intent)
    {
        if (timeToWait > 0)
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    try
                    {
                        long timeLeft = timeToWait;
                        while (timeLeft > 0 && !cancelOpeningAnotherIntent)
                        {
                            sleep(100);
                            timeLeft -= 100;
                        }
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if (!cancelOpeningAnotherIntent)
                        {
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }.start();
        }
        else
        {
            startActivity(intent);
            finish();
        }
    }
}
