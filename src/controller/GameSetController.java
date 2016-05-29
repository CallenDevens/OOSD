package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import model.Game;
import utils.GameSetting;
import view.GameGUI;
import view.SettingMenuWindow;

/** implements ActionListener, 
 *  defines tasks to be performed after user clicks setting item in game menu */
public class GameSetController implements ActionListener{
	
	private SettingMenuWindow setwindow;
	private Game game;
	private GameGUI gameGUI;	

	public void setSettingView(SettingMenuWindow sw){
		this.setwindow = sw;
	}
	
	public void setGameView(GameGUI gg){
		this.gameGUI = gg;
	}
	
	public void setGameModel(Game g){
		this.game =g;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		/* show dialog option*/
		int option = JOptionPane.showOptionDialog(setwindow, 
				"Reastart game with new settings?",
				"Setting",
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, null, null);		
		  
		
		/* player choose to restart the game */
		if (option == JOptionPane.YES_OPTION)
		{
			int boardWidth = setwindow.getSettingWidth();
			int boardHeight = setwindow.getSettingHeight();
			
			int pieceNum = setwindow.getSettingPieceNum();

			/* close setting window and main window */
			setwindow.dispose();
			gameGUI.dispose();
			
			/* update game setting */
			GameSetting.getInstance().setDimensionHeight(boardHeight);
			GameSetting.getInstance().setDimensionWidth(boardWidth);
			GameSetting.getInstance().setPieceNumber(pieceNum);

			
			try {
				PrintWriter pw =new PrintWriter(new File("setting"));
				pw.println(boardHeight);
				pw.println(boardWidth);
				pw.println(pieceNum);
				pw.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			/* start new game */
			game.initializeGame();
			StartGame sg = new StartGame(game);
		}
	}
}
