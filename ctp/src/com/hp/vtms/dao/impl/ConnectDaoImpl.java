package com.hp.vtms.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hp.vtms.dao.ConnectDao;
import com.hp.vtms.model.Connect;

@Repository
public class ConnectDaoImpl implements ConnectDao{
	@Autowired
	private SqlSessionFactory sessionFactory;

	@Autowired
	private SqlSession sqlSession;

	@Override
	public Connect getConnectByVmId(Connect conn) {
		 conn=(Connect) sqlSession.selectOne("connect.getConnectByVmId",conn);
		 return conn;
	}
	
	public Connect getConnectByAppName(Connect conn) {
		 conn=(Connect) sqlSession.selectOne("connect.getConnectByAppName",conn);
		 return conn;
	}
	
	public Connect getConnectByUserId(Connect conn) {
		 conn=(Connect) sqlSession.selectOne("connect.getConnectByUserId",conn);
		 return conn;
	}	
	
	public Connect getTopConnectByUserName(String conUserId){
		Connect conn=(Connect) sqlSession.selectOne("connect.getTopConnectByUserName",conUserId);
		return conn;
	}
	





}
