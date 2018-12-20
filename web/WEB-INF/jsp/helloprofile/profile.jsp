<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%--@elvariable id="user"	type="com.userprofile.User" --%>
<html>
<head>
<title>User Profile</title>
</head>
<body>
	<br> User Id: ${user.getUserId() }
	<br> User name: ${user.username }
	<br> Full name: ${fn:escapeXml(user.lastName) += ', ' += fn:escapeXml(user.firstName) }
	<br>
	<br>
	<b>Permissions (${fn:length(user.permissions) })</b>
	<br> User:${user.permissions["user"] }
	<br> Modelator:${user.permissions["modelator"] }
	<br> Admin:${user.permissions["admin"] }

</body>
</html>