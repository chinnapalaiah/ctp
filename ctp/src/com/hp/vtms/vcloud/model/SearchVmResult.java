package com.hp.vtms.vcloud.model;

import java.io.Serializable;
import java.util.List;

public class SearchVmResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4911055463187430970L;

	private String name;

	private String status;

	private List<Link> links;

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

	@Override
	public String toString() {
		return "SearchVmResult [name=" + name + ", status=" + status + ", links=" + links + "]";
	}

}
