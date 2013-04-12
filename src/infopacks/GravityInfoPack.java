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
	public void addGravity()
	{
		vc.setYVelocity(vc.getYVelocity()+gc.getGravity());
	}
	public long getLastUpdate()
	{
		return gc.getLastUpdate();
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
