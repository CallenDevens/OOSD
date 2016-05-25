package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import model.Board;
import model.Game;
import utils.GameSetting;
import view.GameGUI;
import view.SettingMenuWindow;

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
		int option = JOptionPane.showOptionDialog(setwindow, 
				"Reastart game with new settings?",
				"Setting",
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, null, null);		
		  
		
		if (option == JOptionPane.YES_OPTION)
		{
			int boardWidth = setwindow.getSettingWidth();
			int boardHeight = setwindow.getSettingHeight();
			
			int mageNum = setwindow.getSettingMageNum();
			int rougeNum = setwindow.getSettingRougeNum();
			int warriorNum = setwindow.getSettingWarriorNum();
			
			int paladinNum = setwindow.getSettingPaladinNum();
			int prisstNum = setwindow.getSettingPrisstNum();
			int hunterNum = setwindow.getSettingHunterNum();
			
			int sum = mageNum + rougeNum + warriorNum;
			if(sum!=paladinNum + prisstNum + hunterNum){
				JOptionPane.showConfirmDialog(setwindow, "Players should hold same amount of pieces.");
				return;
			}
			
			else{
				setwindow.dispose();
				gameGUI.dispose();
				
				GameSetting.getInstance().setDimensionHeight(boardHeight);
				GameSetting.getInstance().setDimensionWidth(boardWidth);
				
				try {
					PrintWriter pw =new PrintWriter(new File("setting"));
					pw.println(boardHeight);
					pw.println(boardWidth);
					pw.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				game.initializeGame();
				StartGame sg = new StartGame(game);
			}
		}
	}
}
