package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;

public class VM extends BaseVColoudModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7855453306755033733L;

	private String deployed;
	private String status;
	private String name;
	private String id;
	private String type;
	private String href;

	@JsonProperty("Link")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "links")
	private List<Link> links;

	public String getDeployed() {
		return deployed;
	}

	public void setDeployed(String deployed) {
		this.deployed = deployed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@Override
	public String toString() {
		return "VM [deployed=" + deployed + ", status=" + status + ", name=" + name + ", id=" + id + ", type=" + type
				+ ", href=" + href + ", links=" + links + "]";
	}

}
