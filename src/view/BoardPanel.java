package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
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
	
	private Image imgBackground = null;
	

     @Override
	protected void paintComponent(Graphics g) {
	     super.paintComponent(g);
	     g.drawImage(imgBackground, 0,0, this);
	}

	public BoardPanel(Board board){
		this.board = board;
		this.size = board.size;
		try {
			this.imgBackground = ImageIO.read(new File("image/map.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		grids = new SquarePanel[size][size];	
		
		initBoard();
		initPieces();
		setPiecesListener();
	}


	private void initBoard() {
		 this.setLayout(new GridLayout(size,size));  
		
		 int count = 0;  
		 for(int i = 0; i < grids.length; i++) {  
		      for(int j = 0; j < grids.length; j++) {  

		    	  grids[i][j] = new SquarePanel();
		    	  /*
		          if(count % 2 == 0) {  
		              grids[i][j].setBackground(Color.WHITE);  
		          }
		          else 
		          {
		              grids[i][j].setBackground(Color.BLACK);  
		              grids[i][j].setForeground(Color.WHITE);  

		          } 
		          */
		    	  
		          this.add(grids[i][j]);
		          count++;
		     }
		      
		      count = (size%2 == 0?count+1: count);
		 }		
	}
	public void setPiecesListener(){
		for(int i = 0; i< size; i++){
			for(int j =0; j< size; j++){
				
				final int x = i;
				final int y = j;
				
				if(grids[i][j].isPiece()){
					grids[i][j].getClickablePiece().addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							
							if(board.getPieceByXandY(x, y).isMovable()){

								SquarePanel square = grids[x][y];
								if(square.getState() == SquarePanel.PIECE_NON_CHOSEN){
									BoardPanel.this.activePiecePosX = x;
									BoardPanel.this.activePiecePosY = y;
									square.setState(SquarePanel.PIECE_CHOSEN);
									BoardPanel.this.markMovableArea(x, y);
								}
								else{
									BoardPanel.this.activePiecePosX = -1;
									BoardPanel.this.activePiecePosY = -1;
									square.setState(SquarePanel.PIECE_NON_CHOSEN);
								}
							}
							
							else{
								BoardPanel.this.activePiecePosX = -1;
								BoardPanel.this.activePiecePosY = -1;
							}
							
						}
							
					});
				}//end of if(grids[i][j].isPiece())
				else{
					grids[i][j].addMouseListener(new MouseListener(){

						@Override
						public void mouseClicked(MouseEvent e) {
							if(!BoardPanel.this.isMovableSquare(x,y)){
								return;
							}
							if(BoardPanel.this.activePiecePosX != -1&&BoardPanel.this.activePiecePosY!=-1){
								int pieceX = BoardPanel.this.activePiecePosX;
								int pieceY = BoardPanel.this.activePiecePosY;
								
								board.movePieceFromTo(pieceX, pieceY, x, y);
								
								BoardPanel.this.activePiecePosX = -1;
								BoardPanel.this.activePiecePosY = -1;
								
								redrawPieces();
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
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
					});
				}
			}
		}
	}
    
	protected  void markMovableArea(int x, int y) {
		int distance = board.getPieceByXandY(x, y).getMovableDistance();		
		for(int i = 0; i < grids.length; i++){
			for(int j = 0; j < grids.length; j++){
				if((i-j >= ((x-y)-distance)) && (i - j <= (distance+(x-y)))){
					if((i+j >=(x+y-distance))&&(i+j <= (x + y + distance)))
						this.grids[i][j].markMovable();
				}
			}
		}
		
	}

	protected void redrawPieces() {
 //   	initBoard();
    	
    	for(int i =0 ;i < size; i++){
    		for(int j = 0; j < size; j++){
    			this.grids[i][j].clean(); 
    		}
    	}
    	
		initPieces();
		setPiecesListener();
		
		this.repaint();
		board.switchActivePieces();

	}

	private boolean isMovableSquare(int i, int j) {
		return grids[i][j].isMovable();
	}
	
	public void initPieces(){
    	ArrayList<Piece> pieces = board.getPieces();
    	for(Piece p: pieces){
    		int x = p.getPosX();
    		int y = p.getPosY();
    		grids[x][y].setPiece(p.getPieceClass().toString()+".png");
    	}
	}
}
