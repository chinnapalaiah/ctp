package com.hp.vtms.vcloud.model;

public class ResourceEntity extends BaseVColoudModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4338359183442813539L;

	private String type;

	private String name;

	private String href;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public String toString() {
		return "ResourceEntity [type=" + type + ", name=" + name + ", href=" + href + "]";
	}

}
