package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import model.Game;
import utils.GameSetting;
import view.GameGUI;

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
		GameSL.loadBoard(game, "GameData.xml");
		gameGUI.dispose();		
		StartGame sg = new StartGame(game);
	}

}
