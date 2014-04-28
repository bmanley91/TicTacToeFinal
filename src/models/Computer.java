package models;

import java.util.*;

public class Computer extends Player {
	
	String str;
	public GameBoard board;
	public Game game;
	private String[] move = new String[2];
	private String[] rows = {"row1", "row2", "row3"};
	private String[] cols = {"0", "1", "2"};

	
	public Computer(int d, GameBoard gb, Game g){
		diff = d;
		this.game = g;
		this.board = gb;
		super.username = "Computer";
		super.setId(2);
		this.isLocal = true;
	}
	
	public Computer(int d){
		this.isLocal = true;
		diff = d;
		super.username = "Computer";
		super.setId(2);
	}
	
	public String[] compTurn(){
		
		if(diff == 3)
			return this.hardTurn();
		else if(diff == 2)
			return this.medTurn();
		return this.easyTurn();
	}
	
	private String[] easyTurn(){
		
		Random rand = new Random();
		
		move = this.winMove();
		if(board.isValidMove(move))
			return move;
		for(int i=0; i<25; i++){
			do{
				move[0] = rows[(rand.nextInt(3))];
				move[1] = cols[(rand.nextInt(3))];
			}while( !board.isValidMove(move[0], move[1]));
		}
		return move;
		
	}
	
	private String[] medTurn(){
		System.out.println("med turn");
		
		Player human = game.getOtherPlayer();
		
		move = this.winMove();
		System.out.println("winmove: "+move[0]+", "+move[1]);
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
		
		if( game.numTurn() == 3 && game.isSide(human.getLastMove())){
			return game.getCorner(human.getLastMove());
		}
		
		return this.easyTurn();
	}
	
	private String[] hardTurn(){
		
		System.out.println("Hard diff");
		
		Player human = game.getOtherPlayer();
		
		move = this.winMove();
		//block opponent's winning move
		//or make the winning move
		if(board.isValidMove(move)){
			return move;
		}
		
		
		if(game.numTurn()%2 == 0){//computer went first
			if(game.numTurn() == 0)
				return game.getCorner();
			else if(game.isCorner(human.moveList.get(0))){
				//their first move was a corner
				return game.getCorner();
			}
			else if(game.isSide(human.moveList.get(0))){
				//their first move was a side
				if(board.isValidMove("row2", "1")){
					move[0] = "row2";
					move[1] = "1";
					return move;
				}
				else if(game.numTurn() <= 4){
					return game.getCorner(human.moveList.get(0));
				}
				else return game.getCorner();
					
			}
			else{
				//their first move was the center
				if(game.numTurn() == 2)
					return game.getOppositeCorner(this.moveList.get(0));
				else if(game.isCorner(human.getLastMove()))
					return game.getCorner();
			}
		}
		
		else{//other player went first
			
			move = this.blockFork(human);
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
	
	private String[] blockFork(Player player){
		String m[] = new String[2];
		
		m = game.twoSides(player);
		
		if(board.isValidMove(m))
			return m;
		else if(m[0] != null){
			m = game.getSide();
			if(m[0] != null && board.isValidMove(m))
				return m;
		}
		
		m = game.twoCorners(player);
		
		if(board.isValidMove(m))
			return m;
		
		if(player.moveList.size() ==2 && (game.isSide(player.moveList.get(0)) || game.isSide(player.moveList.get(1))) ){
			if( game.isCorner(player.moveList.get(0)) || game.isCorner(player.moveList.get(1)) ){
				do{
					m = game.getSide();
				}while(m[0].equals(player.getLastMove()[0]) || m[0].equals(player.moveList.get(0)[0]) );
				if(board.isValidMove(m))
					return m;
			}
		}
		
		return new String[2];
	}
	
	
	@Override
	public boolean isComputer() {
		return this instanceof Computer;
	}
	
}
