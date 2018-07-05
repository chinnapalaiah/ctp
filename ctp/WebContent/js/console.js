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
var isHtml=false;
var autoFlush;
var xhr;
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) != -1) return c.substring(name.length,c.length);
    }
    return "";
}
var buttonStyle = {
		toGray:function(cls,url){
			var operator = cls!=null?cls.replace("v-",""):"";
			if(url==""||url==undefined){
				$("."+cls).removeClass("fuzzy");
				$("."+cls).attr("src","img/"+operator+ "_gray."+(operator=="pause"?"jpg":"png"));								
				$("."+cls).css("cursor","not-allowed");
			}else{
				$("."+cls).addClass("fuzzy");
				$("."+cls).attr("src","img/"+operator+"."+(operator=="pause"?"jpg":"png"));
				$("."+cls).css("cursor","pointer");
			}
		},
		changeButtonStyle:function(isConnecting,startUrl,stopUrl,revertUrl,pauseUrl){
			
			buttonStyle.toGray("v-play",startUrl);
			buttonStyle.toGray("v-stop",stopUrl);
			buttonStyle.toGray("v-revert",revertUrl);
			buttonStyle.toGray("v-pause",pauseUrl);
			if(isConnecting==false){
				
    			if (((startUrl==""||startUrl==undefined)&&(stopUrl!=""&&stopUrl!=undefined))) {
    				try {
    					if(!isHtml){
    						status = vmrc.getConnectionState();
    					}
    		            if (status == 2) {
    		            	
    		            	$(".busyArea").html("connected");
    		            }else{
    		            	$(".busyArea").html("disconnected");

    		            	if(isHtml){
    		            		acquireTicket(vappUrl,vmname);
    		            	}else{
    		            		consoleMethod.startVmrc();
    		            	}   		            	
    		            }
    		        } catch (error) {}
    		    	
    		        
    		    } else {
    		    	$(".busyArea").html("disconnected");

    		    }
			}
			
		}
    				
    };

	var consoleMethod={
			autoFlush:"",
    		initiateVmrc:function(vmobject,busyArea){//initiate vmrc and get vmrc ready
    			var object = null;
    			if(navigator.userAgent.indexOf("MSIE")>0||browser.versions.ie11) {  
    				object = "<object id='vmrc' classid='CLSID:4AEA1010-0A0C-405E-9B74-767FC8A998CB' style='width: 100%; height: 100%;'></object>";
    			}else{
    				object = "<object id='vmrc' type='application/x-vmware-remote-console-2012'  style='width: 100%; height: 100%;'></object>";
    			}
    			object = object+ "<object width='1' height='1' id='instance0' type='application/x-vmware-client-support-5-5-0' style='width: 1px; height: 1px;'></object>";
    			$("#"+vmobject).html(object);
    			
    			if (vmrc != null) {
    				vmrc.isReadyToStart();
    				if(navigator.userAgent.indexOf("MSIE")>0||browser.versions.ie11) {
    					vmrc.startup(vmrc.VMRC_Mode("VMRC_MKS"), vmrc.VMRC_MessageMode("VMRC_EVENT_MESSAGES"), 'usebrowserproxy=true;tunnelmks=true');
    				}else{
    					vmrc.startup(vmrc.VMRC_Mode.VMRC_MKS, vmrc.VMRC_MessageMode.VMRC_EVENT_MESSAGES, 'usebrowserproxy=true;tunnelmks=true');
    				}
    				$("."+busyArea).html("connecting...");	
    				
    		    }
    			consoleMethod.attachConnectionStateChangeHandlers();
    		},
    		initiateConsole:function(){//get ticket and connect with Virtual Machine
    			consoleMethod.initiateVmrc("vmobject","busyArea");
    			startUrl=$(".v-play").attr("url");
    			stopUrl=$(".v-stop").attr("url");
    			if (!((startUrl==""||startUrl==undefined)&&(stopUrl!=""&&stopUrl!=undefined))) {
    				$(".busyArea").html("disconnected");
    				isConnecting=false;
    				consoleMethod.autoFlush="autoFlush";
    				setTimeout("consoleMethod.refreshVmStatus()", 10000);   				
    				return false;
    			}
    			$.ajax({
    				type : "GET",
    				url : "trainingmgm/console.do",
    				data : {
    					url : ticketUrl
    				},
    				error :  function(msg){
    					isConnecting=false;
    					$(".busyArea").html("disconnected");    					
    				},
    				cache:false,
    				success : function(msg) {
    					
    					vmid = msg.vmid;
    					host = msg.host;
    					ticket = msg.ticket;
    							
    					try {
    			            status = vmrc.getConnectionState();
    			            
    			            if (status == 2) {	                
    			                vmrc.disconnect();
    			            }
    			        } catch (error) {}
    			        
    			        if(vmid!=null&&host!=null&&ticket!=null&&vmid!=""&&host!=""&&ticket!=""){	        		
    					    vmrc.connect(host, "", true, ticket, "", "", vmid, "", "");
    					    isConnecting=true;
    			        }
    			        try {
    			            status = vmrc.getConnectionState();

    			        } catch (error) {}			
    				}
    			});
    		},
    		startVmrc:function(){
    			startUrl=$(".v-play").attr("url");
    			stopUrl=$(".v-stop").attr("url");
    			if (!((startUrl==""||startUrl==undefined)&&(stopUrl!=""&&stopUrl!=undefined))) {
    				$(".busyArea").html("disconnected");	
    				isConnecting=false;
    				consoleMethod.autoFlush="autoFlush";
    				setTimeout("consoleMethod.refreshVmStatus()", 10000);
    				return false;
    			}
    			try {
    		        status = vmrc.getConnectionState();
    		        
    		        if (status == 2) {	                
    		            return false;
    		        }
    		    } catch (error) {}
    			$.ajax({
    				type : "GET",
    				url : "trainingmgm/console.do",
    				data : {
    					url : ticketUrl
    				},
    				error :  function(msg){
    					isConnecting=false;
    					$(".busyArea").html("disconnected");
    				},
    				cache:false,
    				success : function(msg) {
    					
    					vmid = msg.vmid;
    					host = msg.host;
    					ticket = msg.ticket;
    					try {
    				        status = vmrc.getConnectionState();
    				        
    				        if (status == 2) {	                
    				            return false;
    				        }
    				    } catch (error) {}			        
    			        if(vmid!=null&&host!=null&&ticket!=null&&vmid!=""&&host!=""&&ticket!=""){	        		
    					    vmrc.connect(host, "", true, ticket, "", "", vmid, "", "");
    			        }
    			        try {
    			            status = vmrc.getConnectionState();

    			        } catch (error) {}	
    			        isConnecting=true;
    				}
    			});
    		},
    		attachConnectionStateChangeHandlers:function(){//once the connection state is changed, the content of busyArea is change, too; 
    			if (navigator.userAgent.indexOf("MSIE")>0) {
    		        vmrc.attachEvent("onConnectionStateChange", consoleMethod.handleVmrcControlConnectionStateChange);
    			}
    			else if(browser.versions.ie11){
    				vmrc.addEventListener("onConnectionStateChange", consoleMethod.handleVmrcControlConnectionStateChange);
    				//vmrc.addEventListener("onConnectionStateChange", consoleMethod.handleVmrcControlConnectionStateChange,false);
    		    } else {
    		        vmrc.onConnectionStateChange = consoleMethod.handleVmrcControlConnectionStateChange;
    		    }
    		},
    		getvappUrl:function(){
    			$.ajax({
    				type : "GET",
    				url : "trainingmgm/getConsoleData.do",
    				data : {
    					vmName : vmname,
    					vappName:vappname,
    					flushType:consoleMethod.autoFlush,
    					isStudentPage:isStudentPage
    				},
    				async:false,
    				cache:false,
    				success : function(msg) {
    					vappUrl=msg;
    		       },
    		       error:function(jqXHR, textStatus, errorThrown){
    		       	if(jqXHR.status==403)
    		       	{
    		       		 window.location.href = rootName+"/login.do";
    		       	}
    		       	
    		       }
    		   });
    		},
    		handleVmrcControlConnectionStateChange:function(status){
    			if (status == 2) {
    		    	$(".busyArea").html("connected");
    		    	
    		    } else {
    		    	$(".busyArea").html("disconnected");
    		    }
    		    isConnecting=false;
    		},
    		refreshVmStatus:function(){//refresh status of start, stop and revert buttons
    			xhr= $.ajax({
    				type : "GET",
    				url : "trainingmgm/getStatusOfVm.do",
    				data : {
    					vmName : vmname,
    					vappUrl:vappUrl,
    					flushType:consoleMethod.autoFlush,
    					vappName:vappname
    				},
    				cache:false,
    				success : function(msg) {
    					ticketUrl=msg.ticket;
    					$(".v-play").attr("url",msg.powerOnUrl);
    					$(".v-stop").attr("url",msg.powerOffUrl);
    					
    					if(msg.advanced==0){
    						$(".v-revert").hide();
    					}else{
    						$(".v-revert").attr("url",msg.revertUrl);
    					}
    					$(".v-pause").attr("url",msg.pauseUrl);
    					consoleMethod.autoFlush="autoFlush";
    					if(xhr){
    		    			 xhr.abort();
    		    			}
    					setTimeout("consoleMethod.refreshVmStatus()", 10000);
    					buttonStyle.changeButtonStyle(isConnecting,$(".v-play").attr("url"),$(".v-stop").attr("url"),$(".v-revert").attr("url"),$(".v-pause").attr("url")); 
    	            },
    	            error:function(jqXHR, textStatus, errorThrown){
    	            	if(jqXHR.status==403)
                    	{
                    		 window.location.href = rootName+"/login.do";
                    	}
    	            	
    	            }
    	        });
    		},
    	
    		action:function(url,action){//start, stop, revert vm and refresh status of corresponding buttons
    			if(xhr){
    			 xhr.abort();
    			}
    			buttonStyle.toGray("v-play","");
    			buttonStyle.toGray("v-stop","");
    			buttonStyle.toGray("v-revert","");
    			buttonStyle.toGray("v-pause","");
    			
    			$.ajax({
    				type : "GET",
    				url : "action/consoleAction.do",
    				data : {
    					url : url,
    					action : action
    				},
    				error:function(jqXHR, textStatus, errorThrown){
//    					alert("there is some problem with this action");
    					if(jqXHR.status==403)
                    	{
                    		 window.location.href = rootName+"/login.do";
                    	}else{
    					bootbox.alert("There is some problem with this action", function() {
							});
                    	}
    				},
    				cache:false,
    				success : function(msg) {	
    			    	taskUrl=msg;
    				}
    			 });
    			consoleMethod.refreshVmStatus();
    		}
    };