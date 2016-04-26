package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SquarePanel extends JPanel{

	public final static int PIECE_NON_CHOSEN = 100;
	public final static int PIECE_CHOSEN = 101;
	public final static int EMPTY_SQUARE = 102;
	public final static int MOVABLE_SQUARE = 103;
	
    private JButton pieceButton;
    private int posX;
    private int posY;
    
    private int state;
	private float transparency;

    public SquarePanel() {
    	this.setSize(60, 60);
    	pieceButton = null;
    	this.state = EMPTY_SQUARE;
    	this.setOpaque(false);
    	this.setBackground(Color.YELLOW);
        this.setBorder(BorderFactory.createEtchedBorder());
    }
    
    public void addImage(String imageURL){

    }
    
    public JButton getClickablePiece(){
    	return this.pieceButton;
    }

	public boolean isPiece() {
		return pieceButton!=null;
	}

	public void setPiece(String string) {
		pieceButton = new JButton();
		pieceButton.setPreferredSize(new Dimension(38,38));
		pieceButton.setMinimumSize(new Dimension(38, 38));
		pieceButton.setMaximumSize(new Dimension(38, 38));

		ImageIcon imageIcon = this.resizeImage(new ImageIcon("image/icons/"+string));
		
		pieceButton.setIcon(imageIcon);
		
		this.state = PIECE_NON_CHOSEN;
		this.add(pieceButton);
		
		this.revalidate();
		this.repaint();
	}
	
	private ImageIcon resizeImage(ImageIcon oldImage){
		Image img = oldImage.getImage();
		Image newimg = img.getScaledInstance(56, 56,java.awt.Image.SCALE_SMOOTH ) ; 
		return new ImageIcon(newimg);

	}

	public int getState() {
		return this.state;
	}
	
	public void setState( int st){
		this.state = st;
	}

	public void clean() {
		this.setTransparent(0);
		if(this.pieceButton!=null){
		   this.removeAll();;
		   pieceButton = null;
		}
		
		this.revalidate();
		this.repaint();
		this.setState(EMPTY_SQUARE);;
		
	}

	public void markMovable() {
		//this.setOpaque(true);
		this.setState(MOVABLE_SQUARE);
		this.setTransparent((float) 0.5);
		this.repaint();

	}
	
	public boolean isMovable(){
		return  this.state == MOVABLE_SQUARE;
	}
	
    public void setTransparent(float transparency) {  
        this.transparency = transparency;  
        this.repaint();
    }  
      
    @Override  
    protected void paintComponent(Graphics g){  
        super.paintComponent(g);  
          
            Graphics2D graphics2d = (Graphics2D) g.create();  
              
            graphics2d.setComposite(AlphaComposite.SrcOver.derive(transparency));  
              
              
            graphics2d.setColor(getBackground());  
            graphics2d.fillRect(0, 0, getWidth(), getHeight());                
            graphics2d.dispose();  
    } 
}
