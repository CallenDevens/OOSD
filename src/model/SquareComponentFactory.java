package model;

public class SquareComponentFactory {
	private Piece itsPiece;
	private static final int MAGE_ATTACK = 120;
	private static final int MAGE_HEALTH_POINT = 200;
	private static final int MAGE_AGI = 3;

	private static final int ROGUE_ATTACK = 80;
	private static final int ROGUE_HEALTH_POINT = 200;
	private static final int ROGUE_AGI = 5;

	private static final int WARRIOR_ATTACK = 100;
	private static final int WARRIOR_HEALTH_POINT = 250;
	private static final int WARRIOR_AGI = 2;

	private static final int PALADIN_ATTACK = 110;
	private static final int PALADIN_HEALTH_POINT = 240;
	private static final int PALADIN_AGI = 2;

	private static final int PRISST_ATTACK = 70;
	private static final int PRISST_HEALTH_POINT = 440;
	private static final int PRISST_AGI = 1;

	private static final int HUNTER_ATTACK = 100;
	private static final int HUNTER_HEALTH_POINT = 240;
	private static final int HUNTER_AGI = 4;

	public static Piece createPiece(PieceClass pieceClass, int posx, int posy) {
		switch (pieceClass) {
		case MAGE:
			return new Piece(pieceClass.MAGE, MAGE_ATTACK, MAGE_HEALTH_POINT, MAGE_AGI, posx, posy);
		case ROGUE:
			return new Piece(pieceClass.ROGUE, ROGUE_ATTACK, ROGUE_HEALTH_POINT, ROGUE_AGI, posx, posy);
		case WARRIOR:
			return new Piece(pieceClass.WARRIOR, WARRIOR_ATTACK, WARRIOR_HEALTH_POINT, WARRIOR_AGI, posx, posy);
		case PALADIN:
			return new Piece(pieceClass.PALADIN, PALADIN_ATTACK, PALADIN_HEALTH_POINT, PALADIN_AGI, posx, posy);
		case PRISST:
			return new Piece(pieceClass.PRISST, PRISST_ATTACK, PRISST_HEALTH_POINT, PRISST_AGI, posx, posy);
		case HUNTER:
			return new Piece(pieceClass.HUNTER, HUNTER_ATTACK, HUNTER_HEALTH_POINT, HUNTER_AGI, posx, posy);
		default:
			return null;
		}
	}

}
