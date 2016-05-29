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
import model.command.AttackCommand;
import model.command.Command;
import model.command.CommandStack;
import model.command.MoveCommand;
import view.BasicPanel;
import view.BoardFramePanel;
import view.BoardPanel;
import view.PanelState;
/** a class implements ComponentListener, 
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
	private CommandStack commStack;
		
	public void setBackPanel(BoardFramePanel pane){
		this.backPanel = pane;
	}
	
	public void setModel(Board b, CommandStack cs){
		this.board = b;
		this.commStack = cs;
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
	}

	@Override
	public void componentShown(ComponentEvent e) {
		this.setPieces();
    	this.boardView.revalidate();
    	this.boardView.repaint();
    	
    	//bind action listeners to Player Menu
    	this.addMenuPanelListener();
    	
    	//bind action listeners to square and piece
    	this.initializeSquareActionListener();
	}

	/**
	 * bind action listeners to Player Menu, 
	 * which shows up on the board when player click a piece
	 */
	private void addMenuPanelListener() {
		this.backPanel.addPieceMoveButtonListener(new PieceMoveButtonController());
		this.backPanel.addPieceAttackButtonListener(new PieceAttackButtonController());
		this.backPanel.addMenuResignActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				/* after Resign button is clicked, 
				 * switch active player
				 * enable move button(for active player)
				 * reset board state to BOARD_STATE_UNCERTAIN which represents start of a new turn. */
				board.switchActivePieces();
				backPanel.enableMenuMove();
				backPanel.setMenuVisisble(false);
				boardView.setState(PanelState.BOARD_STATE_UNCERTAIN);

			}
		});
		
		this.backPanel.addMenuCancelActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				/* as menu has been cancelled, switch state*/
				boardView.setState(PanelState.BOARD_NO_MENU_SHOWN);
			}
		});
		
		this.backPanel.addModelListener("ATTACK MODE", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			    /* after button clicked, set state selection menu invisible*/
				backPanel.setStateMenuInvisible();
				
				/* get selected piece*/
				int x = boardView.getActivePiecePosX();
				int y = boardView.getActivePiecePosY();
				Piece p = board.getPieceByXandY(x, y);
					
				/* set the state of selected piece as attack */
				p.SetState(p.new AttackState());
					
				/* show action menu*/
				backPanel.moveAndShowPieceMenu(x, y);
					
				/* update board state*/
				boardView.setState(PanelState.BOARD_STATE_MENU_SHOWN);

			}	
		});
		
		
		this.backPanel.addModelListener("DEFENSIVE MODE", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
			    /* after button clicked, set state selection menu invisible*/
				backPanel.setStateMenuInvisible();
				
				/* get selected piece*/
				int x = boardView.getActivePiecePosX();
				int y = boardView.getActivePiecePosY();	
				Piece p = board.getPieceByXandY(x, y);
				
				/* set the state of selected piece as defensive state */
				p.SetState(p.new DefensiveState());
				
				/* show action menu*/
				backPanel.moveAndShowPieceMenu(x, y);
				
				/* update board state*/
				boardView.setState(PanelState.BOARD_STATE_MENU_SHOWN);
			}	
		});

		
		this.backPanel.addModelListener("NORMAL MODE", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					backPanel.setStateMenuInvisible();
					int x = boardView.getActivePiecePosX();
					int y = boardView.getActivePiecePosY();					
					board.getPieceByXandY(x, y).SetState(null);
					backPanel.moveAndShowPieceMenu(x, y);
					boardView.setState(PanelState.BOARD_STATE_MENU_SHOWN);
				}	
		});
		
		this.backPanel.addModelListener("CANCEL", new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				boardView.setState(PanelState.BOARD_STATE_UNCERTAIN);
			}	
		});
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub	
	}
	
	/**BoardController.setPieces()
	 * Get positions and jobs of pieces from model
	 * draw images of pieces on the view with it
	 */
	private void setPieces(){
    	ArrayList<Piece> pieces = board.getPieces();
    	for(Piece p: pieces){
    		int x = p.getPosX();
    		int y = p.getPosY();
    		boardView.setPieceOnBoard(p.getPieceClass().toString(), x, y);
    	}
	}//End of setPieces()
	
	/**
	 * BoardController.addSquareActionListener()
	 * add ActionListeners to all squares within the board view.
	 */
	private void initializeSquareActionListener(){
		/* get height and width of the board */
		int d_height = board.getBoardHeight();
		int d_width = board.getBoardWidth();
		
		/* go through all squares, bind controllers to them*/
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
			}
		}
	}// End of addSquareActionListener()
	
	/* ----------------------inner classes begin from the line ----------------------------------------*/
	
	/** a class implements ActionListener to specify actions to be performed when pieces are clicked.
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
				if(boardView.getState()!=PanelState.BOARD_STATE_UNCERTAIN && !boardView.isActivePice(x, y)){
					/* keep other pieces from moving in a single round 
					 * if a player has moved a piece in its turn, 
					 * other pieces are not permitted to move until new turn starts */
					return;
				}
				
				else{
					if(boardView.getState() == PanelState.BOARD_STATE_UNCERTAIN){
						/* if the piece state is not selected , show selection menu*/
						backPanel.moveAndShowStateMenu(x, y);
						/* choose clicked piece*/
						boardView.setActivePieceCoordinates(x, y);		
						/* update state*/
						boardView.setState(PanelState.BOARD_STATE_MENU_SHOWN);
					}
					else if(boardView.getState() == PanelState.BOARD_NO_MENU_SHOWN){
						/* show action menu */
						backPanel.moveAndShowPieceMenu(x, y);
						
						//TODO debug
						boardView.setState(PanelState.BOARD_NO_MENU_SHOWN);
						
						/* choose the piece */
						boardView.setActivePieceCoordinates(x, y);	
					}
				}
			}
			else{
				//Select attacked piece( not movable)
				if(boardView.getState() == PanelState.BOARD_WAITING_FOR_ATTACK){
					if(boardView.isBoardPieceChoosen()){
						//there is active attacking piece
						int attackingPiecePosX = boardView.getActivePiecePosX();
						int attackingPiecePosY = boardView.getActivePiecePosY();
						
						// create and execute attack command
						Command atkCommand = new AttackCommand(board, backPanel,attackingPiecePosX, attackingPiecePosY, x, y);
						atkCommand.execute();
						
						//push executed command into stack (for undo)
						commStack.pushCommand(atkCommand);
					}
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
	
	/** a class implements MouseListener to specify actions to be performed when squares are clicked.
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
					boardView.setState(PanelState.BOARD_NO_MENU_SHOWN);
					return;
				}
			}else if(boardView.getState() == PanelState.BOARD_STATE_MENU_SHOWN){
				
				/* click square to conceal state menu*/
				backPanel.setPieceStateMenuInvisible();
				boardView.setState(PanelState.BOARD_STATE_UNCERTAIN);
				return;
				
			}else if(boardView.getState() == PanelState.BOARD_MENU_SHOWN){
				
				/* click square to conceal action menu*/
				backPanel.setPieceMenuInvisible();
				boardView.setState(PanelState.BOARD_NO_MENU_SHOWN);
				return;
			}
			else if(bp.isBoardPieceChoosen()){
				/* move selected piece to clicked square */
				if(boardView.getState() == PanelState.BOARD_WAIT_FOR_MOVE){
					int pieceX = bp.getActivePiecePosX();
					int pieceY = bp.getActivePiecePosY();
					
					if(!boardView.isMovableSquare(x, y)){
						return;
					}
					
					// create and execute moving command
					Command moveCom = new MoveCommand(board,backPanel, pieceX, pieceY, x, y);
					moveCom.execute();
					commStack.pushCommand(moveCom);
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
			/* after button clicked, concel action menu */
			backPanel.setPieceMenuInvisible();
			
			/* get selected component*/
			int x = boardView.getActivePiecePosX();
			int y = boardView.getActivePiecePosY();
			BasicPanel square = boardView.getSubComponent(x, y);
			
			/* highlight movable area*/
			if(square.getState() == PanelState.PIECE_NON_CHOSEN){
				int distance = board.getPieceByXandY(x, y).getMovableDistance();
				boardView.setChosenPieceMovableArea(x, y, distance);
				boardView.setState(PanelState.BOARD_WAIT_FOR_MOVE);
			}
			else{
				//error message
				System.out.println(square.getState().toString());
			}		
		}		
	}

	private class PieceAttackButtonController implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			/* after button clicked, action menu get invisible */
			backPanel.setPieceMenuInvisible();
			
			/* get chosen piece */
			int x = boardView.getActivePiecePosX();
			int y = boardView.getActivePiecePosY();
			Piece p = board.getPieceByXandY(x, y);
			
			/* highlight attackable area*/
			SquareComponent attackPiece = DecoratedPieceProducer.generateAttackPiece(p);
			ArrayList<Coordinate> clist = (ArrayList<Coordinate>) ((AbstractAttackPieceDecorator)attackPiece).getAttackField();
			
			for(Coordinate c: clist){
				boardView.highlightAttackArea(c.getCoordinateX(), c.getCoordinateY());
			}
			/* update board state */
			boardView.setState(PanelState.BOARD_WAITING_FOR_ATTACK);
		}		
	}
}
