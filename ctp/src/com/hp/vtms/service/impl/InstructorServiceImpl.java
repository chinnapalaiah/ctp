package com.hp.vtms.service.impl;

import com.hp.vtms.dao.InstructorDao;
import com.hp.vtms.dao.impl.InstructorDaoImpl;
import com.hp.vtms.model.Instructor;
import com.hp.vtms.service.InstructorService;
import com.hp.vtms.util.HashSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA. User: meij Date: 13-11-21 Time: 3:00 To change
 * this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

	@Autowired
	private InstructorDao instructorDao;

	@Autowired
	private HashSupport passwordSupport;

	@Transactional(readOnly = true)
	
	public List<Instructor> getAllInstructors() {
		return instructorDao.getAllInstructor();
	}

	public void updateInstructorPass(List<Instructor> instructors) {
		instructorDao.updateInstructorPasswords(instructors);
	}
	
	public Instructor login(String username){
		
		Instructor instructor=instructorDao.login(username);
		return instructor;
	}




}
