package models;

import java.util.*;

public class Computer extends Player {
	
	public int difficulty = super.diff;
	String str;
	private GameBoard board;
	public Game game;
	private String[] move = new String[2];
	private String[] rows = {"row1", "row2", "row3"};
	private String[] cols = {"0", "1", "2"};

	
	public Computer(int diff, GameBoard gb){
		this.difficulty = diff;
		this.board = gb;
	}
	
	public String[] compTurn(){
		
		Random rand = new Random();
		//int x;
		
		if(difficulty >= 2){
			String[] m = new String[2];
			//hard comp
			//move = hardMove();
			//System.out.println("Tough computer move "+move[0]+" "+move[1]);
			//m = game.getWinningMove();
			//System.out.println("move = "+m[0]+" "+m[1]);
		}
		
		if(board.isValidMove("row2", "1")){
			move[0] = "row2";
			move[1] = "1";
			return move;
		}
		for(int i=0; i<25; i++){
			do{
				//move[0] = "row";
				move[0] = rows[(rand.nextInt(3))];
				move[1] = cols[(rand.nextInt(3))];
				//System.out.println("compturn "+move[0]+", "+move[1]);
			}while( !board.isValidMove(move[0], move[1]));
		}
		//System.out.println("win "+(board.getWinner()==null));
		
		//System.out.println("compturn "+move[0]+", "+move[1]);
		
		
		return move;
	}
	
	private boolean getPlayer(String x, String y){
		return board.tiles.get(x).get(Integer.parseInt(y)) == 2;
	}
	
	private boolean winMove(){
		
		
		for(int x = 0; x < 3; x++){
			for(int y = 0; y < 3; y++){
				if(board.isValidMove(rows[x], cols[y])){
					board.tiles.get(rows[x]).set(y, 2);
					System.out.println("win move "+move[0]+", "+move[1]);
					if(game.checkForWinner()){
							if( (game.getWinner() != null) && (game.getWinner().isComp) ){
								board.tiles.get(rows[x]).set(y, 0);
								move[0] = rows[x];
								move[1] = cols[y];
								System.out.println("win move2 "+move[0]+", "+move[1]);
								return true;
							}
					}//*/
					board.tiles.get(rows[x]).set(y, 0);
				}
			}
		}
		return false;
		
	}
	
	private boolean blockMove(){
		
		//System.out.println("compturn "+move[0]+", "+move[1]);
		for(int x = 0; x < 3; x++){
			for(int y = 0; y < 3; y++){
				if(board.isValidMove(rows[x], cols[y])){
					board.tiles.get(rows[x]).set(y, 1);
					if( (game.checkForWinner()) && !(game.getWinner().isComp) ){
						board.tiles.get(rows[x]).set(y, 0);
						move[0] = rows[x];
						move[1] = cols[y];
						return true;
					}
					board.tiles.get(rows[x]).set(y, 0);
				}
			}
		}
		return false;
		
	}
	
	private String[] hardMove(){
		
		
		if(winMove()){
			
			return move;
		}
		
		if(board.isValidMove("row2", "1")){
			//always take the center if it's available
			move[0] = "row2";
			move[1] = "1";
			return move;
		}
		
		return move;
	}
	
	
}
