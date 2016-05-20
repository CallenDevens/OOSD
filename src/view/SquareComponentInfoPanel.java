package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SquareComponentInfoPanel extends BasicPanel{

	private JLabel jobLabel = new JLabel();
	private JLabel imageIcon = new JLabel();
	private JLabel attackPointLabel = new JLabel();
	private JLabel healthPointLabel = new JLabel();
	private JLabel moveRangeLabel = new JLabel();
	private JLabel description = new JLabel();
	
	public ImageIcon icon = new ImageIcon();
	public String imageAddress;
	
	/**Layout property construcitons*/
	Box baseBox, boxV1, boxV2, boxV3, boxC1, boxC2;
	
	public SquareComponentInfoPanel(){
		
		/**create box V1*/
		boxV1 = Box.createHorizontalBox();
		boxV1.add(jobLabel);
		
		/**create box V2*/
		icon = new ImageIcon(resizeImage(new ImageIcon(imageAddress)));
		imageIcon.setIcon(icon);
		boxC1 = Box.createVerticalBox();
		boxC1.add(imageIcon);
		
		boxC2 = Box.createVerticalBox();
		boxC2.add(attackPointLabel);
		boxC2.add(Box.createVerticalStrut(5));
		boxC2.add(healthPointLabel);
		boxC2.add(Box.createVerticalStrut(5));
		boxC2.add(moveRangeLabel);
		
		boxV2 = Box.createHorizontalBox();
		boxV2.add(boxC1);
		boxV2.add(Box.createHorizontalStrut(20));
		boxV2.add(boxC2);
		
		/**create box V3*/
		boxV3 = Box.createHorizontalBox();
		boxV3.add(description);
		
		/**create base Box */
		baseBox =  Box.createVerticalBox();
		baseBox.add(boxV1);
		baseBox.add(Box.createVerticalStrut(20));
		baseBox.add(boxV2);
		baseBox.add(Box.createVerticalStrut(20));
		baseBox.add(boxV3);
		
		/**add components*/
	
		jobLabel.setForeground(Color.WHITE);
		imageIcon.setBorder(BorderFactory.createLineBorder(Color.decode("#1691D9"),1));
		attackPointLabel.setForeground(Color.WHITE);
		healthPointLabel.setForeground(Color.WHITE);
		moveRangeLabel.setForeground(Color.WHITE);
		description.setForeground(Color.WHITE);
		
		this.setBackground(Color.DARK_GRAY);
		this.setOpaque(false);
		this.setTransparent((float) 0.8);
		this.setBorder(BorderFactory.createLineBorder(Color.decode("#1691D9"),1));
		this.setLocation(0,0);
		this.add(baseBox);
		this.setSize(160,200);
		this.setVisible(false);
		
	}
	
	public void setJobLabel(String s)
	{
		jobLabel.setText(s);
	}
	
	public void setImageIcon(String s)
	{
		ImageIcon imageIcon = new ImageIcon(resizeImage(new ImageIcon("image/icons/"+s)));
		this.imageIcon.setIcon(imageIcon);
		this.imageIcon.repaint();
	}
	
	public void setAttackPointLabel(String s)
	{
		attackPointLabel.setText("ATK: "+s);
	}
	
	public void setHealthPointLabel(String s)
	{
		healthPointLabel.setText("HP: " + s);
	}
	
	public void setMoveRangeLabel(String s)
	{
		moveRangeLabel.setText("AGI:" + s);
	}
	
	public void setDescriptionLabel(String s)
	{
		description.setText(s);
	}

	@Override
	public void moveAndShowUp(int posX, int posY) {
		this.setLocation((posY)*50, (posX+1)*50);
		this.setVisible(true);
		this.repaint();
	}
}
