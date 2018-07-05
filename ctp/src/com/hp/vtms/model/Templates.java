package com.hp.vtms.model;

import java.io.Serializable;

public class Templates implements Serializable {

	private static final long serialVersionUID = 2904595311342738165L;
	private Long tempId;
	private String tempName;
	private Boolean tempIsAssigned;
	private String tempDescription;
	private String created;
	private Long tempCpus;
	private Long tempRam;
	private Long tempVms;
	private String tempVilibrary;
	private String tempBiz;
	private String tempVendId;
	private String tempCatalog;
	private String tempBaseImage;


	public Long getTempId() {
		return tempId;
	}

	public void setTempId(Long tempId) {
		this.tempId = tempId;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public Boolean getTempIsAssigned() {
		return tempIsAssigned;
	}

	public void setTempIsAssigned(Boolean tempIsAssigned) {
		this.tempIsAssigned = tempIsAssigned;
	}

	public String getTempDescription() {
		return tempDescription;
	}

	public void setTempDescription(String tempDescription) {
		this.tempDescription = tempDescription;
	}

	public String getTempCatalog() {
		return tempCatalog;
	}

	public void setTempCatalog(String tempCatalog) {
		this.tempCatalog = tempCatalog;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public Long getTempCpus() {
		return tempCpus;
	}

	public void setTempCpus(Long tempCpus) {
		this.tempCpus = tempCpus;
	}

	public Long getTempRam() {
		return tempRam;
	}

	public void setTempRam(Long tempRam) {
		this.tempRam = tempRam;
	}

	public Long getTempVms() {
		return tempVms;
	}

	public void setTempVms(Long tempVms) {
		this.tempVms = tempVms;
	}

	public String getTempVilibrary() {
		return tempVilibrary;
	}

	public void setTempVilibrary(String tempVilibrary) {
		this.tempVilibrary = tempVilibrary;
	}

	public String getTempBiz() {
		return tempBiz;
	}

	public void setTempBiz(String tempBiz) {
		this.tempBiz = tempBiz;
	}

	public String getTempVendId() {
		return tempVendId;
	}

	public void setTempVendId(String tempVendId) {
		this.tempVendId = tempVendId;
	}
	
	public String getTempBaseImage() {
		return tempBaseImage;
	}

	public void setTempBaseImage(String tempBaseImage) {
		this.tempBaseImage = tempBaseImage;
	}
	
}
