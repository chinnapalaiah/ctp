package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class VersionInfo extends BaseVColoudModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8496001330507286906L;

	@JacksonXmlProperty(localName = "LoginUrl")
	private String loginUrl;
	@JacksonXmlProperty(localName = "Version")
	private String version;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "VersionInfo [loginUrl=" + loginUrl + ", version=" + version + "]";
	}

}
