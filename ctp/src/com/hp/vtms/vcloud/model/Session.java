package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;

public class Session extends BaseVColoudModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 284623727485613845L;

	private String user;
	private String org;
	@JsonProperty("Link")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "links")
	private List<Link> links;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	@Override
	public String toString() {
		return "Session [user=" + user + ", org=" + org + ", links=" + links + "]";
	}

}
