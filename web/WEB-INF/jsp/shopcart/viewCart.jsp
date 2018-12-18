<%@ page contentType="text/html; charset=UTF-8" import="java.util.Map"%>
<html>
<head>
<title>View the Cart</title>
</head>
<body>
	<h2>View The Current Cart</h2>
	<a href="<c:url value="/shop"/>">Product List</a> <a href="<c:url value="/shop?action=emptyCart"/>">Empty the Cart</a>
	<br />
	<%
		Map<Integer, String> products = (Map<Integer, String>) request.getAttribute("products");
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
		if (cart == null || cart.size() == 0)
			out.println("The Current Cart is Empty");
		else {
			for (int id : cart.keySet()) {
				out.println("*" + products.get(id) + " - 数量:" + cart.get(id) + "<br/>");
			}
		}
	%>

</body>
</html>