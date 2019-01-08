package com.games.tictactoe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.CloseReason;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

@ServerEndpoint("/game/ticTacToe/{gameId}/{username}")
public class TicTacToeServer {
	private static long ID = 1;
	private static Map<Long, Game> pendingGames = new Hashtable<>();
	private static Map<Long, Game> startedGames = new Hashtable<>();
	private static ObjectMapper mapper = new ObjectMapper();

	public static Map<Long, TicTacToeGame> getPendingGames() {
		Map<Long, TicTacToeGame> games = new HashMap<>();
		for (Entry<Long, Game> e : pendingGames.entrySet()) {
			games.put(e.getKey(), e.getValue().ticTacToeGame);
		}
		return games;
	}

	public static long queueGame(String player1Name) {
		long id = ID++;
		Game game = new Game();
		game.gameId = id;
		game.ticTacToeGame = new TicTacToeGame();
		game.ticTacToeGame.setPlayer1(player1Name);
		pendingGames.put(id, game);
		return id;
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("gameId") long gameId, @PathParam("username") String username) {
		try {
			Game game = startedGames.get(gameId);
			if (game != null) {
				session.close(
						new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, "this game has alreay started."));
			}
			List<String> actions = session.getRequestParameterMap().get("action");
			if (actions != null && actions.size() == 1) {
				String action = actions.get(0);
				if ("join".equals(action)) {

				} else if ("start".equals(action)) {
					game = pendingGames.get(gameId);
					game.player1 = session;
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()));
			} catch (IOException ignore) {
			}
		}
	}

	public static class Game {
		public long gameId;
		public Session player1;
		public Session player2;
		public TicTacToeGame ticTacToeGame;
	}
}
