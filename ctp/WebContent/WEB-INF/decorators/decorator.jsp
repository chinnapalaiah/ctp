<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%    
String path = request.getContextPath();     
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";    
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0">

<base href="<%=basePath%>">
<title>CTP - <decorator:title /></title>
<link href="css/fonts.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/vtms.css">
 <link type="text/css" rel="stylesheet" href="css/jquery.contextMenu.css">
<script type="text/javascript" src="js/lib/jquery.js"></script>
<script type="text/javascript" src="js/lib/bootstrap.js"></script>
<script type="text/javascript" src="js/util.js"></script>
<script type="text/javascript" src="js/lib/jquery.contextMenu.js"></script>
 <script src="js/lib/bootbox.js" type="text/javascript"></script>
 <script type="text/javascript" src="js/JudgeOS.js"></script>
 <script type="text/javascript" src="js/rdpActiveX.js"></script>
 

<decorator:head />
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesnt work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" >
    var rootName;
    
    $(function(){
    	$("#navbar-button").click(function(){
        	var dis=$("nav.navbar-collapse.bs-navbar-collapse.in").css("display");
        	if(dis!=undefined)
        	{
        		
        		$("#navbar-button").css("background-color","rgba(0, 0, 0, 0)");
        		$("#navbar-button").css("outline-color","rgb(255, 255, 255)");
        		$("#navbar-button").css("outline-width","0px");
        	
        	}else{
        		//$("#navbar-button").css("background-color","rgb(255, 161, 45)");
        		$("#navbar-button").css("outline-color","rgb(255, 255, 255)");
        		$("#navbar-button").css("outline-width","0px");
        	}
        	
        	
        });
    	
    	
    	$(".sub-title").click(function(){
	    	$(".sub-title").each(function(){
				$(this).removeClass("yellow");   				
			});
			$(this).addClass("yellow");
    	});
    	
		rootName=util.getRootPath;
    });
 
    </script>

</head>
<body>
	<div class="navbar navbar-inverse navar-padding">
		<div class="container">
			<div class="navbar-header" style="margin-right: 0;
margin-left: 0;">
				<button class="navbar-toggle collapsed" type="button" id="navbar-button"
					data-toggle="collapse" data-target=".bs-navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			
			 <a target="_blank" href="http://www.microfocus.com" class="navbar-brand"> <img
                                        src="img/mf_logo_white.svg" style="width: 100px; height: 35px;" > <a class="brand" href="."><font class="ctp-font" style="font-size: 27px;margin-center:96px;">Cloud Training Platform</font></a>
                                </a>
                        </div>
                        <nav class="collapse navbar-collapse bs-navbar-collapse"
                                role="navigation">
                        <ul class="nav navbar-nav">

					<c:if test="${user.userName!=null}">
  						<li><a href="event.do" class="small-body-copy sub-title events">My Events</a></li>
  					</c:if>
                          <!--        <c:if test="${user.isDemo eq null||user.isDemo eq false}">
                                <li><a href="trainingmgm.do" class="small-body-copy sub-title training">Training Management</a></li>
                                </c:if>    -->

<c:if test="${user.type eq 'instructor'&& events[0].numStudents >= '1'}">
                <li><a href="trainingmgm/studentmgm.do" class="small-body-copy sub-title student">Student Training Management</a></li>
       </c:if>
			       
		</ul>

		<c:if test="${user.userName!=null}">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="logout.do" id="logout" class="small-body-copy">Logout</a></li>

				</ul>
			</c:if>

			<!-- <ul class="nav navbar-nav navbar-right">
				<li><a href="../help">Help</a></li>
			</ul> -->
			</nav>
		</div>
	</div>
	<decorator:body />
    <table border="0" width="100%" cellpadding="0" cellspacing="0">
        <tbody>
          <tr>
            <td>
                <table width="100%" height="75px" border="0" cellspacing="0" cellpadding="0" class="BG_Gray_66">
                <tbody>
                
                <tr><td><img src="" alt="" width="1" height="8"></td>
                </tr>
              
              <tr align="center" valign="top"><td valign="top" class="hp_footer ">
                   <a class="hp_footer footer" href="https://software.microfocus.com/en-us/legal/privacy">Privacy Statement</a>&nbsp;|&nbsp;
                   <a class="hp_footer footer" href="https://software.microfocus.com/en-us/legal/terms-of-use">Terms of Use</a>&nbsp;|&nbsp;
	           <a class="hp_footer footer" href="https://software.microfocus.com/contact">Contact</a>&nbsp;|&nbsp;
                   Portal Version 10.40<br><c:out value="©" escapeXml="true"></c:out> Copyright  2017 EntIT Software LLC </td>

                </tr>
              
                   </tbody>
                   </table>
            </td>
        </tr>
        
        </tbody>
    </table>
</body>
</html>
