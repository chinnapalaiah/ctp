package com.hp.vtms.model;

import java.io.Serializable;

public class Event implements Serializable {

    public static final String contextDataFlag = "_instructor";

	private static final long serialVersionUID = 4524012461127861838L;
	private Long id;
	private String name;
    private String startDate;
    private String endDate;
	private String instructor;
	private String description;
	private String adminInfo;
	private Long vms;
	private Long ram;
	private Long cpus;
	private String created;
	private String createdBy;
	private String lastUpdatedBy;
	private String lastUpdated;
	private Long eventTempid;
	private String eventTempName;
	private Boolean approved;
	private Boolean classesProvisioned;
	private String instructor2;
	private String instructor3;
	private String instructor4;
	private String startTime;
	private String endTime;
	private String cell;
	private String cellLocation;
	private String numStudents;
	private String vendorPartId;
	private Boolean advanced;
	private Boolean showVRoom;
    private String eventStatus;
	private String rdpUrl;
    private String business;
    private String eventTimezone;
    
    
    private Byte eventTiered;

    public Byte getEventTiered() {
		return eventTiered;
	}

	public void setEventTiered(Byte eventTiered) {
		this.eventTiered = eventTiered;
	}

    public String getEventTimezone() {
        return eventTimezone;
    }

    public void setEventTimezone(String eventTimezone) {
        this.eventTimezone = eventTimezone;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdminInfo() {
		return adminInfo;
	}

	public void setAdminInfo(String adminInfo) {
		this.adminInfo = adminInfo;
	}

	public Long getVms() {
		return vms;
	}

	public void setVms(Long vms) {
		this.vms = vms;
	}

	public Long getRam() {
		return ram;
	}

	public void setRam(Long ram) {
		this.ram = ram;
	}

	public Long getCpus() {
		return cpus;
	}

	public void setCpus(Long cpus) {
		this.cpus = cpus;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getEventTempid() {
		return eventTempid;
	}

	public void setEventTempid(Long eventTempid) {
		this.eventTempid = eventTempid;
	}

	public String getEventTempName() {
		return eventTempName;
	}

	public void setEventTempName(String eventTempName) {
		this.eventTempName = eventTempName;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public Boolean getClassesProvisioned() {
		return classesProvisioned;
	}

	public void setClassesProvisioned(Boolean classesProvisioned) {
		this.classesProvisioned = classesProvisioned;
	}

	public String getInstructor2() {
		return instructor2;
	}

	public void setInstructor2(String instructor2) {
		this.instructor2 = instructor2;
	}

	public String getInstructor3() {
		return instructor3;
	}

	public void setInstructor3(String instructor3) {
		this.instructor3 = instructor3;
	}

	public String getInstructor4() {
		return instructor4;
	}

	public void setInstructor4(String instructor4) {
		this.instructor4 = instructor4;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getCellLocation() {
		return cellLocation;
	}

	public void setCellLocation(String cellLocation) {
		this.cellLocation = cellLocation;
	}

	public String getNumStudents() {
		return numStudents;
	}

	public void setNumStudents(String numStudents) {
		this.numStudents = numStudents;
	}
	public String getVendorPartId() {
		return vendorPartId;
	}

	public void setVendorPartId(String vendorPartId) {
		this.vendorPartId = vendorPartId;
	}	
	public Boolean getAdvanced() {
		return advanced;
	}

	public void setAdvanced(Boolean advanced) {
		this.advanced = advanced;
	}

	public Boolean getShowVRoom() {
		return showVRoom;
	}

	public void setShowVRoom(Boolean showVRoom) {
		this.showVRoom = showVRoom;
	}
	
	public String getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}
	
	public String getRdpUrl() {
		return rdpUrl;
	}

	public void setRdpUrl(String rdpUrl) {
		this.rdpUrl = rdpUrl;
	}
	
	

}
