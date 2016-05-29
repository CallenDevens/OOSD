package model;

public class Player {
	
	private String name;
	private int undoCount;
	private boolean undoAvailable = true;
	
	public void addUndoCount(){
		this.undoCount++;
		if(undoCount >=3){
			this.undoAvailable = false;
		}
	}
	public boolean isUndoAvailable(){
		return this.undoAvailable;
	}
	
	public void banUndo(){
		this.undoAvailable = false;
	}
	public Player(String name){
		this.name = name;
	}
	
	public String getPlayerName(){
		return name;
	}
	
	public int getUndoCount(){
		return this.undoCount;
	}
	public void setUndoCount(int uc){
		this.undoCount = uc;
	}
	
	public void setUndoAvailable(boolean ua){
		this.undoAvailable = ua;
	}
}
