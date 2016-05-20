package model;

public class Square {
	private int posX;
	private int posY;
	
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
	

	
	public void removePiece(){
		this.pieceholder = null;
	}
	
	public boolean isFilled(){
		return pieceholder !=null;
	}

	public Piece getPiece(){
		if(pieceholder!=null && pieceholder.getClass().equals(Piece.class)){
			return (Piece) this.pieceholder;
		}else{
			return null;
		}
	}
}
