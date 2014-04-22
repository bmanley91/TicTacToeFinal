package models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameBoard{

	public Map<String, ArrayList<Integer>> tiles = new LinkedHashMap<String, ArrayList<Integer>>();
	
	public GameBoard() {
		ArrayList<Integer> row = new ArrayList<Integer>();
		for(int i= 0; i<3;i++)
			row.add(0);
		
		tiles.put("row1", row);
		tiles.put("row2", new ArrayList<Integer>(row));
		tiles.put("row3", new ArrayList<Integer>(row));
	}
	
	public GameBoard(Map<String, ArrayList<Integer>> t){
		this.tiles = t;
	}
	
	public boolean isValidMove(String s[]){
		if(s.length!=2)
			return false;
		return this.isValidMove(s[0], s[1]);
	}
	
	public boolean isValidMove(String xChoice, String yChoice){
		if(xChoice == null || yChoice == null)
			return false;
		return tiles.get(xChoice).get(Integer.parseInt(yChoice)) == 0;
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

	//public boolean checkForWinner(ArrayList<Integer> row) {
		
		
	//}
	
	
}
