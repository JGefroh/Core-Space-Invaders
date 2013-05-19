package com.jgefroh.infopacks;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the TransformSystem.
 * 
 * Controls access to the following components:
 * TransformComponent
 * VelocityComponent
 * 
 * @author Joseph Gefroh
 */
public class MovementInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private VelocityComponent vc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;	
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public MovementInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	@Override
	public boolean isDirty()
	{
		if(owner.hasChanged())
		{
			tc = owner.getComponent(TransformComponent.class);
			vc = owner.getComponent(VelocityComponent.class);			
			if(tc==null||vc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}

	/**
	 * @see TransformComponent#getXPos()
	 */
	public int getXPos()
	{
		return tc.getXPos();
	}
	
	/**
	 * @see TransformComponent#getYPos()
	 */
	public int getYPos()
	{
		return tc.getYPos();
	}

	/**
	 * @see VelocityComponent#getXVelocity()
	 */
	public int getXVelocity()
	{
		return vc.getXVelocity();
	}
	
	/**
	 * @see VelocityComponent#getYVelocity()
	 */
	public int getYVelocity()
	{
		return vc.getYVelocity();
	}
	
	/**
	 * @see VelocityComponent#getInterval()
	 */
	public long getInterval()
	{
		return vc.getInterval();
	}
	
	/**
	 * @see VelocityComponent#getLastUpdated()
	 */
	public long getLastUpdated()
	{
		return vc.getLastUpdated();
	}
	
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	/**
	 * @see TransformComponent#setXPos(int)
	 */
	public void setXPos(final int xPos)
	{
		tc.setXPos(xPos);
	}
	
	/**
	 * @see TransformComponent#setYPos(int)
	 */
	public void setYPos(final int yPos)
	{
		tc.setYPos(yPos);
	}
	
	/**
	 * @see VelocityComponent#setXVelocity(int)
	 */
	public void setXVelocity(final int xVel)
	{
		vc.setXVelocity(xVel);
	}
	
	/**
	 * @see VelocityComponent#setYVelocity(int)
	 */
	public void setYVelocity(final int yVel)
	{
		vc.setYVelocity(yVel);
	}
	
	/**
	 * @see VelocityComponent#setInterval(long)
	 */
	public void setInterval(final long interval)
	{
		vc.setInterval(interval);
	}
	
	/**
	 * @see VelocityComponent#setLastUpdated(long)
	 */
	public void setLastUpdated(final long lastUpdated)
	{
		vc.setLastUpdated(lastUpdated);
	}
}