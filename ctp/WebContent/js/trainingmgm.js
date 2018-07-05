var isStudentPage="false"; 
var pathName;
var projectName;
var localhostPath;
var consolejsp;
var taskUrl = "";
        var name;
        var action="";
        var url;
        var vmOrVapp;
        var xhr;
        var status;

        
        function loadVm(flushType)
        {
        	
           xhr= $.ajax({
                type: "GET",
                url: "trainingmgm/getVmsHtml.do?flushType="+flushType,
                cache:false,
                success: function (msg) 
                {
//                	if(action==""){ 
                		$("#vAppContainer").html(msg); 
                		if(xhr){
        		            xhr.abort();
        		        }
                        setTimeout("loadVm('autoFlush')", 10000);
                		
//                	}else{
//                		action="";
//                		setTimeout("loadVm('handFlush')", 5000);   
//                	}
//  

                },
                error: function(jqXHR, textStatus, errorThrown ) {
                	
                	if(jqXHR.status==403)
                	{
                		 window.location.href = "login.do";
                	}else if(jqXHR.status == 504){
//                    	bootbox.alert("Request failed, please try again later.", function() {
//						});
                    	window.location.href = "trainingmgm/getVmsHtml.do?flushType=handFlush";
                    }
                }
            });
   
             
        }
        
        function runAction(display){
        	 var vappName=$("#vappName").val();
        		if(xhr ){
		            xhr.abort();
		        }
				if(vmOrVapp=="vapp"){
					 $("span[name='vapp_font']").html(display);
					var vm_display=$("div[name='vm_display']");
					for(var i=0;i<vm_display.length;i++)
					{
						var vmName=vm_display.eq(i).attr("id");
						var isInVcloud=vm_display.eq(i).attr("status");
					
						if(isInVcloud!="notInVcloud")
						{
							$("div[id='"+vmName+"']").hide();
							$("div[id='"+vmName+"_loading']").show();
							$("span[id='"+vmName+"_font']").html("Busy");
							}
					}
					
					$.ajax({
						type : "GET",
						url : "action.do",
						data : {
							url : url,
							action : action,
							vmOrVapp:vmOrVapp,
							vappName:vappName,
							status:status,
							page:'training'
							
						},
						cache:false,
						success : function(msg) {
						loadVm('autoFlush');
						},
		                error: function(jqXHR, textStatus, errorThrown ) {
		                	
		                	if(jqXHR.status==403)
		                	{
		                		 window.location.href = "login.do";
		                	}else if(jqXHR.status == 504){
		                    	bootbox.alert("Request failed, please try again later.", function() {
								});
		                		window.location.href = "trainingmgm/getVmsHtml.do?flushType=handFlush";
		                    }
		                }
		             });
				}
				else if(vmOrVapp=="vm")
				{
					    $("span[id='"+name+"_font']").html(display);
					    $("div[name='vapp_display']").hide();
						$("span[name='"+name+"_font']").html(display);
						var isBusy=$("span[id='"+vappName+"_font']").length;
						var temp= $("span[name='vapp_font']").is(":hidden");
						if(isBusy==0&&temp)
						{
							   $("div[name='vapp_hidden']").show();
							   $("span[name='vapp_font']").html("Busy");
						}
						$.ajax({
							type : "GET",
							url : "action.do",
							data : {
								url : url,
								action : action,
								vmOrVapp:vmOrVapp,
								vappName:vappName,
								name:name,
								status:status,
								page:'training'
								
							},
							cache:false,
							success : function(msg) {
							loadVm('autoFlush');
							},
			                error: function(jqXHR, textStatus, errorThrown ) {
			                	
			                	if(jqXHR.status==403)
			                	{
			                		 window.location.href = "login.do";
			                	}else if(jqXHR.status == 504){
			                    	bootbox.alert("Request failed, please try again later.", function() {
									});
			                		window.location.href = "trainingmgm/getVmsHtml.do?flushType=handFlush";
			                    }
			                }
			             });
				}

        }
	
        $(document).ready(function () {
        	
    		$(".sub-title.training").addClass("yellow");
    		
           loadVm("handFlush");
           $("body").on('mouseout',"a[title='Launch RDP']",function(){
        	   $("#rdpRemind").removeClass("rdpRemider");
           });
           $("body").on('mouseover',"a[title='Launch RDP']",function(){
        	   $("#rdpRemind").addClass("rdpRemider");
           });

            $("body").on('click',"a[title='Launch RDP']",function(){
            	var id = $(this).attr("id");
                var vmName = id.split("&")[1];
                var userName=id.split("&")[3];
                if(browser.versions.ios) { 
               	 var param = 'target=_blank,top=0,left=0,right=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no';
		    	 var url="trainingmgm/download.do?vmName="+vmName+"&userName="+userName;
		    	 window.open(url,vmName,param);
//                 window.location.href = "trainingmgm/download.do?vmName="+vmName+"&userName="+userName;
                }else{
                window.location.href = "trainingmgm/download.do?vmName="+vmName+"&userName="+userName;
                }
             });
            /* 	 var id = $(this).attr("id");
                 var vmName = id.split("&")[1];
                 var userName=id.split("&")[3];
                 if(browser.versions.ie8||browser.versions.ie11||browser.versions.ie10) { 
//                	 if(vmName!="1"){
                	   if(xhr && xhr.readyState != 4){
     			            xhr.abort();
     			        }
                	 $.ajax({
         				type : "GET",
         				cache: false,
         				url : "trainingmgm/connContent.do",
         				data:{
         					vmName:vmName,
         					userName:userName
         				},
         				success : function(msg) {
                       	   openRdpfromIE(msg);
                           loadVm('autoFlush');
         				 }
                        });
                 }  else{
                	 window.location.href = "trainingmgm/download.do?vmName="+vmName+"&userName="+userName;
                	 var response='${response}';
                 }
            });
            */
            $("body").on('click', "a[class='col-sm-button-1'][name!='gray']", function () {
            var id = $(this).attr("id");
            name = id.split("&")[1];
            vmOrVapp = id.split("&")[2];
            status=id.split("&")[3];
            url = $(this).attr("name");
			action = $(this).attr("title");
			var loadingId=name+"_loading";
			var fontId=name+"_font";
			
			var display="";
			if(action=="Revert"){
				 display=action+"ing";
				 bootbox.confirm("You are about to revert to the original state, are you sure you want to do this?", function(result) {
					 if(result==true){
						 $("div[id='"+loadingId+"']").show();
						 $("div[id='"+name+"']").hide();
						 runAction(display);
					 }
					
      		   });
		    }else{
		    	$("div[id='"+loadingId+"']").show();
				$("div[id='"+name+"']").hide();
				
				if(action=="Stop"){
			    	display=action+"ping";
			    	 
			    }else if(action=="Start"){
			    	display=action+"ing";
			    }else if(action=="Pause"){
			    	display="Pausing";
			    }else if(action=="Resume"){
			    	display="Resuming";
			    }
				runAction(display);
		    } 
            });

        });
