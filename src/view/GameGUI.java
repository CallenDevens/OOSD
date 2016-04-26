package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controller.BoardController;
import model.Board;
import model.Game;
import model.Piece;

public class GameGUI extends JFrame{

	public static int squareSize = 40;
	
	private JPanel mainPanel = new JPanel();
	private BoardFramePanel backPanel = new BoardFramePanel();
	private JPanel recordPanel = new JPanel();
	
	public BoardFramePanel getLayeredBoardPanel(){
		return this.backPanel;
	}
	public void addComponentPanel(BoardPanel bPanel){
		
		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		
		mainPanel.setLayout(fl);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		recordPanel.setSize(300, 500);
		mainPanel.setSize(660+300, 660+200);
		
		this.recordPanel.add(new JButton("button"));
		backPanel.addBoardView(bPanel);
		backPanel.setVisible(true);

		mainPanel.add(backPanel);		
		recordPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		mainPanel.add(recordPanel);
		this.getContentPane().add(mainPanel);
	}
	
	public GameGUI(){		
		this.setTitle("Game");  
//		this.setLocation(300, 200);  
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setVisible(true);
	}

}
