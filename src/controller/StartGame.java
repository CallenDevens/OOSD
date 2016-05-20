package controller;

/* Entry of the application, bind game controller with view and model */
import model.Board;
import model.Game;
import view.BoardPanel;
import view.GameGUI;

public class StartGame {

	Game game;
	GameGUI view;

	public StartGame() {
		game 	= new Game();
		game.initializeGame();

		int bWidth = game.getBoardDimensionWidth();
		int bHeight = game.getBoardDimensionHeight();
		view 	= new GameGUI(bWidth, bHeight);

		
		GameController gController = new GameController();
		gController.setGameModel(game);
		gController.setView(view);
		
		view.addWindowListener(gController);

	} //end of StartGame()
	
	public StartGame(Game game) {
		this.game = game;
		
		game.initializeGame();
		int bWidth = game.getBoardDimensionWidth();
		int bHeight = game.getBoardDimensionHeight();
		view 	= new GameGUI(bWidth, bHeight);

		GameController gController = new GameController();
		gController.setGameModel(game);
		gController.setView(view);
		view.addWindowListener(gController);		
	}

	
	
	public static void main(String [] args){
		StartGame sg = new StartGame();
	}
}
