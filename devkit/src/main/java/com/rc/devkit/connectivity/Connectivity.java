package com.rc.devkit.connectivity;

import com.rc.devkit.general.PermissionChecker;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Please add <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"> to AndroidManifest
 * 
 */
public class Connectivity
{
	private Context context;
	
	public static Connectivity create(Context context)
	{
		PermissionChecker.create(context).checkPermissions(new ConnectivityPermissionCheck());
		Connectivity connectivity = new Connectivity();
		connectivity.context = context;
		return connectivity;
	}
	
	public boolean isOnline() 
	{ 
	    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);    
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	
	public boolean isWifiConnected()
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);    
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    
	    if (netInfo != null) {
	    	boolean isWifi = (netInfo.getType() == ConnectivityManager.TYPE_WIFI);
	    	return isWifi;
		}
	    
		return false;
	}	
}
