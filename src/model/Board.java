package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
	private final int num = 3;
	public static int size;
	private Square[][] squares;
	private static Map<String, ArrayList<Piece>> playerPieces;
	
	private static ArrayList<Piece> activePlayerPieces = null;

	
	private int turnCount = 0;
	
	public Board(){
		size = 11;
		squares = new Square[size][size];	
		playerPieces = new HashMap<String, ArrayList<Piece>>();
		initSquares();
		
	}

	private void initSquares() {
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
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

	//P1 hunter warrior mage
	//P2 rouge paladin prist
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
//			System.out.println(x+","+y);
		}
	}

	public Square getSquare(int x, int y) {
		return this.squares[x][y];
	}

	public void display() {
		for(int i = 0; i < size; i++){
			for(int j = 0; j < size; j++){
				//System.out.println(i+  "  " +j );
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
				Piece p = SquareComponentFactory.createPiece(p1Pieces[i]);
				this.setPiece(j+1, posY , p);
				p.moveTo(j+1, posY);
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
		System.out.println(playerName);
		
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
		// TODO Auto-generated method stub
		return this.activePlayerPieces;
	}

	public void movePieceFromTo(int pieceX, int pieceY, int x, int y) {
		Piece p = this.getPieceByXandY(pieceX, pieceY);
		p.moveTo(x, y);
		this.getSquare(pieceX, pieceY).setSquareEmpty();
		this.setPiece(x, y, p);
	}
}
