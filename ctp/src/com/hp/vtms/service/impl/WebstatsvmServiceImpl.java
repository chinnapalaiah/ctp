package com.hp.vtms.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.vtms.dao.WebstatsvmDao;
import com.hp.vtms.model.Webstatsvm;
import com.hp.vtms.service.WebstatsvmService;

@Service
@Transactional
public  class WebstatsvmServiceImpl implements WebstatsvmService {

	private static final Logger _LOG = LoggerFactory.getLogger(WebstatsvmServiceImpl.class);
	@Autowired
	private WebstatsvmDao webstatsvmDao;

	
	@Override
	public void updateRdpConsoleDetails(Webstatsvm webstatsvm) {
		_LOG.info("calling udpateRdpConsole dao.....................");
		webstatsvmDao.updateRdpConsoleDetails(webstatsvm);
		_LOG.info("After calling.................");
		
	}


	@Override
	public void insertRdpConsoleDetails(Webstatsvm webstatsvm) {
		_LOG.info("calling insertRdpConsoleDetails dao.....................");
		webstatsvmDao.insertRdpConsoleDetails(webstatsvm);
		_LOG.info("After insertRdpConsoleDetails dao.....................");
	}


	@Override
	public List<Webstatsvm> selectInstructorSession(String studentName) {
		List<Webstatsvm> webstatsvmList = webstatsvmDao.selectInstructorSession(studentName);
		return webstatsvmList;
	}


	@Override
	public List<Webstatsvm> retrieveStudentRecord(Webstatsvm webstatsvm) {
		
		_LOG.info("calling insertRdpConsoleDetails dao.....................");
		List<Webstatsvm> webstatsvmList = webstatsvmDao.retrieveStudentRecord(webstatsvm);
		_LOG.info("After insertRdpConsoleDetails dao.....................");
		return webstatsvmList;
	}


	@Override
	public void deleteAllStudentRecords(String userName) {
		_LOG.info("calling insertRdpConsoleDetails dao.....................");
		webstatsvmDao.deleteAllStudentRecords(userName);
		_LOG.info("After insertRdpConsoleDetails dao.....................");
	
		
	}


	
	

}