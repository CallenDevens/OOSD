package view;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class TransparentPanel extends JPanel{
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
    
	public void moveAndShowUp(int posX, int posY) {
		this.setLocation((posY)*50, (posX+1)*50);
		this.setVisible(true);
		this.repaint();
	}
	

}
