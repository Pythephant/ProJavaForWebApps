<%@ page import="java.util.Map"%>

<html>
<head>
<title>Browse the Product</title>
</head>
<body>
	<h2>Product List</h2>
	<a href="<c:url value="/shop?action=viewCart" />">View The Cart</a><br />
	<%
		@SuppressWarnings("unchecked")
		Map<Integer, String> products = (Map<Integer, String>) request.getAttribute("products");
		for (int id : products.keySet()) {
	%>
			*<%=products.get(id)%><a href="<c:url value="/shop"><c:param name="action" value="addToCart"/><c:param name="productId" value="<%=Integer.toString(id) %>"/></c:url>"> add to cart </a>
			<br />
	<%
		}
	%>
</body>
</html>