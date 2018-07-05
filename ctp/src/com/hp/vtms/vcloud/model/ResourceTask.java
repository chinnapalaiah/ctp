package com.hp.vtms.vcloud.model;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * Resource Status represent VApp or VM's status
 * 
 * @author huanlinp
 * 
 */
public class ResourceTask implements Serializable {

	private static final long serialVersionUID = -5897859000814756699L;
	private ResourceStatus taskstatus;
	private String status;
	private String name;
	private List<Link> links;
	private List<ResourceTask> children;
	private String url;
	private String id;

	public ResourceStatus getTaskstatus() {
		return taskstatus;
	}

	public void setTaskstatus(ResourceStatus taskstatus) {
		this.taskstatus = taskstatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public List<ResourceTask> getChildren() {
		return children;
	}

	public void setChildren(List<ResourceTask> children) {
		this.children = children;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ResourceTask [taskstatus=" + taskstatus + ", status=" + status + ", name=" + name + ", links=" + links
				+ ", children=" + children + ", url=" + url + ", id=" + id + "]";
	}



}
