package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;

public class OrgList extends BaseVColoudModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3113990232893183584L;

	private String type;

	private String href;

	@JsonProperty("Org")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "orgs")
	private List<OrgListChildren> orgs;

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

	public List<OrgListChildren> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<OrgListChildren> orgs) {
		this.orgs = orgs;
	}

}
