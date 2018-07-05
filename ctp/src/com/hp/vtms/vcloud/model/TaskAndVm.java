package com.hp.vtms.vcloud.model;

import java.io.Serializable;
import java.util.List;

public class TaskAndVm implements Serializable {

	private static final long serialVersionUID = 2657499544832639927L;
	private String taskStatus;
	private String vmName;
	private List<Link> links;

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

}
