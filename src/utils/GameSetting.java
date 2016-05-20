package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GameSetting {
	public static GameSetting setting = null;
	
	private int board_height = 11;
	private int board_width = 11;

	private GameSetting(){
		readBoardSetting();
	}
	
	public static GameSetting getInstance(){
		if(setting == null){
			return new GameSetting();
		}
		else{
			return setting;
		}
	}	
	
	public int getDimensionHeight(){
		return board_height;
	}
	
	public int getDimensionWidth(){
		return board_width;
	}
	
	public void setDimensionHeight(int h){
		this.board_height = h;
	}
	
	public void setDimensionWidth(int w){
		this.board_width = w;
	}
	
	public boolean isBoardSquare(){
		return this.board_height == this.board_width;
	}
	
	public void readBoardSetting(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("setting")));
			
			this.board_height = Integer.parseInt(br.readLine());
			this.board_width = Integer.parseInt(br.readLine());
			
			br.close();
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
