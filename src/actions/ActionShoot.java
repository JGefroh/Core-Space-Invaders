package actions;

import systems.Core;
import systems.WeaponSystem;
import entities.IEntity;

public class ActionShoot implements IAction
{
	private final String command = "SHOOT";
	private Core core;
	
	public ActionShoot(final Core core)
	{
		this.core = core;
	}
	
	@Override
	public String getCommand()
	{
		return this.command;
	}

	@Override
	public void execute(final IEntity entity)
	{
		core.getSystem(WeaponSystem.class).fire(entity);
	}

}
