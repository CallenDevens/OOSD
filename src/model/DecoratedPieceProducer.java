package model;

public class DecoratedPieceProducer {
	public static SquareComponent generateAttackPiece(Piece p){
		PieceClass pc = p.getPieceClass();
		switch (pc) {
		case MAGE:
			return new CircleAttackPiece(p);
		case ROGUE:
			return new LinerAttackPiece(p);
		case WARRIOR:
			return new CircleAttackPiece(p);
		case PALADIN:
			return new LinerAttackPiece(p);
		case PRISST:
			return new CircleAttackPiece(p);
		case HUNTER:
			return new LinerAttackPiece(p);
		default:
			return null;
		}	}

}
