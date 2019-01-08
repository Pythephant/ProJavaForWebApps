package com.games.tictactoe;

import java.util.Random;
import java.util.Scanner;

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

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public String getNextMoveBy() {
		return currentPlayer == Player.Player1 ? player1 : player2;
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

		// horizontal winner
		for (int i = 0; i < 3; i++) {
			if (grid[i][0] == null || grid[i][1] == null || grid[i][2] == null)
				draw = false;
			if (grid[i][0] != null && grid[i][0] == grid[i][1] && grid[i][1] == grid[i][2])
				return grid[i][0];
		}

		// vertical winner
		for (int i = 0; i < 3; i++) {
			if (grid[0][i] != null && grid[0][i] == grid[1][i] && grid[1][i] == grid[2][i])
				return grid[0][i];
		}

		// diagonal winner
		if (grid[0][0] != null && grid[0][0] == grid[1][1] && grid[1][1] == grid[2][2])
			return grid[0][0];
		if (grid[2][0] != null && grid[2][0] == grid[1][1] && grid[1][1] == grid[0][2])
			return grid[2][0];

		this.draw = draw;
		return null;
	}

	public enum Player {
		Player1, Player2;

		private static final Random random = new Random();

		public static Player random() {
			return random.nextBoolean() ? Player1 : Player2;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Input the Player1's username:");
		String player1 = sc.nextLine();
		System.out.print("Input the Player2's username:");
		String player2 = sc.nextLine();
		TicTacToeGame game = new TicTacToeGame();
		game.setPlayer1(player1);
		game.setPlayer2(player2);
		System.out.println("Game Started!");
		game.start();
		String curUser, moveStr;
		int row, col;
		while (!game.isOver()) {
			System.out.println("=============================");
			game.printGame();
			curUser = game.getCurrentPlayer() == Player.Player1 ? game.getPlayer1() : game.getPlayer2();
			System.out.print(curUser + " make your move(->:row,col):");
			moveStr = sc.nextLine();
			row = Integer.parseInt(moveStr.split(",")[0]);
			col = Integer.parseInt(moveStr.split(",")[1]);
			try {
				game.move(game.getCurrentPlayer(), row, col);
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				System.out.println("Please try again.");
			}
			System.out.println("=============================");
		}

		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		game.printGame();
		if (game.isDraw()) {
			System.out.println("Ooooops! It is a draw game, no one wins!");
		} else {
			System.out
					.print("Winner is:" + (game.getWinner() == Player.Player1 ? game.getPlayer1() : game.getPlayer2()));
		}
	}

	private void printGame() {
		System.out.println("Player1:" + player1 + "  vs  Player2:" + player2);
		System.out.println("Current Player:" + currentPlayer);
		System.out.println("--------------------------");
		System.out.println("row/col\t0\t1\t2");
		System.out.println("--------------------------");
		for (int i = 0; i < 3; i++) {
			System.out.print(i + "\t");
			for (int j = 0; j < 3; j++) {
				if (grid[i][j] == Player.Player1) {
					System.out.print("1\t");
				} else if (grid[i][j] == Player.Player2) {
					System.out.print("2\t");
				} else {
					System.out.print("*\t");
				}
			}
			System.out.println();
		}
		System.out.println("--------------------------");
	}
}
