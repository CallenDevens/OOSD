package model;

public class Piece extends SquareComponent{
	private static int idCounter = 0;
	private int id;
	private int attack;
	private int healthPoint;
	private int agility;
	
	private boolean movable = false;
	private PieceClass pclass;

	public Piece(PieceClass pclass, int atk,int health, int agility){
		id = idCounter++;
		this.pclass = pclass;
		this.attack = atk;
		this.healthPoint = health;
		this.agility = agility;
	}
	
	public String getPieceClass(){
		return this.pclass.toString();
	}
	
	public int getPieceID(){
		return this.id;
	}
	
	public int getPosX(){
		return x;
	}
	
	public int getPosY(){
		return y;
	}
	
	public void moveTo(int x, int y){
		this.x = x;
		this.y = y;
	}

	public boolean isMovable() {
		// TODO Auto-generated method stub
		return this.movable;
	}

	public void setPieceMovable() {
		this.movable = true;
		
	}

	public void setPieceUnmovable() {
		this.movable = false;
	}
}
