package model;

public class Square {
	private int posX;
	private int posY;
	
	private int color;
	private SquareComponent pieceholder;
	
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
			System.out.printf( "%-6s","n");
		}
		else if (pieceholder.getClass().equals(Piece.class)){
			System.out.print(((Piece)pieceholder).getPieceClass()+" ");
		}
	}
	
	
	public void setSquareEmpty(){
		this.pieceholder = null;
	}
	
	public boolean isSquareOccupied(){
		return this.pieceholder!=null;
	}
	
	public Piece getSquareComponent(){
			return (Piece) this.pieceholder;			
	}
	
	public Piece getPiece(){
		if(pieceholder == null){
			System.out.println("piece null! " + posX + "  " +posY);
		}
		return (Piece) this.pieceholder;
	}
}
