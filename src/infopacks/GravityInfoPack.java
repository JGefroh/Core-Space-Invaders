package infopacks;

import components.GravityComponent;
import components.VelocityComponent;

import entities.IEntity;

public class GravityInfoPack implements IInfoPack
{
	private IEntity parent;
	private GravityComponent gc;
	private VelocityComponent vc;
	public GravityInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	@Override
	public IEntity getParent()
	{
		return this.parent;
	}
	public int getMaxGravity()
	{
		return gc.getMaxGravity();
	}
	public int getGravity()
	{
		return gc.getGravity();
	}
	public int getYVelocity()
	{
		return vc.getYVelocity();
	}
	public long getLastUpdate()
	{
		return gc.getLastUpdate();
	}
	public void setYVelocity(final int yVel)
	{
		vc.setYVelocity(yVel);
	}
	public long getUpdateInterval()
	{
		return gc.getUpdateInterval();
	}
	public void setLastUpdate(final long lastUpdate)
	{
		gc.setLastUpdate(lastUpdate);
	}
	@Override
	public boolean updateReferences()
	{
		gc = parent.getComponent(GravityComponent.class);
		vc = parent.getComponent(VelocityComponent.class);
		if(gc!=null&&vc!=null)
		{
			return true;
		}
		return false;
	}
	
}
