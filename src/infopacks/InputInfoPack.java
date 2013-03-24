package infopacks;

import components.InputComponent;

import entities.IEntity;

public class InputInfoPack implements IInfoPack
{
	private IEntity parent;
	
	public InputInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	public IEntity getParent()
	{
		return this.parent;
	}
	
	public boolean isInterested(final String command)
	{
		return parent.getComponent(InputComponent.class).isInterested(command);
	}
	public boolean isEnabled()
	{
		return parent.getComponent(InputComponent.class).isEnabled();
	}
	@Override
	public boolean updateReferences()
	{
		return true;
	}
}
