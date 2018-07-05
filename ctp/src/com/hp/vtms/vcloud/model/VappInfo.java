package com.hp.vtms.vcloud.model;

import java.io.Serializable;
import java.util.List;

public class VappInfo implements Serializable {

	private static final long serialVersionUID = 3707837960601541886L;
	private List<SearchVmResult> list;

	private String vAppUrl;

	private String name;

	private String status;

	private String taskStatus;

	private List<Link> links;

	public List<SearchVmResult> getList() {
		return list;
	}

	public void setList(List<SearchVmResult> list) {
		this.list = list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getvAppUrl() {
		return vAppUrl;
	}

	public void setvAppUrl(String vAppUrl) {
		this.vAppUrl = vAppUrl;
	}

}
