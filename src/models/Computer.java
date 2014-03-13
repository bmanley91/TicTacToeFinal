package models;

import java.util.*;

public class Computer {
	
	private int difficulty = 0;
	String str;
	private GameBoard board;

	
	public Computer(int diff, GameBoard gb){
		this.difficulty = diff;
		this.board = gb;
	}
	
	public String[] compTurn(){
		String[] move = {"row2", "1"};
		Random rand = new Random();
		//int x;
		
		if(board.isValidMove("row2", "1")){
			return move;
		}
		
		do{
			//move[0] = "row";
			move[0] = "row" + Integer.toString(rand.nextInt(3)+1);
			move[1] = Integer.toString(rand.nextInt(3));
			
		}while( !board.isValidMove(move[0], move[1]));
		
		System.out.println("compturn "+move[0]+", "+move[1]);
		
		
		return move;
	}
	
	
}
