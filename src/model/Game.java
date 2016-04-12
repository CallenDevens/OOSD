package model;

import java.util.ArrayList;
import java.util.Map;

public class Game {
	public static Board board;
	public static ArrayList<Player> players = new ArrayList<Player>();
	//TODO: as an arrayList
	
	private PieceClass [] p1Pieces = {PieceClass.MAGE, PieceClass.WARRIOR, PieceClass.HUNTER};
	private PieceClass [] p2Pieces = {PieceClass.ROGUE, PieceClass.PALADIN, PieceClass.PRISST};
	
	public Game(){
		initializeGame();
	}
	
	public void initializeGame(){
		board = Board.getInstance();
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		
		board.setPieceforPlayer(p1.getPlayerName(),p1Pieces,0);
		board.setPieceforPlayer(p2.getPlayerName(),p2Pieces,board.size-1);
	}
	public void testDisplay(){
		board.display();
	}
	
	public static Board getBoard(){
		return board;
	}
	

	public void movePieceTo(Piece p, int x, int y){
		p.moveTo(x, y);
	}

	public boolean isGameEnd() {
		// TODO Auto-generated method stub
		return board.piecesOfOnePlayerAllRemoved();
	}

	public void startOneTurn() {
		board.switchActivePieces();
	}

	public ArrayList<Piece> getActivePieces() {
		// TODO Auto-generated method stub
		return board.getActivePieces();
	}
}
