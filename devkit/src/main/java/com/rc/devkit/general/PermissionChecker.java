package com.rc.devkit.general;

import android.content.Context;
import android.content.pm.PackageManager;

public class PermissionChecker 
{
	private Context context;
	
	public static PermissionChecker create(Context context) 
	{
		PermissionChecker permissionChecker = new PermissionChecker();
		permissionChecker.context = context;
		return permissionChecker;
	}
	
	public void checkPermissions(PermissionCheck permissionCheck)
	{
		for (String permission : permissionCheck.listNeededPermisions()) {
			boolean isPermissionGranted = isPermissionGranted(permission);
			if (!isPermissionGranted) {
				throw new RuntimeException("Permission are not granted: " + permission);
			}
		}
	}
	
	public boolean isPermissionGranted(String permission)
	{
		PackageManager pm = context.getPackageManager();
	    boolean isPermissionGranted = (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED);
	    return isPermissionGranted;
	}
}
