package com.hp.vtms.dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.hp.vtms.dao.WebstatsvmDao;
import com.hp.vtms.model.Webstatsvm;

@Repository
public class WebstatsvmDaoImpl implements WebstatsvmDao {
	
	private static SqlSessionFactory sqlSessionFactory=null;
	
	private static final Logger _LOG = LoggerFactory.getLogger(WebstatsvmDaoImpl.class);
	
	/*public void updateRdpConsoleDetails(Webstatsvm webstatsvm){
		 SqlSession session = getSqlSessionFactory().openSession();	
		int updated =   session.update("nameSpaceWebstats.updateRdpConsoleDetails", webstatsvm);
		System.out.println("The updated Recorsds is -------> "+updated);
		System.out.println();
		  session.commit();
		  session.close();
	}
	*/
	
	public void deleteAllStudentRecords(String userName){
		 SqlSession session = getSqlSessionFactory().openSession();	
		int deleted =   session.delete("nameSpaceWebstats.deleteAllStudentRecords", userName);
		
		_LOG.info("The  Number of Records  Deleted is -------> "+deleted);
		  session.commit();
		  session.close();
	}
	
	public void insertRdpConsoleDetails(Webstatsvm webstatsvm){
		 SqlSession session = getSqlSessionFactory().openSession();
		 _LOG.info("Before executing the query..........");
		int inserted =   session.insert("nameSpaceWebstats.insertRdpConsoleDetails", webstatsvm);
		_LOG.info("After executing the query..........");
		_LOG.info("The Inserted Records is -------> "+inserted);
		
		  session.commit();
		  session.close();
	}   
	
	@Override
	public void updateRdpConsoleDetails(Webstatsvm webstatsvm) {
		SqlSession session = getSqlSessionFactory().openSession();	
		 try{
		int updated =   session.update("nameSpaceWebstats.updateRdpConsoleDetails", webstatsvm);
		_LOG.info("Number of Records updated -------> "+updated);
		 }catch(Exception e){
			 System.out.println("The Error is ------> "+e);
		 }
		
		
	
		  session.commit();
		  session.close();
	
		
	}
	@Override
	public List<Webstatsvm> selectInstructorSession(String studentName) {

		_LOG.info("getting sessionFactory()..................");
		 SqlSession session = getSqlSessionFactory().openSession();
		 _LOG.info("Before executing the selectInstructorSession..........");	
		List<Webstatsvm> webstatsvmList=   session.selectList("nameSpaceWebstats.selectInstructorSession", studentName);
		_LOG.info("After executing the selectInstructorSession..........");	
		
		  session.commit();
		  session.close();
		  
		  return webstatsvmList;
	
	}

	
	
	public List<Webstatsvm> retrieveStudentRecord(Webstatsvm webstatsvm){
		 SqlSession session = getSqlSessionFactory().openSession();	
		List<Webstatsvm> webstatsvmList =   session.selectList("nameSpaceWebstats.retrieveStudentRecord", webstatsvm);
		
		
		System.out.println();
		  session.commit();
		  session.close();
		  return webstatsvmList;
	}
	

	
	public static SqlSessionFactory getSqlSessionFactory(){
		String resource = "webstats-config.xml";
		InputStream inputStream = null;
		try{
			if(inputStream==null){
				inputStream = Resources.getResourceAsStream(resource);
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
				return sqlSessionFactory;
			}
		}catch(IOException e){
			
		}
		return sqlSessionFactory;
		
	}

	
	
}
