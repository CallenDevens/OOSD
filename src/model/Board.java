/* model class for game board
 * implemented by Singleton design pattern
 * */

package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utils.GameSetting;

public class Board {
	private final int num = 3;
	private Square[][] squares;
	
	private static Map<String, ArrayList<Piece>> playerPieces;
	private static ArrayList<Piece> activePlayerPieces = null;
	
	private int turnCount = 0;
	
	private GameSetting settings = GameSetting.getInstance();
	
	private int boardHeight;
	private int boardWidth;

	/*
	private int boardHeight = settings.getDimensionHeight();
	private int boardWidth = settings.getDimensionWidth();
	*/

	//use singleton pattern to initialize board instance
	//for there is only one board in a game
	
	public Board(){
		
		boardHeight = settings.getDimensionHeight();
		boardWidth = settings.getDimensionWidth();

		squares = new Square[boardHeight][boardWidth];	
		playerPieces = new HashMap<String, ArrayList<Piece>>();
		initSquares();
	}

	private void initSquares() {
		for(int i = 0; i < boardHeight; i++){
			for(int j = 0; j < boardWidth; j++){
				squares[i][j] = new Square(i, j);
			}
		}
	}
	
	public static ArrayList<Piece> getPieces(){
		ArrayList<Piece> allPieces = new ArrayList<Piece>();
		for(Map.Entry<String, ArrayList<Piece>> entry : playerPieces.entrySet()){		
			allPieces.addAll(entry.getValue());
		}
		return allPieces;
	}

	public Piece getPieceByXandY(int x, int y){
		return (Piece) getSquare(x, y).getPiece();
	}
	
	private void setPiece(int x,int y, Piece p){
		Square s = this.getSquare(x,y);
		if(s != null){
		    s.addPiece(p);
		}
		else{
//			throw Exception
		}
	}

	public Square getSquare(int x, int y) {
		return this.squares[x][y];
	}

	public void display() {
		for(int i = 0; i < this.boardHeight; i++){
			for(int j = 0; j < this.boardWidth; j++){
				if(this.getSquare(i, j)!=null){
				    this.getSquare(i, j).display();
				}
			}
			System.out.println("o");
		}
	}

	public void setPieceforPlayer(String playerName, PieceClass[] p1Pieces, int posY) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		playerPieces.put(playerName, pieces);

		int j = 0;
		for(int i = 0; i < p1Pieces.length; i++){
			for(; j < num*(i+1); j++){
				Piece p = (Piece) SquareComponentFactory.createPiece(p1Pieces[i],j+1, posY);
				this.setPiece(j+1, posY , p);
			    pieces.add(p);
			}
		}
	}

	public boolean piecesOfOnePlayerAllRemoved() {
		boolean removed = false;
		for(ArrayList<Piece> pieceList : playerPieces.values()){
			if(pieceList.isEmpty()){
				removed = true;
			}
		}
		return removed;
	}

	public void switchActivePieces() {
		int playerID = (turnCount++)%2 +1;
//		String playerName = "p2";

		String playerName = "p"+ playerID;
		
		for(Map.Entry<String, ArrayList<Piece>> entry : playerPieces.entrySet()){
			if(entry.getKey().equals(playerName)){
				for(Piece p: entry.getValue()){
					p.setPieceMovable();
				}
			}
			else{
				for(Piece p: entry.getValue()){
					p.setPieceUnmovable();
				}
			}
		}
	}

	public ArrayList<Piece> getActivePieces() {
		return this.activePlayerPieces;
	}
	
	public boolean isPiece(int x, int y){
		return squares[x][y].getPiece()!=null;
	}

	public void movePieceFromTo(int pieceX, int pieceY, int x, int y) {
		Piece p = this.getPieceByXandY(pieceX, pieceY);
		p.moveTo(x, y);
		this.getSquare(pieceX, pieceY).setSquareEmpty();
		this.setPiece(x, y, p);
	}

	public void attackFromAtoB(int aPosX, int aPosY, int bPosX, int bPosY) {
		Piece pa = this.getPieceByXandY(aPosX, aPosY);
		Piece pb = this.getPieceByXandY(bPosX, bPosY);
		
		pb.getHurt(pa.getPower());
		
	}

	public int getBoardHeight() {
		return this.boardHeight;
	}

	public int getBoardWidth() {
		return this.boardWidth;
	}
}
