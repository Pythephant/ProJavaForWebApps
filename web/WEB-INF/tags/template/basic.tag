<%@ tag body-content="scriptless" trimDirectiveWhitespaces="false"%>
<%@ attribute name="htmlTitle" type="java.lang.String"
	rtexprvalue="true" required="true"%>
<%@ attribute name="bodyTitle" type="java.lang.String"
	rtexprvalue="true" required="true"%>
<%@ attribute name="extraHeadContent" fragment="true" required="false"%>
<%@ attribute name="extraNavigationContent" fragment="true"
	required="false"%>
<%@ include file="/WEB-INF/jsp/base.jspf"%>
<template:main htmlTitle="${htmlTitle }" bodyTitle="${bodyTitle }">
	<jsp:attribute name="headContent">
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
		<jsp:invoke fragment="extraHeadContent" />
	</jsp:attribute>
	<jsp:attribute name="navigationContent">
		<a href="<c:url value="/tickets"/>">list tickets</a>
		<br>
		<a
			href="<c:url value="/tickets">
					<c:param name="action" value="create" />
				</c:url>">create ticket</a>
		<br>
		<a href="<c:url value="/sessions"/>">
			sessions
		</a>
		<br />
		<a href="javascript:void 0" onclick="newChat()">Create ChatRoom</a>
		<br/>
		<a
			href="<c:url value="/chat" >
					<c:param name="action" value="list"/>
				  </c:url>">
		View CurrentChat</a>
		<br>
		<c:choose>
			<c:when test="${username!=null }">
				<a href="<c:url value="/login?logout" />">Logout</a>
			</c:when>
			<c:otherwise>
				<a href="<c:url value="/login"/>">Login</a>
			</c:otherwise>
		</c:choose>
		<jsp:invoke fragment="extraNavigationContent"></jsp:invoke>
	</jsp:attribute>
	<jsp:body>
        <jsp:doBody />
    </jsp:body>
	
</template:main>
