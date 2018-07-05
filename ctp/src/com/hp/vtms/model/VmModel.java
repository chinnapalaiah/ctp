package com.hp.vtms.model;

import java.io.Serializable;
import java.util.List;

public class VmModel implements Serializable {
	private static final long serialVersionUID = 5263163769080882810L;
	private String vmName;

	private Boolean vmRdp;

	private Boolean isWindows;

	private Boolean vmConsole;

	private Boolean vappStatus;

	private Boolean rdpDisplay;

	private String beforeOPStatus;

	private Boolean consoleDisplay;

	private String status = " ";

	private String powerOnUrl;

	private String powerOffUrl;

	private String revertUrl;

	private String pauseUrl;

	private String ticket;

	private Boolean vcloudVm;

	private String taskStatus;

	private String id;

	private List<String> errorList;

	private Connect connect;

	private Boolean hasTask;

	private String taskError;
	
	private String vappUrl;

	private int advanced;
	

	public String getVappUrl() {
		return vappUrl;
	}

	public void setVappUrl(String vappUrl) {
		this.vappUrl = vappUrl;
	}

	public String getBeforeOPStatus() {
		return beforeOPStatus;
	}

	public void setBeforeOPStatus(String beforeOPStatus) {
		this.beforeOPStatus = beforeOPStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getVcloudVm() {
		return vcloudVm;
	}

	public void setVcloudVm(Boolean vcloudVm) {
		this.vcloudVm = vcloudVm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}

	public Boolean getIsWindows() {
		return isWindows;
	}

	public void setIsWindows(Boolean isWindows) {
		this.isWindows = isWindows;
	}

	public Boolean getVappStatus() {
		return vappStatus;
	}

	public void setVappStatus(Boolean vappStatus) {
		this.vappStatus = vappStatus;
	}

	public Boolean getRdpDisplay() {
		return rdpDisplay;
	}

	public void setRdpDisplay(Boolean rdpDisplay) {
		this.rdpDisplay = rdpDisplay;
	}

	public Boolean getConsoleDisplay() {
		return consoleDisplay;
	}

	public void setConsoleDisplay(Boolean consoleDisplay) {
		this.consoleDisplay = consoleDisplay;
	}

	public Boolean getVmRdp() {
		return vmRdp;
	}

	public void setVmRdp(Boolean vmRdp) {
		this.vmRdp = vmRdp;
	}

	public Boolean getVmConsole() {
		return vmConsole;
	}

	public void setVmConsole(Boolean vmConsole) {
		this.vmConsole = vmConsole;
	}

	public Connect getConnect() {
		return connect;
	}

	public void setConnect(Connect connect) {
		this.connect = connect;
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

	public int getAdvanced() {
		return advanced;
	}

	public void setAdvanced(int advanced) {
		this.advanced = advanced;
	}

}
