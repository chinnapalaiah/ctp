<%--
  Created by IntelliJ IDEA.
  User: meij
  Date: 13-12-4
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false" %>
<meta http-equiv=”Content-Type” content=”text/html; charset="UTF-8">
<html>
<head>

<title>Student Training Management</title>
<script type="text/javascript" src="js/studentmgm.js"></script>
<script type="text/javascript" src="js/toConsole.js"></script>


</head>

<form class="form-horizontal" role="form" action="trainingmgm.do">
	<div class="table-responsive">
		<table class="table table-bordered">
			<div id="vAppContainer">
				<div class='row'>
					<div class="col-md-8 col-xs-12 col-md-offset-2 col-xs-12-padding">
						<div class="thumbnail" style="height: auto;min-height: 450px;">
							<div class='row'>
								<div class="col-md-12 col-xs-12">
									<h2 align="center" class="title">
										Student Training Management
									</h2>
									<hr>
								</div>
							</div>
							<div class="row">
								<div class="small-body-copy col-xs-12 col-md-12"
									style="padding-left: 10%; height: 20px;">
									<font color="red" id="errorMsg">${errorMsg}</font>
								</div>
							</div>
							<c:forEach items="${vAppModelList}" var="vapp" varStatus="s">

								<div class="row ">
									<div
										class="col-md-12  col-sm-12 col-xs-12  col-xs-vappdiv-height"
										style="display: inline-block;">

										<div class="col-stuName">
											<span><strong>Student name:</strong>&nbsp;${vapp.studentName}</span>
										</div>
										
									</div>
									<input type="hidden" id="username${s.count}"	value="${vapp.studentName}"/></div>


								<div class="row">
									<div
										class="col-xs-9 col-md-9 col-md-8-vapp col-xs-vappdiv-height">
										<div style="height: 40px; display: inline-block; float: left;">
											<span class="vapp-icon"></span>

										</div>
										<div class="col-xs-10 col-width1 col-xs-vappline-height1"
											style="display: inline-block; height: 40px;">
											<label class="small-body-copy bold"> Environment:&nbsp; </label>${vapp.name}</div>

										<input type="hidden" id="appName${s.count}"
											value="${vapp.name}"/>

									</div>

									<div class=" col-xs-3 col-md-3" id="${vapp.studentName }_loading">
										<img src="img/loading.gif" alt="" class="v-loading"> <span
											style="color: #4682B4;" id="${vapp.studentName}_font"
											name="vapp_font"></span>
									</div>

								</div>
								<br>

								<c:forEach items="${vapp.vmModelList}" var="vm">

									<div class="row">

										<div class="col-xs-9 col-md-9" style="padding-left: 13%;">
											<img alt="" src="img/vm.png">
											<div class="col-width1" style="display:inline-block;height:40px;padding-left:2%; ">${vm.vmName}</div>
										</div>
										<div class=" col-xs-3 col-md-3">
											<div id="${vm.vmName }_loading"
												name="${vapp.name}_vm_hidden">
												<img src="img/loading.gif" alt="" class="v-loading"> <span
													style="color: #4682B4;" id="${vm.vmName }_font"></span>
											</div>

										</div>
									</div>
								</c:forEach>


								<br>
								<hr>


							</c:forEach>
						
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
                        <br>
                 </c:if>
                 </div>
                </div>
						</div>
						</div>
						</div>
						</div>
						
		</table>
	</div>

</form>

</html>