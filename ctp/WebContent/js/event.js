 var participantKey;
 var bandwidth;
 var lantecy_time;
 var lat_err;
 var flag=false;
 var connectionTestMessage;
 var originalLatencyTestResult;

 function LatencyResult(averageTime,lat_err,latencyThreshold,callback){
	averageTime = parseInt(averageTime);
	latencyTestComment = averageTime;
	var latencyAndConnectionTestDiv = $('#latencyAndConnectionTest');
	var latencyResultDiv=$('#latencyTest');
	var connResultDiv=$('#connectionTest');
	var latencySuccess="The Latency test was successful.";
	var latencyfailed="The Latency test has Failed!";
	var connSuccess="The connection test was successful.";
	var connfailed="The connection test has Failed!";
	var averageTimeContentSuccess="<span style='color:green'>"+averageTime+"</span>";
	var averageTimeContentFailed="<span style='color:#f43030'>"+averageTime+"</span>";
	var errResult = "Latency is "
			+ averageTimeContentFailed
			+ " milliseconds. This value is above the optimal performance range for connections to HP Event servers. This may be caused by a temporary increase in traffic on your network, therefore you should try again later.  If the test continues to fail, you should run it from another location.";
	 if (averageTime > latencyThreshold) {
         result = errResult;
         latencyResultDiv.find('.failedIco').show();
    	 latencyResultDiv.find('span').html(latencyfailed);
    	 latencyResultDiv.find('span').css('color','#f43030');
     } else {
    	 latencyTestResult = 'success';
         result = "Latency is  " + averageTimeContentSuccess + " milliseconds";
         latencyResultDiv.find('.successIco').show();
    	 latencyResultDiv.find('span').html(latencySuccess);
    	 latencyResultDiv.find('span').css('color','green');
     }
	 latencyResultDiv.find('#latencyInformDetail').html(result);
	 
	 if(connectionTestMessage == 'true'){
		 connectionTestMessage="";
		 conntestResult = 'success';
		 connResultDiv.find('.successIco').show();
		 connResultDiv.find('span').html(connSuccess);
		 connResultDiv.find('span').css('color','green');
	 }else{
		 connResultDiv.find('.failedIco').show();
		 connResultDiv.find('span').html(connfailed);
		 connResultDiv.find('span').css('color','#f43030');
		 conntestComment = connectionTestMessage;
	 }
	 connResultDiv.find('#connectionInformDetail').html(connectionTestMessage);
	 if(originalLatencyTestResult){
		 //result = result + "<p style='font-weight:700;padding-top:11px;'> Code: " + originalLatencyTestResult + "</p>" ;
		 latencyAndConnectionTestDiv.find('p').html("Code: " + originalLatencyTestResult);
	 }
     $("#test1").attr('disabled',true);    
//     $("#test2").attr('disabled',true);
     latencyAndConnectionTestDiv.show();
     callback(latencyAndConnectionTestDiv);
 }
    //validate participantUrl, send url
 
 function sendKey(participantUrl){
	  
	 if(participantUrl==null ||participantUrl==""){
		 $('#myModal').modal('show');

	 }else{
		 var content="The existing URL is <a>"+participantUrl+"<a/>, entering a new URL will change the existing participant URL.";
		 $("#VContent").html(content);
		 $('#myModal').modal('show');
	 }
	 
	
 }

    
 function isHPNetWorkClient(ip){
	 var pattern=/(^15\.|16\.){1}.+/;
	 return pattern.test(ip);
 }

 function doLatencyTest(latencyThreshold){
	 var latencyResult = "failed";	 
//	 if(isHPNetWorkClient(clientIP)){
		 $.ajax({
		        type: "GET",
		        url: "event/pingLatency.do",
		        dataType: "json",
		        timeout: 10000,
		        success: function (msg) {
		        	latencyResult = msg;
		        },
		        error: function (jqXHR, textStatus, errorThrown) {
		        	console.log(textStatus);
		        },
		        complete: function(msg) {
		        	if(latencyResult != "failed"){
						LatencyResult(latencyResult,null,latencyThreshold,function (result) {     
							bootbox.alert(result, function() {
								adaptPostion($('#tracepanel'));
					       	 	$('#tracepanel').fadeIn();
								$('#coverdiv').fadeTo(100, 0.5);
							});
						});
						$("#loading").hide();
						$("#test1").show();
		        	}else{
		        		pingFromClient(latencyThreshold);		        		
		        	}
		        }
		        
		 });
//	 }else{
// 		pingFromClient(latencyThreshold);
//	 }
	 
 }
 
 function pingFromClient(latencyThreshold){

	 BOOMR.init({
		//beacon_url: "http://ctp-web.itcs.hp.com/",
		 beacon_url: "http://ctp.itcs.hpe.com/",
		BW: {
//				base_url: "http://ctp-web.itcs.hp.com/ctp/img/latency/",
				// base_url: "http://ctp-web.itcs.hp.com/DO_NOT_REMOVE/latency/",
			base_url: "http://ctp.itcs.hpe.com/DO_NOT_REMOVE/latency",
			}
	 });

	 BOOMR.subscribe('before_beacon', function(o) {
			var html = "";
			if(o.bw) { 
				html += "Your bandwidth to this server is " + parseInt(o.bw/1024) + "kbps (&#x00b1;" + parseInt(o.bw_err*100/o.bw) + "%)<br>"; 
				bandwidth=parseInt(o.bw/1024) + "kbps (&#x00b1" ;
				}
			if(o.lat) { 
				html += "Your latency to this server is " + parseInt(o.lat) + "&#x00b1;" + o.lat_err + "ms<br>"; 
				lantecy_time=parseInt(o.lat);
			}
			if(lantecy_time==""||lantecy_time==undefined){
				lantecy_time=o.nt_res_st-o.nt_req_st;
			}
			if(flag==false){
				flag=true;
				lat_err=o.lat_err;
				originalLatencyTestResult = lantecy_time;
				lantecy_time = dealTheNonHpNetWorkLatency(lantecy_time);
				LatencyResult(lantecy_time,lat_err,latencyThreshold,function (result) {     
					bootbox.alert(result, function() {
						adaptPostion($('#tracepanel'));
			       	 	$('#tracepanel').fadeIn();
						$('#coverdiv').fadeTo(100, 0.5);
					});
				});
				$("#loading").hide();
				$("#test1").show();
			}
	 });
	 

	 if(lantecy_time!=undefined&&flag==true){
		 originalLatencyTestResult = lantecy_time;
		 lantecy_time = dealTheNonHpNetWorkLatency(lantecy_time);
		 LatencyResult(lantecy_time,lat_err,latencyThreshold,function (result) {      
             bootbox.alert(result, function() {
				adaptPostion($('#tracepanel'));
            	$('#tracepanel').fadeIn();
     			$('#coverdiv').fadeTo(100, 0.5);
			 });
          });
		 $("#loading").hide();
         $("#test1").show();
      }
 }
 
 function dealTheNonHpNetWorkLatency(latencyValue){
	 console.log("latencyDivide:"+latencyDivide);
	 console.log("latencySubtract"+latencySubtract);
	 var value=parseInt(latencyValue);
	 if(!$.isNumeric(value)){
		 return latencyValue;
	 }
	 var result; 
	 var divide = parseInt(latencyDivide);

	 /**
	  * In HPE network the @Chinna -  If the ping test will fails, it should work for both hpe and non-hpe client IP Address
	 if(isHPNetWorkClient(clientIP)){
		 divide = 0;
		 console.log("HPNETWORK:"+clientIP);
	 }
	 **/
	 var subtract = parseInt(latencySubtract);
	 if($.isNumeric(divide) && $.isNumeric(subtract)){
		 if(divide != 0){
			 result = value/divide;
		 }
		 if(result==undefined){
			 result = value; 
		 }
		 result = result + subtract;
	 }
	 if(result){
		 return result > 0 ? result : value;
	 }else{
		 return latencyValue;
	 }
 }
 
 function doLatencyAndConnectionTest(latencyThreshold,vcloudUrl,connectionThreshold,isDemo,connectionUrl){

	 // can not do the rdpTest when the latency and connection test is going on.
	 var rdbTestButton = $('#test3'); 
	 if(rdbTestButton){
		 rdbTestButton.attr('disabled',true);
	 }
	 
	 if(connectionUrl!=""){
		 connectionUrl="https://"+connectionUrl+":443";
	 }else{
		 connectionTestMessage="There is not a RDP Gateway server, please contact support.";
		 doLatencyTest(latencyThreshold);
		 return;
	 }
	 if(vcloudUrl!=""){
		 vcloudUrl = "https://"+vcloudUrl+"/api/versions";
//	   vcloudUrl = "https://"+vcloudUrl;
	 }else{
		 if(isDemo != 'true'){
			 connectionTestMessage="There is not a Event server for current user, please contact support.";
			 doLatencyTest(latencyThreshold);
			 return;
		 }
	 }

	 connectionTestMessage="Connection to RDP Gateway failed, please contact support.";
     $.ajax({
        type: "GET",
        url: connectionUrl,
        dataType: "jsonp",
        timeout: 10000,
        jsonp:"jsonpCallback",
        success: function (msg) {
        	if(isDemo == 'true'){
        		connectionTestMessage="true";
        		doLatencyTest(latencyThreshold);
        	}else{
        		testVcloud(vcloudUrl,connectionThreshold,"Connection to Event Server failed, please contact support.");
        	}
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.readyState == 4 && jqXHR.status == 200) {
            	if(isDemo == 'true'){
            		connectionTestMessage="true";
            		doLatencyTest(latencyThreshold);
            	}else{
            		testVcloud(vcloudUrl,connectionThreshold,"Connection to Event Server failed, please contact support.");
            	}
            	
	        } else{
	        	connectionTestMessage= connectionTestMessage + " Can not visit " + connectionUrl + " Cause is: " + textStatus;
	        	doLatencyTest(latencyThreshold);
	        }
        }
        
    });
    
 }

function  testVcloud(URL,connectionThreshold,message){
	connectionTestMessage = message;
	$.ajax({ 
        type: "GET",
        url: URL,
        dataType: "jsonp",
        timeout: 10000,
        jsonp:"jsonpCallback",
        success: function (msg) {
        	connectionTestMessage = "true";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            if (jqXHR.readyState == 4 && jqXHR.status == 200) {
            	connectionTestMessage = "true";
            }else {
	        	connectionTestMessage= connectionTestMessage + "Can not visit " + URL + " Cause is: " + textStatus;
            }
        },
        complete: function() {
	        doLatencyTest(latencyThreshold);
        }
        
    });
}

