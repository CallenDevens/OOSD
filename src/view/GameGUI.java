package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Board;
import model.Game;
import model.Piece;

public class GameGUI extends JFrame{
	public static int squareSize = 60;
	Game game = new Game();
	Board board= Game.getBoard();
	int size = Board.size;	
	
	BoardPanel boardPanel = new BoardPanel(board);
	
	public GameGUI(){
		board.getPieceByXandY(0, 1);
		this.add(boardPanel);
		this.setTitle("Game");  
		this.setLocation(300, 200);  
		this.setSize(squareSize*size, squareSize* size);  
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setVisible(true);
		
	    this.startGame();		
	}  



	private void startGame() {
		//System.out.println("start Game");
	    game.startOneTurn();	    
	}



	public static void main(String[] args) {  
		GameGUI gameGUI  = new GameGUI();
  
    }
}
