package com.hp.vtms.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9125237838592644131L;
	private String userName;
    private String firstName;
    private String lastName;
    private String userEmail;
	private String password;
	private String type;
	private String OSType;
	private Boolean isFirstTime;
	private String rdpClientLink;
	private String vappName;
	private String vappurl;
	private Boolean allowed;/*allow to display dropdownList for instructor */
	private List<Info> infoList;
	private String os;
	private String eventId;
	private String participantUrl;
	private Boolean showVRoom;
	private Boolean isDemo;
	private String systemError;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSystemError() {
		return systemError;
	}
	public void setSystemError(String systemError) {
		this.systemError = systemError;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOSType() {
		return OSType;
	}
	public void setOSType(String oSType) {
		OSType = oSType;
	}
	public Boolean getIsFirstTime() {
		return isFirstTime;
	}
	public void setIsFirstTime(Boolean isFirstTime) {
		this.isFirstTime = isFirstTime;
	}
	public String getVappName() {
		return vappName;
	}
	public void setVappName(String vappName) {
		this.vappName = vappName;
	}
	public String getVappurl() {
		return vappurl;
	}
	public void setVappurl(String vappurl) {
		this.vappurl = vappurl;
	}
	public Boolean getAllowed() {
		return allowed;
	}
	public void setAllowed(Boolean allowed) {
		this.allowed = allowed;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public List<Info> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<Info> infoList) {
		this.infoList = infoList;
	}
	public String getRdpClientLink() {
		return rdpClientLink;
	}
	public void setRdpClientLink(String rdpClientLink) {
		this.rdpClientLink = rdpClientLink;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getParticipantUrl() {
		return participantUrl;
	}
	public void setParticipantUrl(String participantUrl) {
		this.participantUrl = participantUrl;
	}
	
	public Boolean getShowVRoom() {
		return showVRoom;
	}
	public void setShowVRoom(Boolean showVRoom) {
		this.showVRoom = showVRoom;
	}
	public Boolean getIsDemo() {
		return isDemo;
	}
	public void setIsDemo(Boolean isDemo) {
		this.isDemo = isDemo;
	}

}
