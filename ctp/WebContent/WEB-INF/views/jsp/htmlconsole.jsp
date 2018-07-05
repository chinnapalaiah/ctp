<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%    
String path = request.getContextPath(); 
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";    
String data = request.getParameter("data");
%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent" />
<base href="<%=basePath%>">
<title></title>
<link rel="shortcut icon" href="/favicon.ico"/>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/htmlconsole.css"/>
<link rel="stylesheet" href="css/jquery-ui-wmks.1.8.16.css"/>
<link href="css/jquery-ui.1.10.3.css" rel="stylesheet">
<script src="js/lib/jquery.1.8.min.js"></script>
<script src="js/lib/jquery-ui.1.10.3.min.js"></script>
<script type="text/javascript" src="js/lib/bootstrap.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/console.js"></script>
<script src="js/html5console/wmks.js"></script>
<script src="js/html5console/core.js" type="text/javascript"></script>
<script src="js/html5console/event-manager.js"></script>
<script src="js/html5console/wmks-console.js"></script>
<script src="js/html5console/button-manager.js"></script>
<script src="js/html5console/button.js"></script>
<script src="js/html5console/debug.js"></script>
<script src="js/lib/bootbox.js" type="text/javascript"></script>

<!-- jQuery & jQuery UI + theme (required) -->


<!-- keyboard widget css & script (required) -->
<link href="css/keyboard.css" rel="stylesheet">
<script src="js/jquery.keyboard.min.js"></script>

<!-- keyboard extensions (optional) -->
<script src="js/jquery.mousewheel.js"></script>
<script src="js/jquery.keyboard.extension-typing.min.js"></script>
<script src="js/jquery.keyboard.extension-autocomplete.min.js"></script>
<script src="js/keyboardMapper.js"></script>
<script src="js/consolekeyboard.js"></script>
<script src="js/JudgeOS.js"></script>
<script type="text/javascript" src="js/util.js"></script>

<script type="text/javascript">
var vappname;
var vmname;
var ticketUrl;
var vmid;
var host;
var ticket;
var startUrl;
var stopUrl;
var revertUrl;
var pauseUrl;
var vappUrl;
var taskUrl;
var status;
var rootName=util.getRootPath;
var advanced;
var isConnecting=true;
var port;
var vmx;

$(function() {	
    //vmname = '${vm.vmName}';
    //vappname = '${vappName}';
    //ticketUrl = '${vm.ticket}';
    //startUrl = '${vm.powerOnUrl}';
   // stopUrl = '${vm.powerOffUrl}';
   // revertUrl = '${vm.revertUrl}';
   // pauseUrl ='${vm.pauseUrl}';
   // advanced = '${vm.advanced}';
    //vappUrl = '${vappUrl}';
    isHtml = true;
   vmname= getCookie('vmName');
   vappname=getCookie('vappName');
   isStudentPage=getCookie("isStudentPage");
   vappUrl=getCookie("vappUrl");
	//consoleMethod.getvappUrl();
	
 vmname=localStorage.getItem("localVmName"); 
 vappname=localStorage.getItem("localVappName");
 isStudentPage=localStorage.getItem("localIsStudentPage");
 vappUrl=localStorage.getItem("localVappUrl");

    $(".vm-name").html(vmname);
    $(".v-start").attr("url",startUrl);
    $(".v-stop").attr("url",stopUrl);
    $(".v-revert").attr("url",revertUrl);
    $(".v-pause").attr("url",pauseUrl);
	buttonStyle.changeButtonStyle(true,startUrl,stopUrl,revertUrl,pauseUrl);
	document.title= vappname +' - '+vmname;
	if(advanced==0){
    	$(".v-revert").hide();
    }
	
	consoleMethod.autoFlush="handFlush";
	consoleMethod.refreshVmStatus();
	var isReady = function() {
        var ready = true;
        vmware.log("TRACE", "init", "Window ready: {0}".format(ready));

        return ready;
    };
   
    var init = function() {
        vmware.log("TRACE", "init", "Initializing console");

        buildConsoleChrome(vappname,vmname,vmid);
        acquireTicket(vappUrl,vmname);
        
    };
    var giveUp = function() {
        vmware.log("ERROR", "init", "retries exhausted");

        $("body").text("Error reading data from CTP. Please close this window, verify that your session is still active, and try again.");
    }
    init.runWhen(isReady, 100, 10, giveUp);
    os=detectOS();
	if(os=="ios"||os=="android"){
		$("#console").addClass("ipad-screen");
	}
    
    $(".action").click(function(){
    	var css="";
    	css=$(this).css("cursor");
    /*	try{
    	css=this.css("cursor");
    	}catch(error){
    		css=undefined;
    	}
    	*/
    	if(css!=undefined &&css=="pointer" ){
	    	var act = $(this).attr("action");
	    	var url = $(this).attr("url");
	    	var isDisable = $(this).attr("class").indexOf("fuzzy")>0?false:true;
	    	if(act=="Revert"&&!isDisable){
	    		bootbox.confirm("You are about to revert to the original state, are you sure you want to do this?", function(result) {
	    			if(result){
	    			 	consoleMethod.action(url,act);
	    			}
	   		   });
	    	}else{
	    		consoleMethod.action(url,act);
	    	} 
    	}
    });
});		
</script>
<style type="text/css">
	.buttonArea img{
		margin-left:10px;
		margin-right:10px;
	}
</style>
</head>
<body>
<!-- <a href="#" class="hiddenInput">Hidden input</a> -->
<textarea id="hidden" style="display:none;"></textarea>
<div class="header" style="min-height: 80px;">
	<div style="vertical-algin:middle;">
		<div class="vm-info">			
			<div style="font-size:150%;"><span class="vm-name"></span></div>
			<!-- <div>
				<div style="display: inline-block;height:33px;line-height:33px;vertical-align:middle;"><span style="font-size:0.9em;vertical-align:middle;">Note: </span></div>
				<div style="display: inline-block;margin-left:20px;vertical-align:middle;">
					<div><span style="font-size:0.8em;color:#333;">Press Ctrl + Alt to transfer mouse and keyboard input from the virtual machine to the local machine.</span></div>
					<div><span style="font-size:0.8em;color:#333;">Press Ctrl + Alt + Insert to send a Ctrl + Alt + Del signal to the virtual machine.</span></div>
				</div>
			</div> -->
		</div>
		<div class="buttonArea">
			<span class="busyArea" style="display: inline-block;margin-right: 10px;font-size:0.8em;"></span>
			<span class="power-buttons" style="display: inline-block;">
				<img src=" img/play.png" class="v-play fuzzy action"  url="" action="Start" >
				<img src=" img/stop.png" class="v-stop fuzzy action" url="" action="Stop" >
				<img src=" img/pause.jpg" class="v-pause fuzzy action" url="" action="Pause" >
				<img src=" img/revert.png" class="v-revert fuzzy action" url="" action="Revert" >
			</span>
			<span>&nbsp;|&nbsp;</span>
			<span id="plugin-buttons"></span>
		</div>
	</div>
</div>
<div id="console" style="margin-right: auto; margin-left: auto;"></div>
</body>
</html>