package model;

import java.util.ArrayList;
import java.util.Map;

import utils.GameSetting;

public class Game {
	public static Board board;
	public static ArrayList<Player> players = new ArrayList<Player>();
		
	private PieceClass [] p1Pieces = {PieceClass.MAGE, PieceClass.WARRIOR, PieceClass.HUNTER};
	private PieceClass [] p2Pieces = {PieceClass.ROGUE, PieceClass.PALADIN, PieceClass.PRISST};
	
	private int mage_num =3, rougue_num=3, warrior_num=3,
			paladin_num=3, hunter_num=3, prisst_num=3;
	
	public Game(){
		//initializeGame();
	}
	
	public void initializeGame(){
		
		board = new Board();
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		
		board.setPieceforPlayer(p1.getPlayerName(),p1Pieces,0);
		board.setPieceforPlayer(p2.getPlayerName(),p2Pieces,board.getBoardWidth()-1);
		
		System.out.println(board.getBoardWidth());
	}
	public void testDisplay(){
		board.display();
	}
	
	public int getBoardDimensionWidth(){
		return board.getBoardWidth();
	}
	
	public int getBoardDimensionHeight(){
		return board.getBoardHeight();
	}
	public static Board getBoard(){
		return board;
	}
	

	public void movePieceTo(Piece p, int x, int y){
		p.moveTo(x, y);
	}

	public boolean isGameEnd() {
		return board.piecesOfOnePlayerAllRemoved();
	}

	public void startOneTurn() {
		board.switchActivePieces();
	}

	public ArrayList<Piece> getActivePieces() {
		return board.getActivePieces();
	}
}
