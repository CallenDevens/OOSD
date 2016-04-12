package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.BoardController;
import model.Board;
import model.Game;
import model.Piece;

public class GameGUI extends JFrame{

	public static int squareSize = 60;
	
	public void addBoardPanel(BoardPanel bPanel){
		this.getContentPane().add(bPanel);
	}
	
	public GameGUI(){		
		this.setTitle("Game");  
		this.setLocation(300, 200);  
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setVisible(true);
	}

	public static void main(String[] args) {  
		//GameGUI gameGUI  = new GameGUI();  
    }
}
