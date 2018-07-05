package com.hp.vtms.dao.impl;

import com.hp.vtms.dao.EventDao;
import com.hp.vtms.model.Attendees;
import com.hp.vtms.model.Event;
import com.hp.vtms.model.Instructor;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EventDaoImpl implements EventDao {

	@Autowired
	private SqlSessionFactory sessionFactory;

	@Autowired
	private SqlSession sqlSession;

	/**
	 * Get org from event by eventId
	 */
	public String getCell(Long eventId) {
		String cell = sqlSession.selectOne("event.getCell", eventId);

		return cell;
	}

	public void insert(Event event) {

		sqlSession.insert("event.insert", event);
	}

	public Event getEventById(Event Event) {

		Event eventFromDb = (Event) sqlSession.selectOne("event.get", Event);
		return eventFromDb;
	}

	public Event getEventById(Long EventId) {

		Event eventFromDb = (Event) sqlSession.selectOne("event.getTempId", EventId);
		return eventFromDb;
	}

	public List<Event> getAllEvent() {
		List<Event> eventFromDb = sqlSession.selectList("event.getAllEvent");
		return eventFromDb;

	}

	/**
	 * query events by instructor
	 * 
	 * @param args
	 */
	public List<Event> getEventsByInstructor(Instructor instructor) {
		List<Event> events = sqlSession.selectList("event.getEventByInstructor", instructor);
		return events;
	}

	/**
	 * query events by attendees
	 * 
	 * @param args
	 */
	public List<Event> getEventsByAttendees(Attendees attendees) {
		List<Event> events = sqlSession.selectList("event.getEventByAttendees", attendees);
		return events;
	}

	public static void main(String[] args) {

	}

}
