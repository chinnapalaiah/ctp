package com.hp.vtms.model;

import java.io.Serializable;

public class EmailHost implements Serializable {

	private static final long serialVersionUID = 8056401475475322099L;
	
	
	private String emailHost;


	public String getEmailHost() {
		return emailHost;
	}


	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		

}
