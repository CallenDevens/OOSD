package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameSetting {
	public static GameSetting setting = new GameSetting();
	
	private int board_length = 11;
	private int board_width = 11;

	private GameSetting(){
		readBoardSetting();
	}
	
	public static GameSetting getInstance(){
		return setting;
	}	
	
	public int getDimensionHeight(){
		return board_length;
	}
	
	public int getDimensionWidth(){
		return board_width;
	}
	
	public boolean isBoardSquare(){
		return this.board_length == this.board_width;
	}
	
	public void readBoardSetting(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("setting")));
			this.board_length = Integer.parseInt(br.readLine());
			this.board_width = Integer.parseInt(br.readLine());
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
