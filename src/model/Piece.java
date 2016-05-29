package model;

public class Piece extends SquareComponent{

	private int attack;
	private int healthPoint;
	private int agility;
	private int atkrange;
		
	private boolean movable = false;
	private PieceClass pclass;
	
	/* piece state, piece performs different actions in accordance with it*/
	private PieceState state;

	public Piece(PieceClass pclass, int atk,int health, int agility, int atkrange,int posX, int posY){
		super(posX, posY);
		
		this.pclass = pclass;
		this.attack = atk;
		this.healthPoint = health;
		this.agility = agility;
		this.atkrange = atkrange;
	}
	
	public void SetState(PieceState s){
		this.state = s;
	}
	
	public void setHP(int hp){
		this.healthPoint = hp;
	}
	
	public String getPieceClassString(){
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
		if(this.state == null){
			this.healthPoint -=damage;
		}
		else{
			this.state.getHurt(damage);
		}
	}
	
	public boolean isAlive(){
		return this.healthPoint > 0;
	}

	public int getPower() {
		if(state == null){
			return this.attack;
		}
		else{
			return this.state.getPower();
		}
	}
	
	public int getAttackRange() {
		return this.atkrange;
	}

	public PieceClass getPieceClass() {
		return this.pclass;
	}

	public int getHealthyPoint() {
		return this.healthPoint;
	}
	
	public int getMoveRange(){
		return this.atkrange;
	}

	
	public class AttackState implements PieceState{

		@Override
		public int getPower() {
			return (int) (attack*1.1);
		}

		@Override
		public void getHurt(int damage) {
		     healthPoint -= 1.1*damage;
		}
		

		@Override
		public void recoverHP(int damage) {
			healthPoint += damage*1.1	;
		}

	}
	/* inner class implements PieceState */
	public class DefensiveState implements PieceState{

		@Override
		public int getPower() {
			/* a piece gives 80% of original damage */
			return (int) (attack*0.8);
		}

		@Override
		public void getHurt(int damage) {
			/* a piece receives 80% of original damage */
		     healthPoint -= damage*0.8;
		}

		@Override
		public void recoverHP(int damage) {
			healthPoint += damage*0.8	;		
		}
		
	}

	public void recoverHP(int damage) {
		if(this.state == null){
			this.healthPoint +=damage;
		}
		else{
			this.state.recoverHP(damage);
		}
	}
}
