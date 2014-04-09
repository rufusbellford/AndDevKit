package com.rc.devkit.utilities;

import java.util.ArrayList;
import java.util.List;

import com.rc.devkit.general.PermissionCheck;

public class CountStackPermisionCheck implements PermissionCheck {

	@Override
	public List<String> listNeededPermisions() {
		List<String> permissionList = new ArrayList<String>();
		permissionList.add(android.Manifest.permission.GET_TASKS);	
		return permissionList;
	}
}
