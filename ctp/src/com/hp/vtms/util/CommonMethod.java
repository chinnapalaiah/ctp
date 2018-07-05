package com.hp.vtms.util;

import java.util.ArrayList;
import java.util.List;

import com.hp.vtms.model.VmModel;

public class CommonMethod {

	public static List<VmModel>  reSetValue(List<VmModel> dbModelList ,List<VmModel> servletModelList)
	{
		List<VmModel> newServletModel=new ArrayList<VmModel>();
		for(int i=0;i<servletModelList.size();i++)
		{
			for(int j=0; j<dbModelList.size();j++){
				if(servletModelList.get(i).getVmName().equals(dbModelList.get(j).getVmName()))
				{
					VmModel dbModel=dbModelList.get(j);
					VmModel servletModel=servletModelList.get(i);
					servletModel.setVmConsole(dbModel.getVmConsole());
					servletModel.setVmRdp(dbModel.getVmRdp());
					
					if (servletModel.getVcloudVm()!=null &&servletModel.getVcloudVm()==true) {
							if (servletModel.getIsWindows() == false|| servletModel.getIsWindows() == null) {
								servletModel.setRdpDisplay(false);
	
							} else {
									if (servletModel.getVmRdp() != null && servletModel.getVmRdp() == true) {
										servletModel.setRdpDisplay(true);
		
									} else {
										servletModel.setRdpDisplay(false);
									}
	
							}
						
							if (servletModel.getVmConsole() != null && servletModel.getVmConsole() == true) {
								servletModel.setConsoleDisplay(true);
							} else {
								servletModel.setConsoleDisplay(false);
							}
					} else {
						servletModel.setConsoleDisplay(false);
						servletModel.setRdpDisplay(false);

					}
					newServletModel.add(servletModel);
				} 
			
					
				
			}
		}
		return newServletModel;
	}
}
