package controller;

import model.Board;
import model.Game;
import view.BoardPanel;
import view.GameGUI;

public class StartGame {

	public StartGame() {

		//create Model and View
		Game game 	= new Game();
		GameGUI view 	= new GameGUI();
				
		GameController gController = new GameController();
		gController.setGameModel(game);
		gController.setView(view);
		
		view.addWindowListener(gController);

	} //StartGame()
	
	public static void main(String [] args){
		StartGame sg = new StartGame();
	}
}
