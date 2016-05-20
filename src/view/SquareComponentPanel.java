package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class SquareComponentPanel extends BasicPanel{

	private Image imgBackground = null;
		
    public SquareComponentPanel(String pClass){

    	this.setPreferredSize(new Dimension(38,38));
		this.setMinimumSize(new Dimension(38, 38));
		this.setMaximumSize(new Dimension(38, 38));
		imgBackground  = this.resizeImage(new ImageIcon("image/icons/"+pClass));
		this.setVisible(true);		
	}

	@Override
	protected void paintComponent(Graphics g) {
    	// load background image
	     super.paintComponent(g);
	     g.drawImage(imgBackground, 0,0, this);
	}

	@Override
	public void moveAndShowUp(int posX, int posY) {
		// TODO Auto-generated method stub
		
	}

}
