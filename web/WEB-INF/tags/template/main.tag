<%@ tag body-content="scriptless" trimDirectiveWhitespaces="true"%>
<%@ attribute name="htmlTitle" type="java.lang.String"
	rtexprvalue="true" required="true"%>
<%@ attribute name="bodyTitle" type="java.lang.String"
	rtexprvalue="true" required="true"%>
<%@ attribute name="headContent" fragment="true" required="false"%>
<%@ attribute name="navigationContent" fragment="true" required="true"%>
<%@ include file="/WEB-INF/jsp/base.jspf"%>
<!DOCTYPE html>
<html>
<head>
<title>Customer Support :: <c:out value="${fn:trim(htmlTitle) }"></c:out></title>
<link rel="stylesheet"
	href="<c:url value="/resource/stylesheet/main.css"></c:url>" />
	<link rel="stylesheet"
		href="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.1/css/bootstrap.min.css" />
	<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
	<script
		src="http://cdnjs.cloudflare.com/ajax/libs/moment.js/2.0.0/moment.min.js"></script>
	<script type="text/javascript" language="javascript">
    	var postInvisibleForm = new function(url, fields){
    		var form = $('<form id="mapForm" method="post"></form>')
    					.attr({action:url, style:'display: none;'});
    		for(var key in fields){
    			if(fields.hasOwnProperty(key))
    				form.append($('<input type="hidden">').attr({name:key, value:fields[key]}));
    		}
    		$('body').append(form);
    		form.submit();
    		
    	};
    	var newChat = new function(){
    		postInvisibleForm('<c:url value="/chat"/>',{action:'new'});	
    	};
    </script>
<jsp:invoke fragment="headContent"></jsp:invoke>
</head>

<body>
	<h1>Multinational Widget Corporation</h1>
	<table border="0" id="bodyTable">
		<tbody>
			<tr>
				<td class="sidebarCell"><jsp:invoke
						fragment="navigationContent" /></td>
				<td class="contentCell">
					<h2>
						<c:out value="${fn:trim(bodyTitle) }"></c:out>
					</h2> <jsp:doBody />
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>