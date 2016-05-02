package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLayeredPane;

import model.AbstractAttackPieceDecorator;
import model.Board;
import model.Coordinate;
import model.DecoratedPieceProducer;
import model.Piece;
import model.SquareComponent;
import view.BoardFramePanel;
import view.BoardPanel;
import view.BoardState;
import view.MenuPanel;
import view.SquareComponentInfoPanel;
import view.SquarePanel;
/* a class implements ComponentListener, 
 * providing actions to be taken when board view is being loaded.
 * 
 * Warning:
 * As component is set visible by default, to invoke componentShown method,
 * Changing visibility of the component is necessary, 
 * or else program would give no reaction when loading the view
 * 
 * Example for invoking the method: component.setVisible(false);component.setVisible(true);
 * */

public class BoardController implements ComponentListener{
	
    //this controller works as an link between board and visual board
	private Board board = null;
	private BoardPanel boardView = null;
	private BoardFramePanel backPanel = null;
		
	public void setBackPanel(BoardFramePanel pane){
		this.backPanel = pane;
	}
	
	public void setModel(Board b){
		this.board = b;
	}
	
	public void setView(BoardPanel bp){
		this.boardView = bp;
	}


	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		this.setPieces();
    	this.boardView.revalidate();
    	this.boardView.repaint();
    	
    	this.addMenuPanelListener();
    	this.initializeSquareActionListener();
	}

	private void addMenuPanelListener() {
		this.backPanel.addPieceMoveButtonListener(new PieceMoveButtonController());
		this.backPanel.addPieceAttackButtonListener(new PieceAttackButtonController());
		this.backPanel.getMenuPane().getResignButton().addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				board.switchActivePieces();
				backPanel.getMenuPane().enableMove();
				backPanel.getMenuPane().setVisible(false);
				
			}
		});
		//TODO and attack resign
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**BoardController.setPieces()
	 * Get positions and jobs of active pieces from model
	 * draw images of pieces on the view with it
	 */
	private void setPieces(){
    	ArrayList<Piece> pieces = board.getPieces();
    	for(Piece p: pieces){
    		int x = p.getPosX();
    		int y = p.getPosY();
    		boardView.grids[x][y].setPiece(p.getPieceClass().toString()+".png");
    	}
	}//End of setPieces()
	
	/**
	 * BoardController.addSquareActionListener()
	 * add ActionListeners to all squares within the board view.
	 */
	private void initializeSquareActionListener(){
		int size = board.size;
		SquarePanel [][] grids = boardView.grids;

		
		for(int i = 0; i< size; i++){
			for(int j =0; j< size; j++){
				final int x = i;
				final int y = j;
				
				/* ①
				 * if a square is empty, 
				 * which means it doesn't hold any SquareComponent(Piece ,Barrier or HP bonus)
				 * add a MouseListener implemented for square
				 * 
				 * ②
				 * if a square holds a piece
				 * add an ActionLister to its piece view(represented by a JButton by now)
				 */
//				String key = "("+x+","+y+")";
				if(board.isPiece(i, j)){
					PieceController pc = new PieceController(board.getPieceByXandY(x, y));
					grids[i][j].getClickablePiece().addMouseListener(pc);
					
//					this.controllers.put(key, pc);
				}//end of if(grids[i][j].isPiece())
				
				SquareController sc = new SquareController(x, y);
				grids[i][j].addMouseListener(sc);
//				this.controllers.put(key, sc);
				
				//TODO if a square holds something else
			}
		}
	}// End of addSquareActionListener()
	
	/* a class implements ActionListener to specify actions to be performed when pieces are clicked.
	 * make it inner to get an access to board model as coordinates are required during the procedure.
	 * */
	private class PieceController implements MouseListener{
		private Piece p;
		BoardPanel bp = BoardController.this.boardView;

		public PieceController(Piece p){
			this.p = p;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			int x = p.getPosX();
			int y = p.getPosY();
			/* if the piece 
			 * ① is on the active player side && is not chosen by the player
			 * get AGI from model 
			 * calculate and highlight movable area on the board
			 * 
			 * ② is on the active player side && has been chosen by the player
			 * reset highlighted area
			 * 
			 * ③ is not on active side
			 * reset active piece state
			 * */
			if(board.getPieceByXandY(x, y).isMovable()){
				if(!backPanel.getMenuPane().moveEnabled() && !boardView.isActivePice(x, y)){
					
				}
				else{
					backPanel.moveAndShowPieceMenu(x, y);
					backPanel.setPieceInfoInvisible();
					boardView.setActivePieceCoordinates(x, y);
					boardView.setState(BoardState.STATE_MENU_SHOWN);
				}
			}
			else{
				if(boardView.getState() == BoardState.STATE_WAITING_FOR_ATTACK){
					if(bp.isBoardPieceChoosen()){
						int attackingPiecePosX = bp.getActivePiecePosX();
						int attackingPiecePosY = bp.getActivePiecePosY();
						
						board.attackFromAtoB(attackingPiecePosX, attackingPiecePosY, x, y);
						
						bp.resetPieceMoveState();						
						refreshPieces();
						
						boardView.repaint();
						board.switchActivePieces();
				    	backPanel.getMenuPane().enableMove();

					}
					backPanel.setPieceMenuInvisible();
					bp.resetPieceMoveState();
				}
				else if(boardView.getState() == BoardState.STATE_WAIT_FOR_MOVE){
					return;
				}
			}
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			String pclass = p.getPieceClassString();
			String pAtk = p.getPower()+"";
			String pHp = p.getHealthyPoint()+"";
			String pRange = p.getAttackRange()+"";
			backPanel.setPieceInfoPanelContent(pclass, pAtk, pHp, pRange, "");
			backPanel.moveAndShowPieceInfo(p.getPosX(), p.getPosY());
		}

		@Override
		public void mouseExited(MouseEvent e) {
			backPanel.setPieceInfoInvisible();			
		}
	}
	/**
	 * Following players' movements, redraw board and pieces
	 * establish links between squares and specific model
	 * invoked after turn ends.
	 * */

	private void refreshPieces() {
		int size = board.size;
    	for(int i =0 ;i < size; i++){
    		for(int j = 0; j < size; j++){
    			boardView.grids[i][j].clean(); 
    		}
    	}
    	
//		this.setPieces();
//    	this.addSquareActionListener();
		boardView.repaint();
	}
	
	/* a class implements MouseListener to specify actions to be performed when squares are clicked.
	 * make it inner to get an access to board model as coordinates are required during the procedure.
	 * */
	private class SquareController implements MouseListener{
		
		private int x;
		private int y;
		BoardPanel bp = BoardController.this.boardView;

		public SquareController(int x, int y){
			this.x = x;
			this.y = y;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			/* if the square
			 * 
			 *  is contained in highlighted area && there is some chosen piece
			 * move chosen piece onto the square
			 * 
			 * else do nothing
			 * */
			if(boardView.getState() == BoardState.STATE_WAITING_FOR_ATTACK){
				/*if the board is waiting for picking up attacked object 
				 * the user clicked empty square
				 * cancel attack highlight and 
				 * call the menu panel 
				 * */
				if(board.getPieceByXandY(x, y)==null){
					refreshPieces();
					backPanel.getMenuPane().moveAndShowUp(x, y);
				}
			}
	
			if(!bp.isMovableSquare(x,y)){
				backPanel.setPieceMenuInvisible();
				return;
			}else if(bp.isBoardPieceChoosen()){
				if(boardView.getState() == BoardState.STATE_WAIT_FOR_MOVE){
					int pieceX = bp.getActivePiecePosX();
					int pieceY = bp.getActivePiecePosY();
					
					//change model
					board.movePieceFromTo(pieceX, pieceY, x, y);
					
					//change view
					bp.setActivePieceCoordinates(x, y);
					
					JButton piece = boardView.grids[pieceX][pieceY].removePieceButton();
					boardView.addPieceOn(piece, x, y);
					
					backPanel.showPieceMenuAfterMove(x, y);
				    backPanel.getMenuPane().disableMove();
					
				    refreshPieces();
			    }
			}	
		}


		@Override
		public void mousePressed(MouseEvent e) {				
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
				
		}			
	}
	
	
	private class PieceMoveButtonController implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			backPanel.setPieceMenuInvisible();
			
			int x = boardView.getActivePiecePosX();
			int y = boardView.getActivePiecePosY();
			
			SquarePanel square = boardView.getSquareByCoordinates(x, y);
			
			if(square.getState() == SquarePanel.PIECE_NON_CHOSEN){
				int distance = board.getPieceByXandY(x, y).getMovableDistance();
				boardView.setChosenPiece(x, y, distance);
				boardView.setState(BoardState.STATE_WAIT_FOR_MOVE);
			}
			else{
			//	bp.setNonChosenPiece(x, y);
			}		
		}		
	}
	
	
	private class PieceAttackButtonController implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			backPanel.setPieceMenuInvisible();
			
			int x = boardView.getActivePiecePosX();
			int y = boardView.getActivePiecePosY();

			Piece p = board.getPieceByXandY(x, y);
			
			SquareComponent attackPiece = DecoratedPieceProducer.generateAttackPiece(p);
			
			ArrayList<Coordinate> clist = (ArrayList<Coordinate>) ((AbstractAttackPieceDecorator)attackPiece).getAttackField();
			
			for(Coordinate c: clist){
				boardView.highlightAttackArea(c.getCoordinateX(), c.getCoordinateY());
			}
			boardView.setState(BoardState.STATE_WAITING_FOR_ATTACK);
		}		
	}

	
	
}
