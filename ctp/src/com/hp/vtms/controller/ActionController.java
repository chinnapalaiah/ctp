package com.hp.vtms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.vtms.Constants;
import com.hp.vtms.model.TaskModel;
import com.hp.vtms.model.User;
import com.hp.vtms.model.VAppModel;
import com.hp.vtms.service.VCloudService;
import com.hp.vtms.vcloud.model.VCloudMsg;

@Controller
@RequestMapping("action")
public class ActionController {
	
	private static final Logger _LOG = LoggerFactory.getLogger(ActionController.class);
    private static Pattern p = null;
	private static Matcher m = null;
	private static boolean isValid = false;

	@Autowired
	private VCloudService vCloudService;

	@RequestMapping("consoleAction")
	@ResponseBody
	public String consoleAction(
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "action", required = false) String action,
			ModelMap map, HttpServletRequest request) {
		
		if(isValidURL(url))  
		 {
			 _LOG.info("Returning null...........");
			 return null;
		 }

		HttpSession session = request.getSession();
		String orgName = (String) session
				.getAttribute(Constants.SESSION_ORGNAME);

		VCloudMsg cCloudMsg = new VCloudMsg();
		// cCloudMsg=vCloudService.operateVmThreadPool(url, orgName, action);
		if (action.equalsIgnoreCase("start")
				|| action.equalsIgnoreCase("resume")) {
			cCloudMsg = vCloudService.startVmOrVapp(url, orgName);
		} else if (action.equalsIgnoreCase("stop")) {
			cCloudMsg = vCloudService.stopVmOrVapp(url, orgName);
		} else if (action.equalsIgnoreCase("revert")) {
			cCloudMsg = vCloudService.revertVmOrVapp(url, orgName);
		} else if (action.equalsIgnoreCase("Pause")) {
			cCloudMsg = vCloudService.suspendVmOrVapp(url, orgName);
		}

		return cCloudMsg.getTaskUrl();
	}

	@RequestMapping()
	@ResponseBody
	public String action(
			@RequestParam(value = "url", required = false) String url,
			@RequestParam(value = "action", required = false) String action,
			ModelMap map, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String orgName = (String) session
				.getAttribute(Constants.SESSION_ORGNAME);
		String vappOrVm = request.getParameter("vmOrVapp");
		String vappName = request.getParameter("vappName");
		String status = request.getParameter("status");
		String page = request.getParameter("page");
		//User user = (User) session.getAttribute("user");
		String taskUrl = "";
		VCloudMsg cCloudMsg = new VCloudMsg();
		// cCloudMsg=vCloudService.operateVmThreadPool(url, orgName, action);
		if (action.equalsIgnoreCase("start")
				|| action.equalsIgnoreCase("resume")) {
			cCloudMsg = vCloudService.startVmOrVapp(url, orgName);
		} else if (action.equalsIgnoreCase("stop")) {
			cCloudMsg = vCloudService.stopVmOrVapp(url, orgName);
		} else if (action.equalsIgnoreCase("revert")) {
			cCloudMsg = vCloudService.revertVmOrVapp(url, orgName);
		} else if (action.equalsIgnoreCase("Pause")) {
			cCloudMsg = vCloudService.suspendVmOrVapp(url, orgName);
		}
		// cCloudMsg.setStatusCode(403);
		session.setAttribute("url", url);
		session.setAttribute("taskUrl", taskUrl);
		TaskModel taskModel = new TaskModel();
		if (vappOrVm.equals("vm")) {
			String vmName = request.getParameter("name");
			taskModel.setVmName(vmName);
			taskModel.setVappName(vappName);
		} else if (vappOrVm.equals("vapp")) {
			taskModel.setVappName(vappName);
		}
		taskModel.setVappOrVm(vappOrVm);
		taskModel.setStatus(status);
		taskModel = setErrorCode(cCloudMsg.getStatusCode(), taskModel);
		if (page.equals("training")) {
			VAppModel vapp = (VAppModel) session.getAttribute("vapp");
			if (cCloudMsg.getStatusCode() == 200
					|| cCloudMsg.getStatusCode() == 202) {
				vapp = setSignForTask(vapp, taskModel);
			} else {
				vapp = setErrorForTask(vapp, taskModel);
			}
			session.setAttribute("vappTask", vapp);
		} else if (page.equals("student")) {
			VAppModel vappModel = new VAppModel();
			List<VAppModel> vappList = new ArrayList<VAppModel>();
			vappList = (List<VAppModel>) session.getAttribute("vAppModelList");
			if (cCloudMsg.getStatusCode() == 200
					|| cCloudMsg.getStatusCode() == 202) {
				vappList = setSignForTaskList(vappList, taskModel);

			} else {
				vappList = setErrorForTaskList(vappList, taskModel);

			}
			session.setAttribute("vAppModelListTask", vappList);
		}

		return "ok";
	}

	public TaskModel setErrorCode(int code, TaskModel taskModel) {
		// Integer errorCode=(Integer)code;
		if (code == 400) {
			taskModel.setTaskError(Constants.Error_400);

		} else if (code == 401) {
			taskModel.setTaskError(Constants.Error_401);
		} else if (code == 403) {
			taskModel.setTaskError(Constants.Error_403);
		} else if (code == 404) {
			taskModel.setTaskError(Constants.Error_404);
		} else if (code == 405) {
			taskModel.setTaskError(Constants.Error_405);
		} else if (code == 406) {
			taskModel.setTaskError(Constants.Error_406);
		} else if (code == 409) {
			taskModel.setTaskError(Constants.Error_409);
		} else if (code == 500) {
			taskModel.setTaskError(Constants.Error_500);
		} else if (code == 504) {
			taskModel.setTaskError(Constants.Error_504);
		} else {
			taskModel.setTaskError("Operation Failed");
		}
		return taskModel;
	}

	public VAppModel setErrorForTask(VAppModel vapp, TaskModel taskModel) {
		if (vapp.getName().equals(taskModel.getVappName())) {
			if (taskModel.getVappOrVm().equals("vm")
					&& taskModel.getVmName() != null
					&& !taskModel.getVmName().equals("")) {
				for (int j = 0; j < vapp.getVmModelList().size(); j++) {
					if (taskModel.getVmName().equals(
							vapp.getVmModelList().get(j).getVmName())) {
						vapp.getVmModelList().get(j)
								.setTaskError(taskModel.getTaskError());
						vapp.getVmModelList().get(j)
								.setBeforeOPStatus(taskModel.getStatus());
						;
					}
				}
			} else if (taskModel.getVappOrVm().equals("vapp")) {
				vapp.setTaskError(taskModel.getTaskError());
				vapp.setBeforeOPStatus(taskModel.getStatus());
			}
		}

		return vapp;
	}

	public List<VAppModel> setErrorForTaskList(List<VAppModel> vappList,
			TaskModel taskModel) {
		for (int i = 0; i < vappList.size(); i++) {
			if (vappList.get(i).getName().equals(taskModel.getVappName())) {
				VAppModel vapp = vappList.get(i);
				if (taskModel.getVappOrVm().equals("vm")
						&& taskModel.getVmName() != null
						&& !taskModel.getVmName().equals("")) {
					for (int j = 0; j < vapp.getVmModelList().size(); j++) {
						if (taskModel.getVmName().equals(
								vapp.getVmModelList().get(j).getVmName())) {
							vapp.getVmModelList().get(j)
									.setTaskError(taskModel.getTaskError());
							vapp.getVmModelList().get(j)
									.setBeforeOPStatus(taskModel.getStatus());
						}
					}
				} else if (taskModel.getVappOrVm().equals("vapp")) {
					vapp.setTaskError(taskModel.getTaskError());
					vapp.setBeforeOPStatus(taskModel.getStatus());
				}
				break;
			}
		}

		return vappList;
	}

	public VAppModel setSignForTask(VAppModel vapp, TaskModel taskModel) {

		if (vapp.getName().equals(taskModel.getVappName())) {
			if (taskModel.getVappOrVm().equals("vm")
					&& taskModel.getVmName() != null
					&& !taskModel.getVmName().equals("")) {

				for (int j = 0; j < vapp.getVmModelList().size(); j++) {
					if (taskModel.getVmName().equals(
							vapp.getVmModelList().get(j).getVmName())) {
						vapp.getVmModelList().get(j).setHasTask(true);
						vapp.getVmModelList().get(j)
								.setBeforeOPStatus(taskModel.getStatus());
					}
				}
			} else if (taskModel.getVappOrVm().equals("vapp")) {
				vapp.setHasTask(true);
				vapp.setBeforeOPStatus(taskModel.getStatus());
				for (int j = 0; j < vapp.getVmModelList().size(); j++) {
					vapp.getVmModelList().get(j).setHasTask(true);
					vapp.getVmModelList()
							.get(j)
							.setBeforeOPStatus(
									vapp.getVmModelList().get(j).getStatus());
				}

			}
		}
		return vapp;
	}

	public List<VAppModel> setSignForTaskList(List<VAppModel> vappList,
			TaskModel taskModel) {

		for (int i = 0; i < vappList.size(); i++) {
			if (vappList.get(i).getName().equals(taskModel.getVappName())) {
				VAppModel vapp = vappList.get(i);

				if (taskModel.getVappOrVm().equals("vm")
						&& taskModel.getVmName() != null
						&& !taskModel.getVmName().equals("")) {
					for (int j = 0; j < vapp.getVmModelList().size(); j++) {
						if (taskModel.getVmName().equals(
								vapp.getVmModelList().get(j).getVmName())) {
							vapp.getVmModelList().get(j).setHasTask(true);
							// vapp.getVmModelList().get(j).setStatus(taskModel.getStatus());
							vapp.getVmModelList().get(j)
									.setBeforeOPStatus(taskModel.getStatus());
						}
					}
				} else if (taskModel.getVappOrVm().equals("vapp")) {
					if (vapp.getStatus() != null
							&& vapp.getStatus().equals("8")
							&& vapp.getPauseUrl() != null
							&& !vapp.getPauseUrl().equals("")
							&& vapp.getPowerOnUrl() != null
							&& !vapp.getPowerOnUrl().equals("")) {

					} else {
						vapp.setHasTask(true);
						// vapp.setStatus(taskModel.getStatus());
						vapp.setBeforeOPStatus(taskModel.getStatus());
						// for(int j=0;j<vapp.getVmModelList().size();j++){
						// vapp.getVmModelList().get(j).setHasTask(true);
						// vapp.getVmModelList().get(j).setBeforeOPStatus(vapp.getVmModelList().get(j).getStatus());
						// }
					}
				}
				break;
			}
		}

		return vappList;
	}
	
	 // VALIDATION FOR SPECIAL CHARACTERS
 	public static boolean isValidURL(String data) {
 		// String blackList = "[<>\";@#$%^&+=]()\'";
 		String blackList = "[<>\";@#()$%^&+=\']";
 		p = Pattern.compile(blackList);
 		m = p.matcher(data);
 		isValid = m.find();
 		return isValid;
 	}

}
