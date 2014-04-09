package com.rc.devkit.penny;



public class Penny extends PennyCore
{
    private static Penny instance = null;

    private Penny() {
        resetConfiguration();
    }

    public static Penny instance()
    {
        if (instance == null)  {
            instance = new Penny();
        }
        
        return instance;
    }

    public void resetConfiguration()
    {
        config = new PennyConfig();
    }

    public void configure(HandleType defaultHandleType, String LOG_TAG,  String logFileName, int toastLength)
    {
        config = new PennyConfig(LOG_TAG, defaultHandleType, logFileName, toastLength);
    }

    
    public void printMethod()
    {
        String message = Thread.currentThread().getStackTrace()[3].getMethodName().toString();
        build().appendMessage(message).show();
    }

    public void printMethod(HandleType type)
    {
    	String message = Thread.currentThread().getStackTrace()[3].getMethodName().toString();
        build().appendMessage(message).type(type).show();
    }

    public void presentMessage(String message)
    {
    	build().appendMessage(message).show();
    }

    public void presentMessage(String message, HandleType type)
    {
    	build().appendMessage(message).type(type).show();
    }
    
    public Message present()
    {
    	return build();
    }
}
