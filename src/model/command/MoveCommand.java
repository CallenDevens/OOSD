package model.command;

import model.Board;
import view.BasicPanel;
import view.BoardFramePanel;
import view.BoardPanel;
import view.PanelState;

public class MoveCommand implements Command{
	//receiver
	private Board board;
	private BoardPanel boardView;
	private BoardFramePanel backPanel;

	
	private int originX;
	private int originY;
	private int targetX;
	private int targetY;
	
	public MoveCommand(Board b,BoardFramePanel bfp, int originX, int originY, int targetX, int targetY){
		this.board = b;
		this.backPanel = bfp;
		this.boardView = backPanel.getBoardView();
		
		this.originX = originX;
		this.originY = originY;
		this.targetX = targetX;
		this.targetY = targetY;
	}

	@Override
	public void execute() {
		board.movePieceFromTo(originX, originY, targetX, targetY);	
		if(board.isPiece(targetX, targetY)){

			boardView.setActivePieceCoordinates(targetX, targetY);			
			BasicPanel piece = boardView.removePieceOn(originX,originY);

			boardView.addPieceOn(piece, targetX, targetY);
			boardView.notifyObservers();
			boardView.setState(PanelState.BOARD_MENU_SHOWN);
			backPanel.moveAndShowPieceMenu(targetX, targetY);
		    backPanel.disableMenuMove();
		}
	}

	@Override
	public void undo() {
		board.movePieceFromTo(targetX, targetY, originX, originY);
		if(board.isPiece(originX, originY)){
//			boardView.resetPieceMoveState();
//			boardView.setActivePieceCoordinates(targetX, targetY);			
			BasicPanel piece = boardView.removePieceOn(targetX,targetY);
			
			boardView.addPieceOn(piece, originX, originY);
//			boardView.cleanAllSquares();
			boardView.setState(PanelState.BOARD_STATE_UNCERTAIN);
			backPanel.enableMenuMove();
		}
	}
}
