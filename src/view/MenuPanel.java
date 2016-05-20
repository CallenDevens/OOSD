package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends BasicPanel{

	private Map<String, JButton> menuitems = new LinkedHashMap<String,JButton>();
	
	private JButton moveButton = new JButton("MOVE");
	private JButton atkButton = new JButton("ATTACK");
	private JButton resignButton = new JButton("RESIGN");
	private JButton cancelButton =new JButton("CANCEL");
	
	public MenuPanel(){
		
		this.setBackground(Color.BLACK);
		this.setOpaque(false);
		this.setTransparent((float) 0.7);

		this.menuitems.put(moveButton.getText(), moveButton);
		this.menuitems.put(atkButton.getText(), atkButton);
		this.menuitems.put(resignButton.getText(),resignButton);
		this.menuitems.put(cancelButton.getText(),cancelButton);
		
		this.setBorder(BorderFactory.createLineBorder(Color.decode("#1691D9"),2));

		customizeMenuItems();
		
		this.setLocation(0,0);
		this.setVisible(false);
		this.setSize(100,150);
		
		this.cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				MenuPanel.this.setVisible(false);
			}
			
		});
	}
	
	private void customizeMenuItems() {
		for(JButton button:menuitems.values()){
			this.menuItemStyleSetter(button);
			this.add(button);
		}
		
	}
	private void menuItemStyleSetter(JButton button){
		button.setMinimumSize(new Dimension(80,30));
		button.setPreferredSize(new Dimension(80,30));
		button.setBackground(Color.BLACK);
		button.setForeground(Color.WHITE);
		button.setBorder(BorderFactory.createLineBorder(Color.decode("#1691D9"),1));
		
		button.addMouseListener(new MouseAdapter(){
			public void mouseEntered(java.awt.event.MouseEvent evt) {
		        button.setBackground(Color.WHITE);
		        button.setForeground(Color.BLACK);
		        button.setOpaque(true);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(Color.BLACK);
				button.setForeground(Color.WHITE);
				button.setOpaque(false);
			}
		});
	}

	public void addMoveButtonListener(ActionListener moveButtonController) {
		this.moveButton.addActionListener(moveButtonController);
	}
	
	public void addAttackButtonListener(ActionListener l) {
		this.atkButton.addActionListener(l);
	}

	
	public void enableMove(){
	    this.moveButton.setEnabled(true);
	}
	
	public void disableMove(){
		this.moveButton.setEnabled(false);
	}


	public void showUpAfterMove(int posX, int posY) {
		this.setLocation((posY)*50, (posX)*50);	
		this.setVisible(true);
		this.repaint();
	}


	public JButton getResignButton() {
		return this.resignButton;
	}

	public boolean isMoveEnabled() {
		return this.moveButton.isEnabled();
	}

	public void addMenuResignActionListener(ActionListener l) {
		this.resignButton.addActionListener(l);
		
	}

	@Override
	public void moveAndShowUp(int posX, int posY) {
		this.setLocation((posY)*50, (posX+1)*50);
		this.setVisible(true);
		this.repaint();
	}


}
