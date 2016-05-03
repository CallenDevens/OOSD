package model;

import java.util.ArrayList;
import java.util.List;

import utils.GameSetting;

public class CircleAttackPiece extends AbstractAttackPieceDecorator{

	protected CircleAttackPiece(Piece p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Coordinate> getAttackField() {
		ArrayList<Coordinate> coorList = new ArrayList<Coordinate>();
		
		int range = piece.getAttackRange();
		int x = piece.getPosX();
		int y = piece.getPosY();
		
		for(int i = 1; i<=range; i++){
			
			if(x+i<dimension_width){
				Coordinate c1 = new Coordinate(x+i, y);
				coorList.add(c1);
				
				for(int j = 1; j<=range; j++){
    				if(y+j<dimension_height){
    					c1 = new Coordinate(x+i, y+j);
						coorList.add(c1);
    				}
    				if(y-j>=0){
    					c1 = new Coordinate(x+i, y-j);
						coorList.add(c1);
    				}
				}

			}
			
			if(x - i >=0){
				Coordinate c2 = new Coordinate(x-i, y);
				coorList.add(c2);
				
				for(int j = 1; j<=range; j++){
    				if(y+j<dimension_height){
    					c2 = new Coordinate(x-i, y+j);
						coorList.add(c2);
    				}
    				if(y-j>=0){
    					c2 = new Coordinate(x-i, y-j);
						coorList.add(c2);
    				}
				}
			}
			if(y+i<dimension_height){
				Coordinate c3 = new Coordinate(x, y+i);
				coorList.add(c3);
			}
			if(y-i >=0){
				Coordinate c4 = new Coordinate(x, y-i);				
				coorList.add(c4);
			}
		}
		return coorList;

	}

}
