package com.hp.vtms;

import java.util.ResourceBundle;

public class Constants {

	public static ResourceBundle config = ResourceBundle.getBundle("vtms");

	public static final String SESSION_USERNAME = "username";
	public static final String SESSION_TYPE = "type";

	public static final String SESSION_ORGNAME = "orgName";
	public static final String Error_400="Your request is invalid";
	public static final String Error_401="Your request is unauthorized";
	public static final String Error_403="You are forbidden from vCloud server";
	public static final String Error_404="No file is found on vCloud server";
	public static final String Error_405="Your HTTP request is not supported";
	public static final String Error_406="Note: I'm confused that what is not acceptable?";
	public static final String Error_409="There is conflict between local environment and vCloud server";
	public static final String Error_415="The media type is not supported";
	public static final String Error_500="Internal error at the vCloud server.";
	public static final String Error_504="vCloud server is time out";
	
}
