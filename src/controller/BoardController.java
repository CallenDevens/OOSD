package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import model.AbstractAttackPieceDecorator;
import model.Board;
import model.Coordinate;
import model.DecoratedPieceProducer;
import model.Piece;
import model.SquareComponent;
import view.BasicPanel;
import view.BoardFramePanel;
import view.BoardPanel;
import view.PanelState;
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
		this.backPanel.addMenuResignActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int x = boardView.getActivePiecePosX();
				int y = boardView.getActivePiecePosY();					
				board.getPieceByXandY(x, y).SetState(null);
				
				board.switchActivePieces();
				backPanel.enableMenuMove();
				backPanel.setMenuVisisble(false);				
			}
		});
		
		this.backPanel.addModelListener("ATTACK MODEL", new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					backPanel.setStateMenuInvisible();
					int x = boardView.getActivePiecePosX();
					int y = boardView.getActivePiecePosY();
					
					Piece p = board.getPieceByXandY(x, y);
					p.SetState(p.new AttackState());
					backPanel.moveAndShowPieceMenu(x, y);
					
					boardView.setState(PanelState.BOARD_WAIT_ACTION);

				}	
		});
		
		
		this.backPanel.addModelListener("DEFENSIVE MODEL", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					backPanel.setStateMenuInvisible();
					int x = boardView.getActivePiecePosX();
					int y = boardView.getActivePiecePosY();
					
					Piece p = board.getPieceByXandY(x, y);
					p.SetState(p.new DefensiveState());
					backPanel.moveAndShowPieceMenu(x, y);
					boardView.setState(PanelState.BOARD_WAIT_ACTION);
				}	
		});

		
		this.backPanel.addModelListener("NORMAL MODEL", new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
					backPanel.setStateMenuInvisible();
					int x = boardView.getActivePiecePosX();
					int y = boardView.getActivePiecePosY();					
					board.getPieceByXandY(x, y).SetState(null);
					backPanel.moveAndShowPieceMenu(x, y);
					boardView.setState(PanelState.BOARD_WAIT_ACTION);
				}	
		});
		
		this.backPanel.addModelListener("CANCEL", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				boardView.setState(PanelState.BOARD_START_NEW_TURN);
			}	
		});


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
    		boardView.setPieceOnBoard(p.getPieceClass().toString()+".png", x, y);
    	}
	}//End of setPieces()
	
	/**
	 * BoardController.addSquareActionListener()
	 * add ActionListeners to all squares within the board view.
	 */
	private void initializeSquareActionListener(){
		int d_height = board.getBoardHeight();
		int d_width = board.getBoardWidth();
		
		for(int i = 0; i< d_height; i++){
			for(int j =0; j< d_width; j++){
				
				final int x = i;
				final int y = j;
				
				if(board.isPiece(i, j)){
					PieceController pc = new PieceController(board.getPieceByXandY(x, y));
					boardView.getSubComponent(x, y).getSubComponent().addMouseListener(pc);
					
				}//end of if(grids[i][j].isPiece())
				
				SquareController sc = new SquareController(x, y);
				boardView.getSubComponent(x, y).addMouseListener(sc);
				
				//TODO if a square holds something else
			}
		}
	}// End of addSquareActionListener()
	
	/* a class implements ActionListener to specify actions to be performed when pieces are clicked.
	 * make it inner to get an access to board model as coordinates are required during the procedure.
	 * */
	private class PieceController implements MouseListener{
		private Piece p;
		
		public PieceController(Piece p){
			this.p = p;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			int x = p.getPosX();
			int y = p.getPosY();
			
			if(board.getPieceByXandY(x, y).isMovable()){
				
				//keep other pieces from moving in a single round
				if(!backPanel.menuMoveEnabled() && !boardView.isActivePice(x, y)){
					
				}
				
				else{
					if(boardView.getState() == PanelState.BOARD_START_NEW_TURN){
						backPanel.moveAndShowStateMenu(x, y);
						boardView.setActivePieceCoordinates(x, y);
						boardView.setState(PanelState.BOARD_STATE_MENU_SHOWN);
					}
					else if(boardView.getState() == PanelState.BOARD_WAIT_ACTION){
						backPanel.moveAndShowPieceMenu(x, y);
						boardView.setActivePieceCoordinates(x, y);

					}
				}
			}
			else{
				if(boardView.getState() == PanelState.BOARD_WAITING_FOR_ATTACK){
					if(boardView.isBoardPieceChoosen()){
						int attackingPiecePosX = boardView.getActivePiecePosX();
						int attackingPiecePosY = boardView.getActivePiecePosY();
						
						board.attackFromAtoB(attackingPiecePosX, attackingPiecePosY, x, y);
						
						if(!board.isPiece(x, y)){
							boardView.removePieceOn(x, y);
							boardView.repaint();
						}
						board.getPieceByXandY(attackingPiecePosX, attackingPiecePosY).SetState(null);
						
						boardView.resetPieceMoveState();						
						boardView.cleanAllSquares();

						boardView.repaint();
						board.switchActivePieces();
				    	backPanel.getMenuPane().enableMove();

					}
					backPanel.setPieceMenuInvisible();
					boardView.resetPieceMoveState();
				}
				else if(boardView.getState() == PanelState.BOARD_WAIT_FOR_MOVE){
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
			if(boardView.getState() == PanelState.BOARD_WAITING_FOR_ATTACK){
				/*if the board is waiting for picking up attacked object 
				 * the user clicked empty square
				 * cancel attack highlight and 
				 * call the menu panel 
				 * */
				if(board.getPieceByXandY(x, y)==null){
					boardView.cleanAllSquares();
					backPanel.getMenuPane().moveAndShowUp(x, y);
				}
			}
	
			if(!bp.isMovableSquare(x,y)){
				backPanel.setPieceMenuInvisible();
				return;
			}else if(bp.isBoardPieceChoosen()){
				if(boardView.getState() == PanelState.BOARD_WAIT_FOR_MOVE){
					int pieceX = bp.getActivePiecePosX();
					int pieceY = bp.getActivePiecePosY();
					
					//change model
					board.movePieceFromTo(pieceX, pieceY, x, y);
					
					//change view
					bp.setActivePieceCoordinates(x, y);
										
					BasicPanel piece = boardView.removePieceOn(pieceX,pieceY);
					if(piece == null){
					}
					boardView.addPieceOn(piece, x, y);
					
					backPanel.moveAndShowPieceMenu(x, y);
				    backPanel.disableMenuMove();
				    
					boardView.cleanAllSquares();
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
			
			BasicPanel square = boardView.getSubComponent(x, y);
			
			if(square.getState() == PanelState.PIECE_NON_CHOSEN){
				int distance = board.getPieceByXandY(x, y).getMovableDistance();
				boardView.setChosenPieceMovableArea(x, y, distance);
				boardView.setState(PanelState.BOARD_WAIT_FOR_MOVE);
			}
			else{
				System.out.println(square.getState().toString());
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
			boardView.setState(PanelState.BOARD_WAITING_FOR_ATTACK);
		}		
	}
}
