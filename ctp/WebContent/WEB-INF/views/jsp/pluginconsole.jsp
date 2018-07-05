<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%    
String path = request.getContextPath();     
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";    
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--[if IE 11]>  
<meta http-equiv="X-UA-Compatible" content="IE=10" /> 
<![endif]-->  
<!--[if !(IE 11)]>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<![endif]-->

<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
<base href="<%=basePath%>">
<title></title>
<link rel="shortcut icon" href="/favicon.ico"/>
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="css/vmconsole.css"/>
<script type="text/javascript" src="js/lib/jquery.js"></script>
<script type="text/javascript" src="js/lib/bootstrap.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/console.js"></script>
<script src="js/lib/bootbox.js" type="text/javascript"></script>

<script type="text/javascript">
//initiation UI

$(function () {	
	 vmname= getCookie('vmName');
     vappname=getCookie('vappName');
     isStudentPage=getCookie("isStudentPage");
     //consoleMethod.getvappUrl();
     vappUrl=getCookie("vappUrl");
	//vmname = '${vm.vmName}';
    //vappname = '${vappName}';
    //ticketUrl = '${vm.ticket}';
    //alert(ticketUrl);
    //startUrl = '${vm.powerOnUrl}';
   // stopUrl = '${vm.powerOffUrl}';
   // revertUrl = '${vm.revertUrl}';
    //pauseUrl = '${vm.pauseUrl}';    
   // advanced = '${vm.advanced}';
    //vappUrl = '${vappUrl}';
    isHtml = false;

    vmname=localStorage.getItem("localVmName");
    vappname=localStorage.getItem("localVappName");
    isStudentPage=localStorage.getItem("localIsStudentPage");
    vappUrl=localStorage.getItem("localVappUrl");

    
		$(".vm-name").html(vmname);
		$(".v-start").attr("url", startUrl);
		$(".v-stop").attr("url", stopUrl);
		$(".v-revert").attr("url", revertUrl);
		$(".v-pause").attr("url", pauseUrl);
		buttonStyle.changeButtonStyle(true, startUrl, stopUrl, revertUrl,
				pauseUrl);
		document.title = vappname + ' - ' + vmname;
		if (advanced == 0) {
			$(".v-revert").hide();
		}
		consoleMethod.autoFlush = "handFlush";
		consoleMethod.refreshVmStatus();
		consoleMethod.initiateConsole();//get ticket and connect with Virtual Machine

		// consoleMethod.refreshVmStatus();
		//consoleMethod.initiateConsole();//get ticket and connect with Virtual Machine
		$(".action")
				.click(
						function() {
							var css = "";
							css = $(this).css("cursor");
							if (css != undefined && css == "pointer") {
								var act = $(this).attr("action");
								var url = $(this).attr("url");
								var isDisable = $(this).attr("class").indexOf(
										"fuzzy") > 0 ? false : true;
								if (act == "Revert" && !isDisable) {
									var top = $("#vmrc").css("top");
									$("#vmrc").css("top", "200px");
									bootbox
											.confirm(
													"You are about to revert to the original state, are you sure you want to do this?",
													function(result) {
														if (result) {
															consoleMethod
																	.action(
																			url,
																			act);
														}
														$("#vmrc").css("top",
																top);
													});
								} else {
									consoleMethod.action(url, act);
								}
							}
						});
	});

	// disconnect vmrc only if it is run. 
	$(window).unload(function() {
		try {
			status = vmrc.getConnectionState();
			if (status == 2) {
				vmrc.disconnect();
			}
		} catch (error) {
		}
		
	});
	

</script>
</head>
<body>
<div class="header">
    <div class="vm-info">                                    
            <div style="font-size:110%"><span class="vm-name"></span>
                            <span style="font-size:0.9em;vertical-align:middle;">
                            	<img src="img/ctrl-alt-del-16x16.png" alt="Leave" title="Press Ctrl + Alt + Insert to send a Ctrl + Alt + Del command to the virtual machine" hspace="30" vspace="5">
                                <img src="img/mouse.png" alt="Send" title="Press Ctrl + Alt to transfer mouse from the virtual machine to the local machine." >
                            </span>
            </div>
        </div>
        <div class="buttonArea">
                        <span class="busyArea" style="display: inline-block;margin-right: 10px;font-size:0.8em;"></span>
                        <span class="power-buttons" style="display: inline-block;">
                                        <img src=" img/play.png" style="width:15px; height:15px;" class="v-play fuzzy action"  url="" action="Start">
                                        <img src=" img/stop.png" style="width:15px; height:15px;" class="v-stop fuzzy action" url="" action="Stop"> 
                                        <img src=" img/pause.jpg" style="width:15px; height:15px;" class="v-pause fuzzy action" url="" action="Pause">
                                        <img src=" img/revert.png" style="width:15px; height:15px;" class="v-revert fuzzy action" url="" action="Revert">
                        </span>
        </div>
    </div>
</div>
<div id="vmobject" style="text-align:center;width: 100%; height: 100%;"></div>>
</body>
</html>
