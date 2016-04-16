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

import javax.imageio.ImageIO;

import model.Board;
import model.Piece;
import view.BoardPanel;
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
    	
    	this.addSquareActionListener();
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
	private void addSquareActionListener(){
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
				if(board.isPiece(i, j)){
					grids[i][j].getClickablePiece().addActionListener(new PieceController(x, y));
				}//end of if(grids[i][j].isPiece())
				else{
					grids[i][j].addMouseListener(new SquareController(x, y));
				}
				
				//TODO if a square holds something else
			}
		}
	}// End of addSquareActionListener()
	
	/* a class implements ActionListener to specify actions to be performed when pieces are clicked.
	 * make it inner to get an access to board model as coordinates are required during the procedure.
	 * */
	private class PieceController implements ActionListener{
		private int x;
		private int y;
		BoardPanel bp = BoardController.this.boardView;

		public PieceController(int x, int y){
			this.x = x;
			this.y = y;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
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
				SquarePanel square = bp.grids[x][y];

				if(square.getState() == SquarePanel.PIECE_NON_CHOSEN){
					int distance = board.getPieceByXandY(x, y).getMovableDistance();
					bp.setChosenPiece(x, y, distance);
				}
				else{
					bp.setNonChosenPiece(x, y);
				}
			}
			else{
				bp.resetPieceMoveState();
			}
			
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
    	
		this.setPieces();
    	this.addSquareActionListener();
		
		boardView.repaint();
		board.switchActivePieces();

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
			 *  is contained in highlighted area && there is some chosen piece
			 * move chosen piece onto the square
			 * 
			 * else do nothing
			 * */
	
			if(!bp.isMovableSquare(x,y)){
				return;
			}
			if(bp.activePiecePosX != -1&& bp.activePiecePosY!=-1){
				
				int pieceX = bp.activePiecePosX;
				int pieceY = bp.activePiecePosY;
					
				board.movePieceFromTo(pieceX, pieceY, x, y);
				bp.resetPieceMoveState();
				
				refreshPieces();
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
}
