package com.hp.vtms.model;

import java.io.Serializable;

public class Vms implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3330697792847717121L;
	
	private Long vmId;
	
	private Long vmTempId;
	
	private String vmName;
	
	private Boolean vmConsole;
	
	private Boolean vmRdp;
	
	private Long vmRam;
	
	private Long vmCpu;
	
	private String vmIp;
	
	private String vmMac;

    private String userName;

	private Boolean vmIsWindows;
	

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getVmId() {
		return vmId;
	}

	public void setVmId(Long vmId) {
		this.vmId = vmId;
	}

	public Long getVmTempId() {
		return vmTempId;
	}

	public void setVmTempId(Long vmTempId) {
		this.vmTempId = vmTempId;
	}

	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public Boolean getVmConsole() {
		return vmConsole;
	}

	public void setVmConsole(Boolean vmConsole) {
		this.vmConsole = vmConsole;
	}

	public Boolean getVmRdp() {
		return vmRdp;
	}

	public void setVmRdp(Boolean vmRdp) {
		this.vmRdp = vmRdp;
	}

	public Long getVmRam() {
		return vmRam;
	}

	public void setVmRam(Long vmRam) {
		this.vmRam = vmRam;
	}

	public Long getVmCpu() {
		return vmCpu;
	}

	public void setVmCpu(Long vmCpu) {
		this.vmCpu = vmCpu;
	}

	public String getVmIp() {
		return vmIp;
	}

	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}

	public String getVmMac() {
		return vmMac;
	}

	public void setVmMac(String vmMac) {
		this.vmMac = vmMac;
	}

	public Boolean getVmIsWindows() {
		return vmIsWindows;
	}

	public void setVmIsWindows(Boolean vmIsWindows) {
		this.vmIsWindows = vmIsWindows;
	}
	
	
	
	

}
