package view;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/** Component of Composite design pattern */
public abstract class BasicPanel extends JPanel{
	
	protected float transparency;

    protected void setTransparent(float transparency) {  
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
    
    public abstract void moveAndShowUp(int posX, int posY);
	
	protected Image resizeImage(ImageIcon oldImage){
		Image img = oldImage.getImage();
		Image newimg = img.getScaledInstance(56, 56,java.awt.Image.SCALE_SMOOTH ) ; 
		return newimg;

	}
	public PanelState getState() {
		return null;
	}

	
	public BasicPanel getSubComponent(){
		return null;
	}
	
	protected BasicPanel getSubComponent(int posX, int posY){
		return null;
	}
	
	public void addComponent(BasicPanel panel){
		
	}
	
	public void removeComponent(BasicPanel panel){
		
	}
	
	public void addComponent(BasicPanel panel, int posX, int posY){
		
	}
	
	public void removeComponent(int posX, int posY){
		
	}
}
