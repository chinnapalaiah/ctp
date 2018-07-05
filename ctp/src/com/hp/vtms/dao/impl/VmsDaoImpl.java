package com.hp.vtms.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.hp.vtms.dao.VmsDao;
import com.hp.vtms.model.Vms;

@Repository
public class VmsDaoImpl implements VmsDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<Vms> getVmsListByTemplateId(Long templateId)
	{
		List<Vms> vmsList=sqlSession.selectList("vms.getVmsByTempId", templateId);
		return vmsList;
	}

	public Vms getVmByName(Vms vm){
		vm=sqlSession.selectOne("vms.getVmByName", vm);
		return vm;
	}
	
	public Long getVmID(Vms vm) {
		 vm  = sqlSession.selectOne("vms.getVmIdByVm", vm);
		return vm.getVmId();
	}
	
	public Vms getVmByName(String userName){
		Vms vm=sqlSession.selectOne("vms.getVmByName", userName);
		return vm;
	}
}
