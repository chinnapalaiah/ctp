package com.hp.vtms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.vtms.dao.InfoDao;
import com.hp.vtms.model.Info;
import com.hp.vtms.model.User;
import com.hp.vtms.service.InfoService;

@Service
public class InfoServiceImpl implements InfoService {
	
	@Autowired 
	private InfoDao infoDao;
	
	public List<Info> getInfo(){
		List<Info> infoList=infoDao.getInfoList();
		return infoList;
	}

	public Info getInfo(String item){
		Info info=infoDao.getInfo(item);
		return info;
	}
	public User getInfoByType(User user){
		List<Info> infoList=infoDao.getInfoList();
		
		if(user.getOs().equals("macos")||user.getOs().equals("ios")||user.getOs().equals("android")){
//		if("macos".equals("macos")){
			Info info=infoDao.getInfo(user.getOs());
//			Info info=infoDao.getLinkFromInfo("macos");
			user.setRdpClientLink(info.getInfoValue());
		}
		
		if(user.getType().equals("attendees")){
			
			for(int i=0;i<infoList.size();i++){
				if(infoList.get(i).getInfoItem().equals("inst_message"))
				{
					infoList.remove(i);
				}
			}
			//if(infoList.size()>0){
			user.setInfoList(infoList);
			//}
			
			
		}else if(user.getType().equals("instructor")){
			
			for(int i=0;i<infoList.size();i++){
				if(infoList.get(i).getInfoItem().equals("stdnt_message"))
				{
					infoList.remove(i);
				}
			}
			//if(infoList.size()>0){
			user.setInfoList(infoList);
			//}
		}
		
		return user;
	}
}
