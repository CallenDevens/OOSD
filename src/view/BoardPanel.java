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

	public SquarePanel[][] grids ;  
	public int size = -1;

	/* activePiecePosX, activePiecePosY
	 * coordinates of a piece that is chosen yet not moved by the player
	 * if no piece is set active, assign -1 to them 
	 * */
	public int activePiecePosX = -1;
	public int activePiecePosY = -1;
	
	public Image imgBackground = null;

    @Override
	protected void paintComponent(Graphics g) {
    	// load background image
	     super.paintComponent(g);
	     g.drawImage(imgBackground, 0,0, this);
	}

	public BoardPanel(int size){
		this.size = size;
		try {
			this.imgBackground = ImageIO.read(new File("image/map.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		grids = new SquarePanel[size][size];		
		initBoard();
	}

	public void initBoard() {
		 this.setLayout(new GridLayout(size,size));  
		 int count = 0;  
		 for(int i = 0; i < grids.length; i++) {  
		      for(int j = 0; j < grids.length; j++) {  
		    	  grids[i][j] = new SquarePanel();
		          this.add(grids[i][j]);
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
}
