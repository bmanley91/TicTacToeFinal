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
			checkForWinner(board);
			checkForTie(board);
		}
		else
			System.out.println("Wrong turn!");
	}
	
	public String[] getWinningMove(int player){
		
		String[] rows = {"row1", "row2", "row3"};
		String[] cols = {"0", "1", "2"};
		String[] move = new String[2];
		GameBoard newBoard = new GameBoard(board.tiles);
		boolean winner = false;
		boolean valid = false;
		
		//move[0] = rows[0];
		//move[1] = cols[0];
		//if(!newBoard.equals(board)){
			System.out.println("new board "+newBoard.toString());
			System.out.println("board "+board.toString());
		//}
		
		
		for(int i=0; i<rows.length; i++){
			for(int j=0; j<cols.length; j++){
				
				if(newBoard.isValidMove(rows[i], cols[j])){
					newBoard.tiles.get(rows[i]).set(Integer.parseInt(cols[j]), player);
					valid = true;
				}
				
				if(checkForWinner(newBoard)){
					winnerId = 0;
					isTie = false;
					newBoard.tiles.get(rows[i]).set(Integer.parseInt(cols[j]), 0);
					
					System.out.println("Col1, "+ j+ " "+ newBoard.getCol1().get(j) );
					System.out.println("Col2, "+ j+ " "+ newBoard.getCol2().get(j) );
					System.out.println("Col3, "+ j+ " "+ newBoard.getCol3().get(j) );
					move[0] = rows[i];
					move[1] = cols[j];
					//newBoard.tiles.get(rows[i]).set(Integer.parseInt(cols[j]), 0);
					return move;
					
				}
				
				if(valid){
					newBoard.tiles.get(rows[i]).set(Integer.parseInt(cols[j]), 0);
					valid = false;
				}
				
			}
		}
		
		
		return move;
	}

	public boolean checkForWinner(GameBoard gb) {
		return checkAllRows(gb) || checkAllCols(gb) || checkDiagonals(gb);
	}
	
	public boolean checkForTie(GameBoard gb) {
		if(checkRow1ForTie(gb) && checkRow2ForTie(gb) && checkRow3ForTie(gb))
			isTie = true;
		return isTie;
	}
	
	
	private boolean checkAllCols(GameBoard gb) {
		return checkCol1(gb) || checkCol2(gb) || checkCol3(gb);
	}
	private boolean checkDiagonals(GameBoard gb) {
		return checkUpHillDiagonal(gb) || checkDownHillDiagonal(gb);
	}

	private boolean checkAllRows(GameBoard gb) {
		return checkRow1(gb) || checkRow2(gb) || checkRow3(gb);
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
	
	public boolean checkRow1(GameBoard gb) {
		return checkRowForWinner(gb.getRow1());
	}
	public boolean checkRow2(GameBoard gb) {
		return checkRowForWinner(gb.getRow2());
	}
	public boolean checkRow3(GameBoard gb) {
		return checkRowForWinner(gb.getRow3());
	}
	public boolean checkRow1ForTie(GameBoard gb) {
		return checkRowForTie(gb.getRow1());
	}
	public boolean checkRow2ForTie(GameBoard gb) {
		return checkRowForTie(gb.getRow2());
	}
	public boolean checkRow3ForTie(GameBoard gb) {
		return checkRowForTie(gb.getRow3());
	}
	public boolean checkDownHillDiagonal(GameBoard gb) {
		return checkRowForWinner(gb.getDownHillDiagonal());
	}

	public boolean checkUpHillDiagonal(GameBoard gb) {
		return checkRowForWinner(gb.getUpHillDiagonal());
	}
	public boolean checkCol1(GameBoard gb) {
		return checkRowForWinner(gb.getCol1());
	}
	public boolean checkCol2(GameBoard gb) {
		return checkRowForWinner(gb.getCol2());
	}
	public boolean checkCol3(GameBoard gb) {
		return checkRowForWinner(gb.getCol3());
	}
	public boolean checkRowForWinner(ArrayList<Integer> row) {
		//return this.board.checkForWinner(row);
		
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
