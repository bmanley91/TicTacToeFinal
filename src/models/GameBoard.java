package models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {

	public Map<String, ArrayList<Integer>> tiles = new LinkedHashMap<String, ArrayList<Integer>>();
	
	public GameBoard() {
		ArrayList<Integer> row = new ArrayList<Integer>();
		for(int i= 0; i<3;i++)
			row.add(0);
		
		tiles.put("row1", row);
		tiles.put("row2", new ArrayList<Integer>(row));
		tiles.put("row3", new ArrayList<Integer>(row));
	}
	
	public boolean isValidMove(String xChoice, String yChoice){
		
		//boolean validMove = false;
		//ArrayList<Integer> row = new ArrayList<Integer>();
		System.out.println(xChoice+", "+yChoice);
		
		if(xChoice.equals("row1")){
			//row = getRow1();
			//System.out.println("Valid row1");
			
			if(yChoice.equals("0")){
				return getRow1().get(0) == 0;
			}
			else if(yChoice.equals("1")){
				return getRow1().get(1) == 0;
			}
			else if(yChoice.equals("2")){
				return getRow1().get(2) == 0;
			}
			
			
		}
		
		else if(xChoice.equals("row2")){
			//System.out.println("Valid row2");
			//row = getRow1();
			
			if(yChoice.equals("0")){
				return getRow2().get(0) == 0;
			}
			else if(yChoice.equals("1")){
				return getRow2().get(1) == 0;
			}
			else if(yChoice.equals("2")){
				return getRow2().get(2) == 0;
			}
			
		}
		
		else if(xChoice.equals("row3")){
			//System.out.println("Valid row3");
			//row = getRow1();
			
			if(yChoice.equals("0")){
				return getRow3().get(0) == 0;
			}
			else if(yChoice.equals("1")){
				return getRow3().get(1) == 0;
			}
			else if(yChoice.equals("2")){
				return getRow3().get(2) == 0;
			}
			
		}
		
		return false;
	}
	
	public ArrayList<Integer> getRow1() {
		return tiles.get("row1");
	}
	public ArrayList<Integer> getRow2() {
		return tiles.get("row2");
	}
	public ArrayList<Integer> getRow3() {
		return tiles.get("row3");
	}
	public void setPlayerChoice(String xChoice, String yChoice, int playersTurn) {
		if(isValidMove(xChoice, yChoice)){
			System.out.println("Valid Move "+xChoice+" "+yChoice);
		}
		else{
			System.out.println("Invalid move "+xChoice+" "+yChoice);
		}
		tiles.get(xChoice).set(Integer.parseInt(yChoice), playersTurn);
	}
	public ArrayList<Integer> getUpHillDiagonal() {
		ArrayList<Integer> diagonal = new ArrayList<Integer>();
		diagonal.add(getRow1().get(0));
		diagonal.add(getRow2().get(1));
		diagonal.add(getRow3().get(2));
		return diagonal;
	}
	public ArrayList<Integer> getDownHillDiagonal() {
		ArrayList<Integer> diagonal = new ArrayList<Integer>();
		diagonal.add(getRow1().get(2));
		diagonal.add(getRow2().get(1));
		diagonal.add(getRow3().get(0));
		return diagonal;
	}
	public ArrayList<Integer> getCol1() {
		ArrayList<Integer> column = new ArrayList<Integer>();
		column.add(getRow1().get(0));
		column.add(getRow2().get(0));
		column.add(getRow3().get(0));
		return column;
	}
	public ArrayList<Integer> getCol2() {
		ArrayList<Integer> column = new ArrayList<Integer>();
		column.add(getRow1().get(1));
		column.add(getRow2().get(1));
		column.add(getRow3().get(1));
		return column;
	}
	public ArrayList<Integer> getCol3() {
		ArrayList<Integer> column = new ArrayList<Integer>();
		column.add(getRow1().get(2));
		column.add(getRow2().get(2));
		column.add(getRow3().get(2));
		return column;
	}
	
	
}
