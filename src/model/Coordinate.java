package model;

public class Coordinate {
	private int x;
	private int y;
	
	public int getCoordinateX(){
		return this.x;
	}
	
	public int getCoordinateY(){
		return this.y;
	}
	
	public Coordinate(int x, int y){
	    this.x = x;
	    this.y = y;
	}
	
	public Coordinate(){
		this.x = -1;
		this.y = -1;
	}
	
	public void setCoordinateX(int x){
		this.x = x;
	}
	
	public void setCoordinateY(int y){
		this.y = y;
	}

}
