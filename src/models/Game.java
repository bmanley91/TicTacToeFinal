package models;

import java.util.ArrayList;
import java.util.List;

public class Game {

	public GameBoard board;
	public int playersTurn = 1 + (int)(Math.random() * ((2 - 1) + 1));
	public List<Player> players; 
	public int winnerId = 0;
	public boolean isTie = false;
	public Computer comp;
	
	
	public Game(List<Player> players) {
		this.board = new GameBoard();
		this.players = players;
		comp = new Computer(0, board);
		
	}

	public void takeTurn(String xChoice, String yChoice, int playersTurn2) {
		System.out.println(xChoice+", "+yChoice);
		if(this.playersTurn == playersTurn2) {
			board.setPlayerChoice(xChoice,yChoice,playersTurn);
			if (playersTurn == 1)
				playersTurn = 2;
			else
				playersTurn = 1;
			checkForWinner();
			checkForTie();
		}
		else
			System.out.println("Wrong turn!");
	}

	public boolean checkForWinner() {
		return checkAllRows() || checkAllCols() || checkDiagonals();
	}
	
	public boolean checkForTie() {
		if(checkRow1ForTie() && checkRow2ForTie() && checkRow3ForTie())
			isTie = true;
		return isTie;
	}
	
	
	private boolean checkAllCols() {
		return checkCol1() || checkCol2() || checkCol3();
	}
	private boolean checkDiagonals() {
		return checkUpHillDiagonal() || checkDownHillDiagonal();
	}

	private boolean checkAllRows() {
		return checkRow1() || checkRow2() || checkRow3();
	}

	public Player getPlayer1() {
		return players.get(0);
	}
	public Player getPlayer2() {
		return players.get(1);
	}
	
	public Player getCurrentPlayer() {
		return players.get(playersTurn-1);
	}
	
	public boolean checkRow1() {
		return checkRowForWinner(board.getRow1());
	}
	public boolean checkRow2() {
		return checkRowForWinner(board.getRow2());
	}
	public boolean checkRow3() {
		return checkRowForWinner(board.getRow3());
	}
	public boolean checkRow1ForTie() {
		return checkRowForTie(board.getRow1());
	}
	public boolean checkRow2ForTie() {
		return checkRowForTie(board.getRow2());
	}
	public boolean checkRow3ForTie() {
		return checkRowForTie(board.getRow3());
	}
	public boolean checkDownHillDiagonal() {
		return checkRowForWinner(board.getDownHillDiagonal());
	}

	public boolean checkUpHillDiagonal() {
		return checkRowForWinner(board.getUpHillDiagonal());
	}
	public boolean checkCol1() {
		return checkRowForWinner(board.getCol1());
	}
	public boolean checkCol2() {
		return checkRowForWinner(board.getCol2());
	}
	public boolean checkCol3() {
		return checkRowForWinner(board.getCol3());
	}
	public boolean checkRowForWinner(ArrayList<Integer> row) {
		Integer currentTileInRow = null;
		for(Integer i:row) {
			if(i == 0)
				return false;
			else if(currentTileInRow == null)
				currentTileInRow = i;
			else if(currentTileInRow != i)
				return false;
		}
		winnerId = currentTileInRow;
		return true;
	}
	
	public boolean checkRowForTie(ArrayList<Integer> row) {
		if (winnerId != 0)
			return false;
		for(Integer i:row) 
			if(i == 0)
				return false;
		return true;
	}
	
	public Player getWinner() {
		if(winnerId == 0)
			return null;
		return players.get(winnerId-1);
	}
	
	public List<String> getRow1Imgs() {
		return getRowImgs(board.getRow1());
	}
	
	public List<String> getRow2Imgs() {
		return getRowImgs(board.getRow2());
	}
	
	public List<String> getRow3Imgs() {
		return getRowImgs(board.getRow3());
	}
	
	public List<String> getRowImgs(List<Integer> row) {
		List <String>imgLinks = new ArrayList<String>();
		for(Integer i: row) {
			if(i == 0)
				imgLinks.add("");
			else if(i ==1)
				imgLinks.add(getPlayer1Img());
			else
				imgLinks.add(getPlayer2Img());
		}
		return imgLinks;
	}

	public String getPlayer2Img() {
		//return "O";
		return "/TicTacToe/resources/images/blueO.png";
	}

	public String getPlayer1Img() {
		//return "X";
		return "/TicTacToe/resources/images/redX.png";
	}

	public boolean isWinner() {
		return winnerId != 0;
	}
	
	public boolean isOver() {
		return isWinner() || isTie;
	}
	
}
