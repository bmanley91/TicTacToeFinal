package models;

import java.util.*;

public class Computer extends Player {
	
	//public int difficulty = super.diff;
	String str;
	public GameBoard board;
	public Game game;
	private String[] move = new String[2];
	private String[] rows = {"row1", "row2", "row3"};
	private String[] cols = {"0", "1", "2"};

	
	public Computer(int d, GameBoard gb, Game g){
		//this.difficulty = diff;
		diff = d;
		this.game = g;
		this.board = gb;
		super.username = "Computer";
		super.setId(2);
	}
	
	public Computer(int d){
		diff = d;
		super.username = "Computer";
		super.setId(2);
	}
	
	public String[] compTurn(){
		
		Random rand = new Random();
		Player human = game.getOtherPlayer();
		//int x;
		
		if(diff >= 2){
			//set up the first couple of moves
			//if(diff == 2)
				//return this.medTurn();
			String m[] = new String[2];
			for(int i =0; i< human.moveList.size(); i++){
				m = human.moveList.get(i);
				System.out.println("move list!!!: "+m[0]+", "+m[1]+": "+m.toString());
			}
			
			
			if(diff == 3)
				return this.hardTurn();
			
			return this.medTurn();
			
		}
		
		
		return this.easyTurn();
	}
	
	public void gameMap(){
		////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
	
	private String[] easyTurn(){
		
		Random rand = new Random();
		
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
	
	private String[] medTurn(){
		//move = game.getWinningMove(1);
		Player human = game.getOtherPlayer();
		
		move = this.winMove();
		System.out.println("winmove: "+move[0]+", "+move[1]);
		if(move[0] != null){
			return move;
		}
		
		/*move = game.getWinningMove(2);
		if(move[0] == null){
			move = game.getWinningMove(1);
		}
		System.out.println("move = "+move[0]+" "+move[1]);
		if(move[0] != null && board.isValidMove(move[0], move[1])){
			return move;
		}*/
		
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
		
		if( game.numTurn() == 3 && game.isSide(human.getLastMove())){
			return game.getCorner(human.getLastMove());
		}
		
		return this.easyTurn();
	}
	
	private String[] hardTurn(){
		
		Player human = game.getOtherPlayer();
		
		move = this.winMove();
		//block opponent's winning move
		//or make the winning move
		if(move[0] != null){
			return move;
		}
		
		
		
		if(game.numTurn()%2 == 0){//computer went first
			if(game.numTurn() == 0)
				return game.getCorner();
			else if(game.isCorner(human.moveList.get(0))){
				//their first move was a corner
				System.out.println("you played a corner lol");
				return game.getCorner();
			}
			else if(game.isSide(human.moveList.get(0))){
				//their first move was a side
				System.out.println("you played a side lol");
				if(board.isValidMove("row2", "1")){
					move[0] = "row2";
					move[1] = "1";
					return move;
				}
				else if(game.numTurn() <= 4){
					System.out.println("watata");
					return game.getCorner(human.moveList.get(0));
				}
				else return game.getCorner();
					
			}
			else{
				//their first move was the center
				System.out.println("you played the center lol");
				if(game.numTurn() == 2)
					return game.getOppositeCorner(this.moveList.get(0));
				else if(game.isCorner(human.getLastMove()))
					return game.getCorner();
			}
		}
		
		else{//other player went first
			move = game.twoSides(game.getOtherPlayer());
			//System.out.println("play in corner: "+move[0]+", "+move[1]);
			
			if(board.isValidMove(move))
				return move;
			else if(move[0] != null){
				move = game.getSide();
				if(move[0] != null && board.isValidMove(move))
					return move;
			}
			
			move = game.twoCorners(game.getOtherPlayer());
			
			if(board.isValidMove(move))
				return move;
			
			if(board.isValidMove("row2", "1")){
				//take the center if it's free
				move[0] = "row2";
				move[1] = "1";
				return move;
			}
			else if(game.isCorner(human.moveList.get(0)) && game.numTurn() >= 1)
				return game.getOppositeCorner(human.moveList.get(0));
				//block the possibility of them creating a fork
				//going for the tie here
			
			
			
		}
		
		
		
		return this.medTurn();
	}
	
	private String[] winMove(){
		String m[] = new String[2];
		
		m = game.getWinningMove(2);//winning move for comp
		if(m[0] == null){
			//block opponent's winning move
			m = game.getWinningMove(1);
		}
		//System.out.println("move = "+move[0]+" "+move[1]);
		if(m[0] != null && board.isValidMove(m[0], m[1])){
			return m;
		}
		return m;
	}
	
	
	@Override
	public boolean isComputer() {
		System.out.println("Overriding");
		return this instanceof Computer;
	}
	
}
