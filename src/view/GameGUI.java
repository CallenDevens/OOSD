package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controller.BoardController;
import model.Board;
import model.Game;
import model.Piece;

public class GameGUI extends JFrame{

	public static int squareSize = 40;
	
	private JPanel mainPanel = new JPanel();
	private JPanel recordPanel = new JPanel();
	private BoardFramePanel backPanel;
	
	private JMenuBar menuBar;
	private JMenu menu, submenu;
	
	public BoardFramePanel getLayeredBoardPanel(){
		return this.backPanel;
	}
	
	public BoardPanel getBoardPane(){
		return this.backPanel.getBoardView();
	}
	
	public void addBoardPanel(int bHeight, int bWidth){
		
		BoardPanel bPanel= new BoardPanel(bHeight, bWidth);

		FlowLayout fl = new FlowLayout();
		fl.setAlignment(FlowLayout.LEFT);
		
		mainPanel.setLayout(fl);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		recordPanel.setSize(300, 500);
		mainPanel.setSize(660+300, 660+200);
		mainPanel.setMinimumSize(mainPanel.getSize());
		mainPanel.setPreferredSize(mainPanel.getSize());
		
		this.recordPanel.add(new JButton("button"));
		
//		backPanel = new BoardFramePanel(bPanel);

		backPanel = new BoardFramePanel(bHeight,bWidth);

		backPanel.setVisible(true);

		mainPanel.add(backPanel);		
		recordPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
		mainPanel.add(recordPanel);
		this.getContentPane().add(mainPanel);
	}
	
	public GameGUI(){		
		this.initializeMenu();
		this.setJMenuBar(menuBar);

		this.setTitle("Game");  
//		this.setLocation(300, 200);  
		this.setResizable(false);
		this.setSize(660+300, 660+40);
		this.setMinimumSize(getSize());
		this.setPreferredSize(getSize());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setVisible(true);
	}
	
	private void initializeMenu() {
		menuBar = new JMenuBar();
		
		//Build the first menu.
		menu = new JMenu("Game");
		JMenu setMenu = new JMenu("Preferences");

		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);
		
		menuBar.add(setMenu);

		//a group of JMenuItems
		JMenuItem loadItem = new JMenuItem("load",KeyEvent.VK_T);
		menu.add(loadItem);
		
		JMenuItem saveItem = new JMenuItem("save");
		menu.add(saveItem);
	}

	public void addBoardListener(ComponentListener bController) {
		this.backPanel.addBoardViewListener(bController);
		
	}

}
