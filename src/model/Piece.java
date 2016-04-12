package model;

public class Piece extends SquareComponent{

	private int attack;
	private int healthPoint;
	private int agility;
	
	private boolean movable = false;
	private PieceClass pclass;

	public Piece(PieceClass pclass, int atk,int health, int agility, int posX, int posY){
		super(posX, posY);
		
		this.pclass = pclass;
		this.attack = atk;
		this.healthPoint = health;
		this.agility = agility;
	}
	
	public String getPieceClass(){
		return this.pclass.toString();
	}
	
	public void moveTo(int x, int y){
		this.x = x;
		this.y = y;
	}

	public boolean isMovable() {
		return this.movable;
	}

	public void setPieceMovable() {
		this.movable = true;
		
	}

	public void setPieceUnmovable() {
		this.movable = false;
	}

	public int getMovableDistance() {
		return this.agility;
	}
	
	public void getHurt(int damage){
		this.healthPoint -=damage;
	}
	
	public boolean isAlive(){
		return this.healthPoint > 0;
	}
}
