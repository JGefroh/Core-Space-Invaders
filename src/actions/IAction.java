package actions;

import entities.IEntity;

/**
 * Interface that describes the action/control system.
 * @author Joseph Gefroh
 *
 */
public interface IAction
{
	public String getCommand();
	public void execute(final IEntity entity);
}
