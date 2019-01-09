package com.games.tictactoe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.games.tictactoe.TicTacToeGame.Player;

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

	@OnMessage
	public void onMessage(Session session, String message, @PathParam("gameId") long gameId) {
		Game game = startedGames.get(gameId);
		boolean isPlayer1 = (session == game.player1);
		try {
			Move move = TicTacToeServer.mapper.readValue(message, Move.class);
			game.ticTacToeGame.move(isPlayer1 ? Player.Player1 : Player.Player2, move.getRow(), move.getColumn());
			this.sendJsonMessage((isPlayer1?game.player2:game.player1), game, new OpponentMadeMoveMessage(move));
			if (game.ticTacToeGame.isOver()) {
				if (game.ticTacToeGame.isDraw()) {
					this.sendJsonMessage(game.player1, game, new GameIsDrawMessage());
					this.sendJsonMessage(game.player2, game, new GameIsDrawMessage());
				} else {
					boolean isPlayer1Win = (game.ticTacToeGame.getWinner()==Player.Player1);
					this.sendJsonMessage(game.player1, game, new GameOverMessage(isPlayer1Win));
					this.sendJsonMessage(game.player2, game, new GameOverMessage(!isPlayer1Win));
				}
				game.player1.close();
				game.player2.close();
			}
		} catch (IOException e) {
			handleException(e, game);
		}
	}

	@OnClose
	public void onClose(Session session, @PathParam("gameId") long gameId) {
		Game game = TicTacToeServer.startedGames.get(gameId);
		
		
		if(game == null) {	//pending games waiting for another player;
			TicTacToeServer.pendingGames.remove(gameId);
			return;
		}else {
			//started game
			boolean isPlayer1 = (session == game.player1);
			if(!game.ticTacToeGame.isOver()) {
				this.sendJsonMessage((isPlayer1?game.player2:game.player1), game, new GameForfeitedMessage());
				game.ticTacToeGame.forfeited(isPlayer1?Player.Player1:Player.Player2);
				if(isPlayer1) {
					game.player1=null;
				}else {
					game.player2=null;
				}
			}
			if(game.player1==null && game.player2==null) {
				TicTacToeServer.startedGames.remove(gameId);
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

	public static class OpponentMadeMoveMessage extends Message {
		private final Move move;

		public OpponentMadeMoveMessage(Move move) {
			super("opponentMadeMove");
			this.move = move;
		}

		public Move getMove() {
			return move;
		}
	}

	public static class GameOverMessage extends Message {
		private final boolean winner;

		public GameOverMessage(boolean winner) {
			super("gameOver");
			this.winner = winner;
		}

		public boolean getWinner() {
			return winner;
		}
	}

	public static class GameIsDrawMessage extends Message {
		public GameIsDrawMessage() {
			super("gameIsDraw");
		}
	}
	
	public static class GameForfeitedMessage extends Message {
		public GameForfeitedMessage() {
			super("gameForfeited");
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

	public static class Move {
		private int row;
		private int column;

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getColumn() {
			return column;
		}

		public void setColumn(int column) {
			this.column = column;
		}

	}

	public static class Game {
		public long gameId;
		public Session player1;
		public Session player2;
		public TicTacToeGame ticTacToeGame;
	}
}
