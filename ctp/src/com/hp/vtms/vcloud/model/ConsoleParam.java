package com.hp.vtms.vcloud.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleParam implements Serializable {
	private static final long serialVersionUID = -1970759247066834215L;
	private String vmid;
	private String ticket;
	private String host;
	
	private String vmName;
	private String vappName;
	private String startUrl;
	private String stopUrl;
	private String revertUrl;
	private String vappUrl;
	private String advanced;
	private String isStudentPage;

	private static final Logger _LOG = LoggerFactory.getLogger(ConsoleParam.class);

	public String getVmid() {
		return vmid;
	}

	public void setVmid(String vmid) {
		this.vmid = vmid;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		_LOG.info("ticket:{}", ticket);
		try {
			ticket = URLDecoder.decode(ticket, "utf-8");
			_LOG.info("ticket:{}", ticket);
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		this.ticket = ticket;

	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public String getVmName() {
		return vmName;
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public String getVappName() {
		return vappName;
	}

	public void setVappName(String vappName) {
		this.vappName = vappName;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public String getStopUrl() {
		return stopUrl;
	}

	public void setStopUrl(String stopUrl) {
		this.stopUrl = stopUrl;
	}

	public String getRevertUrl() {
		return revertUrl;
	}

	public void setRevertUrl(String revertUrl) {
		this.revertUrl = revertUrl;
	}

	public String getVappUrl() {
		return vappUrl;
	}

	public void setVappUrl(String vappUrl) {
		this.vappUrl = vappUrl;
	}

	public String getAdvanced() {
		return advanced;
	}

	public void setAdvanced(String advanced) {
		this.advanced = advanced;
	}

	public String getIsStudentPage() {
		return isStudentPage;
	}

	public void setIsStudentPage(String isStudentPage) {
		this.isStudentPage = isStudentPage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static Logger getLog() {
		return _LOG;
	}

	@Override
	public String toString() {
		return "ConsoleParam [vmid=" + vmid + ", ticket=" + ticket + ", host="
				+ host + ", vmName=" + vmName + ", vappName=" + vappName
				+ ", startUrl=" + startUrl + ", stopUrl=" + stopUrl
				+ ", revertUrl=" + revertUrl + ", vappUrl=" + vappUrl
				+ ", advanced=" + advanced + ", isStudentPage=" + isStudentPage
				+ "]";
	}

}
