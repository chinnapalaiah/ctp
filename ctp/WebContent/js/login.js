function getPlatForm(){
	 var platform="";
	var os=detectOS();
    if((os.indexOf("Win")!=-1||os.indexOf("other")!=-1)&&window.navigator.platform.indexOf("Win32")!=-1){
   	 platform="win_jre_32bit";
   	 
    }else if((os.indexOf("Win")!=-1||os.indexOf("other")!=-1)&&window.navigator.platform.indexOf("Win64")!=-1){
   	 platform="win_jre_64bit";
    }
    
    if(os=="macos"&&(navigator.userAgent.indexOf("Safari")!=-1||navigator.userAgent.indexOf("Firefox")!=-1)){
   	 platform="mac_safari_jre_7";
    } else if(os=="macos"&&navigator.userAgent.indexOf("Chrome")!=-1){
   	 platform="mac_safari_jre_6";
    }
    return platform;
}




   // $(function(){
    	
       function login(exponent,modulus){
	   		var  username = $("#username").val();
	   	    var password = $("#password").val();
	 	    $("div#loginDiv span").css('display','none');

     	    $("#username,#password").removeClass("errorColor");
	   		 /*$("#validateSubmit").css('display', 'none');
	         $("#eventDateError").css('display', 'none');
	         $("#eventExpError").css('display', 'none');*/
   		
			 if(password=="")
			  {
					 $("#passwordlabel").css("display","block");
			  }
   		 
            if(username=="")
 			  {
 					 $("#nameLabel").css("display","block");
 			  }
 			 if(password=="")
 			  {
 					 $("#passwordlabel").css("display","block");
 			  }
 			  if(username==""||password=="")
 				{
 				  return false;
 				}
            if (username!=""&&password!="") {
                if(password.length<8){ 
               	 $("#password").addClass("errorColor");
               	 $("#validateLength").css('display', 'block');
               	 return false;
                }else{
               	  $("#password").removeClass("errorColor");
               	 $("#validateLength").css('display', 'none');
                }
               // var exponent='${keyPairData.exponent}';
                //var modulus='${keyPairData.modulus}';
                var os=detectOS();
               // var sha1Password=password;
                var sha1Password = CryptoJS.SHA1(password).toString();
               // if(exponent!=""&&modulus!=""){
                // var key=RSAUtils.getKeyPair(exponent, '', modulus);
                // sha1Password=password;
                 //sha1Password=encodeURI(password);
                // sha1Password= RSAUtils.encryptedString(key,password);
               // }
              /*  $.ajax({
                    type: "POST",
                    url: "attendees/login.do" ,
                    data:{
                   	 os:os,
                   	 username:username,
                   	 pass:sha1Password
                    },
                    cache:false,
             
                    success: function (msg) {
                        if (msg == "success") {
                       	 //var platForm=getPlatForm();
                            window.location.href = "event.do";
                            
                        }else {
                            $("#"+msg).css('display', 'block');
                            // $("#password").focus();
                             $("#username,#password").addClass("errorColor");
                        }
                    }
                });*/
                
                $.getJSON("https://"+domainAddr+"/ctp/attendees/login.do?jsoncallback=?&os=" + os + "&username=" + username + "&pass=" + sha1Password + "&clientIP=" + clientIP, function(data) {
                	if (data.result == "success") {
                      	 //var platForm=getPlatForm();
                           window.location.href = "event.do";
                           
                       }else {
                           $("#"+data.result).css('display', 'block');
                           // $("#password").focus();
                            $("#username,#password").addClass("errorColor");
                     }
           		});
            }else{
           	 
           	 $("#username,#password").addClass("errorColor");
           	 
           	  $("#username,#password").mouseout(function(){
           		  
           		  $("#username,#password").removeClass("errorColor");
           		  
                   });
            }
      
      };
    
    

//});
    