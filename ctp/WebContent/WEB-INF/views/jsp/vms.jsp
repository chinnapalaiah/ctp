<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<script type="text/javascript">
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

		<div class="thumbnail" style="height: auto;min-height:610px; ">
			<div class='row'>
				<div class="col-md-12 col-xs-12">
					<h2 align="center" class="title">Training Management</h2>
					<hr>
				</div>
			</div>
		<div class="col-sm-12 col-md-12">
			<h3 style="padding-left: 2%; padding-bottom: 10px;" class="subTitle">${user.userName}</h3>
		</div>
		<c:if test="${user.os ne null &&user.os ne '' &&(user.os eq 'ios' || user.os eq 'android' ||user.os eq 'macos')}">
			<div class='row'>
			   <div class="small-body-copy col-xs-12 col-md-12" style="padding-left:10%;">
			    <label class="title" style="font-size:16px;"><strong id="rdpRemind">If you cannot open or download the RDP connection, please download and install
			     <a href="${user.rdpClientLink}" target="_blank" class=" rdpClient" ata-toggle="tooltip" data-placement="top" id="rdpClient"
										data-animation="true"   style="color:red;" 
										>Client</a>.</strong> </label>
	          </div>
	        </div>
	        </c:if>

			<input type="hidden" id="vappName" value="${vapp.name}" />
			<div class="row">
				<c:if test="${vapp.url eq '' || vapp.url eq null}">

					<div
						class="col-md-8  col-sm-8 col-xs-12 col-md-8-vapp col-xs-vappdiv-height"
						style="display: inline-block;">

						<div class="col-vapp">
							<span class="vapp-icon " id="vappNIV"
								data-toggle="tooltip" data-placement="top"
								title="There is a problem connecting to the Environment and VM"></span> <img
								class="block" src="img/warning.png">
						</div>
						<div class="col-xs-10 col-width col-xs-vappline-height"
							style="display: inline-block; height: 40px; padding-left: 10px; padding-right: 0px;">
							<label class="small-body-copy bold">Environment:&nbsp; </label>
							${vapp.name}
						</div>
					</div>

				</c:if>

				<c:if test="${vapp.url ne null && vapp.url ne ''}">


				<c:if test="${vapp.stopedVm > 0  }">
					
					  <c:if test="${fn:length(vapp.vmModelList) eq vapp.stopedVm}">
					      <div
							class="col-md-8  col-sm-8 col-xs-12 col-md-8-vapp col-xs-vappdiv-height"
							style="display: inline-block;">
							<div class="col-vapp">
								<span class="vapp-icon" id="vappFR" data-toggle="tooltip"
									data-placement="top" title="The Environment is not running"></span>
								<img src="img/warning.png" class="block-vapp ">

							</div>

							<div class="col-xs-10 col-width col-xs-vappline-height"
								style="display: inline-block; height: 40px; padding-left: 10px; padding-right: 0px;">
								<label class="small-body-copy bold">Environment:&nbsp; </label>
								${vapp.name}
							</div>

						</div>
					  </c:if>
					 <c:if test="${vapp.stopedVm lt fn:length(vapp.vmModelList)}">
						<div
							class="col-md-8  col-sm-8 col-xs-12 col-md-8-vapp col-xs-vappdiv-height"
							style="display: inline-block;">
							<div class="col-vapp">
								<span class="vapp-icon" id="vappFR" data-toggle="tooltip"
									data-placement="top" title="The Environment is partially running"></span>
								<img src="img/warning.png" class="block-vapp ">

							</div>

							<div class="col-xs-10 col-width col-xs-vappline-height"
								style="display: inline-block; height: 40px; padding-left:10px; padding-right: 0px;">
								<label class="small-body-copy bold">Environment:&nbsp; </label>
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

							<div class="col-xs-10 col-width col-xs-vappline-height"
								style="display: inline-block; height: 40px; padding-left:10px; padding-right: 0px;">
								<label class="small-body-copy bold">&nbsp; Environment:&nbsp; </label>${vapp.name}&nbsp;&nbsp;
							</div>

						</div>
					</c:if>

				</c:if>
				
				<div class="col-md-4 col-sm-4 col-xs-12 col-xs-6-vm style="height:40px;display:inline-block;">

					<div style="display: none" class="col-vapp-trbtn col-vm-btn"
						id="${vapp.name }_loading" name="vapp_hidden">
						<img class="v-loading" src="img/loading.gif"> <span
							name="vapp_font" class="busy"></span>
					</div>

					<c:if test="${vapp.taskStatus eq 'Progressing'}">
						<div id="Loading&${vapp.name}&vm" class="col-vapp-trbtn col-vm-btn">
							<img class="v-loading" src="img/loading.gif"></span> <span
								id="${vapp.name}_font" class="busy">Busy</span>

						</div>
					</c:if>

					<c:if test="${vapp.taskStatus ne 'Progressing'}">

						<div class="col-vapp-trbtn col-vm-btn" name="vapp_display"
							id="${vapp.name}">


							<c:if test="${vapp.url eq null || vapp.url eq ''}">

								<!--  <a href="javascript:void(0)" class="col-sm-button-1"
									style="cursor: not-allowed;" id="Start&${vapp.name}&vapp"
									onclick="return false;" title="Start" name="gray"> <img
									src=" img/play_gray.png" class="v-start ">
								</a>

								<a href="javascript:void(0)" class="col-sm-button-1"
									id="Stop&${vapp.name}&vapp" onclick="return false;"
									title="Stop" style="cursor: not-allowed;" name="gray"><img
									src=" img/stop_gray.png" class="v-stop "> </a>
								<a href="javascript:void(0)" class="col-sm-button-1"
										title="Pause" style="cursor: not-allowed;"
										id="Pause&${vapp.name }&vm"
										onclick="return false;" name="gray"><img
										class="v-pause" src="img/pause_gray.png"></a>
								<c:if test="${vapp.advanced ne null && vapp.advanced eq 1 }">
									<a href="javascript:void(0)" title="Revert"
										class="col-sm-button-1" style="cursor: not-allowed;"
										id="Revert&${vapp.name }&vapp " onclick="return false;"
										name="gray"> <img src=" img/revert_gray.png"
										class="v-revert">
									</a>

								</c:if>
                               -->
							</c:if>
					<%--  <c:if test="${user.type ne null && user.type eq 'instructor'}">  --%>

							<c:if test="${vapp.url ne null && vapp.url ne ''}">

								<c:if
									test="${vapp.powerOnUrl eq null || vapp.powerOnUrl eq '' }">
									<a href="javascript:void(0)" class="col-sm-button-1"
										style="cursor: not-allowed;" id="Start&${vapp.name}&vapp&${vapp.status}"
										onclick="return false;" title="Start" name="gray"> <img
										src=" img/play_gray.png" class="v-start "></a>

								</c:if>

								<c:if
									test="${vapp.powerOnUrl ne null && vapp.powerOnUrl ne ''  }">
									<a href="javascript:void(0)" class="col-sm-button-1"
										id="Start&${vapp.name}&vapp&${vapp.status}" title="Start"
										name="${vapp.powerOnUrl }"> <img src=" img/play.png"
										class="v-start fuzzy"></a>

								</c:if>

								<c:if
									test="${vapp.powerOffUrl eq '' || vapp.powerOffUrl eq null }">
									<a href="javascript:void(0)" class="col-sm-button-1"
										style="cursor: not-allowed;" id="Stop&${vapp.name}&vapp&${vapp.status}"
										onclick="return false;" title="Stop" name="gray"> <img
										src=" img/stop_gray.png" class="v-stop">
									</a>

								</c:if>

								<c:if
									test="${vapp.powerOffUrl ne null && vapp.powerOffUrl ne '' }">

									<a href="javascript:void(0)" class="col-sm-button-1"
										id="Stop&${vapp.name}&vapp&${vapp.status}" title="Stop"
										name="${vapp.powerOffUrl }"> <img src=" img/stop.png"
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

									<c:if test="${vapp.revertUrl eq null || vapp.revertUrl eq '' }">
										<a href="javascript:void(0)" title="Revert"
											class="col-sm-button-1" style="cursor: not-allowed;"
											id="Revert&${vapp.name }&vapp&${vapp.status}" onclick="return false;"
											name="gray"> <img class="v-revert"
											src="img/revert_gray.png">
										</a>
									</c:if>
									<c:if test="${vapp.revertUrl ne null && vapp.revertUrl ne '' }">
										<a href="javascript:void(0)" title="Revert"
											class="col-sm-button-1" name="${vapp.revertUrl}"
											id="Revert&${vapp.name }&vapp&${vapp.status}"> <img
											class="v-revert fuzzy" src="img/revert.png">
										</a>

									</c:if>
								</c:if>

								<c:if test="${vapp.advanced eq null || vapp.advanced eq 0}">

									<a href="javascript:void(0)" title="Revert"
										class="col-sm-button-1" style="visibility: hidden;"
										id="Revert&${vapp.name }&vapp&${vapp.status}" onclick="return false;"
										name="gray"> <img class="v-revert"
										src="img/revert_gray.png">
									</a>

								</c:if>


					<%--		</c:if>  --%>
						</c:if>
						</div>
					</c:if>

				</div>

               <div class="row" style="">

					  <c:forEach items="${vapp.vappErrors }" var="vappErrors">
					    <div class="col-md-8 col-xs-12 col-sm-8 " style="padding-left: 13%">
						<font class="error-font">${vappErrors }</font>
						</div>
						</c:forEach>
					
					   <c:if test="${vapp.taskError ne ''}">
						         <c:if test="${fn:length(vapp.vappErrors)eq 0 }">
						             <div class="col-md-12 col-xs-12 col-sm-12 col-error-xs-1" style="">
										<font class="error-font">${vapp.taskError }</font>
								    </div>
						         </c:if>
						         <c:if test="${fn:length(vapp.vappErrors) > 0 }">
								  <div class="col-md-4 col-xs-12 col-sm-4 col-error-xs" style="">
										<font class="error-font">${vapp.taskError }</font>
								 </div>
								</c:if>
					 </c:if>
					 
			</div>

			<c:forEach items="${vapp.vmModelList}" var="vm">

				<div class="row">

					<div class="col-md-5 col-xs-12 col-sm-5 col-xs12-height" style="padding-left: 13%;">
					      <c:if test="${vm.errorList eq null || fn:length(vm.errorList) eq 0}">
					                  <div class="col-vm">
											<span class="vm" style="width: 50px;"></span>
										</div>

										<div class="col-vm-name">${vm.vmName }</div>
							</c:if>
							 <c:if test="${vm.errorList ne null && fn:length(vm.errorList)>0}">
							      <div class="col-vm">
											<span class="vm" style="width: 50px;" data-toggle="tooltip"
												id="vmNotRDP" data-placement="top"
												title="${vm.errorList[0]}"></span> <img
												class="block" src="img/warning.png">
										</div>
										<div class="col-vm-name">${vm.vmName }</div>
							</c:if>
					</div>

					<div class=" col-md-3 col-xs-6 col-sm-3 col-xs5-padding"
						style="line-height: 40px;padding-right:0px;">

						<c:if test="${user.type ne null && user.type eq 'attendees'}">
							<c:if test="${vm.rdpDisplay ne null && vm.rdpDisplay eq true}">
								<c:if test="${vm.connect ne null }">
								
									<a
										class="col-sm-button-2" title="Launch RDP" name="gray" target=""
										id="LaunchRdp&${vm.vmName  }&vm&${user.userName}" style="z-index: 3;display: inline-block;padding-right: 20px;cursor:pointer "> <img
										class="v-rdp fuzzy" src="img/rdp.png">
									</a>

								</c:if>
								<c:if test="${vm.connect eq null }">
									<a href="" class="col-sm-button-2"
										id="LaunchRdp&${vm.vmName  }&vm" onclick="return false;"
										name="gray"
										style="display: inline-block; cursor: not-allowed;padding-right: 20px;"> <img
										class="v-rdp fuzzy-gray" id="rdp-gray" src="img/rdp.png"
										data-toggle="tooltip" data-placement="top"
										data-animation="true"
										title="Could not create RDP file "> <img
										class="block" src="img/warning.png"
										style="left: 11px; top: -29px;">
									</a>
								</c:if>
							</c:if>
							<c:if
								test="${vm.consoleDisplay ne null && vm.consoleDisplay eq true}">

								<a href="" class="vapp_console col-sm-button-2"  onclick="return false;"
									title="Launch Console" id="LaunchConsole&${vm.vmName  }&vm"
									 name="gray" style="z-index: 3;"> <img
									src="img/console.png" class="v-console fuzzy "
									vappid="${vapp.name }" vappurl="${vapp.url }"
									ticket="${vm.ticket  }" vmname="${vm.vmName  }"
									advanced="${vapp.advanced}" vmstatus="${vm.status}" studentPage="false"
									start="${vm.powerOnUrl}" stop="${vm.powerOffUrl}"
									revert="${vm.revertUrl}">
									</a>
							</c:if>

						</c:if>
						<c:if test="${user.type ne null && user.type eq 'instructor'}">
							<c:if test="${vm.isWindows ne null && vm.rdpDisplay ne null && vm.rdpDisplay eq true}">
								<c:if test="${vm.connect ne null }">
									<a  
										class="col-sm-button-2" title="Launch RDP" name="gray" target=""
										id="LaunchRdp&${vm.vmName  }&vm&${user.userName}" style="z-index: 3;cursor:pointer;padding-right: 20px;"> <img
										class="v-rdp fuzzy" src="img/rdp.png">
									</a>
								</c:if>
								<c:if test="${vm.connect eq null}">
									<a href="" class="col-sm-button-2"
										id="LaunchRdp&${vm.vmName  }&vm" onclick="return false;"
										name="gray"
										style="display: inline-block; cursor: not-allowed;padding-right: 20px;"> <img
										class="v-rdp fuzzy-gray" id="rdp-gray" src="img/rdp.png"
										data-toggle="tooltip" data-placement="top"
										data-animation="true"
										title="Could not create RDP file"> <img
										class="block" src="img/warning.png"
										style="left: 11px; top: -29px;">
									</a>

                             </c:if>
						 </c:if>
						<c:if test="${vm.vcloudVm ne null && vm.vcloudVm eq true}">
								<a href="" class="vapp_console col-sm-button-2" onclick="return false;"
									title="Launch Console" id="LaunchConsole&${vm.vmName  }&vm"
									 name="gray" style="z-index: 3;"> <img
									src="img/console.png" class="v-console fuzzy "
									vappid="${vapp.name }" vappurl="${vapp.url }" ticket="${vm.ticket  }"
									vmname="${vm.vmName  }" advanced="${vapp.advanced}" studentPage="false"
									vmstatus="${vm.status}" start="${vm.powerOnUrl}"
									stop="${vm.powerOffUrl}" revert="${vm.revertUrl}"></a>
							</c:if>
						</c:if>
					</div>

					<div class="col-md-4 col-xs-6 col-sm-4 col-vm-btn-loading"
						style="display: none;" id="${vm.vmName }_loading" name="vm_hidden">
						<img alt="" class="v-loading" src="img/loading.gif"> <span
							style="color: #4682B4;" id="${vm.vmName  }_font" class="busy"></span>
					</div>

					<c:if test="${vm.vcloudVm ne null && vm.vcloudVm eq true}">
						<c:if test="${vm.taskStatus eq 'Progressing'}">
							<div class="col-md-4 col-xs-6 col-sm-4 col-vm-btn-loading"
								id="Loading&${vm.vmName}&vm">
								<img class="v-loading " src="img/loading.gif"> <span
									class="busy">Busy</span>

							</div>
						</c:if>


						<c:if test="${vm.taskStatus ne 'Progressing'}">
							<div class=" col-md-4 col-xs-6 col-sm-4 col-xs-12-vm"
								style="line-height: 50px; height: 50px;padding-left:1.2%;" name="vm_display"
								id="${vm.vmName}">

								<c:if test="${vm.powerOnUrl ne null &&vm.powerOnUrl ne ''}">

                                    <c:if test="${vm.status eq '3'}">
										<a href="javascript:void(0)" class="col-sm-button-1"
											title="Resume" name="${vm.powerOnUrl}"
											id="Start&${vm.vmName  }&vm&${vm.status}"> <img class="v-start fuzzy"
											src="img/play.png">
										</a>
									</c:if>
									  <c:if test="${vm.status eq '8'}">
										<a href="javascript:void(0)" class="col-sm-button-1"
											title="Start" name="${vm.powerOnUrl}"
											id="Start&${vm.vmName  }&vm&${vm.status}"> <img class="v-start fuzzy"
											src="img/play.png">
										</a>
									</c:if>

								</c:if>
								<c:if test="${vm.powerOnUrl eq null ||vm.powerOnUrl eq '' }">

									<a href="javascript:void(0)" class="col-sm-button-1"
										title="Start" style="cursor: not-allowed;"
										id="Start&${vm.vmName  }&vm&${vm.status}" onclick="return false;"
										name="gray"> <img class="v-start "
										src="img/play_gray.png">
									</a>
								</c:if>

								<c:if test="${vm.powerOffUrl ne null && vm.powerOffUrl ne '' }">

									<a href="javascript:void(0)" class="col-sm-button-1"
										name="${vm.powerOffUrl}" id="Stop&${vm.vmName }&vm&${vm.status}"
										title="Stop"><img class="v-stop fuzzy" src="img/stop.png"></a>


								</c:if>
								<c:if test="${vm.powerOffUrl eq null ||vm.powerOffUrl eq '' }">

									<a href="javascript:void(0)" class="col-sm-button-1"
										title="Stop" style="cursor: not-allowed;"
										id="Stop&${vm.vmName }&vm&${vm.status}"
										onclick="return false;" name="gray"><img
										class="v-stop " src="img/stop_gray.png"></a>
								</c:if>

                                 <c:if test="${vm.pauseUrl ne null && vm.pauseUrl ne ''  }">
                                  	<a href="javascript:void(0)" class="col-sm-button-1"
										title="Pause" 
										id="Pause&${vm.vmName }&vm&${vm.status}"
										 name="${vm.pauseUrl}"><img
										class="v-pause fuzzy" src="img/pause.png"></a>
								</c:if>
								  <c:if test="${vm.pauseUrl eq null || vm.pauseUrl eq ''  }">
                                  	<a href="javascript:void(0)" class="col-sm-button-1"
										title="Pause"  style="cursor: not-allowed;"
										id="Pause&${vm.vmName }&vm&${vm.status}"
										onclick="return false;" name="gray"><img
										class="v-pause" src="img/pause_gray.png"></a>
								</c:if>
                                    
										
								<c:if test="${vapp.advanced ne null && vapp.advanced eq 1}">

									<c:if test="${vm.revertUrl ne null &&vm.revertUrl ne '' }">
										<a href="javascript:void(0)" title="Revert"
											class="col-sm-button-1" name="${vm.revertUrl}"
											id="Revert&${vm.vmName }&vm&${vm.status}"> <img class="v-revert fuzzy"
											src="img/revert.png">
										</a>

									</c:if>
									<c:if test="${vm.revertUrl eq null || vm.revertUrl eq '' }">
										<a href="javascript:void(0)" title="Revert"
											class="col-sm-button-1" style="cursor: not-allowed;"
											id="Revert&${vm.vmName }&vm&${vm.status}" onclick="return false;"
											name="gray"> <img class="v-revert "
											src="img/revert_gray.png">
										</a>
									</c:if>
								</c:if>

								<c:if test="${vapp.advanced eq null || vapp.advanced eq 0}">

									<a href="javascript:void(0)" title="Revert"
										class="col-sm-button-1" style="visibility: hidden;"
										id="Revert&${vm.vmName }&vm" onclick="return false;"
										name="gray"> <img class="v-revert "
										src="img/revert_gray.png">
									</a>

								</c:if>


							</div>
						</c:if>

					</c:if>

					<c:if test="${vm.vcloudVm eq null || vm.vcloudVm eq false }">
					  <!--  
						<div class=" col-md-4 col-xs-6 col-sm-4 col-xs-12-vm" 
							name="vm_display" style="line-height: 50px;padding-left:1.2%" id="${vm.vmName  }"
							status="notInVcloud">
							<a href="javascript:void(0)" class="col-sm-button-1"
								title="Start" style="cursor: not-allowed;"
								id="Start&${vm.vmName  }&vm" onclick="return false;" name="gray">
								<img class="v-start " src="img/play_gray.png">
							</a> <a href="javascript:void(0)" class="col-sm-button-1"
								title="Stop" style="cursor: not-allowed;"
								id="Stop&${information.rdpRealName }&vm" onclick="return false;"
								name="gray"><img class="v-stop "
								src="img/stop_gray.png"> </a>
                             <a href="javascript:void(0)" class="col-sm-button-1"
										title="Pause" style="cursor: not-allowed;"
										id="Pause&${vm.vmName }&vm"
										onclick="return false;" name="gray"><img
										class="v-pause " src="img/pause_gray.png"></a>
							<c:if test="${vapp.advanced ne null && vapp.advanced eq 1 }">
								<a href="javascript:void(0)" title="Revert"
									class="col-sm-button-1" style="cursor: not-allowed;"
									id="Revert&${vm.vmName }&vm" onclick="return false;"
									name="gray"> <img class="v-revert "
									src="img/revert_gray.png">
								</a>
							</c:if>

                         
						</div>
                         -->
					</c:if>

					<div style="clear: both;">
						
						    <c:forEach items="${vm.errorList }" var="errorList">
						     <div class="col-md-8 col-xs-12 col-sm-8 " style="padding-left: 13%;height:40px;line-height:40px;">
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
			 
			 <c:if test="${user.type ne null && user.type eq 'attendees'&& user.participantUrl ne null && user.participantUrl ne '' &&user.showVRoom ne null && user.showVRoom eq true}">
			
			 <div class="row" >
			    <div class="col-sm-12 col-md-12 col-xs-12 small-body-copy" align="center">
				  <label class="title" style="font-size:16px;"><strong id="rdpRemind">  Please open this 
				   <a class=" rdpClient"  href="${user.participantUrl }" target=_blank style="color:red;">Link</a> to join virtual room.
				   </label>
			      </div>
			 </div>
			 </c:if>
			  <br>

			<hr>
			
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
                 </c:if>
                 </div>
                </div>
		</div>

	</div>
</div>

<script>
	$('#vappNIV,#vmNIV,#vmNotRDP,#rdp-gray,#vmCC,#vappFR').tooltip();
</script>

<div id="vmobject"></div>
<div id="rdpObject"></div>
