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
import com.wrox.TicTacToeServer;
import com.wrox.TicTacToeServer.Game;
import com.wrox.TicTacToeServer.Message;

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
				return;
			}
			List<String> actions = session.getRequestParameterMap().get("action");
			if (actions != null && actions.size() == 1) {
				String action = actions.get(0);
				if ("join".equals(action)) {
					game = pendingGames.get(gameId);
					game.player2 = session;
					pendingGames.remove(gameId);
					startedGames.put(gameId, game);
					game.ticTacToeGame.setPlayer2(username);
					game.ticTacToeGame.start();
					this.sendJsonMessage(game.player1, game, new GameStartedMessage(game.ticTacToeGame));
					this.sendJsonMessage(game.player2, game, new GameStartedMessage(game.ticTacToeGame));
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

	public static abstract class Message {
		private final String action;

		public Message(String action) {
			this.action = action;
		}

		public String getAction() {
			return this.action;
		}
	}

	public static class GameStartedMessage extends Message {
		private final TicTacToeGame game;

		public GameStartedMessage(TicTacToeGame game) {
			super("gameStarted");
			this.game = game;
		}

		public TicTacToeGame getGame() {
			return game;
		}
	}

	private void sendJsonMessage(Session session, Game game, Message message) {
		try {
			session.getBasicRemote().sendText(TicTacToeServer.mapper.writeValueAsString(message));
		} catch (IOException e) {
			this.handleException(e, game);
		}
	}

	private void handleException(Throwable t, Game game) {
		t.printStackTrace();
		String message = t.toString();
		try {
			game.player1.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, message));
		} catch (IOException ignore) {
		}
		try {
			game.player2.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, message));
		} catch (IOException ignore) {
		}
	}

	public static class Game {
		public long gameId;
		public Session player1;
		public Session player2;
		public TicTacToeGame ticTacToeGame;
	}
}
