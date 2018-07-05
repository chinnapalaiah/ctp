package com.hp.vtms.service;

import java.util.List;

import com.hp.vtms.model.Webstatsvm;

public abstract interface WebstatsvmService
{
	public abstract void updateRdpConsoleDetails(Webstatsvm webstatsvm);
	public abstract void insertRdpConsoleDetails(Webstatsvm webstatsvm);
	public abstract List<Webstatsvm> selectInstructorSession(String studentName);
	public List<Webstatsvm> retrieveStudentRecord(Webstatsvm webstatsvm);
	public void deleteAllStudentRecords(String userName);
}
