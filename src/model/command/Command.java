package model.command;
/**
 * 
 * @author aya
 * command in Command pattern
 */
public interface Command {
	public abstract void execute();
	public abstract void undo();	
}
