package com.jgefroh.actions;

import com.jgefroh.core.IEntity;

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
