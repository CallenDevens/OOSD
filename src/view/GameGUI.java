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
	Game game = new Game();
	Board board= Game.getBoard();
	
	int size = Board.size;
	JLabel[][] grids = new JLabel[size][size];  
	
	public GameGUI(){
	   initBoard();
	   initPieces();
	   
		this.setTitle("Game");  
		this.setLocation(300, 200);  
		this.setSize(600, 600);  
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setVisible(true);
	}  
		      
    private void initPieces(){
    	ArrayList<Piece> pieces = board.getPieces();
    	for(Piece p: pieces){
    		int x = p.getPosX();
    		int y = p.getPosY();
    		grids[x][y].setText(p.getPieceClass());
    		
    	}
	}

	private void initBoard() {
		 this.getContentPane().setLayout(new GridLayout(size,size));  
		 int count = 0;  
		 for(int i = 0; i < grids.length; i++) {  
		      for(int j = 0; j < grids.length; j++) {  

		    	  grids[i][j] = new JLabel();
		          if(count % 2 == 0) {  
		              grids[i][j].setBackground(Color.WHITE);  
		          }
		          else 
		          {
		              grids[i][j].setBackground(Color.BLACK);  
		              grids[i][j].setForeground(Color.WHITE);  

		          }  
		          grids[i][j].setOpaque(true);
		         // grids[i][j].setText(count+"");
		          this.getContentPane().add(grids[i][j]);
		          count++;
		     }
		      
		      count = (size%2 == 0?count+1: count);
		 }		
	}

	public static void main(String[] args) {  
		GameGUI gameGUI  = new GameGUI();
  
    } 
	
}
