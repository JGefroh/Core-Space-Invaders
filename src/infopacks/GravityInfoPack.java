package infopacks;

import components.GravityComponent;

import entities.IEntity;

public class GravityInfoPack implements IInfoPack
{
	private IEntity parent;
	private GravityComponent gc;
	public GravityInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	@Override
	public IEntity getParent()
	{
		return this.parent;
	}

	@Override
	public boolean updateReferences()
	{
		gc = parent.getComponent(GravityComponent.class);
		if(gc!=null)
		{
			return true;
		}
		return false;
	}
	
}
