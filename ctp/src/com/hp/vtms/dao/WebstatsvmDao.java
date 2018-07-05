package com.hp.vtms.dao;

import java.util.List;

import com.hp.vtms.model.Webstatsvm;

public abstract interface WebstatsvmDao
{
	public abstract void updateRdpConsoleDetails(Webstatsvm webstats);
	public abstract void insertRdpConsoleDetails(Webstatsvm webstatsvm);
	public abstract List<Webstatsvm> selectInstructorSession(String studentName);
	public List<Webstatsvm> retrieveStudentRecord(Webstatsvm webstatsvm);
	public void deleteAllStudentRecords(String userName);
	
  
 
}
