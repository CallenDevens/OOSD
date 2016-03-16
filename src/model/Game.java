package model;

import java.util.Map;

public class Game {
	public static Board board;
	public static Player p1;
	public static Player p2;
	
	private PieceClass [] p1Pieces = {PieceClass.MAGE, PieceClass.WARRIOR, PieceClass.HUNTER};
	private PieceClass [] p2Pieces = {PieceClass.ROGUE, PieceClass.PALADIN, PieceClass.PRISST};
	
	public Game(){
		initializeGame();
	}
	
	public void initializeGame(){
		board = new Board();
		p1 = new Player("p1");
		p2 = new Player("p2");
		
		board.setPieceforPlayer(p1.getPlayerName(),p1Pieces,0);
		board.setPieceforPlayer(p2.getPlayerName(),p2Pieces,board.size-1);

		//board.setPieceforPlayer2(p2);
	}
	public void testDisplay(){
		board.display();
	}
	
	public static Board getBoard(){
		return board;
	}


}
