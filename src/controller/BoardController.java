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

public class BoardController implements ComponentListener{
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
	private void setPieces(){
    	ArrayList<Piece> pieces = board.getPieces();
    	for(Piece p: pieces){
    		int x = p.getPosX();
    		int y = p.getPosY();
    		boardView.grids[x][y].setPiece(p.getPieceClass().toString()+".png");
    	}
	}
	
	private void addSquareActionListener(){
		int size = board.size;
		SquarePanel [][] grids = boardView.grids;

		for(int i = 0; i< size; i++){
			for(int j =0; j< size; j++){
				
				final int x = i;
				final int y = j;
				
				if(board.isPiece(i, j)){
					grids[i][j].getClickablePiece().addActionListener(new PieceController(x, y));
				}//end of if(grids[i][j].isPiece())
				else{
					grids[i][j].addMouseListener(new SquareController(x, y));
				}
			}
		}
	}
	
	
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
