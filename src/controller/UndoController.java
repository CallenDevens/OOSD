package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.Game;
import model.command.CommandStack;

public class UndoController implements ActionListener{
	private CommandStack cstack;
	private Game game;
	
	public UndoController(Game g){
		this.cstack = g.getCommandStack();
		this.game = g;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(game.getActivePlayer().isUndoAvailable()){
			cstack.undoCommand();
		}
		else{
			JOptionPane.showMessageDialog(null, "You can exercise undo option only once(less than 3 moves) per game");		
		}
		game.getActivePlayer().addUndoCount();
		game.markTurnUndo();
	}
}
