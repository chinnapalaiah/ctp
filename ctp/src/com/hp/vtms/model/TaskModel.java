package com.hp.vtms.model;

public class TaskModel {

	private String vappName;
	private String vmName;
	private String status;
	private String vappOrVm;
	private String taskError;
	public String getVappName() {
		return vappName;
	}
	public void setVappName(String vappName) {
		this.vappName = vappName;
	}
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVappOrVm() {
		return vappOrVm;
	}
	public void setVappOrVm(String vappOrVm) {
		this.vappOrVm = vappOrVm;
	}
	public String getTaskError() {
		return taskError;
	}
	public void setTaskError(String taskError) {
		this.taskError = taskError;
	}
	
	
	
}
