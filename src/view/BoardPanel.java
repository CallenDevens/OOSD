package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Board;
import model.Piece;

public class BoardPanel extends JPanel{
	private SquarePanel[][] grids ;  
	private int size = -1;
	Board board ;

	private int activePiecePosX = -1;
	private int activePiecePosY = -1;

	public BoardPanel(Board board){
		this.board = board;
		this.size = board.size;
		grids = new SquarePanel[size][size];
		initBoard();
		initPieces();
		setPiecesListener();
		
		setSquarePieces();
	}
	

	private void initBoard() {
		 this.setLayout(new GridLayout(size,size));  
		 int count = 0;  
		 for(int i = 0; i < grids.length; i++) {  
		      for(int j = 0; j < grids.length; j++) {  

		    	  grids[i][j] = new SquarePanel();
		          if(count % 2 == 0) {  
		              grids[i][j].setBackground(Color.WHITE);  
		          }
		          else 
		          {
		              grids[i][j].setBackground(Color.BLACK);  
		              grids[i][j].setForeground(Color.WHITE);  

		          }  
		          this.add(grids[i][j]);
		          count++;
		     }
		      
		      count = (size%2 == 0?count+1: count);
		 }		
	}
	public void setPiecesListener(){
		for(int i = 0; i< size; i++){
			for(int j =0; j< size; j++){
				if(grids[i][j].isPiece()){
					
					final int x = i;
					final int y = j;
					
					grids[i][j].getClickablePiece().addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							
							if(board.getPieceByXandY(x, y).isMovable()){
								System.out.println(x + " " + y);

								SquarePanel square = grids[x][y];
								if(square.getState() == SquarePanel.PIECE_NON_CHOSEN){
									BoardPanel.this.activePiecePosX = x;
									BoardPanel.this.activePiecePosY = y;
									square.setState(SquarePanel.PIECE_CHOSEN);
								}
								else{
									BoardPanel.this.activePiecePosX = -1;
									BoardPanel.this.activePiecePosY = -1;
									square.setState(SquarePanel.PIECE_NON_CHOSEN);
								}
							}
							
						}
						
					});
				}//end of if(grids[i][j].isPiece())
			}
		}
	}
    public void initPieces(){
    	ArrayList<Piece> pieces = board.getPieces();
    	for(Piece p: pieces){
    		int x = p.getPosX();
    		int y = p.getPosY();
    		grids[x][y].setPiece("button.png");
    	}
	}
}
