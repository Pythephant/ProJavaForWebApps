package com.shopcart;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "shopCartServlet", urlPatterns = "/shop")
public class ShopCartServlet extends HttpServlet {
	Map<Integer, String> products;

	public ShopCartServlet() {
		products = new HashMap<>();
		products.put(1, "banana");
		products.put(2, "wcao");
		products.put(3, "lstan");
		products.put(4, "hyang");
		products.put(5, "yingwang");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String action = req.getParameter("action");
		if (action == null)
			action = "browse";
		switch (action) {
		case "addToCart":
			this.addToCart(req, resp);
			break;
		case "viewCart":
			this.viewCart(req, resp);
			break;
		case "emptyCart":
			this.emptyCart(req, resp);
			break;
		case "browser":
		default:
			this.browse(req, resp);
			break;
		}

	}

	private void addToCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		int productId;
		try {
			productId = Integer.parseInt(req.getParameter("productId"));
		} catch (Exception e) {
			resp.sendRedirect("shop");
			return;
		}

		HttpSession session = req.getSession();
		if (session.getAttribute("cart") == null)
			session.setAttribute("cart", new HashMap<Integer, Integer>());
		Map<Integer, Integer> cart = (Map<Integer, Integer>) session.getAttribute("cart");
		if (cart.containsKey(productId) == false)
			cart.put(productId, 0);
		cart.put(productId, cart.get(productId) + 1);
		resp.sendRedirect("shop?action=viewCart");
	}

	private void viewCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("products", this.products);
		req.getRequestDispatcher("/WEB-INF/jsp/shopcart/viewCart.jsp").forward(req, resp);
	}

	private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("products", this.products);
		req.getRequestDispatcher("/WEB-INF/jsp/shopcart/browse.jsp").forward(req, resp);
	}

	private void emptyCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.getSession().removeAttribute("cart");
		resp.sendRedirect("shop?action=viewCart");
	}
}
