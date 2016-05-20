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

public class StateSelectionPanel extends BasicPanel{
	private Map<String, JButton> menuitems = new LinkedHashMap<String,JButton>();

	private JButton attackButton = new JButton("ATTACK MODEL");
	private JButton defenceButton = new JButton("DEFENSIVE MODEL");
	private JButton normalButton = new JButton("NORMAL MODEL");
	private JButton cancelButton = new JButton("CANCEL");

	public StateSelectionPanel(){
		
		this.setBackground(Color.BLACK);
		this.setOpaque(false);
		this.setTransparent((float) 0.7);

		this.menuitems.put(attackButton.getText(), attackButton);
		this.menuitems.put(defenceButton.getText(),defenceButton);
		this.menuitems.put(normalButton.getText(),normalButton);
		this.menuitems.put(cancelButton.getText(), cancelButton);
		
		this.setBorder(BorderFactory.createLineBorder(Color.decode("#1691D9"),2));

		customizeMenuItems();
		
		this.setLocation(0,0);
		this.setVisible(false);
		this.setSize(150,150);
		
		this.cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				StateSelectionPanel.this.setVisible(false);
			}
			
		});
	}
	
	private void menuItemStyleSetter(JButton button){
		button.setMinimumSize(new Dimension(120,30));
		button.setPreferredSize(new Dimension(120,30));
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


	private void customizeMenuItems() {
		for(JButton button:menuitems.values()){
			this.menuItemStyleSetter(button);
			this.add(button);
		}
		
	}

	@Override
	public void moveAndShowUp(int posX, int posY) {
		this.setLocation((posY)*50, (posX+1)*50);
		this.setVisible(true);
		this.repaint();
	}

	public Map<String, JButton> getButtons() {
		return this.menuitems;
	}

}
