package com.hp.vtms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hp.vtms.dao.EmailHostDao;
import com.hp.vtms.service.EmailHostService;

@Service
@Transactional
public class EmailHostServiceImpl implements EmailHostService {

	@Autowired
	private EmailHostDao eamilHostDao;

	@Transactional(readOnly = true)
	@Override
	public String getEmailHost(String smptServer) {
		String emailHost = eamilHostDao.getEmailHost(smptServer);
		return emailHost;
	}

}
