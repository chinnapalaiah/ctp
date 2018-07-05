package com.hp.vtms.dao.impl;

import com.hp.vtms.dao.AttendeesDao;
import com.hp.vtms.model.Attendees;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AttendeesDaoImpl implements AttendeesDao {

	@Autowired
	private SqlSessionFactory sessionFactory;

	@Autowired
	private SqlSession sqlSession;

	public Attendees login(String username) {
		Attendees attendees=sqlSession.selectOne("attendees.login", username);
		return attendees;
	}

	@Override
	public List<Attendees> allMatchStudents(Long eventId) {
		return sqlSession.selectList("attendees.selectAll", eventId);
	}


}
