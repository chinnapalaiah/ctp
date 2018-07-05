package com.hp.vtms.vcloud.model;

import org.apache.http.StatusLine;

public class VCloudMsg {
	private byte[] taskin;
	private int statusCode;
	private StatusLine statusLine;
	private String taskUrl;

	public byte[] getTaskin() {
		return taskin;
	}

	public void setTaskin(byte[] taskin) {
		this.taskin = taskin;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public StatusLine getStatusLine() {
		return statusLine;
	}

	public void setStatusLine(StatusLine statusLine) {
		this.statusLine = statusLine;
	}

	public String getTaskUrl() {
		return taskUrl;
	}

	public void setTaskUrl(String taskUrl) {
		this.taskUrl = taskUrl;
	}



}
