package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OrgListChildren extends BaseVColoudModel {

	private static final long serialVersionUID = -3878998661980337407L;

	@JacksonXmlProperty
	private String name;
	private String type;
	private String href;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "OrgListChildren [name=" + name + ", type=" + type + ", href=" + href + "]";
	}

}
