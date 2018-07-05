var isStudentPage="true"; 
var taskUrl = "";
        var name;
        var action = "";
        var url;
        var vmOrVapp;
        var xhr;
        var vmElement,gloabElement ;
        var display;
        var vappName;
        var vmNamex;
        var status;

        
        function initialLoadVm(flushType) {
        	xhr =$.ajax({
                type: "post",
                url: "trainingmgm/initialStudentmgm.do",
                data:{
                	flushType:flushType
                },
                success: function (msg) {
  
                    if(action==""){ 
                    	$("#vAppContainer").html(msg);
                    	if(xhr ){
        		            xhr.abort();
        		        }
                        setTimeout("initialLoadVm('autoFlush')", 10000);
                		
                	}else if(action!=""&&action!="show"){
                		if(xhr ){
        		            xhr.abort();
        		        }
                		action="";
                		setTimeout("initialLoadVm('handFlush')", 5000);  
                	}
                	else if(action=="show"){
//                		if(xhr ){
//        		            xhr.abort();
//        		        }
                		setTimeout("initialLoadVm('handFlush')", 5000);  
                	}
                },
                error: function (jqXHR, textStatus, errorThrown) {

                    if (jqXHR.status == 403) {
                        window.location.href = "login.do";
                    }else if(jqXHR.status == 504){
//                    	bootbox.alert("Request failed, please try again later.", function() {
//						});
                    	window.location.href = "trainingmgm/initialStudentmgm.do?flushType=handFlush";
                    }
                }
            });


        }

        function oneVm(result){
        	if(result==true){
        	vmElement.checked=true;
      		var id=vmElement.id;
          	var rdpOrConsole=id.split("&")[0];
          	var vappName=id.split("&")[1];
          	var vmName=id.split("&")[2];
      	    action="show";
      	    $.ajax({
  				type : "GET",
  				url : "trainingmgm/chooseRdpOrConsole.do",
  				data : {
  					vappName : vappName,
  					vmName : vmName,
  					rdpOrConsole : rdpOrConsole
  				},
  				cache:false,
  				success : function(msg) {
  					if(msg=="ok"){
  						action="";

  						bootbox.alert("Applied Successfully!", function() {
							});
//  						initialLoadVm('handFlush');
  					}
  				},
  			    error: function(jqXHR, textStatus, errorThrown ) {
  	                	
  	                	if(jqXHR.status==403)
  	                	{
  	                		 window.location.href = "login.do";
  	                	}else if(jqXHR.status == 504){
  	                    	bootbox.alert("Request failed, please try again later.", function() {
  							});
  	                		window.location.href = "trainingmgm/initialStudentmgm.do?flushType=handFlush";
  	                    }
  	                }
                  });
        	}

        }
        
 
        function gloabVm(result){
         if(result==true){
   		   gloabElement.checked=true;
      		   action="show";
				var id=gloabElement.id;
				var rdpOrConsole=id.split("&")[0];
				var instructorName=id.split("&")[1];
				$(".showOption").each(function(){
						if(rdpOrConsole=="showRdp"){
							$(".showRdp").attr("checked","checked");
						}else if(rdpOrConsole=="showConsole"){
							$(".showConsole").attr("checked","checked");
						}else if(rdpOrConsole=="showBoth"){
							$(".showBoth").attr("checked","checked");
						}
    			});
				 $.ajax({
					type : "GET",
					url : "trainingmgm/chooseRdpOrConsoleGloab.do",
					data : {
						rdpOrConsole : rdpOrConsole,
						instructorName : instructorName
					},
					cache:false,
					success : function(msg) {
						if(msg=="ok"){
							action="";
//							 $('#success').modal({
//		        		        keyboard: false,
//		        		      });
						//	alert("Applied Successfully!");
							bootbox.alert("Applied Successfully!", function() {
 							});
							
//						    initialLoadVm('handFlush');
						}
					},
				    error: function(jqXHR, textStatus, errorThrown ) {
		                	
		                	if(jqXHR.status==403)
		                	{
		                		 window.location.href = "login.do";
		                	}else if(jqXHR.status == 504){
	  	                    	bootbox.alert("Request failed, please try again later.", function() {
	  							});
		                		window.location.href = "trainingmgm/initialStudentmgm.do?flushType=handFlush";
	  	                    }
		                }
	                });
        	}
//         else{
//        		initialLoadVm('autoFlush');
//        	}
        }

        function stRunAction(appNameX) {
//        	 if(xhr ){
//		            xhr.abort();
//		        }
        	 if (vmOrVapp == "vapp") {
                 $("span[name='" + appNameX + "*vapp_font']").html(display);
                 var vm_display=$("div[name='" + appNameX + "*vm_display']");
                 for(var i=0;i<vm_display.length;i++){
                     var vmName=vm_display.eq(i).attr("id");
                     var isInVcloud=vm_display.eq(i).attr("status");
                     if(isInVcloud!="notInVcloud"){
                         $("div[id='"+vmName+"']").hide();
                         $("div[id='"+vmName+"_loading']").show();
                         $("span[id='"+vmName+"_font']").html("Busy");
                     }
                 }
                 $.ajax({
                     type: "POST",
                     url: "action.do",
                     data: {
                         url: url,
                         action: action,
                         vmOrVapp:vmOrVapp,
                         vappName:appNameX,
                         page:'student',
                         status:status
                         
                     },
                     success: function (msg) {
                        taskUrl = msg;
//                     	setTimeout("initialLoadVm('autoFlush')", 10000);
                     },
		                error: function(jqXHR, textStatus, errorThrown ) {
		                	
		                	if(jqXHR.status==403)
		                	{
		                		 window.location.href = "login.do";
		                	}else if(jqXHR.status == 504){
		                    	bootbox.alert("Request failed, please try again later.", function() {
								});
		                    	window.location.href = "trainingmgm/initialStudentmgm.do?flushType=handFlush";
		                    }
		                }

                 }); 
                 
             }
             else if (vmOrVapp == "vm") {
                 $("span[id='" + name + "_font']").html(display);
                 $("div[name='" + appNameX + "*vapp_display']").hide();
                 $("span[name='" + name + "_font']").html(display);
                 var isBusy = $("span[id='" + appNameX + "_font']").length;
                 var temp = $("span[name='" + appNameX + "*vapp_font']").is(":hidden");
                 if (isBusy == 0 && temp) {
                     $("div[name='"+appNameX+"*vapp_hidden']").show();
                     $("span[name='"+appNameX+"*vapp_font']").html("Busy");
                 }

               
                 $.ajax({
                     type: "POST",
                     url: "action.do",
                     data: {
                         url: url,
                         action: action,
                         vappName:appNameX,
                         vmOrVapp:vmOrVapp,
                         name:vmNamex,
                         page:'student',
                         status:status
                     },
                     success: function (msg) {
                         taskUrl = msg;
                     },
		              error: function(jqXHR, textStatus, errorThrown ) {
		                	
		                	if(jqXHR.status==403)
		                	{
		                		 window.location.href = "login.do";
		                	}else if(jqXHR.status == 504){
		                    	bootbox.alert("Request failed, please try again later.", function() {
								});
		                		window.location.href = "trainingmgm/initialStudentmgm.do?flushType=handFlush";
		                    }
		                }

                 });
             }

        	
         
        }
        

        $(document).ready(function () {
        	$("body").on('mouseout',"a[title='Launch RDP']",function(){
         	   $("#rdpRemind").removeClass("rdpRemider");
            });
            $("body").on('mouseover',"a[title='Launch RDP']",function(){
         	   $("#rdpRemind").addClass("rdpRemider");
            });
            
        		
        	$("body").on('click',"a[title='Launch RDP']",function(){
                  	 var id = $(this).attr("id");
                  	 
                       var vmName = id.split("&")[2];
                       var userName=id.split("&")[4];
                       if(browser.versions.ios) { 
                         var param = 'target=_blank,top=0,left=0,right=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no';
          		    	 var url="/ctp/trainingmgm/download.do?vmName="+vmName+"&userName="+userName;
          		    	 window.open(url,vmName,param);
                          }else{
                          window.location.href = "/ctp/trainingmgm/download.do?vmName="+vmName+"&userName="+userName;
                          }
                  	
                  });
                  
        	
        	 $("body").on('mousedown', ".showOption", function () {
        		 vmElement=this;
        		 action="show";
        		    bootbox.confirm("This will apply to this VM only, press OK to apply.", function(result) {
        		    	oneVm(result);
          		   });
        		
        	   });
        	
        	 $("body").on('mousedown', ".showGloabOption", function () {
        		 action="show";
        		 gloabElement = this;

     		   bootbox.confirm("This will apply to all VMs, press OK to apply.", function(result) {
     			  gloabVm(result);
     		   });
        	 });
     	

      
        	
    		$(".sub-title.student").addClass("yellow");
    		
            
    		initialLoadVm('autoFlush');


            $("body").on('click', "a[class='col-sm-button-1'][name!='gray']", function () {

                var flag = false;

                var id = $(this).attr("id");
                name = id.split("&")[1];
                var appNameX = name.split("*")[0];
                vmNamex=name.split("*")[1];
                vmOrVapp = id.split("&")[2];
                status=id.split("&")[3];
                url = $(this).attr("name");
                

                action = $(this).attr("title");
                var loadingId = name + "_loading";
                var fontId = name + "_font";
                
                if(action=="Revert"){
                	 bootbox.confirm("You are about to revert to the original state, are you sure you want to do this?", function(result) {
    					 if(result==true){
    						 display = action + "ing";
    						 $("div[id='" + loadingId + "']").show();
    			             $("div[id='" + name + "']").hide();
    						 stRunAction(appNameX);
    					 }
          		   });
                	
                }else{
                	if (action == "Stop") {
                        display = action + "ping";
                    } else if(action=="Pause"){
                        display = "Pausing";
                    }else if(action=="Resume"){
                    	display="Resuming";
                    }
                    else{
                    	display = action + "ing";
                    
                    }
                	$("div[id='" + loadingId + "']").show();
                    $("div[id='" + name + "']").hide();
                	stRunAction(appNameX);
                }
                 

            });
        });


