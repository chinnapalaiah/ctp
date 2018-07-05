package com.hp.vtms.vcloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.io.Serializable;
import java.util.List;

public class Tasks implements Serializable {

	private static final long serialVersionUID = -3262813907165529216L;
	@JsonProperty("Task")
	@JacksonXmlElementWrapper(useWrapping = false, localName = "task")
	private List<Task> task;

	public List<Task> getTask() {
		return task;
	}

	public void setTask(List<Task> task) {
		this.task = task;
	}

}
