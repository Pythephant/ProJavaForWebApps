package com.games.tictactoe;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ticTacToeServlet", urlPatterns = "/game/ticTacToe")
public class TicTacToeServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("pendingGames", TicTacToeServer.getPendingGames());
		this.view("list", req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action= req.getParameter("action");
		if("join".equals(action)) {
			
		}else if("start".equals(action)) {
			String player1Name = req.getParameter("username");
			if(player1Name == null) {
				this.list(req, resp);
			}else {
				req.setAttribute("username", player1Name);
				long gameId = TicTacToeServer.queueGame(player1Name);
				req.setAttribute("gameId", gameId);
				this.view("game", req, resp);
			}
		}else {
			this.list(req, resp);
		}
	}

	private void view(String viewName, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/game/ticTacToe/" + viewName + ".jsp").forward(req, resp);
	}
	private void list(HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        response.sendRedirect(response.encodeRedirectURL(
                request.getContextPath() + "/ticTacToe"
        ));
    }
}
