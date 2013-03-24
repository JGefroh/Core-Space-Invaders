package actions;

import entities.IEntity;

/**
 * Interface that describes the action/control system.
 * @author Joseph Gefroh
 *
 */
public interface IAction
{
	public static final String MOVERIGHT = "MOVERIGHT";
	public static final String MOVELEFT = "MOVELEFT";
	public static final String SHOOT = "SHOOT";
	public static final String MOVEUP = "MOVEUP";
	public static final String MOVEDOWN = "MOVEDOWN";
	public static final String STOPX = "STOPX";
	public static final String STOPY = "STOPY";
	
	public void setCommand(final String command);
	public String getCommand();
	public void execute(final IEntity entity);
}
