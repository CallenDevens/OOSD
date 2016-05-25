package model;

interface PieceState {
	public abstract int getPower();
	public abstract void getHurt(int damage);
	public abstract void recoverHP(int damage);
}
