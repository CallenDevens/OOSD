package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends TransparentPanel{

	private Map<String, JButton> menuitems = new HashMap<String,JButton>();
	
	private JButton moveButton = new JButton("MOVE");
	private JButton atkButton = new JButton("ATTACK");
	private JButton resignButton = new JButton("RESIGN");
	
	public MenuPanel(int i, int j){
		
		this.setBackground(Color.BLACK);
		this.setOpaque(false);
		this.setTransparent((float) 0.7);
//		this.setLayout(new GridLayout(2,1));
		
		this.menuitems.put(atkButton.getText(), atkButton);
		this.menuitems.put(resignButton.getText(),resignButton);
		this.menuitems.put(moveButton.getText(), moveButton);
		this.setBorder(BorderFactory.createLineBorder(Color.decode("#1691D9"),2));

		customizeMenuItems();
//		atkButton.setEnabled(false);
		this.add(moveButton);
		this.add(atkButton);
		this.add(resignButton);
		
		this.setLocation(j, i);
		this.setVisible(true);
		this.setSize(100,120);
	}
	
	
	private void customizeMenuItems() {
		for(JButton button:menuitems.values()){
			this.menuItemStyleSetter(button);
		}
		
	}


	public void menuItemStyleSetter(JButton button){
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
				button.setOpaque(false);}
		});
	}
}
