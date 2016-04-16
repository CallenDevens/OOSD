package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import model.Board;
import model.Game;
import view.BoardPanel;
import view.GameGUI;
/* implements WindowsListner to launch initialization of MVC binds*/

public class GameController implements WindowListener{

	public static int squareSize = 60;
	
	private Game game;
	private GameGUI gameGUI;
	
	private BoardPanel boardPanel;
	private Board board;
	
	public void setGameModel(Game g){
		this.game = g;
	}
	
	public void setView(GameGUI frame){
		this.gameGUI = frame;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {

		int size = Game.getBoard().size;
		int squareSize = gameGUI.squareSize;

		boardPanel= new BoardPanel(size);

		this.gameGUI.addBoardPanel(boardPanel);
		this.gameGUI.setSize(squareSize*size, squareSize* size);  

		this.gameGUI.revalidate();
		this.gameGUI.repaint();		
		this.game.startOneTurn();
		
		/* call addBoardController here 
		as board controller must be bound to model and view 
		after GameController is stably connected to its M and V
		*/
		this.addBoardController();
		
	}

	private void addBoardController() {
		board = Game.getBoard();
		
		BoardController bController = new BoardController();
		bController.setModel(board);
		bController.setView(boardPanel);
		boardPanel.addComponentListener(bController);
		
		//invoke component listener
		boardPanel.setVisible(false);
		boardPanel.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
