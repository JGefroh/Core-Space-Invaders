package com.jgefroh.infopacks;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * This class is used by the TransformSystem to move object smoothly.
 * @author Joseph Gefroh
 */
public class MovementInfoPack implements IInfoPack
{
	private IEntity parent;
	private TransformComponent tc;
	private VelocityComponent vc;
	
	public MovementInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	@Override
	public IEntity getOwner()
	{
		return this.parent;
	}
	@Override
	public boolean updateReferences()
	{
		tc = parent.getComponent(TransformComponent.class);
		vc = parent.getComponent(VelocityComponent.class);
		
		if(tc!=null&&vc!=null)
		{
			return true;
		}
		return false;
	}
	////////////////////
	public int getXPos()
	{
		return tc.getXPos();
	}
	public int getYPos()
	{
		return tc.getYPos();
	}
	public int getXVelocity()
	{
		return vc.getXVelocity();
	}
	public int getYVelocity()
	{
		return vc.getYVelocity();
	}
	public long getInterval()
	{
		return vc.getInterval();
	}
	public long getLastUpdated()
	{
		return vc.getLastUpdated();
	}
	
	////////////////////
	public void setXPos(final int xPos)
	{
		tc.setXPos(xPos);
	}
	public void setYPos(final int yPos)
	{
		tc.setYPos(yPos);
	}
	public void setXVelocity(final int xVel)
	{
		vc.setXVelocity(xVel);
	}
	public void setYVelocity(final int yVel)
	{
		vc.setYVelocity(yVel);
	}
	public void setInterval(final long interval)
	{
		vc.setInterval(interval);
	}
	public void setLastUpdated(final long lastUpdated)
	{
		vc.setLastUpdated(lastUpdated);
	}
}
