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
		
		//System.out.println("Turn number = "+game.numTurn());
		System.out.println("Last move corner ?= "+game.isCorner(game.lastMove) );
		System.out.println("Last move side ?= "+game.isSide(game.lastMove) );
		
		if(game.numTurn() <= 3 && difficulty >= 2){
			//set up the first couple of moves
			
			move = game.getWinningMove(1);
			
			if(move[0] != null){
				return move;
			}
			
			if(board.isValidMove("row2", "1")){
				//always take the center if it's free
				move[0] = "row2";
				move[1] = "1";
				return move;
			}
			
			else if (board.tiles.get("row2").get(1) == 1 && game.numTurn() <= 1){
				//opponent has the center
				return game.getCorner();
			}
			
			if( (game.isCorner(game.lastMove) || game.isSide(game.lastMove)) && difficulty == 3 ){
				return game.getOppositeCorner();
			}
			
		}
		
		if(difficulty >= 2){
			String[] m = new String[2];
			//hard comp
			//move = hardMove();
			//System.out.println("Tough computer move "+move[0]+" "+move[1]);
			m = game.getWinningMove(2);
			if(m[0] == null){
				m = game.getWinningMove(1);
			}
			System.out.println("move = "+m[0]+" "+m[1]);
			if(m[0] != null && board.isValidMove(m[0], m[1])){
				return m;
			}
		}
		
		//easy computer
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
	
		
	
}
