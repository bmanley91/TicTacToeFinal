package models;

import java.util.ArrayList;
import java.util.List;

public class Game{

	public GameBoard board;
	public int playersTurn = 1 + (int)(Math.random() * ((2 - 1) + 1));
	public List<Player> players; 
	public int winnerId = 0;
	public boolean isTie = false;
	public Computer comp;
	
	public Game(){
		
	}
	
	public Game(List<Player> players) {
		this.board = new GameBoard();
		this.players = players;
		this.comp = new Computer(1, board);
		
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
	
	public String[] getWinningMove(){
		
		String[] rows = {"row1", "row2", "row3"};
		String[] cols = {"0", "1", "2"};
		String[] move = new String[2];
		GameBoard newBoard = board;
		boolean winner = false;
		
		move[0] = rows[0];
		move[1] = cols[0];
		if(newBoard == null){
			System.out.println("game is null");
		}
		for(int i=0; i<rows.length; i++){
			for(int j=0; j<cols.length; j++){
				
				if(newBoard.isValidMove(rows[i], cols[j]))
					newBoard.setPlayerChoice(rows[i], cols[j], playersTurn);
				
				/*if(newBoard.checkForWinner()){
					System.out.println("Col1, "+ j+ " "+ newBoard.getCol1().get(j) );
					System.out.println("Col2, "+ j+ " "+ newBoard.getCol2().get(j) );
					System.out.println("Col3, "+ j+ " "+ newBoard.getCol3().get(j) );
					move[0] = rows[i];
					move[1] = cols[j];
					return move;
					
				}*/
				
				else{
					newBoard = board;
				}
				
			}
		}
		
		
		return move;
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
		return "https://elasticbeanstalk-us-east-1-920081316738.s3.amazonaws.com/418Resources/blueO.png";
	}

	public String getPlayer1Img() {
		//return "X";
		return "https://elasticbeanstalk-us-east-1-920081316738.s3.amazonaws.com/418Resources/redX.png";
	}

	public boolean isWinner() {
		return winnerId != 0;
	}
	
	public boolean isOver() {
		return isWinner() || isTie;
	}
	
}
