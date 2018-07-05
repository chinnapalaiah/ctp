package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;

public class SupportedVersions extends BaseVColoudModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2962011235900278992L;
	@JsonProperty("VersionInfo")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "versions")
	private List<VersionInfo> versions;

	public List<VersionInfo> getVersions() {
		return versions;
	}

	public void setVersions(List<VersionInfo> versions) {
		this.versions = versions;
	}

	@Override
	public String toString() {
		return "SupportedVersions [versions=" + versions + "]";
	}

}
