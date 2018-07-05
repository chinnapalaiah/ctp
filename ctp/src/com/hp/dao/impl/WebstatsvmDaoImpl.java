package com.hp.dao.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.hp.vtms.dao.WebstatsvmDao;
import com.hp.vtms.model.Webstatsvm;

public class WebstatsvmDaoImpl implements WebstatsvmDao{
	
	private static SqlSessionFactory sqlSessionFactory=null;
	
	/*public void updateRdpConsoleDetails(Webstatsvm webstatsvm){
		 SqlSession session = getSqlSessionFactory().openSession();	
		int updated =   session.update("nameSpaceWebstats.updateRdpConsoleDetails", webstatsvm);
		System.out.println("The updated Recorsds is -------> "+updated);
		System.out.println();
		  session.commit();
		  session.close();
	}
	*/
	
	public void insertRdpConsoleDetails(Webstatsvm webstatsvm){
		 SqlSession session = getSqlSessionFactory().openSession();	
		int updated =   session.insert("nameSpaceWebstats.insertRdpConsoleBoth", webstatsvm);
		
		System.out.println("The Inserted Recorsds is -------> "+updated);
		System.out.println();
		  session.commit();
		  session.close();
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
	@Override
	public void updateRdpConsoleDetails(Webstatsvm webstats) {
		// TODO Auto-generated method stub
		
	}
	

}
