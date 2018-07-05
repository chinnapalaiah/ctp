package com.hp.vtms.model;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA. User: meij Date: 13-11-21 Time: 2:40 To change
 * this template use File | Settings | File Templates.
 */
public class Instructor implements Serializable {

	private static final long serialVersionUID = -8211165208969597535L;

	private long inUserId;

	private String inFrame;

    private String inLastName;

	private String inEmailAddress;

	private boolean inIsAssigned;
	
	private Boolean gloabRdp;
	
	private Boolean gloabConsole;

	private String inHpId;

	private String inUsername;

	private String inPassword;

	private Long eventId;

	private String inAppName;

	private boolean inAppProvisioned;

	private String created;

	private String createdBy;
	
	private Boolean inAllowControl;
	
	

    public Boolean getGloabRdp() {
		return gloabRdp;
	}

	public void setGloabRdp(Boolean gloabRdp) {
		this.gloabRdp = gloabRdp;
	}

	public Boolean getGloabConsole() {
		return gloabConsole;
	}

	public void setGloabConsole(Boolean gloabConsole) {
		this.gloabConsole = gloabConsole;
	}

	public String getInFrame() {
		return inFrame;
	}

	public void setInFrame(String inFrame) {
		this.inFrame = inFrame;
	}

    public String getInLastName() {
        return inLastName;
    }

    public void setInLastName(String inLastName) {
        this.inLastName = inLastName;
    }

    public String getInEmailAddress() {
		return inEmailAddress;
	}

	public void setInEmailAddress(String inEmailAddress) {
		this.inEmailAddress = inEmailAddress;
	}

	public boolean isInIsAssigned() {
		return inIsAssigned;
	}

	public void setInIsAssigned(boolean inIsAssigned) {
		this.inIsAssigned = inIsAssigned;
	}

	public String getInHpId() {
		return inHpId;
	}

	public void setInHpId(String inHpId) {
		this.inHpId = inHpId;
	}

	public String getInAppName() {
		return inAppName;
	}

	public void setInAppName(String inAppName) {
		this.inAppName = inAppName;
	}

	public boolean isInAppProvisioned() {
		return inAppProvisioned;
	}

	public void setInAppProvisioned(boolean inAppProvisioned) {
		this.inAppProvisioned = inAppProvisioned;
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

	public boolean isInActivated() {
		return inActivated;
	}

	public void setInActivated(boolean inActivated) {
		this.inActivated = inActivated;
	}

	private boolean inActivated;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public long getInUserId() {
		return inUserId;
	}

	public void setInUserId(long inUserId) {
		this.inUserId = inUserId;
	}

	public String getInUsername() {
		return inUsername;
	}

	public void setInUsername(String inUsername) {
		this.inUsername = inUsername;
	}

	public String getInPassword() {
		return inPassword;
	}

	public void setInPassword(String inPassword) {
		this.inPassword = inPassword;
	}

	public Boolean getInAllowControl() {
		return inAllowControl;
	}

	public void setInAllowControl(Boolean inAllowControl) {
		this.inAllowControl = inAllowControl;
	}

	
}
