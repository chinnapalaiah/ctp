package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;

public class ResourceEntities extends BaseVColoudModel {

	private static final long serialVersionUID = -8018886184983903140L;

	@JsonProperty("ResourceEntity")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "resourceEntities")
	private List<ResourceEntity> resourceEntities;

	public List<ResourceEntity> getResourceEntities() {
		return resourceEntities;
	}

	public void setResourceEntities(List<ResourceEntity> resourceEntities) {
		this.resourceEntities = resourceEntities;
	}

	@Override
	public String toString() {
		return "ResourceEntities [resourceEntities=" + resourceEntities + "]";
	}

}
