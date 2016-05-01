package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class BoardFramePanel extends JLayeredPane{
	private final static Integer BOARD_LAYER_NUM = 100;
	private final static Integer MENU_LAYER_NUM = 200;
	
	
	private JPanel boardView ;
	private MenuPanel pieceMenuView;
	private JPanel pieceInfoView;
	
	public BoardFramePanel(JPanel bp){
		this.setLayout(new LayeredPaneLayout(this));
		this.setVisible(true);
		
		this.setSize(660, 660);
		this.setMinimumSize(getSize());
		this.setPreferredSize(getSize());
		
		
		this.boardView = bp;
		this.add(boardView, BOARD_LAYER_NUM);

		pieceMenuView = new MenuPanel();
		this.add(pieceMenuView, MENU_LAYER_NUM);
//		pieceMenuView.setVisible(true);
	}
	
	/* To be removed in next version

	public void addpieceMenuView(JPanel pm){
		this.pieceMenuView = pm;
		this.add(pieceMenuView, MENU_LAYER_NUM);
	}*/
	
	public void addPieceMoveButtonListener(ActionListener l){
		((MenuPanel) this.pieceMenuView).addMoveButtonListener(l);
	}
	public void addPieceAttackButtonListener(ActionListener l)  {
		((MenuPanel) this.pieceMenuView).addAttackButtonListener(l);		
	}

	
	public void moveAndShowPieceMenu(int poxX, int posY){
		((MenuPanel)this.pieceMenuView).moveAndShowUp(poxX, posY);
		this.revalidate();
		this.repaint();
	}
	
	public void showPieceMenuAfterMove(int x, int y) {
		((MenuPanel)this.pieceMenuView).showUpAfterMove(x, y);
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
	
	
	/* To be removed in next version
	public void removePieceMenu(){
		if(this.pieceMenuView != null){
			this.remove(pieceMenuView);
			this.repaint();
			this.pieceMenuView = null;	
		}
	}*/

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
}
