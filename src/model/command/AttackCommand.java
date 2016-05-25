package model.command;

import model.Board;
import model.Piece;
import model.PieceClass;
import model.SquareComponentFactory;
import view.BasicPanel;
import view.BoardFramePanel;
import view.BoardPanel;
import view.PanelState;
import view.SquareComponentPanel;

public class AttackCommand implements Command{
	//receivers
	private Board board;
	private BoardFramePanel backPanel;
	private BoardPanel boardView;

	private Piece p2;
	private PieceClass pc;
	private int damage;
	
	private int originX;
	private int originY;
	private int targetX;
	private int targetY;
	private BasicPanel pPanel;
	
	public AttackCommand(Board board,BoardFramePanel bfp, int oX, int oY, int tX, int tY){
		this.board = board;
		this.backPanel = bfp;
		this.boardView = backPanel.getBoardView();
		
		this.originX = oX;
		this.originY = oY;
		this.targetX = tX;
		this.targetY = tY;
	}
	@Override
	public void execute() {
		Piece p1 = board.getPieceByXandY(originX, originY);
		p2 = board.getPieceByXandY(targetX, targetY);
		pc = p2.getPieceClass();
		
		damage = p1.getPower();
		
		p2.getHurt(damage);
		if(!p2.isAlive()){
			board.getSquare(targetX, targetY).removePiece();
		}
		board.switchActivePieces();
		
		if(!board.isPiece(targetX, targetY)){
			System.out.println("remove!");
			pPanel = boardView.removePieceOn(targetX, targetY);
			boardView.repaint();
		}						
		boardView.resetPieceMoveState();						
		boardView.cleanAllSquares();
    	backPanel.enableMenuMove();
    	boardView.setState(PanelState.BOARD_STATE_UNCERTAIN);
	}
	
	@Override
	public void undo() {
		p2.recoverHP(damage);
		if(board.isPiece(targetX, targetY)){
		}
		else{
			board.getSquare(targetX, targetY).addPiece(p2);			
			if(board.isPiece(targetX, targetY)){
				boardView.addPieceOn(pPanel,
						targetX, targetY);
			}
			
		}
		boardView.resetPieceMoveState();						
		boardView.cleanAllSquares();		
    	boardView.setState(PanelState.BOARD_STATE_UNCERTAIN);
	}
}
