package infopacks;

import components.TransformComponent;
import components.VelocityComponent;

import entities.IEntity;

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
	public IEntity getParent()
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
	public long getXPos()
	{
		return tc.getXPos();
	}
	public long getYPos()
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
	public void setXPos(final long xPos)
	{
		tc.setXPos(xPos);
	}
	public void setYPos(final long yPos)
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
