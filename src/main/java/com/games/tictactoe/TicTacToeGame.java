package com.games.tictactoe;

import java.util.Random;

public class TicTacToeGame {
	private String player1;
	private String player2;
	private boolean over;
	private boolean draw;
	private Player winner;
	private Player[][] grid = new Player[3][3];
	private Player currentPlayer;

	public TicTacToeGame() {
		this.over = false;
		this.draw = false;
	}

	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public boolean isOver() {
		return over;
	}

	public boolean isDraw() {
		return draw;
	}

	public void start() {
		currentPlayer = Player.random();
	}

	public synchronized void move(Player player, int row, int col) {
		if (player != this.currentPlayer)
			throw new IllegalArgumentException("It is not your turn!");

		if (row > 2 || col > 2)
			throw new IllegalArgumentException("Row and column must be 0-3.");

		if (this.grid[row][col] != null)
			throw new IllegalArgumentException("Move already made at " + row + "," + col);

		grid[row][col] = player;
		// flip the player
		if (currentPlayer == Player.Player1)
			currentPlayer = Player.Player2;
		else
			currentPlayer = Player.Player1;
		this.winner = calWinner();
		if (getWinner() != null || isDraw()) {
			this.over = true;
		}
	}

	public Player calWinner() {
		boolean draw = true;

		// horizontal win
		for (int i = 0; i < 3; i++) {
			if (grid[i][0] == null || grid[i][1] == null || grid[i][2] == null)
				draw = false;
			if (grid[i][0] != null && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2])
				return grid[i][0];
		}

		return null;
	}

	public enum Player {
		Player1, Player2;

		private static final Random random = new Random();

		public static Player random() {
			return random.nextBoolean() ? Player1 : Player2;
		}
	}
}
