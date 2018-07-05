<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type='text/css'>

#latencyAndConnectionTest .cleanFloat {
	clear: both;
}
#latencyAndConnectionTest {
}
#latencyAndConnectionTest img{
	height: 20px;
	width: 20px;
	display: none;
}
#latencyInformMessage,#connectionInformMessage{
	float: left;
	width: 49%;
    padding-right: 10px;
    word-break: break-all;
}

#latencyInformDetail,#connectionInformDetail{
	float: left;
	width: 49%;
	padding-right: 3px;
	font-family: initial;
}

#latencyInformDetail{
	margin-top: -12px;
}

#connectionTest {
	padding-top: 10px;
}

.formTitle {
	font-family: 'Open Sans', sans-serif;
	font-size: 14px;
	color: #004489;
	font-weight: 800;
	width: 100px;
	text-align: right;
	padding-right: 10px;
}

#tracetitle {
	font-family: 'Open Sans', sans-serif;
	font-size: 14px;
	color: #004489;
	font-weight: 800;
	text-align: right;
	text-align: left;
	padding: 16px;
}

#coverdiv {
	display: none;
	height: 100%;
	width: 100%;
	position: absolute;
	top: 0px;
	background-color: black;
	border: 1px solid red;
	z-index: 99;
}

#tracepanel {
	display: none;
	width: 500px;
	margin: auto;
	padding: 16px;
	background-color: white;
	z-index: 1009;
	position: absolute;
}

#tracepanel li {
	list-style-type: none;
	margin-bottom: 14px;
}

#formpanel input {
	width: 233px;
}

#formpanel input[name='rdptestResult'] {
	width: 38px;
}

#tracepanel span {
	display: inline-block;
}

#buttonpanel {
	text-align: center;
	padding-top: 20px;
	padding-bottom: 20px;
}

#buttonpanel span {
	display: inline-block;
	width: 100px;
}

.validateLabel {
	color: red;
	display: none;
	padding-left: 107px;
}

#innerRdptestbutton {
	display: inline-block;
	cursor: pointer;
}

#innerRdptestbutton:hover {
	text-decoration: none;
}
</style>

<%--<script type="text/javascript" src="js/javascriptSleep.js"></script>--%>
<link rel="shortcut icon" href="/favicon.ico" />
<title>MyEvents</title>
<link rel="stylesheet" href="css/vtms.css" />
<link rel="shortcut icon" href="/favicon.ico" />
<script type="text/javascript" src="js/event.js" />
<script type="text/javascript" src="js/javascriptPing.js"></script>
<script src="js/lib/boomerang.js" type="text/javascript"></script>
<script src="js/lib/zzz_last_plugin.js" type="text/javascript"></script>
<script src="js/lib/bw.js" type="text/javascript"></script>
<script src="js/lib/navtiming.js" type="text/javascript"></script>
<script type="text/javascript">
	var clientIP ="<%=(String) session.getAttribute("clientIP")%>";
	var latencyDivide = '${infoLatencyOffsetDivide}';
	var latencySubtract = '${infoLatencyOffsetSubtract}';
	var conntestResult = 'failed';  
	var conntestComment = '';
	var latencyTestResult = 'failed';
	var latencyTestComment = '';
   	var isDomeUser = 0;
    var vcloudUrl='${getVCloudUrl}';
    var connectionThreshold='${connectionThreshold}';
    var latencyThreshold='${latencyThreshold}';
    var connectionUrl='${connectionUrl}';
    var isDemo='${user.isDemo}';
    var eventName='${events[0].name}';
    var eventID='${events[0].id}';
    var eventNumStudents='${events[0].numStudents}';
    var tempBaseImage='${templates.tempName}';
    var os="";
    var jreUrl='${jreUrl}';
    var particiValue="";
    $(function () {

    	$(".sub-title.events").addClass("yellow");
        $("#launch").click(function () {
            window.location.href = "trainingmgm.do";
        });
    	 $("body").on('click',"#participant",function(){
    	     var participantUrl=$('#participantUrl').attr("value");
    	    sendKey(participantUrl);    	    
    	     
    	 });
    	 $("body").on('click',"#btn_ok",function(){
    		 particiValue= $("#participantValue").val();
    		 if(isValidURL(particiValue)&&particiValue!=""){
    			 var status = checkUrlValidation(particiValue);
    			 if(status!="errors"){
    			 $.ajax({
                     type: "GET",
                     url: "participant.do",
                     data: {
                   	  participantValue:particiValue
                     },
                     success: function (data) {
                  	   $('#participantUrl').val(particiValue);  
                  	 bootbox.alert("Participant URL send successfully!", function() {
					  });
                     },
                     error: function(jqXHR, textStatus, errorThrown ) {
                     	
                     	if(jqXHR.status==403)
                     	{
                     		 window.location.href = "login.do";
                     	}
                     }
                 });
    			 $('#myModal').modal('hide');
    		 }else{
    			 $("#validUrl").show();
    		 }}else{
    			 $("#validUrl").show();
    		 }
    		 
    	 });
    	 
    	
    	 function checkUrlValidation(particiValue){
    		 var status ;
    		 
    		 $.ajax({
                 type: "GET",
                 url: "participant.do",
                 data: {
               	  participantValue:particiValue
                 },
                 success: function (data) {
                	status = data;
                 },
                 error: function(jqXHR, textStatus, errorThrown ) {
                 	
                 	if(jqXHR.status==403)
                 	{
                 		 window.location.href = "login.do";
                 	}
                 },
                 async: false
             });
    		 return status;    		 
    	 }
    	 //After hidden, do something
    	 $('#myModal').on('hidden.bs.modal', function (e) {
    		 $("#participantValue").val("");
    		 $("#validUrl").hide();
    		});
    	 $(".sub-title.events").addClass("yellow");
         $("#test1").click(function () {
             $("#test1").hide();
             $("#loading").show();
             
             doLatencyAndConnectionTest(parseInt(latencyThreshold),vcloudUrl,connectionThreshold,isDemo,connectionUrl);
             
         });
         
         $("#test3").click(doRdptest);
    	
    });
    
    function doRdptest(){
        var url="trainingmgm/download.do?downType=rdpTest&vmName=RDP Test";
        if(browser.versions.ios) { 
           	 var param = 'target=_blank,top=0,left=0,right=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no';
	    	 window.open(url,"RDP Test",param);
//         	 window.location.href = "trainingmgm/download.do?vmName="+vmName+"&userName="+userName;
        }else{
       		 window.location.href = url;
        }
    }
    function validateForm(){
    	var reg = /[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?/;
		var emailip = $('#userEmail');
		var warnlabel = emailip.closest('span').next('label');
		if(!emailip.val()){
			warnlabel.show();
			warnlabel.html('* Email is required');
			return false;
		}else{
			if(reg.test(emailip.val())){
				warnlabel.hide();
				return true;
			}else{
				warnlabel.html('* Email is invalid');
				warnlabel.show();
				return false;
			}
		}
	}
    
    function adaptPostion(element){
    	
    	var pageWidth=window.innerWidth
		|| document.documentElement.clientWidth
		|| document.body.clientWidth;

		var pageHeight=window.innerHeight
		|| document.documentElement.clientHeight
		|| document.body.clientHeight;
		
    	var ele = $(element);
		var eleWidth = parseInt(ele.css('width'));
		var eleHeight = parseInt(ele.css('height'));
		var freeWidth = pageWidth - eleWidth;
		var freeHeight = pageHeight-eleHeight;

		
		var contentHeight = $(document).outerHeight();
		var contentWidth = $(document).outerWidth();
		var coverDiv = $('#coverdiv');
		coverDiv.css('height',contentHeight);
		coverDiv.css('width',contentWidth);
		
		if(freeWidth > 10){
			var leftValue = freeWidth/2;
			ele.css('left',leftValue);
		}
		if(freeHeight > 10){
			var topValue = freeHeight/2;
			if(topValue > 173){
				topValue = 173;
			}
			ele.css('top',topValue);
		}
    }
    
	function sendTrance(){
		var userEmail = $('#userEmail').val();
		var usereventID = '${events[0].id}';
		var rdpTestResult = 'not_run';
		var rdpresRadio = $('input:radio[name="rdptestResult"]:checked');
		var firstName = $('#userFirstName').val();
		var lastName = $('#userLastName').val();
		var userEventName = $('#userEventName').val();
		if(rdpresRadio && rdpresRadio.length != 0){
			rdpTestResult=rdpresRadio.val();
		}
		if(isDomeUser == 1){
			usereventID = null;
			if($('#userEventId').val != ''){
				usereventID = $('#userEventId').val();
			}
		}
	   	var theuserinfo={
	   		webstats_fname: firstName,
	   		webstats_lname: lastName,
	   		webstats_username: '${user.userName}',
	   		webstats_email: userEmail,
	   		webstats_is_demouser: isDomeUser,
	   		webstats_event: userEventName,
	   		webstats_event_id: usereventID,
	   		webstats_conn_test_result: conntestResult,
	   		webstats_lat_test_result: latencyTestResult,
	   		webstats_rdp_test_result: rdpTestResult,
	   		latTestComment: latencyTestComment,
	   		connTestComment: conntestComment
	   	};
		if(validateForm()){
			$.ajax({
		         type: "POST",
		         url: "trace/sendEmail.do",
				 contentType: 'application/json',
				 data: JSON.stringify(theuserinfo),
		         success: function (data,textStatus,jqXHR) {
		        	 console.log(data);
		         },
		         error: function (jqXHR, textStatus, errorThrown) {
		        	 console.log(textStatus);
		         },
		         complete: function(){
					$('#tracepanel,#coverdiv').fadeOut();
					location.reload();
		         }
		     });
		}
	}
		
    $(document).ready(function(){
    	
    	/* $('#userEmail').on('keyup',function(event){	
    		if(event.keyCode == 8){
	    		$(this).val('');
	    		$(this).css('color','#555555');
    		}
    	}); */
    	
    	if(isDomeUser == 0){
    		$('.canBeDisabled').attr('disabled','disabled');
    	}
    	$(window).on('resize',function(){
    		adaptPostion($('#tracepanel'));
		})

		$('#cancelSend').on('click',function(){
			$('#tracepanel,#coverdiv').fadeOut();
			location.reload();
		});
		$('#innerRdptestbutton').on('click',function(event){
			doRdptest();
			event.stopPropagation();
		});
    	$('#tracepanel').on('click keyup',function(event){
    		if(event.keyCode ==13){
    			sendTrance();
    		}else{
    			validateForm();
    		}
		});
 		$('#sendTrance').on('click',sendTrance);
 		
 		$('#sendEmailTest').on('click',function(){
 			
 			var timesTest = $('#timesTest');
 			var times = timesTest.val();
 			
 			var userEmail = $('#userEmail').val();
 			var usereventID = '${events[0].id}';
 			var rdpTestResult = 'not_run';
 			var rdpresRadio = $('input:radio[name="rdptestResult"]:checked');
 			var firstName = $('#userFirstName').val();
 			var lastName = $('#userLastName').val();
 			var userEventName = $('#userEventName').val();
 			if(rdpresRadio && rdpresRadio.length != 0){
 				rdpTestResult=rdpresRadio.val();
 			}
 			if(isDomeUser == 1){
 				usereventID = null;
 				if($('#userEventId').val != ''){
 					usereventID = $('#userEventId').val();
 				}
 			}
 		   	var theuserinfo={
 		   		webstats_fname: firstName,
 		   		webstats_lname: lastName,
 		   		webstats_username: '${user.userName}',
 		   		webstats_email: userEmail,
 		   		webstats_is_demouser: isDomeUser,
 		   		webstats_event: userEventName,
 		   		webstats_event_id: usereventID,
 		   		webstats_conn_test_result: conntestResult,
 		   		webstats_lat_test_result: latencyTestResult,
 		   		webstats_rdp_test_result: rdpTestResult,
 		   		latTestComment: latencyTestComment,
 		   		connTestComment: conntestComment
 		   	};
 			
 			for(var i=0;i<times;i++){

 	 			//if(validateForm()){
 	 				$.ajax({
 	 			         type: "POST",
 	 			         url: "trace/sendEmail.do",
 	 					 contentType: 'application/json',
 	 					 data: JSON.stringify(theuserinfo),
 	 			         success: function (data,textStatus,jqXHR) {
 	 			        	 console.log(data);
 	 			         },
 	 			         error: function (jqXHR, textStatus, errorThrown) {
 	 			        	 console.log(textStatus);
 	 			         },
 	 			         complete: function(){
 	 						$('#tracepanel,#coverdiv').fadeOut();
 	 						location.reload();
 	 			         }
 	 			     });
 	 			//}
 				
 				
 			}
 		});
	});
    </script>

</head>


<body class="app signed-out  sessions new">

<!-- <div>
<input type='button' value='sendEmailTest' id='sendEmailTest' />
<input type='text' value='1' id='timesTest'/>
</div> -->

	<div class="container col-xs-container-padding" id="page">
		<div class="row" id="event">
			<div class="col-md-9 col-xs-12 col-md-offset-1 col-xs-event-padding">
				<div class="thumbnail">

					<div class="col-sm-12 col-md-12">
						<h1 align="center" class="title">My Event</h1>
					</div>


					<hr>
					<c:choose>
						<c:when
							test="${user.eventId eq 0 && user.isDemo ne null && user.isDemo eq true }">
							<script>
		            			isDomeUser = 1;
		            		</script>
							<div class="col-sm-12 col-md-12">
								<p class="small-body-copy"
									style="font-size: 14px; padding-left: 7%; padding-right: 4%; line-height: 200%">Welcome
									 to the Cloud Training Platform connectivity Test. Below are 
									 two test buttons to check RDP connections through your firewall, 
									 Internet latency and to validate connectivity to the MicroFocus event servers. 
									 This information is helpful in determining how your experience will
									 be when accessing your event resources from your
									 current location.  When running the “RDP test”, you will use the 
									 same password that was used to log into this website. A successful
									 test occurs after you click the test button, open the file, then 
									 enter the password and press “OK”. A connection launches, answer 
									 “yes” to any question about “…connect anyway?”, a login screen and 
									 a popup Logon Message confirms a successful test result. You do 
									 not need to log onto the computer desktop.	</p>
								<p class="small-body-copy"
									style="font-size: 14px; padding-left: 7%; padding-right: 4%; line-height: 200%">If 
									any test fails, we recommend that you try back later and test 
									again. If a test continues to fail, you should contact your 
									local administrator to discuss firewalls that may be  blocking 
									port 443 outbound communication and any options you may have. 
									Otherwise, you should run the tests from another location for 
									improved results.</p>
							</div>
						</c:when>

						<c:otherwise>
							<div class="col-sm-12 col-md-12">
								<h3 style="padding-left: 2%;" class="subTitle">${user.userName}</h3>
							</div>

							<input type="hidden" id="participantUrl"
								value="${user.participantUrl}" />
							<c:if test="${fn:length(events) > 0}">
								<c:forEach items="${events}" var="event">
									
									<div class="row">
										<div class="col-xs-6 col-xs-6-l">
											<label class="small-body-copy">ID</label>
										</div>

										<div class="col-xs-6 col-xs-6-r small-body-copy">${event.id}</div>
									 </div>
									<div class="row">
										<div class="col-xs-6 col-xs-6-l">
											<label class="small-body-copy">Name</label>

										</div>

										<div class="col-xs-6 col-xs-6-r small-body-copy">${event.name}</div>
									</div>
									<div class="row">
										<c:set var = "stimeRW" value = "${event.startTime}"/>
										<c:set var = "stime" value = "${fn:replace(stimeRW, ':00.0000000', '')}" />
										<c:set var = "etimeRW" value = "${event.endTime}"/>
										<c:set var = "etime" value = "${fn:replace(etimeRW, ':00.0000000', '')}" />
										<div class="col-xs-6 col-xs-6-l">
											<label class="small-body-copy">Event Starts</label>
										</div>
										<div class="col-xs-6 col-xs-6-r small-body-copy">${event.startDate} &nbsp [ ${stime} ]</div>
									</div>
									<div class="row">
										<div class="col-xs-6 col-xs-6-l">
											<label class="small-body-copy">Event Ends</label>
										</div>
										<div class="col-xs-6 col-xs-6-r small-body-copy">${event.endDate} &nbsp [ ${etime} ]</div>
									</div>
									<div class="row">
										<div class="col-xs-6 col-xs-6-l">
											<label class="small-body-copy">Time Zone</label>
										</div>
										<div class="col-xs-6 col-xs-6-r small-body-copy">${event.eventTimezone} </div>
									</div>									
									<div class="row">
										<div class="col-xs-6 col-xs-6-l">
											<label class="small-body-copy">Location</label>
										</div>
										<c:set var = "cell" value = "${event.cell}"/>
										<c:set var = "cellID" value = "${fn:replace(cell, 'ELL', '')}" />
										<div class="col-xs-6 col-xs-6-r small-body-copy"> ${cellID}
										</div>
									</div>								
									<div class="row">
										<div class="col-xs-6 col-xs-6-l">
											<label class="small-body-copy">Advanced Event</label>
										</div>
										<c:if test="${event.advanced eq 'true'}">
											<div class="col-xs-6 col-xs-6-r small-body-copy">Yes</div>
										</c:if>
										<c:if test="${event.advanced ne 'true'}">
											<div class="col-xs-6 col-xs-6-r small-body-copy">No</div>
										</c:if>
									</div>								
									<c:if test="${user.type eq 'instructor'}">
										<div class="row">
											<div class="col-xs-6 col-xs-6-l">
												<label class="small-body-copy">Number of Students</label>
											</div>
											<div class="col-xs-6 col-xs-6-r small-body-copy">${event.numStudents}
											</div>
										</div>
										<div class="row">
											<div class="col-xs-6 col-xs-6-l">
												<label class="small-body-copy">Event Template</label>
											</div>
											<div class="col-xs-6 col-xs-6-r small-body-copy">${event.vendorPartId}
											</div>
										</div>
										<div class="row">
											<div class="col-xs-6 col-xs-6-l">
												<label class="small-body-copy">Event Status</label>
											</div>
										<c:if test="${event.eventStatus eq 'activated'}">
											<div class="col-xs-6 col-xs-6-r small-body-copy">
											<img alt="" src="img/Tick.png" style="width: 18px; height: 18px; vertical-align: text-bottom;"> Running
											</div>
										</c:if>
										<c:if test="${event.eventStatus eq 'deactivated'}">
											<div class="col-xs-6 col-xs-6-r small-body-copy">
											<img alt="" src="img/error.jpg" style="width: 18px; height: 18px; vertical-align: text-bottom;"> Ended
											</div>
										</c:if>
										<c:if test="${event.eventStatus eq 'exporting'}">
											<div class="col-xs-6 col-xs-6-r small-body-copy">
											<img alt="" src="img/exporting.png" style="width: 18px; height: 18px; vertical-align: text-bottom;"> Preparing Template...
											</div>
										</c:if>										
										<c:if test="${event.eventStatus eq 'activating'}">
											<div class="col-xs-6 col-xs-6-r small-body-copy">
											<img alt="" src="img/creating.png" style="width: 18px; height: 18px; vertical-align: text-bottom;"> Creating...
											</div>
										</c:if>
										<c:if test="${event.eventStatus eq 'scheduled'}">
											<div class="col-xs-6 col-xs-6-r small-body-copy">
											<img alt="" src="img/revert_gray.png" style="width: 18px; height: 18px; vertical-align: text-bottom;"> Scheduled
											</div>
										</c:if>
										<c:if test="${event.eventStatus eq 'failed'}">
											<div class="col-xs-6 col-xs-6-r small-body-copy">
											<img alt="" src="img/Cross.png" style="width: 18px; height: 18px; vertical-align: text-bottom;"> Not Ready
											</div>
										</c:if>																										
										</div>
									</c:if>
									<div class="row">
										<div class="col-xs-6 col-xs-6-l">
											<label class="small-body-copy">&nbsp;</label>
										</div>

										<div class="col-xs-6 col-xs-6-r small-body-copy">&nbsp;</div>
									 </div>
									<div class="row">
										<c:if
											test="${event.showVRoom eq null || event.showVRoom eq false || user.type eq 'attendees'}">
											<div class="col-xs-12 col-xs-12-p">

							<!-- <input class="btn btn-primary signup-button button1_sm" data-disable-with="launch" name="commit" id="launch"type="button" value="Launch"/> -->
												<input class="button1_sm" data-disable-with="launch"
													name="commit" id="launch" type="button" value="Launch" />

											</div>
										</c:if>
										<c:if
											test="${event.showVRoom eq true && user.type eq 'instructor'}">
											<div class="col-xs-6 " style="padding-left: 25%">

												<input class="button1_sm " data-disable-with="launch"
													name="commit" id="launch" type="button" value="Launch" />
											</div>
											<div class="col-xs-6" style="padding-left: 10%">
												<input class="button1_sm" data-disable-with="launch"
													name="commit" id="participant" type="button"
													value="Send Key" />

											</div>

										</c:if>



									</div>

								</c:forEach>
							</c:if>

						</c:otherwise>
					</c:choose>
					<hr>
					<div class='row'>


						<%--<div class="col-sm-12 col-md-12 col-xs-12">--%>
						<%--<h4 style="padding-left: 12%;">--%>
						<%--<font color="#4682B4">Tool</font>--%>
						<%--</h4>--%>
						<%--</div>--%>


						<div class="col-sm-12 col-md-12 col-xs-12">
							<h3 style="padding-left: 7%;" class="subTitle">Tools</h3>

						</div>


						<div class='row'>
							<div class="col-sm-8 col-md-8 col-xs-8 small-body-copy"
                                                                style="padding-left: 10%;">
							<img style="height: 30px; padding-right: 10px; float: left; display: inline-block; min-width: 38px;" src="img/testTime.png">
								<label class="small-body-copy bold" style="display: inline-block;" >Latency and Connection test:</label>&nbsp;Test Internet connection from your PC to the event servers.
							</div>
							<div class="col-sm-3 col-md-3 col-xs-3">
								<div class=" col-sm-3-1">

									<input class="button2_sm" data-disable-with="Test..."
										name="commit" id="test1" type="button" value="Test" style="float: left;"/>
								</div>
								<div class=" col-sm-button-1" style="display: none; padding-left: 20px;" id="loading"
									name="vapp_hidden">
									<img alt="" src="img/loading.gif"
										style="width: 40px; height: 40px;"> <span
										style="color: #4682B4;" id="font" name="vapp_font"></span>
								</div>
							</div>

						</div>
						<!-- <div class='row'>
		                    <div class="col-sm-8 col-md-8 col-xs-8 small-body-copy" style="padding-left: 12%;padding-bottom:3px;">
		                        <img src="img/Connection.png" style="width:38px;height:34px;">
		                        <label class="small-body-copy bold"> Connection test: </label>Test connection
		                        is available between the remote server and your machine
		
		                    </div>
		                    <div class="col-sm-3 col-md-3 col-xs-3">
		                        <div class=" col-sm-3-1">
		                            <input class="btn btn-primary signup-button"
		                                   data-disable-with="Test..." name="commit" id="test2"
		                                   type="button" value="Test"/>
		                            <input class="button2_sm"
		                                   data-disable-with="Test..." name="commit" id="test2"
		                                   type="button" value="Test"/>
		                        </div>
		                        <div class=" col-sm-button-1 small-body-copy" style="display: none" id="loading2"
		                             name="vapp_hidden">
		                            <img alt="" src="img/loading.gif" style="width: 40px; height: 40px;">
		                            <span style="color:#4682B4;" id="font2" name="vapp_font"></span>
		                        </div>
		                        
		                    </div>
	                    </div> -->


						<c:if
							test="${user.eventId eq 0 && user.isDemo ne null && user.isDemo eq true && rdpTest.infoShow eq true}">
							<div class='row' style = "margin-top: 20px;">
								<div class="col-sm-8 col-md-8 col-xs-8 small-body-copy"
									style="padding-left: 10%; padding-bottom: 20px;">
									<img style="height: 36px; padding-right: 10px; float: left; display: inline-block; width: 48px;" src="img/testRdp.png">
									<label class="small-body-copy bold">RDP test:</label>&nbsp; Test your
									connection to the event servers
								</div>
								<div class="col-sm-3 col-md-3 col-xs-3">
									<div class=" col-sm-3-1">

										<input class="button2_sm" data-disable-with="Test..."
											name="commit" id="test3" type="button" value="Test" style="float: left;" />
									</div>
									<div style="display: none"
										id="loading" name="vapp_hidden">
										<img alt="" src="img/loading.gif"
											style="width: 40px; height: 40px;"> <span
											style="color: #4682B4;" id="font" name="vapp_font"></span>
									</div>
								</div>
							</div>
						</c:if>

					</div>
					<hr>
					<div class="row">
						<div class="col-sm-12 col-md-12 col-xs-12 small-body-copy"
							align="center">
							<c:if test="${user.infoList!=null}">
								<c:forEach items="${user.infoList}" var="info">
									<div>
										<c:if
											test="${info.infoWarning!=null && info.infoWarning eq true}">
											<font class="BG_Gray_66"
												style="color: red; font-weight: bold">${info.infoValue}.</font>
										</c:if>
										<c:if
											test="${info.infoWarning eq null ||info.infoWarning eq false }">
											<font class="BG_Gray_66">${info.infoValue}.</font>
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
	</div>


	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<!-- <h3 class="modal-title title" id="myModalLabel">Virtual Room</h3> -->
					<h3 class="modal-title title" id="myModalLabel">MyRoom Meeting</h3>
				</div>
				<div class="modal-body">
					<!-- <span id="VContent"> Participant URL for the event is?</span><br> -->
					<span id="VContent"> Please provide the Participant URL </span><br>
					<br> <input type="text" id="participantValue"
						class="form-control"><br> <label id='validUrl'
						style='display: none; color: red;'>Invalid URL</label>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
					<button type="button" class="btn btn-primary" id="btn_ok">OK</button>
				</div>
			</div>
		</div>
	</div>

	<div id="tracepanel" class='thumbnail'>
		<div id='tracetitle'>Please send us the following information</div>
		<div id='formpanel'>
			<ul>
				<li><span  class="formTitle">First Name:</span> <span><input id= 'userFirstName'
						value='${user.firstName}' class='focus form-control canBeDisabled' style="display: inline-block;"
						type="text" /></span></li>
				<li><span class="formTitle">Last Name:</span> <span><input id= 'userLastName'
						value='${user.lastName}' class='focus form-control canBeDisabled' style="display: inline-block;"
						type="text" /></span></li>
				<li><span class="formTitle">Your Email:</span> <span><input
						id='userEmail' class='focus form-control' style="display: inline-block;" type="text" /></span> <label
					class="validateLabel"></label></li>
				<li><span class="formTitle">Event Name:</span> <span><input id='userEventName'
						value='${events[0].name}' class='focus form-control canBeDisabled' style="display: inline-block;"
						type="text" /></span></li>
				<li><span class="formTitle">Event ID:</span> <span><input
						id='userEventId' disabled value='${events[0].id}'
						class='focus form-control canBeDisabled' style="display: inline-block;" type="text" /></span></li>
				<c:if
					test="${user.eventId eq 0 && user.isDemo ne null && user.isDemo eq true && rdpTest.infoShow eq true}">
					<li style='margin-bottom: 4px;padding-right: 2px;'>
					 	<label class="validateLabel" style="display: inline-block; color: red; font-family: 'Open Sans', sans-serif; font-size: 14px;">
							* Please run RDP test to verify RDP is available <a id="innerRdptestbutton">Test now</a>
						</label>
					</li>
					<li><span class="formTitle">RDP test:</span> <label><input
							name="rdptestResult" value="success" type="radio">Success</label>
						<label><input name="rdptestResult" value="failed"
							type="radio">Failure</label>
					</li>
				</c:if>
			</ul>
		</div>
		<div id='buttonpanel'>
			<span><input class="button1_sm" id="sendTrance" type="button"
				value="Send"></span> <span><input class="button1_sm"
				id="cancelSend" type="button" value="Cancel"></span>
		</div>
	</div>
	<div id='coverdiv'></div>
	<div id='latencyAndConnectionTest'>
		<div id='latencyTest'>
			<div id="latencyInformMessage">
				<img class='successIco' src='img/Tick.png' />
				<img class='failedIco' src='img/Cross.png' />
				<span>
				
				</span>
			</div>
			<div id="latencyInformDetail"></div>
			<div class='cleanFloat'></div>
		</div>
		<div id='connectionTest'>
			<div id="connectionInformMessage">
				<img class='successIco' src='img/Tick.png' />
				<img class='failedIco' src='img/Cross.png' />
				<span></span>
			</div>
			<div id="connectionInformDetail"></div>
			<div class='cleanFloat'></div>
		</div>
	    <p style='padding-top:11px;'></p>
	</div>
</body>
</html>
