package com.hp.vtms.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: meij Date: 13-12-9 Time: 2:39 To change
 * this template use File | Settings | File Templates.
 */
public class RDPFileInformation implements Serializable {
	private static final long serialVersionUID = 7963866736081567066L;

	private String rdpfilename = "";

	private String rdpfilePath = "";

	private String rdpfileRealName = "";

	public String getRdpfileRealName() {
		return rdpfileRealName;
	}

	public void setRdpfileRealName(String rdpfileRealName) {
		this.rdpfileRealName = rdpfileRealName;
	}

	public String getRdpfilePath() {
		return rdpfilePath;
	}

	public void setRdpfilePath(String rdpfilePath) {
		this.rdpfilePath = rdpfilePath;
	}

	public String getRdpfilename() {
		return rdpfilename;
	}

	public void setRdpfilename(String rdpfilename) {
		this.rdpfilename = rdpfilename;
	}
}
