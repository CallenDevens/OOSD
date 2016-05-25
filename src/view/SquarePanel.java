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
//    private JButton pieceButton;
	
	private SquareComponentPanel scp;
	
    private int posX;
    private int posY;
    
    private PanelState state;
	private float transparency;

    public SquarePanel() {
    	this.setSize(60, 60);
    	this.setMinimumSize(new Dimension(60, 60));
    	this.setPreferredSize(getSize());
    	
 //   	pieceButton = null;
 //   	this.state = EMPTY_SQUARE;
    	
    	this.setState(PanelState.SQUARE_EMPTY);
    	this.setOpaque(false);
    	this.setBackground(Color.YELLOW);
        this.setBorder(BorderFactory.createEtchedBorder());
    }
    
    @Override
	public BasicPanel getSubComponent(){
    	return this.scp;
	}

    
    public BasicPanel getSquareComponentView(){
    	return this.scp;
    }
    
	public boolean containsPiece() {
		return (this.getState() == PanelState.PIECE_CHOSEN || this.getState() == PanelState.PIECE_NON_CHOSEN);
	}
	
	@Override
	public void addComponent(BasicPanel p){
		this.scp = (SquareComponentPanel) p;
		this.add(scp);
		this.setState(PanelState.PIECE_NON_CHOSEN);
		this.repaint();
	}

	
	public void setPiece(String pClass) {
		this.scp = new SquareComponentPanel(pClass);		
		this.setState(PanelState.PIECE_NON_CHOSEN);
		
		this.addComponent(scp);
		
		this.revalidate();
		this.repaint();
	}
	
	@Override
	public PanelState getState() {
		return this.state;
	}
	
	public void setState( PanelState state){
		this.state = state;
	}
	
	public void clean() {
		this.setTransparent(0);
		this.setBackground(Color.YELLOW);
		
		if(this.containsPiece()||(this.getState() == PanelState.SQUARE_ATTACKABLE && this.scp!=null)){
			this.setState(PanelState.PIECE_NON_CHOSEN);
		}
		else{
			this.setState(PanelState.SQUARE_EMPTY);;
		}
		this.revalidate();
		this.repaint();
		
	}
	
	
	public BasicPanel removeSquareComponent(){
		BasicPanel p = this.scp;
		if(this.scp!=null){
			this.removeAll();;
			this.scp = null;
		}
		this.setState(PanelState.SQUARE_EMPTY);;
		this.repaint();
		return p;
	}

	public void markMovable() {
		if(this.scp == null){
			this.setState(PanelState.SQUARE_MOVABLE);
		}
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

	@Override
	public void moveAndShowUp(int posX, int posY) {
		
	} 
}
