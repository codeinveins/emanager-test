package com.supra.sso.utiities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ApplicationConstants {

	public static String ATTENDANCE_MODULE = "ATTENDANCE";
	public static String TIMESHEET_MODULE = "TIMESHEET";
	
	public static String ROLE_HR = "ROLE_ADMIN";
	
	public static List<String> MODULES_LIST = new ArrayList<String>();
	public static List<String> ROLES_LIST = new ArrayList<String>();
	static {
		MODULES_LIST.add(ATTENDANCE_MODULE);
		MODULES_LIST.add(TIMESHEET_MODULE);
		
		ROLES_LIST.add(ROLE_HR);
	}
	
	
}
