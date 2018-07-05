 var ticket ;
var vmstatus ;
var vmName ;
var vappName ;
var startUrl ;
var stopUrl ;
var revertUrl ;
var vappUrl ;
var advanced ;
var vmrc;

       function toConsole(vmstatus){   	   
		if(vmstatus!='4'){
			bootbox.alert("Please start the Virtual Machine to view Console.", function() {
				});
		}else{
    		vmrc = document.getElementById("vmrc");
	    	 var name = vappName +' - '+vmName;	    	 	    	 
   			 
	    	 var height = window.screen.availHeight;
	    	 var width = window.screen.availWidth;
    		 try{
    			 var ret = vmrc.isReadyToStart();    			 
            	 if(width>350){
            		 width = 800;           		 
            	 }            	 
   		   		 if(ret){
   		   			
   		   			 var param = 'height=500px,width='+width+'px,top=0,left=0,right=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no';  		   			 
//   		   			 var url=rootName+'/trainingmgm/toPluginConsole.do?vappName='+vappName+'&vmName='+vmName+'&isStudentPage='+isStudentPage;   		   			 
   		   			 //window.open(url,name,param);  
   		   		//document.cookie="vmName="+vmName+"; vappName="+vappName+"; isStudentPage="+isStudentPage+";"; 
   		   	 document.cookie="vmName="+vmName+";";
			 document.cookie="vappName="+vappName+";";
			 document.cookie="isStudentPage="+isStudentPage+";";
			 document.cookie="vappUrl="+vappUrl+";";
   		   	 var url=rootName+'/trainingmgm/toPluginConsole.do';
   		   	 

   			 localStorage.setItem("localVmName",vmName);
			 localStorage.setItem("localVappName",vappName);
			 localStorage.setItem("localIsStudentPage",isStudentPage);
			 localStorage.setItem("localVappUrl",vappUrl);

   		   	   window.open(url,name,param);
   		   	    		   	
   		   		 }
    		}catch(e){
    			if(window.navigator.platform == "Win64"&&(browser.versions.webKit==false&&browser.versions.ie10==false&&browser.versions.ie11==false)){
    				bootbox.alert("Your current browser/version does not support the required plugin, please use a different browser or version .", function() {
						 });
    			}
    			
    			else if (window.navigator.platform == "Win32"&&(browser.versions.webKit==false&&browser.versions.ie10==false&&browser.versions.ie11==false)) {
//    			else if ((window.navigator.platform == "Win64"||window.navigator.platform == "Win32")&&(browser.versions.webKit==false&&browser.versions.ie10==false&&browser.versions.ie11==false)) {
    				window.location.href=rootName+"/plugin/VMware-ClientIntegrationPlugin-5.5.0.exe";
    			} else { 
					 var param = 'height=500px,width=650px,top=0,left=0,right=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no';
					 document.cookie="vmName="+vmName+";";
					 document.cookie="vappName="+vappName+";";
					 document.cookie="isStudentPage="+isStudentPage+";";
					 document.cookie="vappUrl="+vappUrl+";";
					 var url=rootName+'/trainingmgm/toHtmlConsole.do';

					 localStorage.setItem("localVmName",vmName);
					 localStorage.setItem("localVappName",vappName);
					 localStorage.setItem("localIsStudentPage",isStudentPage);
					 localStorage.setItem("localVappUrl",vappUrl);

					window.open(url,name,param);
					
    			}				
    		}
		}
       }
