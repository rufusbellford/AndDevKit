package com.rc.devkit.penny;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.rc.devkit.utilities.Registry;
import com.rc.devkit.utilities.Strings;

public class PennyCore 
{
    protected PennyConfig config;
    private CustomHandlerCallback customCallback;
    
    public void setCustomCallback(CustomHandlerCallback customCallback) {
		this.customCallback = customCallback;
	}

	protected Message build()
    {
    	return Message.build(this);
    }
   
	public void displayLog(String message)
    {
        Log.e(config.LOG_TAG, message);
    }

	public void displayToast(String message)
    {
        Toast.makeText(Registry.getInstance().getApplicationContext(), message, config.toastLength).show();
    }

	public void displayAlert(String title, String message)
    {
        new AlertDialog.Builder(Registry.getInstance().getApplicationContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
    
	public void customCallback(String title, String message)
    {
    	if (customCallback == null) {
			throw new RuntimeException("You are trying to use custom callback, but you didn't specified one");
		}
    	
    	customCallback.handleCallback(title, message);
    }

	public void writeToFile(String message)
    {
        if (config.logFile == null)
        {
            config.createLogFile(config.logFileName, true);
        }

        try
        {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(config.logFile, true)));
            printWriter.println(message);
            printWriter.close();
        }
        catch(Exception e)
        {
            displayLog("Can't write file. " + e.getMessage());
        }
    }
    
    public static enum HandleType
    {
        LOG,
        NOTHING,
        FILE,
        ALERT,
        TOAST,
        CUSTOM
    }
    
    public static interface CustomHandlerCallback
    {
    	public void handleCallback(String title, String message);
    }
    
    public static class Message
    {
    	private PennyCore core;
    	private String title;
    	private String prefix;
    	private String message;
    	private String postfix;
    	private HandleType type;
    	
    	public static Message build(PennyCore core)
    	{
    		Message message = new Message();
    		message.core = core;
    		message.type = core.config.defaultHandleType;
    		message.title = "";
    		message.prefix = "";
    		message.postfix = "";
    		message.message = "";
    		return message;
    	}
    	
    	public Message type(HandleType type) 
    	{
    		this.type = type;
    		return this;
    	}
    	
    	public Message appendDate(Date date) 
    	{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
            prefix = simpleDateFormat.format(date) + "#" + prefix;
    		return this;
    	}
    	
    	public Message appendNowDate() {
    		Date date = new Date();
    		return appendDate(date);
    	}
    	
    	public Message appendTitle(String newTitle) {
    		title += newTitle;
    		return this;
    	}
    	
    	public Message appendMessage(String newMessage) {
    		message += newMessage;
    		return this;
    	}

    	public Message appendPostfix(String newPostfix) {
    		postfix += newPostfix;
    		return this;
    	}

    	public Message appendPrefix(String newPrefix) {
    		prefix += newPrefix;
    		return this;
    	}
    	
        protected void show()
        {
            switch (type) 
            {
                case LOG:
                	core.displayLog(prepareMessageAllInOne());
                    break;
                    
                case FILE:
                	core.writeToFile(prepareMessageAllInOne());
                    break;
                    
                case ALERT:
                	core.displayAlert(title, prepareMessageOnly());
                    break;
                    
                case TOAST:
                	core.displayToast(prepareMessageAllInOne());
                    break;
                    
                case CUSTOM:
                	core.customCallback(title, prepareMessageOnly());
                    break;
                    
                case NOTHING:
                default:
                    break;
            }
        }
        
        private String prepareMessageAllInOne()
        {
        	String resultMessage = "";
            boolean hasTitle = false;

            if (!Strings.isEmpty(title)) {
                resultMessage += title;
                hasTitle = true;
            }

            String messageOnly = prepareMessageOnly();
            
            if (!Strings.isEmpty(messageOnly))
            {
                if (hasTitle) {
                    resultMessage += ": ";
                }

                resultMessage += messageOnly;
            }

            return resultMessage;
        }
        
        private String prepareMessageOnly()
        {
        	String messageOnly = prefix + " " + message + " " + postfix;
        	return messageOnly.trim();
        }
    }
}
