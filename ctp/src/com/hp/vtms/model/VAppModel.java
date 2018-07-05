package com.hp.vtms.model;

import java.io.Serializable;
import java.util.List;

public class VAppModel implements Serializable {

	private static final long serialVersionUID = -5603447071133674326L;
	private String orgName;
	private String name;
	private String status;
	private String beforeOPStatus;
	private String url;
	private String powerOnUrl;

	private String studentName;

	private String powerOffUrl;

	private String revertUrl;
	
	private String pauseUrl;

	private String taskStatus;
	private Boolean isAllowed;
	private int advanced;

	private int stopedVm;
	
    private Boolean hasTask;
	
	private String taskError;  

	private List<VmModel> vmModelList;

	private List<String> vappErrors;

	public List<VmModel> getVmModelList() {
		return vmModelList;
	}

	public void setVmModelList(List<VmModel> vmModelList) {
		this.vmModelList = vmModelList;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeforeOPStatus() {
		return beforeOPStatus;
	}

	public void setBeforeOPStatus(String beforeOPStatus) {
		this.beforeOPStatus = beforeOPStatus;
	}

	public String getPowerOnUrl() {
		return powerOnUrl;
	}

	public void setPowerOnUrl(String powerOnUrl) {
		this.powerOnUrl = powerOnUrl;
	}

	public String getPowerOffUrl() {
		return powerOffUrl;
	}

	public void setPowerOffUrl(String powerOffUrl) {
		this.powerOffUrl = powerOffUrl;
	}

	public String getRevertUrl() {
		return revertUrl;
	}

	public void setRevertUrl(String revertUrl) {
		this.revertUrl = revertUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public int getAdvanced() {
		return advanced;
	}

	public void setAdvanced(int advanced) {
		this.advanced = advanced;
	}

	public int getStopedVm() {
		return stopedVm;
	}

	public void setStopedVm(int stopedVm) {
		this.stopedVm = stopedVm;
	}

	public List<String> getVappErrors() {
		return vappErrors;
	}

	public void setVappErrors(List<String> vappErrors) {
		this.vappErrors = vappErrors;
	}

	public Boolean getIsAllowed() {
		return isAllowed;
	}

	public void setIsAllowed(Boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

	
	public String getPauseUrl() {
		return pauseUrl;
	}

	public void setPauseUrl(String pauseUrl) {
		this.pauseUrl = pauseUrl;
	}
	

	public Boolean getHasTask() {
		return hasTask;
	}

	public void setHasTask(Boolean hasTask) {
		this.hasTask = hasTask;
	}

	public String getTaskError() {
		return taskError;
	}

	public void setTaskError(String taskError) {
		this.taskError = taskError;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
