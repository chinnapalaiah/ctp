package com.hp.vtms.dao.impl;

import com.hp.vtms.dao.TemplatesDao;
import com.hp.vtms.model.Templates;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TemplatesDaoImpl implements TemplatesDao {
	@Autowired
	private SqlSessionFactory sessionFactory;

	@Autowired
	private SqlSession sqlSession;

	public Templates getVmsName(Long tempId) {

		Templates templates = sqlSession.selectOne("templates.getVmsByEventId", tempId);

		return templates;
	}
	
}
