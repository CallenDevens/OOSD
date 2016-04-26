package view;

import java.awt.FlowLayout;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class BoardFramePanel extends JLayeredPane{
	private final static Integer BOARD_LAYER_NUM = 100;
	private final static Integer MENU_LAYER_NUM = 200;
	
	
	private JPanel boardView ;
	private JPanel pieceMenuView;
	private JPanel pieceInfoView;
	
	public BoardFramePanel(){
		this.setLayout(new FlowLayout());
		this.setVisible(true);
	}
	
	public void addBoardView(JPanel bp){
		this.boardView = bp;
		this.add(boardView, BOARD_LAYER_NUM);
	}
	
	public void addpieceMenuView(JPanel pm){
		this.pieceMenuView = pm;
		this.add(pieceMenuView, MENU_LAYER_NUM);
	}
	
	public void removePieceMenu(){
		if(this.pieceMenuView != null){
			this.remove(pieceMenuView);
			this.repaint();
			this.pieceMenuView = null;	
		}
	}

	public boolean isMenuShown() {
		return pieceMenuView!=null;
	}

}
