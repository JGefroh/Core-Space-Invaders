package com.jgefroh.infopacks;

import com.jgefroh.components.GravityComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


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
	public IEntity getOwner()
	{
		return this.parent;
	}
	public int getMaxGravity()
	{
		return gc.getMaxAcceleration();
	}
	public int getGravity()
	{
		return gc.getAcceleration();
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
