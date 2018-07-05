<%--
  Created by IntelliJ IDEA.
  User: meij
  Date: 13-12-4
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv=”Content-Type” content=”text/html; charset=gb2312”>
<title>Training Management</title>
<script type="text/javascript" src="js/trainingmgm.js"> </script>
<script type="text/javascript" src="js/toConsole.js"> </script>

</head>
<body>

<form class="form-horizontal" role="form" action="trainingmgm.do">
	<div id="vAppContainer"> 
	  <div class='row'>

       <div class="col-md-8 col-xs-12 col-md-offset-2 col-xs-12-padding"> 
            
        <div class="thumbnail" style="height: auto;"> 
	     <div class='row'>
          <div class="col-md-12 col-xs-12"> 
                <h2 align="center" class="title">
                    Training Management
                </h2>
                <hr>
         </div>
        </div>
	     
	<div class="col-sm-12 col-md-12">
	<h3 style="padding-left: 2%;" class="subTitle">${user.userName}</h3>
	</div>	        
		 <div class="row">
	        <div class="small-body-copy col-xs-12 col-md-12" style="padding-left:10%;height:20px;">
	            <font color="red" id="errorMsg">${errorMsg}</font>
	        </div>
	    </div>
	  <div class="row" style="height: 60px">
		     <div class="col-xs-9 col-md-9 col-md-8-vapp col-xs-vappdiv-height" >
			
				    <div class="" style="height: 40px; display:inline-block; float: left;">
					<span class="vapp-icon"></span>
					</div>
					<div class="col-xs-10 col-width1 col-xs-vappline-height" style="display:inline-block;height:40px; "> <label class="small-body-copy bold">Environment: &nbsp;</label> ${vapp.name }
					
					</div>
	        </div>

		      <div class=" col-xs-3 col-md-3"  id="${vapp.name }_loading" >
			    <img alt="" class="v-loading" src="img/loading.gif">
			    <span style="color:#004AB8;" id="${vapp.name}_font" name="vapp_font"></span>
			    </div>
		</div>

	<c:forEach items="${vapp.vmModelList}" var="vm">

		<%--<div class="row" style="height: 50px">--%>
		
		<div class="row">
		
				<%--<div class="col-xs-9 col-md-9" style="padding-left:13%">--%>
				<div class="col-md-5 col-xs-12 col-sm-5 col-xs12-height" style="padding-left: 13%;">				
					<img alt="" src="img/vm.png"> <div class="col-width1" style="display:inline-block;height:40px;padding-left:2% ;">${vm.vmName}</div>
				</div>
			<div class=" col-xs-3 col-md-3">
			    <div   id="${vm.vmName }_loading" name="vm_hidden">
			    <img alt="" class="v-loading" src="img/loading.gif">
			    <span style="color:#004AB8;" id="${vm.vmName }_font"></span>
			    </div>
			
		</div>
		</div>
	</c:forEach>

		<hr>
		<div class="row" >
                  <div class="col-sm-12 col-md-12 col-xs-12 small-body-copy" align="center">
                   <c:if test="${user.infoList!=null}">
	                <c:forEach items="${user.infoList}" var="info">
	                   <div >
	                   <c:if test="${info.infoWarning!=null && info.infoWarning eq true}">
	                     <font class="BG_Gray_66" style="color:red;font-weight:bold">${info.infoValue}.</font>
	                   </c:if>
	                   <c:if test="${info.infoWarning eq null ||info.infoWarning eq false }">
	                    <font class="BG_Gray_66" >${info.infoValue}.</font>
	                   </c:if>
	                   </div>
	                </c:forEach>
	                  <br>
                 </c:if>
                 </div>
                </div>

	</div>
       </div>
       </div>
       </div>
       

</form>
</body>
</html>
