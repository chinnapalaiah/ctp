package com.hp.vtms.model;

import java.io.Serializable;

public class Connect implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2604286657790801164L;
	private Long conId;
	private String conUserId;
	private String conAppName;
	private Long conTempId ;
	private Long conVmId;
	private String conVmName;
	private String conCell;
	private String conIpAddress;
	private String conIpXAddress;
	private String conNic;
	private String conUsername;
	private String conGateway;
	public Long getConId() {
		return conId;
	}
	public void setConId(Long conId) {
		this.conId = conId;
	}
	public String getConUserId() {
		return conUserId;
	}
	public void setConUserId(String conUserId) {
		this.conUserId = conUserId;
	}
	public String getConAppName() {
		return conAppName;
	}
	public void setConAppName(String conAppName) {
		this.conAppName = conAppName;
	}
	public Long getConTempId() {
		return conTempId;
	}
	public void setConTempId(Long conTempId) {
		this.conTempId = conTempId;
	}
	public Long getConVmId() {
		return conVmId;
	}
	public void setConVmId(Long conVmId) {
		this.conVmId = conVmId;
	}
	public String getConVmName() {
		return conVmName;
	}
	public void setConVmName(String conVmName) {
		this.conVmName = conVmName;
	}
	public String getConCell() {
		return conCell;
	}
	public void setConCell(String conCell) {
		this.conCell = conCell;
	}
	public String getConIpAddress() {
		return conIpAddress;
	}
	public void setConIpAddress(String conIpAddress) {
		this.conIpAddress = conIpAddress;
	}
	public String getConIpXAddress() {
		return conIpXAddress;
	}
	public void setConIpXAddress(String conIpXAddress) {
		this.conIpXAddress = conIpXAddress;
	}
	public String getConNic() {
		return conNic;
	}
	public void setConNic(String conNic) {
		this.conNic = conNic;
	}
	public String getConUsername() {
		return conUsername;
	}
	public void setConUsername(String conUsername) {
		this.conUsername = conUsername;
	}
	public String getConGateway() {
		return conGateway;
	}
	public void setConGateway(String conGateway) {
		this.conGateway = conGateway;
	}
	
	
}
