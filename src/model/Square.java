package model;

public class Square {
	private int posX;
	private int posY;
	
	private int color;
	private Piece pieceholder;
	
	public Square(int x, int y){
		this.posX = x;
		this.posY = y;
		pieceholder = null;
	}
	
	public Square(){
		pieceholder = null;
	}

	public void addPiece(Piece p) {
		this.pieceholder = p;
	}

	public void display() {
		if(pieceholder== null){
			System.out.print("n	");
		}
		else{
			System.out.print(pieceholder.getPieceClass()+" ");
		}
	}
	
	
	public void setSquareEmpty(){
		this.pieceholder = null;
	}
	
	public void setPieceOnSquare(Piece p){
		this.pieceholder = p;
	}
}
