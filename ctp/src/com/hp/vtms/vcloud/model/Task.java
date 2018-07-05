package com.hp.vtms.vcloud.model;

import java.io.Serializable;

public class Task implements Serializable {

	private static final long serialVersionUID = -4922044469258156658L;
	private String status;
	private String operationName;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

}
