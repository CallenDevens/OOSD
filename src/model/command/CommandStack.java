package model.command;

import java.util.Stack;

public class CommandStack {
	private Stack<Command> commands = new Stack<Command>();
	
	public void pushCommand(Command c){
		commands.push(c);
	}
	
	public void undoCommand(){
		if(!commands.isEmpty()){
			Command c = commands.pop();
			c.undo();
		}
	}

}
