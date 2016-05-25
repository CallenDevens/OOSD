package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;

import javax.swing.JLayeredPane;

public class BoardFramePanel extends JLayeredPane{
	private final static Integer BOARD_LAYER_NUM = 100;
	private final static Integer MODEL_LAYER_NUM = 150;

	private final static Integer MENU_LAYER_NUM = 200;
	private final static Integer PIECE_INFO_NUM = 300;
	
	private BoardPanel boardView ;
	private MenuPanel pieceMenuView;
	private StateSelectionPanel stateMenuView;
	private SquareComponentInfoPanel pieceInfoView;
	
	public BoardFramePanel(int bHeight, int bWidth){
		
		this.boardView = new BoardPanel(bHeight, bWidth);
		this.setLayout(new LayeredPaneLayout(this));
		this.setVisible(true);
		
		this.setSize(bWidth*60, bHeight*60);
		this.setMinimumSize(getSize());
		this.setPreferredSize(getSize());
		
		this.add(boardView, BOARD_LAYER_NUM);

		pieceMenuView = new MenuPanel();
		this.add(pieceMenuView, MENU_LAYER_NUM);
		
		pieceInfoView = new SquareComponentInfoPanel();
		this.add(pieceInfoView, PIECE_INFO_NUM);
		
		stateMenuView = new StateSelectionPanel();
		this.add(stateMenuView, MODEL_LAYER_NUM);
	}
	public BoardPanel getBoardView(){
		return this.boardView;
	}
	public SquareComponentInfoPanel getPieceInfoPanel(){
		return this.pieceInfoView;
	}
	
	public void setPieceInfoPanelContent(String job, String atk, String hp, String range, String des){
		
		pieceInfoView.setJobLabel(job);
		pieceInfoView.setImageIcon(job+".png");
		pieceInfoView.setAttackPointLabel(atk);
		pieceInfoView.setHealthPointLabel(hp);
		pieceInfoView.setMoveRangeLabel(range);
		pieceInfoView.setDescriptionLabel(des);
		
	}
	
	public void addPieceMoveButtonListener(ActionListener l){
		this.pieceMenuView.addMoveButtonListener(l);
	}
	public void addPieceAttackButtonListener(ActionListener l)  {
		this.pieceMenuView.addAttackButtonListener(l);		
	}

	
	public void moveAndShowPieceMenu(int poxX, int posY){
		this.pieceMenuView.moveAndShowUp(poxX, posY);
		this.revalidate();
		this.repaint();
	}
	
	public void moveAndShowStateMenu(int poxX, int posY){
		this.stateMenuView.moveAndShowUp(poxX, posY);
		this.pieceMenuView.setVisible(false);
		this.pieceInfoView.setVisible(false);
		this.revalidate();
		this.repaint();
	}

	
	public void moveAndShowPieceInfo(int poxX, int posY){
		this.pieceInfoView.moveAndShowUp(poxX, posY);
		this.revalidate();
		this.repaint();
	}
	
	public void setPieceMenuInvisible(){
		if(this.pieceMenuView != null){
			this.pieceMenuView.setVisible(false);
			this.pieceMenuView.repaint();
//			this.pieceMenuView = null;	
		}
	}
	

	public boolean isMenuShown() {
		return pieceMenuView.isVisible();
	}
	
	
	
	public class LayeredPaneLayout implements LayoutManager {

	    private final Container target;
	    private final Dimension preferredSize = new Dimension(500, 500);

	    public LayeredPaneLayout(final Container target) {
	            this.target = target;
	    }

	    @Override
	    public void addLayoutComponent(final String name, final Component comp) {
	    }

	    @Override
	    public void layoutContainer(final Container container) {
	            for (final Component component : container.getComponents()) {
//	            	System.out.println(component.getWidth());
	            	Point p = component.getLocation();
//	            	System.out.println(p.x+", " +p.y);
	                    component.setBounds(new Rectangle(p.x, p.y, component.getWidth(), component.getHeight()));
	            }
	    }

	    @Override
	    public Dimension minimumLayoutSize(final Container parent) {
	            return preferredLayoutSize(parent);
	    }

	    @Override
	    public Dimension preferredLayoutSize(final Container parent) {
	            return preferredSize;
	    }

	    @Override
	    public void removeLayoutComponent(final Component comp) {
	    }
	}



	public  MenuPanel getMenuPane() {
		return this.pieceMenuView;
	}

	public void setPieceInfoInvisible() {
		if(this.pieceInfoView != null){
			this.pieceInfoView.setVisible(false);
			this.pieceInfoView.repaint();
		}
	}

	public void enableMenuMove() {
		this.pieceMenuView.enableMove();
	}

	public void setMenuVisisble(boolean b) {
		this.pieceMenuView.setVisible(b);
		
	}

	public void addMenuResignActionListener(ActionListener l) {
		this.pieceMenuView.addMenuResignActionListener(l);
		
	}

	public boolean menuMoveEnabled() {
		return this.pieceMenuView.isMoveEnabled();
	}

	public void disableMenuMove() {
		this.pieceMenuView.disableMove();	
	}
	public void addBoardViewListener(ComponentListener bController) {
		this.boardView.addComponentListener(bController);
		this.boardView.setVisible(false);
		this.boardView.setVisible(true);
	}
	public void setStateMenuInvisible() {
		this.stateMenuView.setVisible(false);
		this.stateMenuView.repaint();
		
	}
	public void addModelListener(String string, ActionListener l) {
		this.stateMenuView.getButtons().get(string).addActionListener(l);
		
	}
	public void setPieceStateMenuInvisible() {
		this.stateMenuView.setVisible(false);
		
	}
	public void addMenuCancelActionListener(ActionListener l) {
		this.pieceMenuView.addCancelButtonListener(l);
		
	}
}
