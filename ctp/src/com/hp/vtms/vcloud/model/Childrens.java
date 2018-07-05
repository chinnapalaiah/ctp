package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.List;

public class Childrens extends BaseVColoudModel {

	private static final long serialVersionUID = -7780059949060246601L;

	@JsonProperty("Vm")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "vms")
	private List<VM> vms;

	public List<VM> getVms() {
		return vms;
	}

	public void setVms(List<VM> vms) {
		this.vms = vms;
	}

	@Override
	public String toString() {
		return "Childrens [vms=" + vms + "]";
	}

}
