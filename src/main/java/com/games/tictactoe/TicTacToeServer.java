package com.games.tictactoe;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;

@ServerEndpoint("/game/ticTacToe/{gameId}/{username}")
public class TicTacToeServer {
	private static long ID = 1;
	private static Map<Long, Game> pendingGames = new Hashtable<>();
	private static Map<Long, Game> startedGames = new Hashtable<>();
	private static ObjectMapper mapper = new ObjectMapper();

	public static class Game {
		public long gameId;
		public Session player1;
		public Session player2;
		public TicTacToeGame ticTacToeGame;
	}
}
