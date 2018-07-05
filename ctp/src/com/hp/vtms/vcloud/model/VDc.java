package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;

public class VDc extends BaseVColoudModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2642713681766876676L;

	private String status;
	private String name;
	private String id;
	private String type;
	private String href;

	@JsonProperty("Link")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "links")
	private List<Link> links;

	@JsonProperty("ResourceEntities")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "resourceEntities")
	private ResourceEntities resourceEntities;

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

	public ResourceEntities getResourceEntities() {
		return resourceEntities;
	}

	public void setResourceEntities(ResourceEntities resourceEntities) {
		this.resourceEntities = resourceEntities;
	}

	@Override
	public String toString() {
		return "VDc [status=" + status + ", name=" + name + ", id=" + id + ", type=" + type + ", href=" + href
				+ ", links=" + links + ", resourceEntities=" + resourceEntities + "]";
	}

}
