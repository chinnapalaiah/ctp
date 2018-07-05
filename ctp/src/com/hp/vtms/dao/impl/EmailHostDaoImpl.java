package com.hp.vtms.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hp.vtms.dao.EmailHostDao;

@Repository
public class EmailHostDaoImpl implements EmailHostDao {

	@Autowired
	private SqlSessionFactory sessionFactory;

	@Autowired
	private SqlSession sqlSession;

	public String getEmailHost(String smptServer) {
		String emailHostValue = sqlSession.selectOne("emailHost.getEmailHost", smptServer);
		return emailHostValue;
	}


}
