package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;

public class Children extends BaseVColoudModel {

	private static final long serialVersionUID = -8323675508376864552L;

	@JsonProperty("Vm")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "vms")
	private List<VM> vms;

	@Override
	public String toString() {
		return "Children [vms=" + vms + "]";
	}

}
