package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SquarePanel extends BasicPanel{	
    private JButton pieceButton;
    private int posX;
    private int posY;
    
    private PanelState state;
	private float transparency;

    public SquarePanel() {
    	this.setSize(60, 60);
    	this.setMinimumSize(new Dimension(60, 60));
    	this.setPreferredSize(getSize());
    	
    	pieceButton = null;
 //   	this.state = EMPTY_SQUARE;
    	
    	this.setState(PanelState.SQUARE_EMPTY);
    	this.setOpaque(false);
    	this.setBackground(Color.YELLOW);
        this.setBorder(BorderFactory.createEtchedBorder());
    }
    
    public JButton getClickablePiece(){
    	return this.pieceButton;
    }

	public boolean isPiece() {
		return pieceButton!=null;
	}
	
	public void addPiece(JButton p){
		this.pieceButton = p;
		this.add(p);
		this.setState(PanelState.PIECE_NON_CHOSEN);
	}
	
	public void setPiece(String string) {
		pieceButton = new JButton();
		pieceButton.setPreferredSize(new Dimension(38,38));
		pieceButton.setMinimumSize(new Dimension(38, 38));
		pieceButton.setMaximumSize(new Dimension(38, 38));

		ImageIcon imageIcon = this.resizeImage(new ImageIcon("image/icons/"+string));
		
		pieceButton.setIcon(imageIcon);
		
		this.setState(PanelState.PIECE_NON_CHOSEN);
		this.add(pieceButton);
		this.revalidate();
		this.repaint();
	}
	
	private ImageIcon resizeImage(ImageIcon oldImage){
		Image img = oldImage.getImage();
		Image newimg = img.getScaledInstance(56, 56,java.awt.Image.SCALE_SMOOTH ) ; 
		return new ImageIcon(newimg);

	}

	public PanelState getState() {
		return this.state;
	}
	
	public void setState( PanelState state){
		this.state = state;
	}

	
	public void clean() {
		this.setTransparent(0);
		this.setBackground(Color.YELLOW);
		
		if(this.pieceButton!=null){
			this.setState(PanelState.PIECE_NON_CHOSEN);
		}
		else{
			this.setState(PanelState.SQUARE_EMPTY);;
		}
		this.revalidate();
		this.repaint();
		
	}
	
	
	public JButton removePieceButton(){
		JButton p = this.pieceButton;
		if(this.pieceButton!=null){
			this.removeAll();;
			pieceButton = null;
		}
		this.setState(PanelState.SQUARE_EMPTY);;
		return p;
	}

	public void markMovable() {
		//this.setOpaque(true);
		this.setState(PanelState.SQUARE_MOVABLE);
		this.setTransparent((float) 0.5);
		this.repaint();

	}
	
	public boolean isMovable(){
		return  this.state == PanelState.SQUARE_MOVABLE;
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

	public void markAttackable() {
    	this.setBackground(Color.RED);
		this.setState(PanelState.SQUARE_ATTACKABLE);
		this.setTransparent((float) 0.5);
		this.repaint();
		
	} 
}
