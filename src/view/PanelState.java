package view;
/**
 * state enum
 * board state and piece state
 * in a turn of the game, according to board state, c
 * licking different components would invoke different tasks*/
public enum PanelState {
	BOARD_STATE_UNCERTAIN,
	BOARD_MENU_SHOWN,
	BOARD_WAIT_FOR_MOVE,
	BOARD_WAITING_FOR_ATTACK,
	BOARD_STATE_MENU_SHOWN,
	BOARD_NO_MENU_SHOWN,
	
	PIECE_NON_CHOSEN,
	PIECE_CHOSEN,
	SQUARE_EMPTY,
	SQUARE_MOVABLE,
	SQUARE_ATTACKABLE, ITEM_UNVISITED,  
}
