package com.hp.vtms.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hp.vtms.dao.InfoDao;
import com.hp.vtms.model.Info;

@Repository  
public class InfoDaoImpl implements InfoDao{
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	@Autowired
	private SqlSession sqlSession;
	
	
	public Info getInfo(String item)
	{
		Info info=sqlSession.selectOne("info.getValueByItem", item);
		return info;
	}
	public List<Info> getInfoList(){
		List<Info> infoList=sqlSession.selectList("info.getInfo");
		return infoList;
	}

}
