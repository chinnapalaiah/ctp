package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;

public class VApp extends BaseVColoudModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4395176422698242122L;

	private String deployed;
	private String status;
	private String name;
	private String id;
	private String type;
	private String href;

	@JsonProperty("Link")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "links")
	private List<Link> links;

	@JsonProperty("Children")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "childrens")
	private List<Childrens> childrens;

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

	public List<Childrens> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Childrens> childrens) {
		this.childrens = childrens;
	}

	@Override
	public String toString() {
		return "VApp [deployed=" + deployed + ", status=" + status + ", name=" + name + ", id=" + id + ", type=" + type
				+ ", href=" + href + ", links=" + links + ", childrens=" + childrens + "]";
	}

}
