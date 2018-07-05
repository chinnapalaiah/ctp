<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" href="/favicon.ico"/>

   
    <title>Login</title>
    <link rel="shortcut icon" href="/favicon.ico"/>
    <style type="text/css">
    #login-form {
    	padding: 1em 1em;
    }
    
    .control-group {
    	margin: 0em 0em;
    }
    </style>
    <script type="text/javascript" src="js/lib/sha1.js"></script>
      <script type="text/javascript" src="js/RSA/security.js"></script>
      <script type="text/javascript" src="js/login.js"></script>
      <script type="text/javascript">
      
       var domainAddr = '<%=request.getServerName() %>';
       var clientIP = '<%=request.getHeader("X-Forwarded-For")==null? request.getRemoteAddr() : request.getHeader("X-Forwarded-For")%>';
       var exponent='${keyPairData.exponent}';
       var modulus='${keyPairData.modulus}';
       $(function () {
    	
    	 	$(".sub-title").each(function(){
    	  		$(this).removeClass("yellow");   				
    	  	}); 				
    		
    	    $("#username,#password").bind('input', function () {
    	 	    $("div#loginDiv span").css('display','none');
         	    $("#username,#password").removeClass("errorColor");
    	    });
	 	    $("div#loginDiv span").css('display','none');
    	    var username = $("#username").val();
    	    var password = $("#password").val();
    	    $(document).on('keyup click',function () {            	
    	    	username = $("#username").val();
    	        if(username=="")
    			    {    
    					$("#nameLabel").css("display","block");
    			   }else{
    				$("#nameLabel").css("display","none");
    			  } 
    	        $("#username").removeClass("errorColor");
            	
    	        password = $("#password").val();
    	        
    	        if(password=="")
    				  {         
    					 $("#passwordlabel").css("display","block");
    				  }else{
    					 $("#passwordlabel").css("display","none");
    				  }
    	        $("#password").removeClass("errorColor");
    	    });
       $("#login").click(function () {
       	
       	login(username,password,exponent,modulus);
       });
   	$(document).keyup(function(event){
		  if(event.keyCode ==13){
				login(username,password,exponent,modulus);
			  
		  }
	});
  });
   
       </script>

</head>
<body class="app signed-out  sessions new" style="background-color:#EDEEF3">
<div style='color: #d6dbe1;display:none'>
		<%
		    out.println("X-Forwarded-For: "
							+ request.getHeader("X-Forwarded-For"));
			out.println("remote hosts: " + request.getRemoteAddr());
			String clientIP = request.getHeader("X-Forwarded-For");
			out.println("remote servername: " + request.getServerName());
		%>
</div>
<div class="container" id="page" style="padding-right: 4%;padding-top:10px;">
    <div class='row'>
        <div class='col-md-7 col-xs-12 col-md-offset-3 ' id="Layer1"
             align="center" style="float: none;">

            <div class="account-form thumbnail" style="padding: 40px; width: 400px;">
		<img style="display:inline; width: 100px; height: 35px;" src="img/mf_logo_blue.svg">
                <h1 class="title" style="font-size:30px;vertical-align:middle;color:#0073e7;font-weight:600;">Training Login</h1>
                <hr>
                <form accept-charset="UTF-8" action="/users" class="form-vertical"
                      id="loginForm" method="post">
                    <div style="margin: 10; padding: 0; display: inline">
                        <input name="utf8" type="hidden" value="&#x2713;"/> <input
                            name="authenticity_token" type="hidden" 
                            value="oTWqvETV8u5ROyi51KOynKTJGaFwOaaMHBc05dNVZ+g="/>
                    </div>
                    <div id="login-form" align="left">
                        <div class="control-group" align="left">
                            <label class="control-label section-title" for="user_username">
                                Username</label>
                            <div class="controls">
                                <input class="focus form-control " id="username" name="username" style="width: 100%;"
                                  class="form-text"     placeholder="Required" size="30" type="text" data-required/>
                                  <br><label id="nameLabel" style="display:none;color:red;">Username field is required</label>
                            </div>
                        </div>
                        <div class="control-group" align="left">
                            <label class="control-label section-title" for="user_password">
                                Password</label>
                            <div class="controls">
                                <input class="form-control " autocomplete="off" id="password" name="password" style="width: 100%;"
                                  class="form-text"     placeholder="Required" size="30" type="password" data-required/>
                                  <br><label id="passwordlabel" style="display:none;color:red;">Password field is required</label>
                            </div>
                        </div>
                        <hr>
                        <div class="actions" id="loginDiv" style="height: 30%" align="center">
                            <input class="button1_sm .button_sm"
                                   data-disable-with="Signing In..." name="commit" id="login"
                                   type="button" value="Log In"/>
                                   <span
                                id="acountExp"
                                style="display: none; color: red; font-size: 14px">Username or Password incorrect</span>
								<span
                                id="validateLength"
                                style="display: none; color: red; font-size: 14px">Password must be at least  8 characters</span>
								<span
                                id="eventDateError"
                                style="display: none; color: red; font-size: 14px">An error happened during login, please see and Administrator for assistance</span>
                        </div>
                </form>
            </div>
        </div>
    </div>
    <br>
</div>


</body>
</html>
