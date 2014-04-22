package com.rc.devkit.penny;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.rc.devkit.utilities.Registry;
import com.rc.devkit.utilities.Strings;

public class PennyCore 
{
    protected PennyConfig config;
    private PennyMessagePresenterDelegate messagePresenterDelegate;
    
    public void setMessagePresenterDelegate(PennyMessagePresenterDelegate messagePresenterDelegate) {
		this.messagePresenterDelegate = messagePresenterDelegate;
	}

	protected Message build()
    {
    	return Message.build(this);
    }

    protected void displayLog(String message)
    {
        Log.e(config.LOG_TAG, message);
    }

    protected void displayToast(String message)
    {
        Toast.makeText(Registry.getInstance().getApplicationContext(), message, config.toastLength).show();
    }

    protected void displayAlert(String title, String message)
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
    
	protected void customCallback(String title, String message, MessageFeel messageFeel)
    {
    	if (messagePresenterDelegate == null) {
			throw new RuntimeException("You are trying to use custom callback, but you didn't specified one");
		}

        messagePresenterDelegate.handleCallback(title, message, messageFeel);
    }

    protected void writeToFile(String message)
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

    public static class Message
    {
    	private PennyCore core;
    	private String title;
    	private String prefix;
    	private List<String> messageList;
    	private String postfix;
        private MessageFeel messageFeel;
    	private HandleType type;
    	
    	public static Message build(PennyCore core)
    	{
    		Message message = new Message();
    		message.core = core;
    		message.type = core.config.defaultHandleType;
    		message.title = "";
    		message.prefix = "";
    		message.postfix = "";
    		message.messageList = new ArrayList<String>();
            message.messageFeel = MessageFeel.NEUTRAL;
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
            prefix = simpleDateFormat.format(date) + "# " + prefix;
    		return this;
    	}

        public Message itIsNeutralMessage()
        {
            return feel(MessageFeel.NEUTRAL);
        }

        public Message itIsFailMessage()
        {
            return feel(MessageFeel.FAILURE);
        }

        public Message itIsSuccessMessage()
        {
            return feel(MessageFeel.SUCCESS);
        }

        public Message feel(MessageFeel messageFeel)
        {
            this.messageFeel = messageFeel;
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
    		messageList.add(newMessage);
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
    	
        public void show()
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
                	core.customCallback(title, prepareMessageOnly(), messageFeel);
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
            StringBuilder builder = new StringBuilder(prefix + " ");

            for (int i = 0 ; i < messageList.size() ; i++)
            {
                if (i != 0) {
                    builder.append(", ");
                }

                String message = messageList.get(i);
                builder.append(message);
            }

            builder.append(" " + postfix);

        	return builder.toString().trim();
        }
    }
}
