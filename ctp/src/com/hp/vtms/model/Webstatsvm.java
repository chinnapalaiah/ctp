package com.hp.vtms.model;

import java.io.Serializable;

public class Webstatsvm implements Serializable {

	private static final long serialVersionUID = -1808706672491813081L;

	private Long userId;
	private String userName;
	private String sessionServer;
	private Boolean showRdp;
	private Boolean showConsole;
	private Boolean update;
	private String vmname;
	
	public String getVmname() {
		return vmname;
	}
	public void setVmname(String vmname) {
		this.vmname = vmname;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSessionServer() {
		return sessionServer;
	}
	public void setSessionServer(String sessionServer) {
		this.sessionServer = sessionServer;
	}
	public Boolean getShowRdp() {
		return showRdp;
	}
	public void setShowRdp(Boolean showRdp) {
		this.showRdp = showRdp;
	}
	public Boolean getShowConsole() {
		return showConsole;
	}
	public void setShowConsole(Boolean showConsole) {
		this.showConsole = showConsole;
	}
	public Boolean getUpdate() {
		return update;
	}
	public void setUpdate(Boolean update) {
		this.update = update;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
