package view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class SettingMenuWindow extends JFrame {
		
	private JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(11, 9, 20, 1));
	private JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(11, 9, 20, 1));
	
	private JSpinner mageNumSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
	private JSpinner rougeNumSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
	private JSpinner warriorNumSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
	
	private JSpinner paladinNumSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
	private JSpinner prisstNumSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
	private JSpinner hunterNumSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 5, 1));
	
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	
	Container con = this.getContentPane();

	public SettingMenuWindow(){
		customizeUI();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setLocation(80,180);
		this.setSize(340, 200);
	}

	private void customizeUI() {
		
		this.getContentPane().setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		
		this.addInputGroup(new JLabel("Board Width: "), widthSpinner,c);
		this.addInputGroup(new JLabel("Board Height: "), heightSpinner,c);
		con.add(Box.createRigidArea(new Dimension(15,20)),c);

		c.gridx = 1;
		c.gridy++;
		
		this.addInputGroup(new JLabel("Mage    : "),mageNumSpinner,c);
		this.addInputGroup(new JLabel("Paladin : "), paladinNumSpinner,c);
		con.add(Box.createRigidArea(new Dimension(15,20)),c);

		c.gridx = 1;
		c.gridy++;
		this.addInputGroup(new JLabel("Rouge   : "),rougeNumSpinner,c);
		this.addInputGroup(new JLabel("Prisst  : "), prisstNumSpinner,c);
		con.add(Box.createRigidArea(new Dimension(15,20)),c);

		
		c.gridx = 1;
		c.gridy++;
		this.addInputGroup(new JLabel("Warrior : "),warriorNumSpinner,c);
		this.addInputGroup(new JLabel("Hunter  : "), hunterNumSpinner,c);
		con.add(Box.createRigidArea(new Dimension(15,20)),c);
		
		c.gridx = 2;
		c.gridy++;
		
		this.add(okButton,c);
		c.gridx = c.gridx + 3;
		this.add(cancelButton,c);
		
		this.cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
	}

	private void addInputGroup(JLabel lbl, JSpinner spin, GridBagConstraints c) {
		con.add(Box.createRigidArea(new Dimension(15,20)),c);
		++c.gridx;
		lbl.setAlignmentX(LEFT_ALIGNMENT);
		con.add(lbl, c);
		++c.gridx;
		con.add(spin, c);
		++c.gridx;
	}

	public int getSettingWidth() {
		return (int) this.widthSpinner.getValue();
	}
	
	public int getSettingHeight(){
		return (int) this.heightSpinner.getValue();
	}
	
	public int getSettingMageNum(){
		return (int) this.mageNumSpinner.getValue();
	}
	
	public int getSettingRougeNum(){
		return (int) this.rougeNumSpinner.getValue();
	}

	public int getSettingWarriorNum(){
		return (int) this.warriorNumSpinner.getValue();
	}
	
	public int getSettingPaladinNum(){
		return (int) this.paladinNumSpinner.getValue();
	}
	
	public int getSettingPrisstNum(){
		return (int) this.prisstNumSpinner.getValue();
	}
	
	public int getSettingHunterNum(){
		return (int) this.hunterNumSpinner.getValue();
	}

	public void addSettingOKButtonListener(ActionListener l) {
		this.okButton.addActionListener(l);
	}
}
