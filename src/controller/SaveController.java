package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Game;

public class SaveController implements ActionListener{
	private Game game;
	
	public SaveController(Game g){
		this.game = g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		GameSL.saveBoard(game, "GameData.xml");
	}

}
