package model;

import java.util.List;

import utils.GameSetting;

public abstract class AbstractAttackPieceDecorator extends SquareComponent{
	
	public Piece piece;
	protected GameSetting settings = GameSetting.getInstance();
	protected int dimension_height = settings.getDimensionHeight();
	protected int dimension_width = settings.getDimensionWidth();
	
	protected AbstractAttackPieceDecorator(Piece p){
		this(p.getPosX(), p.getPosY());
		this.piece = p;
	}

	private AbstractAttackPieceDecorator(int posX, int posY) {
		super(posX, posY);
	}
	public abstract List<Coordinate> getAttackField();
}
