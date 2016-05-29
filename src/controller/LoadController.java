package controller;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.File;
import model.Game;
import view.GameGUI;

/** implements ActionListener, 
 *  defines tasks to be performed after user clicks load item in game menu */
public class LoadController implements ActionListener {
	
	GameGUI gameGUI;
	public LoadController(GameGUI gui){
		this.gameGUI = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Game game = new Game();
		File file = new File("GameData.xml");
		if(!file.exists()){
			return;
		}
		/* close window to redraw, for new game may have a board of different size */
		gameGUI.dispose();
		
		/* load save data into game model*/
		GameSL.loadBoard(game, "GameData.xml");
		StartGame sg = new StartGame(game);
	}
}
