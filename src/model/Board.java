package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
	private final int num = 3;
	public static int size;
	private Square[][] squares;
	private static Map<String, ArrayList<Piece>> playerPieces;
	
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

	private Square getSquare(int x, int y) {
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
				Piece p = PieceFactory.createPiece(p1Pieces[i]);
				this.setPiece(j+1, posY , p);
				p.moveTo(j+1, posY);
			    pieces.add(p);
			}

		}
	}
}
