<%--@elvariable id="chatRooms" type=""--%>
<template:basic htmlTitle="Support Chat" bodyTitle="Support Chat Rooms">
	<c:choose>
		<c:when test="${fn:length(chatRooms) == 0 }">
			<i>There are on support chat rooms</i>
		</c:when>
		<c:otherwise>
			Click on a chat room to start:<br />
			<br />
			<c:forEach items="${chatRooms }" var="entry">
				<a href="javascript:void 0;" onclick="join(${entry.value.roomId})">
					${entry.value.roomId } : ${entry.value.customerUsername }</a>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	<script type="text/javascript" language="javascript">
		var join = function(id){
			postInvisibleForm('<c:url value="/chat" />',{action:'join', roomId:id});
		};
	</script>

</template:basic>