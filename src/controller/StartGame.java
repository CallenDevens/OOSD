package controller;

/** Entry of the application, bind game controller with view and model */
import model.Game;
import view.GameGUI;

public class StartGame {
	Game game;
	GameGUI view;
	
	/** start game from scratch*/
	public StartGame() {
		game 	= new Game();
		game.initializeGame();
		prepare();

	} //end of StartGame()
	
	/**
	 * restart game
	 * @param game model of game
	 */
	public StartGame(Game game) {
		/* continue game with given model */
		this.game = game;	
		prepare();
	}	
	/**
	 * initialize game GUI
	 * bind controller to game GUI;
	 */
	private void prepare(){
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
