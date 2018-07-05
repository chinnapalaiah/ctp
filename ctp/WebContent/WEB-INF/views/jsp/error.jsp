<%--
  Created by IntelliJ IDEA.
  User: meij
  Date: 13-12-17
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<HTML>
<HEAD>
<TITLE>Server down</TITLE>
<meta http-equiv=”Content-Type” content=”text/html; charset=gb2312”>

<STYLE type=text/css>
.font14 {
	FONT-SIZE: 14px
}

.font12 {
	FONT-SIZE: 12px
}

.font12 a {
	FONT-SIZE: 12px;
	color: #CC0000;
	text-decoration: none;
}
</STYLE>

<META content="MSHTML 6.00.2900.3354" name=GENERATOR>

<script type="text/javascript">
	$(function() {
		$(".sub-title").each(function() {
			$(this).removeClass("yellow");
		});
	});
</script>
</HEAD>
<BODY>

	<div class="container col-xs-container-padding">

		<div class='row'>
			<div class="col-md-9 col-xs-12 col-md-offset-1 col-xs-event-padding " style="height: 70%">
				<div class="thumbnail">
				
				 <br>
            <br>
             <br>
              <br>
               <br>
                <br>

					<div class="col-md-3 col-xs-12 col-error-xs-padding col-md-error-padding" style="height: 30%;">
						<img src="img/error.jpg" style="width: 90px;">
					</div>

					<div class="col-md-9 col-xs-12 col-error-xs-padding col-md-error-padding" style="height: 30%">
						<SPAN style="width: 200px;"> 
						   <font class="title"
							style="font-size: 24px;"> we are sorry...</font>
					    </SPAN><br> 
						<SPAN style="width: 200px"> 
							<strong class="error-font">
								${errorMsg}</strong>
						</SPAN>
						<br> 
						<span style="width: 200px"> 
							<FONT color=#666666 class="small-body-copy">Return to 
							<A href="event.do">CTP</A> Home page.
						   </FONT>
						</span>
					
				  </div>
				<br> <br> <br> <br> <br> <br> <br>
				<br> <br> <br> <br> <br> <br> <br>


             </div>
			</div>
		</div>
	</div>

</BODY>




</HTML>
