package models;

import java.util.*;

public class Game{

	private long id;
	public GameBoard board;
	public int playersTurn = 1 + (int)(Math.random() * ((2 - 1) + 1));
	public List<Player> players = new ArrayList<Player>();
	public int winnerId = 0;
	public boolean isTie = false;
	public Computer comp;
	
	public Game(List<Player> players) {
		this.board = new GameBoard();
		this.players = players;
	}
	
	public Game(Player p, int difficulty) {
		this.board = new GameBoard();
		this.players.add(p);
		comp = new Computer(difficulty, this.board, this);
		this.players.add(comp);
	}
	

	public Game(Player user, Player friend) {
		this.board = new GameBoard();
		this.players = new ArrayList<Player>();
		this.players.add(user);
		this.players.add(friend);
	}
	
	public Game(Player p1, Player p2, Map<String, ArrayList<Integer>> tiles, int playersTurn, long gameId) {
		this.players = new ArrayList<Player>();
		this.players.add(p1);
		this.players.add(p2);
		this.board = new GameBoard(tiles);
		this.playersTurn = playersTurn;
		this.id = gameId;
	}

	public Game(Player user, String player2Name) {
		this.players = new ArrayList<Player>();
		this.players.add(user);
		Player tempPlayer = new Player(player2Name);
		this.players.add(tempPlayer);
		this.board = new GameBoard();
	}

	public void takeTurn(String xChoice, String yChoice, int playersTurn2) {
		
		String move[] = new String[2];
		
		if(isComputer()){
			move = comp.compTurn();
			xChoice = move[0];
			yChoice = move[1];
		}
		
		if(this.playersTurn == playersTurn2) {
			board.setPlayerChoice(xChoice,yChoice,playersTurn);
			this.getCurrentPlayer().setLastMove(xChoice, yChoice);
			
			if (playersTurn == 1)
				playersTurn = 2;
			else
				playersTurn = 1;
			checkForWinner(board);
			checkForTie(board);
			if(this.getCurrentPlayer().isComputer() && !this.isOver())
				this.takeTurn(xChoice, yChoice, playersTurn);
		}
		else
			System.out.println("Wrong turn!");
	}
	
	public boolean isCorner(String m[]){
		
		String rows[] = {"row1", "row3"};
		String cols[] = {"0", "2"};
		
		if(m[0] == null)
			return false;
		
		if(m[0].equals("row2") && m[1].equals("1")){
			//center square
			return false;
		}
		
		for(int i=0; i< rows.length; i++){
			for(int j=0; j<cols.length; j++){
				
				if( rows[i].equals(m[0]) && cols[j].equals(m[1]) ){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isSide(String m[]){
		
		if(m[0] == null)
			return false;
		
		if(m[0].equals("row2") && m[1].equals("1")){
			//center square
			return false;
		}
		
		return !isCorner(m);
	}
	
	public String[] getSide(){
		String move[] = new String[2];
		String sides[][] = { {"row1", "1"}, {"row2", "0"},
						{"row2", "2"}, {"row3", "1"} };
		
		Random rand = new Random();
		
		do{
			for(int x=0; x<10; x++){
				
				move = sides[rand.nextInt(sides.length)];
			}
		}while(!isSide(move) && !board.isValidMove(move[0], move[1]));
		return move;
	}
	
	public String[] getCorner(String[] m){
		return this.getCorner(m[0], m[1], 25);
	}
	
	private String[] getCorner(String row, String col, int i){
		String m[] = getCorner();
		if( (row.equals(m[0]) || col.equals(m[1])) && i >= 0 ){
			return this.getCorner(row, col, i-1);
		}
		
		return m;
	}
	
	public boolean isLocalGame() {
		for(Player p: this.players)
			if(p.isLocal)
				return true;
		return false;
	}
	public String[] getCorner(){
		
		String move[] = new String[2];
		Random rand = new Random();
		String rows[] = {"row1", "row3"};
		String cols[] = {"0", "2"};
		
		do{
			for(int x = 0; x < 10; x++){
				
				move[0] = rows[(rand.nextInt(rows.length))];
				move[1] = cols[(rand.nextInt(cols.length))];
			}
		}while(!board.isValidMove(move[0], move[1]));
		
		return move;
	}
	
	public String[] getOppositeCorner(String lastTurn[]){
		
		String move[] = getCorner();
		int i = 0;
		
		while(lastTurn[0].equals(move[0]) || lastTurn[1].equals(move[1]) ){
			move = getCorner();
			if(i > 10)
				break;
			i++;
		}
		
		return move;
		
	}
	
	public int numTurn(){
		
		int turn = 0;
		String[] rows = {"row1", "row2", "row3"};
		//String[] cols = {"0", "1", "2"};
		
		for(int i=0; i<rows.length; i++){
			for(int j=0; j<rows.length; j++){
				
				if( board.tiles.get(rows[i]).get(j) != 0){
					turn++;
				}
				
			}
		}
		
		return turn;
	}
	
	
	public String[] getWinningMove(int player){
		
		String[] rows = {"row1", "row2", "row3"};
		String[] cols = {"0", "1", "2"};
		String[] move = new String[2];
		GameBoard newBoard = new GameBoard(board.tiles);
		boolean valid = false;
		
		
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
					move[0] = rows[i];
					move[1] = cols[j];
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
	
	public String[] twoCorners(Player player){
		//check if the player has 2 corners that could lead to a win
		if(player.moveList.size()< 2)
			return new String[2];
		
		String c1[] = player.moveList.get(0);
		String c2[] = player.moveList.get(1);
		
		if(this.isCorner(c1) && this.isCorner(c2)){
			return this.getSide();
		}
		return new String[2];
	}
	
	public String[] twoSides(Player player){
		//checks if the other player has 2 sides that could lead to a win
		String move[] = new String[2];
		String side1[] = new String[2];
		String side2[] = new String[2];
		
		if(player.moveList.size() < 2)
			return move;//not enough moves yet
		
		for(int i=0; i<player.moveList.size(); i++){
			if(side1[0] == null && this.isSide(player.moveList.get(i)))
				side1 = player.moveList.get(i);
			if(side1[0] != null && this.isSide(player.moveList.get(i))){
				if(!player.moveList.get(i)[0].equals(side1[0]) && !player.moveList.get(i)[1].equals(side1[1]) )
					side2 = player.moveList.get(i);
			}
		}
		
		if(side1[0] == null || side1[1] == null || side2[0] == null || side2[1] == null ){
			return new String[2];
		}
		
		if( side2[0].equals("row2")){
			move[0] = side1[0];
			move[1] = side2[1];
		}
		
		else{
			move[0] = side2[0];
			move[1] = side1[1];
		}
		
		//move[0] = this.getOppositeCorner(move)[0];
		
		/*if(board.tiles.get("row2").get(0) == 1){
			move[1] = "0";
			if(board.tiles.get("row1").get(1) == 1)
				move[0] = "row1";
			else if(board.tiles.get("row3").get(1) == 1)
				move[0] = "row3";
		}
		
		else if(board.tiles.get("row2").get(2) == 1){
			move[1] = "2";
			if(board.tiles.get("row1").get(1) == 1)
				move[0] = "row1";
			else if(board.tiles.get("row3").get(1) == 1)
				move[0] = "row3";
		}
		
		else if(board.tiles.get("row1").get(1) == 1){
			move[0] = "row1";
			if(board.tiles.get("row2").get(0) == 1)
				move[1] = "0";
			else if(board.tiles.get("row2").get(2) == 1)
				move[1] = "2";
		}
		
		else if(board.tiles.get("row3").get(1) == 1){
			move[0] = "row3";
			if(board.tiles.get("row2").get(0) == 1)
				move[1] = "0";
			else if(board.tiles.get("row2").get(2) == 1)
				move[1] = "2";
		}*/
		
		return move;
	}
	
	public boolean isComputer(){
		return getCurrentPlayer().isComputer();
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
	
	public Player getOtherPlayer(){
		return players.get(0) == this.getCurrentPlayer() ? players.get(1) : players.get(0); 
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
		
		Integer currentTileInRow = null;
		for(Integer i:row) {
			if(i == 0)
				return false;
			else if(currentTileInRow == null)
				currentTileInRow = i;
			else if(currentTileInRow != i)
				return false;
		}
		System.out.println("Setting winner id:" +currentTileInRow);
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
		System.out.println("returning winner: "+winnerId);
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

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getWinnerId() {
		Player winner = getWinner();
		if (winner != null)
			return winner.getId();
		return null;
	}

	public boolean isPlayersTurn(Player p) {
		return getCurrentPlayer().getId() == p.getId();
	}
	
	public String getMsg() {
		if(!this.isOver())
			return "It's " +this.getCurrentPlayer().getName() + "'s turn";
		else if(this.isWinner())
			return this.getWinner().getName()+ " Wins!";
		else
			return "It's a Tie!";
	}
	
}
