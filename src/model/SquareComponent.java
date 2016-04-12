package model;

public abstract class SquareComponent {
	protected int x;
	protected int y;
	
	protected SquareComponent(int posX, int posY){
		this.x = posX;
		this.y = posY;	
	}

	public int getPosX(){
		return x;
	}
	

	public int getPosY(){
		return y;
	}
}
