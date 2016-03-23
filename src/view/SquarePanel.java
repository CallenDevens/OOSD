package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class SquarePanel extends JPanel{
	public final static int PIECE_NON_CHOSEN = 100;
	public final static int PIECE_CHOSEN = 101;
	
    private JButton pieceButton;
    private int posX;
    private int posY;
    
    private int state;

    public SquarePanel() {
 //   	this.setSize(80, 80);
    	pieceButton = null;
    	this.state = PIECE_NON_CHOSEN;
    }
    
    public void addImage(String imageURL){

    }
    
    public JButton getClickablePiece(){
    	return this.pieceButton;
    }

	public boolean isPiece() {
		// TODO Auto-generated method stub
		return pieceButton!=null;
	}

	public void setPiece(String string) {
		ImageIcon imageIcon = this.resizeImage(new ImageIcon(string));
		pieceButton.setIcon(imageIcon);
		
		this.add(pieceButton);
	}
	
	private ImageIcon resizeImage(ImageIcon oldImage){
		pieceButton = new JButton();
		pieceButton.setPreferredSize(new Dimension(56,56));
		pieceButton.setMinimumSize(new Dimension(56, 56));

		Image img = oldImage.getImage();
		Image newimg = img.getScaledInstance(78, 78,java.awt.Image.SCALE_SMOOTH ) ; 
		return new ImageIcon(newimg);

	}

	public int getState() {
		return this.state;
	}
	
	public void setState( int st){
		this.state = st;
	}
}
