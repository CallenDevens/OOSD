package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.SpringLayout.Constraints;

import model.Board;
import model.Piece;

public class BoardPanel extends JPanel{
	
	public SquarePanel[][] grids ;  
	public int size = -1;
	
	private BoardState state;

	/* activePiecePosX, activePiecePosY
	 * coordinates of a piece that is chosen yet not moved by the player
	 * if no piece is set active, assign -1 to them 
	 * */
	private int activePiecePosX = -1;
	private int activePiecePosY = -1;
	
	public Image imgBackground = null;

    @Override
	protected void paintComponent(Graphics g) {
    	// load background image
	     super.paintComponent(g);
	     g.drawImage(imgBackground, 0,0, this);
	}
    
    public int getActivePiecePosX(){
    	return this.activePiecePosX;
    }
    
    public int getActivePiecePosY(){
    	return this.activePiecePosY;
    }
    
	public void setState(BoardState state){
		this.state = state;
	}
	public boolean isBoardPieceChoosen(){
		return this.activePiecePosX!=-1&&this.activePiecePosY!=-1;
	}
	
	public void setActivePieceCoordinates(int x, int y){
		this.activePiecePosX = x;
		this.activePiecePosY = y;
	}

	public BoardPanel(int size){
		this.setLocation(0,0);
		this.size = size;
		try {
			this.imgBackground = ImageIO.read(new File("image/map.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		grids = new SquarePanel[size][size];		
		initBoard();
		
		this.setSize(60*11, 60*11);
		this.setMinimumSize(getSize());
		this.setPreferredSize(getSize());
	}

	public void initBoard() {
		 this.setLayout(new GridBagLayout());
		 
		 GridBagConstraints c = new GridBagConstraints();
		 c.gridx = 0;
		 c.gridy = 0;
		 
//		 int count = 0;  
		 for(int i = 0; i < grids.length; i++) {  
		      for(int j = 0; j < grids.length; j++) {  
		    	  grids[i][j] = new SquarePanel();
		    	 
		    	  c.gridx = j;
		    	  c.gridy = i;
		    	  
		          this.add(grids[i][j], c);
		          
//		          System.out.println(grids[i][j].getWidth());
		     }
		 }
		 

	}
	
    
	protected  void markMovableArea(int x, int y, int distance) {
		for(int i = 0; i < grids.length; i++){
			for(int j = 0; j < grids.length; j++){
				if((i-j >= ((x-y)-distance)) && (i - j <= (distance+(x-y)))){
					if((i+j >=(x+y-distance))&&(i+j <= (x + y + distance)))
						this.grids[i][j].markMovable();
				}
			}
		}
		
	}

	public void cancelHighlighted(){
		for(int i = 0; i < grids.length; i++){
			for(int j = 0; j < grids.length; j++){
				this.grids[i][j].setTransparent(0);;
			}
		}
	}

	public boolean isMovableSquare(int i, int j) {
		return grids[i][j].isMovable();
	}
	
	public void setChosenPiece(int x, int y, int distance) {
		this.activePiecePosX = x;
		this.activePiecePosY = y;
		grids[x][y].setState(SquarePanel.PIECE_CHOSEN);
		this.markMovableArea(x, y, distance);		
	}

	public void setNonChosenPiece(int x, int y) {
		this.activePiecePosX = -1;
		this.activePiecePosY = -1;
		grids[x][y].setState(SquarePanel.PIECE_NON_CHOSEN);	
		this.cancelHighlighted();
	}

	public void resetPieceMoveState() {
		this.activePiecePosX = -1;
		this.activePiecePosY = -1;
	}

	public SquarePanel getSquareByCoordinates(int x, int y) {
		return this.grids[x][y];
	}

	public BoardState getState() {
		return this.state;
	}

	public void highlightAttackArea(int x, int y) {
		 this.grids[x][y].markAttackable();
		 this.repaint();
	}
}
