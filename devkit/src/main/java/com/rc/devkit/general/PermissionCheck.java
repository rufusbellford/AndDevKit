package com.rc.devkit.general;

import java.util.List;

// TODO: Do somehow to permission check occured only once in application lifetime !
public interface PermissionCheck {
	public List<String> listNeededPermisions();
}
