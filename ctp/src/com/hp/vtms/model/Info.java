package com.hp.vtms.model;

import java.io.Serializable;

public class Info implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6940460404081157168L;


	private Long infoId;
	private Boolean infoShow;
	private String infoItem;
	private String infoValue;
	private String infoValue2;
	private String infoValue3;
	private Boolean infoWarning;
	public String getInfoValue2() {
		return infoValue2;
	}
	public void setInfoValue2(String infoValue2) {
		this.infoValue2 = infoValue2;
	}
	public String getInfoValue3() {
		return infoValue3;
	}
	public void setInfoValue3(String infoValue3) {
		this.infoValue3 = infoValue3;
	}
	public Long getInfoId() {
		return infoId;
	}
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}
	public Boolean getInfoShow() {
		return infoShow;
	}
	public void setInfoShow(Boolean infoShow) {
		this.infoShow = infoShow;
	}
	public String getInfoItem() {
		return infoItem;
	}
	public void setInfoItem(String infoItem) {
		this.infoItem = infoItem;
	}
	public String getInfoValue() {
		return infoValue;
	}
	public void setInfoValue(String infoValue) {
		this.infoValue = infoValue;
	}
	public Boolean getInfoWarning() {
		return infoWarning;
	}
	public void setInfoWarning(Boolean infoWarning) {
		this.infoWarning = infoWarning;
	}

	
}
