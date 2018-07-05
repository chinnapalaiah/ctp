package com.hp.vtms.vcloud.model;

public class Link extends BaseVColoudModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 286032741283282948L;

	private String rel;
	private String type;
	private String href;
	private String name;

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Link [rel=" + rel + ", type=" + type + ", href=" + href + ", name=" + name + "]";
	}

}
