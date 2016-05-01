package model;

import java.util.List;

public abstract class AbstractAttackPieceDecorator extends SquareComponent{
	
	public Piece piece;
	
	protected AbstractAttackPieceDecorator(Piece p){
		this(p.getPosX(), p.getPosY());
		this.piece = p;
	}

	private AbstractAttackPieceDecorator(int posX, int posY) {
		super(posX, posY);
	}
	public abstract List<Coordinate> getAttackField();
}
