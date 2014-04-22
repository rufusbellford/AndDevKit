package com.rc.devkit.utilities;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ActivityExecutor
{
    private Bundle extras = null;
    private boolean shouldFinishCurrent = false;
    private boolean startForResult = false;
    private int newIntentRequestCode = -1;
    private Class<?> activityClassToRun = null;
    protected Activity activityToRunFrom = null;

    protected ActivityExecutor(Activity activity)
    {
        this.activityToRunFrom = activity;
    }

    public static ActivityExecutor create(Activity activity)
    {
        return new ActivityExecutor(activity);
    }

    public ActivityExecutor extras(Bundle extras)
    {
        this.extras = extras;
        return this;
    }

    public ActivityExecutor finishCurrent()
    {
        this.shouldFinishCurrent = true;
        return this;
    }

    public ActivityExecutor startForResult(int newIntentRequestCode)
    {
        this.startForResult = true;
        this.newIntentRequestCode = newIntentRequestCode;
        return this;
    }

    public ActivityExecutor startClass(Class<?> activityClass)
    {
        this.activityClassToRun = activityClass;
        return this;
    }

    public void execute()
    {
        if (activityClassToRun == null){
            throw new RuntimeException("You didn't specified class to run!");
        }
        
        if (activityToRunFrom == null){
            throw new RuntimeException("You didn't specified activity which run from!");
        } 

        Intent intent = new Intent(activityToRunFrom, activityClassToRun);

        if (extras != null) {
            intent.putExtras(extras);
        }

        if (startForResult) {
            activityToRunFrom.startActivityForResult(intent, newIntentRequestCode);
        }
        else {
            activityToRunFrom.startActivity(intent);
        }

        if (shouldFinishCurrent) {
            activityToRunFrom.finish();
        }
    }
    
    public static class ExtrasBuilder
    {
    	private Bundle extras;
    	
    	public static ExtrasBuilder builder()
    	{
    		return new ExtrasBuilder();
    	}
    	
    	private ExtrasBuilder()
    	{
    		extras = new Bundle();
    	}
    	
    	private Method getMehtod(Object object) throws NoSuchMethodException 
    	{
    		Method method = null;
    		
    		if (object instanceof String) {
    			method = Bundle.class.getMethod("putString", String.class, String.class);
			}
    		else if (object instanceof Integer) {
    			method = Bundle.class.getMethod("putInt", String.class, Integer.class);
			}
    		else if (object instanceof Serializable) {
    			method = Bundle.class.getMethod("putSerializable", String.class, Serializable.class);
			}
    		
    		return method;
    	}
    	
    	public ExtrasBuilder putExtra(String tag, Object object)
    	{
    		try {
				Method method = getMehtod(object);
				method.invoke(extras, tag, object);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
    		
			return this;
    	}
    	
    	public Bundle build()
    	{
    		return extras;
    	}
    }
}
