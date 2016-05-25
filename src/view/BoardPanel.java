package view;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class BoardPanel extends BasicPanel{
	
	private SquarePanel[][] grids ;  
	private int dimension_width = -1;
	private int dimension_height = -1;
	private PanelState state;

	private int activePiecePosX = -1;
	private int activePiecePosY = -1;

	private Image imgBackground = null;

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
    
	public void setState(PanelState state){
		this.state = state;
	}
	public boolean isBoardPieceChoosen(){
		return this.activePiecePosX!=-1&&this.activePiecePosY!=-1;
	}
	
	public void setActivePieceCoordinates(int x, int y){
		this.activePiecePosX = x;
		this.activePiecePosY = y;
	}
	
	public boolean isActivePice(int x, int y){
		return this.activePiecePosX == x && this.activePiecePosY == y;
	}

	public BoardPanel(int height, int width){
		this.setLocation(0,0);
		
		this.dimension_width = width;
		this.dimension_height = height;

		try {
			this.imgBackground = ImageIO.read(new File("image/map.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		grids = new SquarePanel[height][width];		
		initBoard();
		
		this.setSize(60*width, 60*height);
		this.setMinimumSize(getSize());
		this.setPreferredSize(getSize());
		
		this.setState(PanelState.BOARD_STATE_UNCERTAIN);
	}

	public void initBoard() {
		 this.setLayout(new GridBagLayout());
		 
		 for(int i = 0; i < this.dimension_height; i++) {  
		      for(int j = 0; j < this.dimension_width; j++) {  
		    	  grids[i][j] = new SquarePanel();
		          this.addComponent(grids[i][j], i,j);
		     }
		 }
	}
	
	@Override
	protected void addComponent(BasicPanel panel, int posX, int posY){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = posY;
		c.gridy = posX;
		this.add(panel, c);

	}
    
	protected  void markMovableArea(int x, int y, int distance) {
		for(int i = 0; i < dimension_height; i++){
			for(int j = 0; j < dimension_width; j++){
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
	
	public void setChosenPieceMovableArea(int x, int y, int distance) {
		this.activePiecePosX = x;
		this.activePiecePosY = y;
		grids[x][y].setState(PanelState.PIECE_CHOSEN);
		this.markMovableArea(x, y, distance);		
	}

	public void setNonChosenPiece(int x, int y) {
		this.activePiecePosX = -1;
		this.activePiecePosY = -1;
		grids[x][y].setState(PanelState.PIECE_NON_CHOSEN);	
		this.cancelHighlighted();
	}

	public void resetPieceMoveState() {
		this.activePiecePosX = -1;
		this.activePiecePosY = -1;
	}

	@Override
	public BasicPanel getSubComponent(int posX, int posY){
		return this.grids[posX][posY];
	}

	public PanelState getState() {
		return this.state;
	}

	public void highlightAttackArea(int x, int y) {
		 this.grids[x][y].markAttackable();
		 this.repaint();
	}

	
	public void addPieceOn(BasicPanel bp, int x, int y) {
		this.grids[x][y].addComponent(bp);
	}
	

	public void setPieceOnBoard(String imgName,int x, int y) {
		this.grids[x][y].setPiece(imgName);
	}

	public void cleanAllSquares() {
    	for(int i =0 ;i < this.dimension_height; i++){
    		for(int j = 0; j < this.dimension_width; j++){
    			grids[i][j].clean(); 
    		}
    	}
    	this.repaint();
	}

	
	public BasicPanel removePieceOn(int pieceX, int pieceY) {
		return grids[pieceX][pieceY].removeSquareComponent();
	}

	@Override
	public void moveAndShowUp(int posX, int posY) {
		// TODO Auto-generated method stub
		
	}
}
