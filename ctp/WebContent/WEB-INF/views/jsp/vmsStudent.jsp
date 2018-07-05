
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ page isELIgnored="false" %>




 <script type="text/javascript">
    $('.dropdown-toggle').dropdown();
    var object = null;
    if(navigator.userAgent.indexOf("MSIE")>0||browser.versions.ie11) {
    	object = "<object id='vmrc' classid='CLSID:4AEA1010-0A0C-405E-9B74-767FC8A998CB' style='width: 1px; height: 1px;'></object>";
    }else{
    	object = "<object id='vmrc' type='application/x-vmware-remote-console-2012' style='width: 1px; height: 1px;'></object>";
    }
    object = object+ "<object width='1' height='1' id='instance0' type='application/x-vmware-client-support-5-5-0' style='width: 1px; height: 1px;'></object>";
    $("#vmobject").html(object);
    $(".v-console").bind("click",function(){
    	vmstatus = $(this).attr("vmstatus");
    	vmName = $(this).attr("vmname");
    	vappName = $(this).attr("vappid");
    	ticket = $(this).attr("ticket");
     	 startUrl = $(this).attr("start");
     	 stopUrl = $(this).attr("stop");
     	  revertUrl = $(this).attr("revert");
     	 vappUrl = $(this).attr("vappurl");
     	 advanced = $(this).attr("advanced");
    	 toConsole(vmstatus);
    	 
    	});
</script>
<div class='row'>
	<div class="col-md-8 col-xs-12 col-md-offset-2 col-xs-12-padding">
		<div class="thumbnail" style="height: auto;
min-height: 510px;">
			<div class='row'>
				<div class="col-md-12 col-xs-12">
				<h2 align="center" class="title">Student Training Management</h2>
					<hr>
				</div>
			</div>
			<c:if test="${user.os ne null &&user.os ne '' &&(user.os eq 'ios' || user.os eq 'android' ||user.os eq 'macos')}">
			<div class='row'>
			   <div class="col-md-12 col-xs-12" style="padding-left:9.5%">
			    <label class="title" style="font-size:16px;"><strong id="rdpRemind">If you cannot open or download the RDP connection, please download and install 
			     <a href="${user.rdpClientLink}" class=" rdpClient" ata-toggle="tooltip" data-placement="top"
										data-animation="true"  target=_blank style="color:red;" 
										>Client</a>.</strong> </label>
	          </div>
	        </div>
	        </c:if>
		<c:if test="${user.allowed ne null && user.allowed eq true}">
		  <c:if test="${vappCount!=null && vappCount>0 }">
		   <c:if test="${vmIsWCount!=null && vmIsWCount>0 }">
			<div class='row'>
			   <div class="col-md-12 col-xs-12" style="padding-left:9.5%">
	
				<label class="title" style="font-size:16px;"><strong>Choose RDP or Console (applies to all) : &nbsp;</strong> </label>
			
				
				
				<c:if test="${instructorGloabOption.gloabOption eq null || instructorGloabOption.gloabOption eq false}">
			     <input type="radio" value="ShowRdp" name="gloab" id="showRdp&${user.userName}" class="showGloabOption"> <label class="label-option">Rdp&nbsp;</label>&nbsp;
				  <input type="radio" value="ShowConsole" name="gloab" id="showConsole&${user.userName}" class="showGloabOption"> <label class="label-option">Console&nbsp;</label>&nbsp;
				  <input type="radio" value="Show Both" name="gloab" id="showBoth&${user.userName}" class="showGloabOption"> <label class="label-option">Both&nbsp;</label>&nbsp;
				</c:if>
				<c:if test="${instructorGloabOption.gloabOption ne null && instructorGloabOption.gloabOption eq true}">
				  
				  <c:if test="${instructorGloabOption.gloabConsole eq null || instructorGloabOption.gloabConsole eq false && instructorGloabOption.gloabRdp ne null && instructorGloabOption.gloabRdp eq true}">
					 <input type="radio" value="ShowRdp" name="gloab" id="showRdp&${user.userName}" class="showGloabOption" checked="checked"> <label class="label-option">Rdp&nbsp;</label>&nbsp;
					 <input type="radio" value="ShowConsole" name="gloab" id="showConsole&${user.userName}" class="showGloabOption"> <label class="label-option">Console&nbsp;</label>&nbsp;
					   <input type="radio" value="Show Both" name="gloab" id="showBoth&${user.userName}" class="showGloabOption"> <label class="label-option">Both&nbsp;</label>&nbsp;
				  </c:if>
				   <c:if test="${instructorGloabOption.gloabRdp eq null || instructorGloabOption.gloabRdp eq false && instructorGloabOption.gloabConsole ne null && instructorGloabOption.gloabConsole eq true}">
					  <input type="radio" value="ShowRdp" name="gloab" id="showRdp&${user.userName}" class="showGloabOption"> <label class="label-option">Rdp&nbsp;</label>&nbsp;
					 <input type="radio" value="ShowConsole" name="gloab" id="showConsole&${user.userName}" class="showGloabOption" checked="checked"> <label class="label-option">Console&nbsp;</label>&nbsp;
					 <input type="radio" value="Show Both" name="gloab" id="showBoth&${user.userName}" class="showGloabOption"> <label class="label-option">Both&nbsp;</label>&nbsp;
				  </c:if>
				   <c:if test="${instructorGloabOption.gloabConsole ne null && instructorGloabOption.gloabConsole eq true && instructorGloabOption.gloabRdp ne null&& instructorGloabOption.gloabRdp eq true}">
					  <input type="radio" value="ShowRdp" name="gloab" id="showRdp&${user.userName}" class="showGloabOption"> <label class="label-option">Rdp&nbsp;</label>&nbsp;
					<input type="radio" value="ShowConsole" name="gloab" id="showConsole&${user.userName}" class="showGloabOption"> <label class="label-option">Console&nbsp;</label>&nbsp;
					  <input type="radio" value="Show Both" name="gloab" id="showBoth&${user.userName}" class="showGloabOption" checked="checked"> <label class="label-option">Both&nbsp;</label>&nbsp;
				  </c:if>
				</c:if>
				</div>
				</div>
		      
			<br>
		</c:if>
		</c:if>
		</c:if>
			<c:forEach items="${vAppModelList}" var="vapp">
			


				<div class="row ">
					<div class="col-md-12  col-sm-12 col-xs-12  col-xs-vappdiv-height"
						style="display: inline-block;">

						<div class="col-stuName">
							<span><strong>Student Name:</strong>&nbsp;${vapp.studentName}</span>
						</div>
						
					</div>

				</div>

				<div class="row col-xs-vapprow-height">
					<c:if test="${vapp.url eq '' || vapp.url eq null}">
						<div
							class="col-md-8  col-sm-8 col-xs-12 col-md-8-vapp col-xs-vappdiv-height"
							style="display: inline-block;">

							<div class="col-vapp">
								<span class="vapp-icon  " data-toggle="tooltip" id="VappNIC"
									data-placement="top" title="There is a problem connecting to the Environment and VM"></span>
								<img src="img/warning.png" class="block">
							</div>
							<div class="col-xs-10 col-width col-xs-vappline1-height"
								style="display: inline-block; height: 40px;padding-right:0px;">
								<label class="small-body-copy bold">Environment:&nbsp; </label>
								${vapp.name}
							</div>
						</div>
							
					</c:if>

                   

					<c:if test="${vapp.url ne null && vapp.url ne ''}">
						<c:if test="${vapp.stopedVm > 0  }">
						  <c:if test="${vapp.stopedVm lt fn:length(vapp.vmModelList)}">
						  
						    <div
								class="col-md-8  col-sm-8 col-xs-12 col-md-8-vapp col-xs-vappdiv-height"
								style="display: inline-block;">
								<div class="col-vapp">
									<span class="vapp-icon" data-toggle="tooltip"
										data-placement="top" id="VAppFR"
										title="The Environment is partially running"></span> <img
										src="img/warning.png" class="block-vapp">


								</div>

								<div class="col-xs-10 col-width col-xs-vappline1-height"
									style="display: inline-block; height: 40px;padding-right:0px;">
									<label class="small-body-copy bold">Environment:&nbsp;</label>
									${vapp.name}
								</div>

							</div>
						  
						  </c:if>
						  <c:if test="${fn:length(vapp.vmModelList) eq vapp.stopedVm}">
						     <div
								class="col-md-8  col-sm-8 col-xs-12 col-md-8-vapp col-xs-vappdiv-height"
								style="display: inline-block;">
								<div class="col-vapp">
									<span class="vapp-icon" data-toggle="tooltip"
										data-placement="top" id="VAppFR"
										title="The Environment is not running"></span> <img
										src="img/warning.png" class="block-vapp">


								</div>

								<div class="col-xs-10 col-width col-xs-vappline1-height"
									style="display: inline-block; height: 40px;padding-right:0px;">
									<label class="small-body-copy bold">Environment:&nbsp;</label>
									${vapp.name}
								</div>

							</div>
						  
						  </c:if>
							

						</c:if>

						<c:if test="${vapp.stopedVm eq 0  }">

							<div
								class="col-md-8  col-sm-8 col-xs-12 col-md-8-vapp col-xs-vappdiv-height"
								style="display: inline-block;">
								<div class=" col-vapp">
									<span class="vapp-icon"></span>
								</div>

								<div class="col-xs-10 col-width col-xs-vappline1-height"
									style="display: inline-block; height: 40px;padding-right:0px;">
									<label class="small-body-copy bold">Environment:&nbsp; </label>${vapp.name}</div>

							</div>
						</c:if>



					</c:if>


					<div class="col-md-4 col-sm-4 col-xs-12 col-xs-6-vm style="height:40px;display:inline-block;">


						<div style="display: none" class="col-vapp-strbtn col-vm-btn"
							id="${vapp.name }_loading" name="${vapp.name}*vapp_hidden">
							<img src="img/loading.gif" alt="" class="v-loading"> <span
								name="${vapp.name}*vapp_font" class="busy"></span>
						</div>

						<c:if test="${vapp.url ne null && vapp.url ne '' && vapp.taskStatus eq 'Progressing'}">
							<div id="Loading&${vapp.name}&vm" class="col-vapp-strbtn col-vm-btn">
								<img src="img/loading.gif" class="v-loading"> <span
									id="${vapp.name}_font" class="busy">Busy</span>

							</div>
						</c:if>

						<c:if test="${vapp.taskStatus ne 'Progressing'}">

							<div class="col-vapp-strbtn col-vm-btn"
								name="${vapp.name}*vapp_display" id="${vapp.name}">


								<c:if test="${vapp.url ne null && vapp.url ne ''}">

									<c:if
										test="${vapp.powerOnUrl eq null || vapp.powerOnUrl eq '' }">
										<a href="javascript:void(0)" class="col-sm-button-1"
											style="cursor: not-allowed;" id="Start&${vapp.name}&vapp&${vapp.status}"
											onclick="return false;" title="Start" name="gray"> <img
											src="img/play_gray.png" class="v-start "></a>

									</c:if>

									<c:if
										test="${vapp.powerOnUrl ne null && vapp.powerOnUrl ne ''  }">
										<a href="javascript:void(0)" class="col-sm-button-1"
											id="Start&${vapp.name}&vapp&${vapp.status}" title="Start"
											name="${vapp.powerOnUrl }"> <img src="img/play.png"
											class="v-start fuzzy"></a>


									</c:if>

									<c:if
										test="${vapp.powerOffUrl eq '' || vapp.powerOffUrl eq null }">
										<a href="javascript:void(0)" class="col-sm-button-1"
											style="cursor: not-allowed;" id="Stop&${vapp.name}&vapp&${vapp.status}"
											onclick="return false;" title="Stop" name="gray"> <img
											src="img/stop_gray.png" class="v-stop">
										</a>

									</c:if>

									<c:if
										test="${vapp.powerOffUrl ne null && vapp.powerOffUrl ne '' }">

										<a href="javascript:void(0)" class="col-sm-button-1"
											id="Stop&${vapp.name}&vapp&${vapp.status}" title="Stop"
											name="${vapp.powerOffUrl }"> <img src="img/stop.png"
											class="v-stop fuzzy">
										</a>

									</c:if>
								
									
								<c:if test="${vapp.pauseUrl ne null && vapp.pauseUrl ne ''  }">
                                  	<a href="javascript:void(0)" class="col-sm-button-1"
										title="Pause" id="Pause&${vapp.name }&vapp&${vapp.status}"
										 name="${vapp.pauseUrl }"><img
										class="v-pause" src="img/pause.png"></a>
								 </c:if>
								  <c:if test="${vapp.pauseUrl eq null || vapp.pauseUrl eq ''  }">
                                  	<a href="javascript:void(0)" class="col-sm-button-1"
										title="Pause"  style="cursor: not-allowed;"
										id="Pause&${vapp.name }&vapp&${vapp.status}"
										onclick="return false;" name="gray"><img
										class="v-pause" src="img/pause_gray.png"></a>
								</c:if>

									<c:if test="${vapp.advanced ne null && vapp.advanced eq 1}">

										<c:if
											test="${vapp.revertUrl eq null || vapp.revertUrl eq '' }">
											<a href="javascript:void(0)" title="Revert"
												class="col-sm-button-1" style="cursor: not-allowed;"
												id="Revert&${vapp.name }&vapp&${vapp.status}" onclick="return false;"
												name="gray"> <img src="img/revert_gray.png"
												class="v-revert">
											</a>
										</c:if>
										<c:if
											test="${vapp.revertUrl ne null && vapp.revertUrl ne '' }">
											<a href="javascript:void(0)" title="Revert"
												class="col-sm-button-1" name="${vapp.revertUrl}"
												id="Revert&${vapp.name }&vapp&${vapp.status}"> <img
												src="img/revert.png" class="v-revert fuzzy">
											</a>

										</c:if>
									</c:if>

									<c:if test="${vapp.advanced eq null || vapp.advanced eq 0}">
										<a href="javascript:void(0)" title="Revert"
											class="col-sm-button-1" style="visibility: hidden;"
											id="Revert&${vapp.name }&vapp&${vapp.status}" onclick="return false;"
											name="gray"> <img src="img/revert_gray.png"
											class="v-revert">
										</a>

									</c:if>


								</c:if>
							</div>
						</c:if>

					</div>
						<div style="clear: both;"></div>
                         <div class="row" >
                          
							   <c:forEach items="${vapp.vappErrors }" var="vappErrors">
							    <div class="col-md-8 col-xs-12 col-sm-8" style="padding-left: 13%;">
										<font class="error-font">${vappErrors }</font>
									</div>
						      </c:forEach>
						    
						      <c:if test="${vapp.taskError ne ''}">
						       <c:if test="${fn:length(vapp.vappErrors)eq 0 }">
								  <div class="col-md-12 col-xs-12 col-sm-12 col-error-xs-1" style="">
										<font class="error-font">${vapp.taskError }</font>
								 </div>
								</c:if>
						         <c:if test="${fn:length(vapp.vappErrors)> 0 }">
								   <div class="col-md-4 col-xs-12 col-sm-4 col-error-xs" style="">
										<font class="error-font">${vapp.taskError }</font>
								 </div>
								</c:if>
							
							 </c:if>
					  </div>
				
                   </div>                  
					
				
				<c:forEach items="${vapp.vmModelList}" var="vm">

					<div class="row">



						<div class="col-md-5 col-xs-12 col-sm-5 col-xs12-height"
							style="padding-left: 13%;">
						    <c:if test="${fn:length(vm.errorList) eq 0 || vm.errorList eq null}">
					                  <div class="col-vm">
											<span class="vm" style="width: 50px;"></span>
										</div>

										<div class="col-vm-name">${vm.vmName }</div>
							</c:if>
							
							 <c:if test="${vm.errorList ne null && fn:length(vm.errorList)>0}">
							      <div class="col-vm">
											<span class="vm" style="width: 50px;" data-toggle="tooltip"
												id="vmNotRDP" data-placement="top"
												title="${ vm.errorList[0]}"></span> <img
												class="block" src="img/warning.png">
										</div>
										<div class="col-vm-name">${vm.vmName }</div>
							</c:if>	
						
                        </div>
                        

                     <div class=" col-md-3 col-xs-6 col-sm-3 col-xs5-padding"
								style="line-height: 40px;padding-right:0px;">
				  
						<c:if test="${vm.isWindows ne null}">
	                    	<c:if test="${vm.connect ne null }">
	                    
											<a 
												title="Launch RDP"
												class="col-sm-button-2"  name="gray"
												id="LaunchRdp&${vapp.name}&${vm.vmName  }&vm&${vapp.studentName}" 
												style="z-index: 3;cursor:pointer;padding-right: 13%;"> <img src="img/rdp.png"
												class="v-rdp fuzzy"></a>
							</c:if>
							<c:if test="${vm.connect eq null }">
							
							         <a href="" class="col-sm-button-2"
												id="LaunchRdp&${vapp.name}*${vm.vmName  }&vm"
												onclick="return false;" name="gray"
												style="display: inline-block; cursor: not-allowed;padding-right: 13%;" > 

												<img
												src="img/rdp.png" class="v-rdp fuzzy-gray" id="rdp-gray"
												data-toggle="tooltip" data-placement="top"
												title="Could not create RDP file"> <img
												src="img/warning.png" class="rdp-block" style="left: 11px;top: -29px;">
											</a>
							</c:if>
							

					   </c:if>
					   
					   <c:if test="${ vapp.url ne null && vapp.url ne ''}">
					     <c:if test="${vm.vcloudVm ne null && vm.vcloudVm eq true}">
					      <a href="" class="vapp_console col-sm-button-2"
										id="console&${vapp.name}&${vm.vmName  }" title="Launch Console"
										onclick="return false;" name="gray" style="z-index: 1;padding-right: 13%;"> <img
										src="img/console.png" class="v-console" vappurl="${vapp.url }" vappid="${vapp.name }"
										ticket="${vm.ticket  }" vmname="${vm.vmName  }" advanced="${vapp.advanced}" studentPage="true"
										vmstatus="${vm.status}" powerOn="${vm.powerOnUrl}"
										powerOff="${vm.powerOffUrl}" revert="${vm.revertUrl}"></a>
                     </c:if>
					  
				
					
			
					   <c:if test="${vm.isWindows ne null}">
						<c:if test="${vapp.isAllowed ne null && vapp.isAllowed eq true}">
				           <form id="dropDownForm" method="post">
						     <div class="dropdown">
								
								     <a id="dLabel" role="button" data-toggle="dropdown" data-target="#" href="/page.html" 
								     id="${vapp.name}&${vm.vmName  }"  style="padding-top: 10px;font-size: 20px;color: rgb(51, 51, 51);padding-right:0px;width:0px;"
								     title="Show RDP or Console for Student" data-toggle="dropdown tooltip" data-placement="top"
								     class="col-sm-button-1 glyphicon glyphicon-list dropdown-toggle fuzzy">
		                             </a>
									 <ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dLabel">
									  <c:if test="${vm.rdpDisplay ne null && vm.rdpDisplay eq true && vm.consoleDisplay eq null || vm.consoleDisplay eq false}">
									        <li role="presentation" class=" option">
									        <input type="radio" class="showOption showRdp" action="showRdp" checked="checked" name="${vapp.name}&${vm.vmName}"  id="showRdp&${vapp.name}&${vm.vmName}">
									        <label class="label-option">&nbsp;RDP</label></li>
									        <li role="presentation" class="option"><input type="radio"  class="showOption showConsole" action="showConsole" name="${vapp.name}&${vm.vmName}"  id="showConsole&${vapp.name}&${vm.vmName}"><label class="label-option" >&nbsp;Console</label></li>
									        <li role="presentation" class="option"><input type="radio" class="showOption showBoth" action="showBoth" name="${vapp.name}&${vm.vmName}"  id="showBoth&${vapp.name}&${vm.vmName}"><label class="label-option" >&nbsp;Both</label></li>
									  </c:if>
									  <c:if test="${vm.rdpDisplay eq null || vm.rdpDisplay eq false && vm.consoleDisplay ne null && vm.consoleDisplay eq true}">
									       <li role="presentation" class="option"><input type="radio" class="showOption showRdp" action="showRdp" name="${vapp.name}&${vm.vmName}"  id="showRdp&${vapp.name}&${vm.vmName}"><label class="label-option"><label class="label-option" >&nbsp;RDP</label></label></li>
									       <li role="presentation" class=" option"><input type="radio" class="showOption showConsole" action="showConsole" checked="checked" name="${vapp.name}&${vm.vmName}" id="showConsole&${vapp.name}&${vm.vmName}"><label class="label-option" >&nbsp;Console</label></li>
									       <li role="presentation" class="option"><input type="radio" class="showOption showBoth"  action="showBoth" name="${vapp.name}&${vm.vmName}"  id="showBoth&${vapp.name}&${vm.vmName}">&nbsp;Both</li>
									   </c:if>
									   <c:if test="${vm.rdpDisplay eq true && vm.consoleDisplay eq true}">
									    
									       <li role="presentation" class="option"><input type="radio" class="showOption showRdp" action="showRdp" name="${vapp.name}&${vm.vmName}"  id="showRdp&${vapp.name}&${vm.vmName}"><label class="label-option" >&nbsp;RDP</label></li>
									       <li role="presentation" class=" option"><input type="radio" class="showOption showConsole"  action="showConsole" name="${vapp.name}&${vm.vmName}" id="showConsole&${vapp.name}&${vm.vmName}"><label class="label-option" >&nbsp;Console</label></li>
									       <li role="presentation" class="option"><input type="radio" class="showOption showBoth" action="showBoth" checked="checked"  name="${vapp.name}&${vm.vmName}"  id="showBoth&${vapp.name}&${vm.vmName}"><label class="label-option" >&nbsp;Both</label></li>
									   </c:if>
									   
								    </ul>
							 </div>
						  </form>
						 </c:if>
						</c:if>
                   </c:if>
                     </div>
                     


							<div class="col-md-4 col-xs-6 col-sm-4 col-xs-12-vm"
								style="display: none;" id="${vapp.name}*${vm.vmName }_loading"
								name="${vapp.name}*vm_hidden">
								<img src="img/loading.gif" alt="" class="v-loading"> <span
									id="${vapp.name}*${vm.vmName }_font" class="busy"></span>
							</div>

							<c:if test="${vm.vcloudVm ne null && vm.vcloudVm eq true}">
								<c:if test="${vm.taskStatus eq 'Progressing'}">
									<div class="col-md-4 col-xs-6 col-sm-4 col-xs-12-vm"
										id="Loading&${vapp.name}*${vm.vmName}&vm">
										<img class="v-loading" src="img/loading.gif"> <span
											id="${vapp.name}*${vm.vmName  }_font" class="busy">Busy</span>

									</div>
								</c:if>


								<c:if test="${vm.taskStatus ne 'Progressing'}">
									<div class=" col-md-4 col-xs-6 col-sm-4 col-xs-12-vm"
										style="line-height: 50px;" name="${vapp.name}*vm_display"
										id="${vapp.name}*${vm.vmName}">


										<c:if test="${vm.powerOnUrl ne null &&vm.powerOnUrl ne ''}">
                                           <c:if test="${vm.status eq '8'}">
												<a href="javascript:void(0)" class="col-sm-button-1"
													title="Start" name="${vm.powerOnUrl}"
													id="Start&${vapp.name}*${vm.vmName  }&vm&${vm.status}"> <img
													src="img/play.png" class="v-start fuzzy">
												</a>
											</c:if>
											<c:if test="${vm.status eq '3'}">
												<a href="javascript:void(0)" class="col-sm-button-1"
													title="Resume" name="${vm.powerOnUrl}"
													id="Start&${vapp.name}*${vm.vmName  }&vm&${vm.status}"> <img
													src="img/play.png" class="v-start fuzzy">
												</a>
											</c:if>

										</c:if>
										<c:if test="${vm.powerOnUrl eq null ||vm.powerOnUrl eq '' }">

											<a href="javascript:void(0)" class="col-sm-button-1"
												title="Start" style="cursor: not-allowed;"
												id="Start&${vapp.name}*${vm.vmName  }&vm&${vm.status}"
												onclick="return false;" name="gray"> <img
												class="v-start " src="img/play_gray.png">
											</a>
										</c:if>

										<c:if
											test="${vm.powerOffUrl ne null && vm.powerOffUrl ne '' }">

											<a href="javascript:void(0)" class="col-sm-button-1"
												name="${vm.powerOffUrl}"
												id="Stop&${vapp.name}*${vm.vmName }&vm&${vm.status}" title="Stop"><img
												src="img/stop.png" class="v-stop fuzzy"></a>


										</c:if>
										<c:if test="${vm.powerOffUrl eq null ||vm.powerOffUrl eq '' }">

											<a href="javascript:void(0)" class="col-sm-button-1"
												title="Stop" style="cursor: not-allowed;"
												id="Stop&${vapp.name}*${vm.vmName }&vm&${vm.status}"
												onclick="return false;" name="gray"><img
												src="img/stop_gray.png" class="v-stop "></a>
										</c:if>
										
									  <c:if test="${vm.pauseUrl ne null && vm.pauseUrl ne ''  }">
                                  	    <a href="javascript:void(0)" class="col-sm-button-1"
										title="Pause" id="Pause&${vapp.name}*${vm.vmName }&vm&${vm.status}"
										name="${vm.pauseUrl}"><img
										class="v-pause fuzzy" src="img/pause.png"></a>
								     </c:if>
								     <c:if test="${vm.pauseUrl eq null || vm.pauseUrl eq ''  }">
                                  	  <a href="javascript:void(0)" class="col-sm-button-1"
										title="Pause"  style="cursor: not-allowed;"
										id="Pause&${vapp.name}*${vm.vmName }&vm&${vm.status}"
										onclick="return false;" name="gray"><img
										class="v-pause" src="img/pause_gray.png"></a>
								  </c:if>
                                    

										<c:if test="${vapp.advanced ne null && vapp.advanced eq 1}">

											<c:if test="${vm.revertUrl ne null &&vm.revertUrl ne '' }">
												<a href="javascript:void(0)" title="Revert"
													class="col-sm-button-1" name="${vm.revertUrl}"
													id="Revert&${vapp.name}*${vm.vmName }&vm&${vm.status}"> <img
													src="img/revert.png" class="v-revert fuzzy">
												</a>

											</c:if>
											<c:if test="${vm.revertUrl eq null || vm.revertUrl eq '' }">
												<a href="javascript:void(0)" title="Revert"
													class="col-sm-button-1" style="cursor: not-allowed;"
													id="Revert&${vapp.name}*${vm.vmName }&vm&${vm.status}"
													onclick="return false;" name="gray"> <img
													src="img/revert_gray.png" class="v-revert">
												</a>
											</c:if>
										</c:if>

										<c:if test="${vapp.advanced eq null || vapp.advanced eq 0}">

											<a href="javascript:void(0)" title="Revert"
												class="col-sm-button-1" style="visibility: hidden;"
												id="Revert&${vapp.name}*${vm.vmName }&vm&${vm.status}"
												onclick="return false;" name="gray"> <img
												class="v-revert " src="img/revert_gray.png">
											</a>

										</c:if>


									</div>
								</c:if>

							</c:if>
							
                          
						<!-- 	<c:if test="${vm.vcloudVm eq null || vm.vcloudVm eq false }">
								<div class=" col-md-4 col-xs-6 col-sm-4 col-xs-12-vm"
									style="line-height: 50px;" name="${vapp.name}*vm_display"
									status="notInVcloud">
									<a href="javascript:void(0)" class="col-sm-button-1"
										title="Start" style="cursor: not-allowed;"
										id="Start&${vapp.name}*${vm.vmName  }&vm&${vm.status}"
										onclick="return false;" name="gray"> <img
										src="img/play_gray.png" class="v-start ">
									</a> 
									<a href="javascript:void(0)" class="col-sm-button-1"
										title="Stop" style="cursor: not-allowed;"
										id="Stop&${vapp.name}*${vm.vmName }&vm&${vm.status}"
										onclick="return false;" name="gray"><img
										src="img/stop_gray.png" class="v-stop"> </a>
										
										
								  <a href="javascript:void(0)" class="col-sm-button-1"
										title="Pause" style="cursor: not-allowed;"
										id="Pause&${vapp.name}*${vm.vmName }&vm&${vm.status}"
										onclick="return false;" name="gray"><img
										class="v-pause " src="img/pause_gray.png"></a>
								
								   <c:if test="${vapp.advanced ne null && vapp.advanced eq 1 }">
										 
									<a
										href="javascript:void(0)" title="Revert"
										class="col-sm-button-1" style="cursor: not-allowed;"
										id="Revert&${vapp.name}*${vm.vmName }&vm&${vm.status}"
										onclick="return false;" name="gray"> <img
										class="v-revert" src="img/revert_gray.png"></span>
									</a>
									</c:if>

								</div>

						  </c:if>
						 
                       -->
					    	


						<div style="clear: both;"></div>
                         <div class="row" >
							   <c:forEach items="${vm.errorList }" var="errorList">
							     <div class="col-md-8 col-xs-12 col-sm-8" style="padding-left: 13%;height:40px;line-height:40px;">
										<font class="error-font">${errorList }</font>
								 </div>
						      </c:forEach>
						   
						      <c:if test="${vm.taskError ne ''}">
						         <c:if test="${fn:length(vm.errorList)eq 0 }">
						             <div class="col-md-12 col-xs-12 col-sm-12 col-error-xs-1" style="">
										<font class="error-font">${vm.taskError }</font>
								    </div>
						         </c:if>
						         <c:if test="${fn:length(vm.errorList) > 0 }">
								  <div class="col-md-4 col-xs-12 col-sm-4 col-error-xs" style="">
										<font class="error-font">${vm.taskError }</font>
								</div>
								</c:if>
							
							 </c:if>
					  </div>
                    </div>
			</c:forEach>
			
	<br>
<hr>

</c:forEach>

		  <div class="row" >
                  <div class="col-sm-12 col-md-12 col-xs-12 small-body-copy" align="center">
                   <c:if test="${user.infoList!=null}">
	                <c:forEach items="${user.infoList}" var="info">
	                   <div >
	                   <c:if test="${info.infoWarning!=null && info.infoWarning eq true}">
	                     <font class="BG_Gray_66" style="color:red;font-weight:bold">${info.infoValue}.</font>
	                   </c:if>
	                   <c:if test="${info.infoWarning eq null ||info.infoWarning eq false }">
	                    <font class="BG_Gray_66" >${info.infoValue}.</font>
	                   </c:if>
	                   </div>
	                </c:forEach>
	                  <br>
                        <br>
                 </c:if>
                 </div>
                </div>
		</div>
	</div>
</div>



<script>
	$(".block-vapp,#VappNIC,#rdp-gray,#vmNIC,#vmCantRDP,#VmCC,#VAppFR,.dropdown-toggle,#dLabel,#vmNotRDP")
			.tooltip();
</script>


<div id="vmobject"></div>
<div id="rdpObject"></div>








