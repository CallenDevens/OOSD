package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import model.Game;
import view.GameGUI;
/* implements WindowsListner to launch initialization of MVC binds*/

public class GameController implements WindowListener{

	public static int squareSize = 60;
	
	private Game game;
	private GameGUI gameGUI;
	
	public void setGameModel(Game g){
		this.game = g;
	}
	
	public void setView(GameGUI frame){
		this.gameGUI = frame;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		int boardHight = game.getBoardDimensionHeight();
		int boardWidth = game.getBoardDimensionWidth();	
		int squareSize = gameGUI.squareSize;
		
		this.gameGUI.addBoardPanel(boardHight, boardWidth);
		this.gameGUI.setSize(squareSize*boardWidth+300, squareSize* boardHight+200);  
		this.gameGUI.revalidate();
		this.gameGUI.repaint();	
		this.game.startOneTurn();
		
		this.addBoardController();
	}

	private void addBoardController() {
		
		BoardController bController = new BoardController();
		bController.setModel(Game.getBoard());
		bController.setView(gameGUI.getBoardPane());
		bController.setBackPanel(gameGUI.getLayeredBoardPanel());
		gameGUI.addBoardListener(bController);
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
