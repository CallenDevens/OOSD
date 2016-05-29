package model;

import java.util.ArrayList;
import model.command.CommandStack;

public class Game {
	public Board board;
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	private CommandStack cs = new CommandStack();
		
	private PieceClass [] p1Pieces = {PieceClass.MAGE, PieceClass.WARRIOR, PieceClass.HUNTER};
	private PieceClass [] p2Pieces = {PieceClass.ROGUE, PieceClass.PALADIN, PieceClass.PRISST};
	
	Player p1;
	Player p2;
		
	public Game(){
		//initializeGame();
	}
	public ArrayList<Player> getPlayers(){
		return this.players;
	}
	public Player getActivePlayer(){
		return board.getActivePlayer();
	}
	
	public void initializeGame(){
		initializeBoard();
		p1 = new Player("p1");
		p2 = new Player("p2");

		this.addPlayer(p1);
		this.addPlayer(p2);

		board.setPieceforPlayer(p1,p1Pieces,0);
		board.setPieceforPlayer(p2,p2Pieces,board.getBoardWidth()-1);		
	}
	
	public void initializeBoard(){
		board = new Board();
	}
	
	public void addPlayer(Player player){
		this.players.add(player);
		board.addPlayer(player);
	}

	public int getBoardDimensionWidth(){
		return board.getBoardWidth();
	}
	
	public int getBoardDimensionHeight(){
		return board.getBoardHeight();
	}
	public Board getBoard(){
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

	public CommandStack getCommandStack() {
		return this.cs;
	}

	public void markTurnUndo() {
		board.markActivePlayerTurnUndo();
	}
}
