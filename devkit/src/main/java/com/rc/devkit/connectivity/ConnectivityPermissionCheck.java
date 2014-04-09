package com.rc.devkit.connectivity;

import java.util.ArrayList;
import java.util.List;

import com.rc.devkit.general.PermissionCheck;

public class ConnectivityPermissionCheck implements PermissionCheck 
{
	@Override
	public List<String> listNeededPermisions() {
		List<String> permissionList = new ArrayList<String>();
		permissionList.add(android.Manifest.permission.ACCESS_NETWORK_STATE);		
		return permissionList;
	}

}
