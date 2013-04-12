package infopacks;

import components.InputComponent;

import entities.IEntity;

public class InputInfoPack implements IInfoPack
{
	private IEntity parent;
	
	private InputComponent ic;
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
		return ic.isInterested(command);
	}
	public boolean isEnabled()
	{
		return ic.isEnabled();
	}
	@Override
	public boolean updateReferences()
	{
		ic = parent.getComponent(InputComponent.class);
		if(ic!=null)
		{
			return true;
		}
		return false;
	}
}
