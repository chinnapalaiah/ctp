package com.hp.vtms.model;

import java.io.Serializable;

public class Attendees implements Serializable {

	private static final long serialVersionUID = 8056401475475322099L;
	private Long userId;
	private Long eventId;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String hpId;
	private String password;
	private String username;
	private String attAppName;
	private boolean attAppProvisioned;
	private String userActDate;
	private String userExpDate;
	private String created;
	private String createdBy;
	private Long groupId;
	private boolean activated;
	private Boolean isDemo;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getHpId() {
		return hpId;
	}

	public void setHpId(String hpId) {
		this.hpId = hpId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAttAppName() {
		return attAppName;
	}

	public void setAttAppName(String attAppName) {
		this.attAppName = attAppName;
	}

	public boolean isAttAppProvisioned() {
		return attAppProvisioned;
	}

	public void setAttAppProvisioned(boolean attAppProvisioned) {
		this.attAppProvisioned = attAppProvisioned;
	}

	public String getUserActDate() {
		return userActDate;
	}

	public void setUserActDate(String userActDate) {
		this.userActDate = userActDate;
	}

	public String getUserExpDate() {
		return userExpDate;
	}

	public void setUserExpDate(String userExpDate) {
		this.userExpDate = userExpDate;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public Boolean getIsDemo() {
		return isDemo;
	}

	public void setIsDemo(Boolean isDemo) {
		this.isDemo = isDemo;
	}
	

}
