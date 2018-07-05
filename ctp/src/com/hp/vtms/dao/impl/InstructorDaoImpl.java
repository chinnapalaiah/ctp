package com.hp.vtms.dao.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.hp.vtms.dao.InstructorDao;
import com.hp.vtms.model.Instructor;

/**
 * Created with IntelliJ IDEA. User: meij Date: 13-11-21 Time: 3:19 To change
 * this template use File | Settings | File Templates.
 */
@Repository
public class InstructorDaoImpl implements InstructorDao {
	@Autowired
	private SqlSessionFactory sessionFactory;

	@Autowired
	private SqlSession sqlSession;

	public List<Instructor> getAllInstructor() {
		List<Instructor> instructorFromDb = sqlSession.selectList("instructor.getAllInstructor");
		return instructorFromDb;

	}

	public void updateInstructorPasswords(List<Instructor> instructor) {
		sqlSession.update("instructor.batchUpdatePass", instructor);
	}

	/**
	 * instructor login author vivan
	 * 
	 * @param instructor
	 */

	public Instructor login(String username) {

		Instructor instructor = sqlSession.selectOne("instructor.login", username);
		return instructor;

	}
	
	public String getNameByEventId(Long eventId)
	{
		String username=sqlSession.selectOne("instructor.getNameByEventId", eventId);
		
		return username;
		
	}

    @Override
    public String getInstructorNameById(String instructorId) {
        String username = sqlSession.selectOne("instructor.getInstructorNameById", instructorId);

        return username;
    }
}
