package com.jgefroh.infopacks;

import com.jgefroh.components.GravityComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;

/**
 * Intended to be used by the GravitySystem.
 * 
 * Controls access to the following components:
 * GravityComponent
 * VelocityComponent
 * 
 * @author Joseph Gefroh
 */
public class GravityInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private GravityComponent gc;
	
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
	public GravityInfoPack(final IEntity owner)
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
			gc = owner.getComponent(GravityComponent.class);
			vc = owner.getComponent(VelocityComponent.class);			
			if(gc==null||vc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}
	
	/**
	 * @see GravityComponent#getMaxAcceleration()
	 */
	public int getMaxGravity()
	{
		return gc.getMaxAcceleration();
	}
	
	/**
	 * @see GravityComponent#getAcceleration()
	 */
	public int getGravity()
	{
		return gc.getAcceleration();
	}
	
	/**
	 * @see VelocityComponent#getYVelocity()
	 */
	public int getYVelocity()
	{
		return vc.getYVelocity();
	}
	
	/**
	 * @see GravityComponent#getLastUpdate()
	 */
	public long getLastUpdate()
	{
		return gc.getLastUpdate();
	}
	
	/**
	 * @see GravityComponent#getUpdateInterval()
	 */
	public long getUpdateInterval()
	{
		return gc.getUpdateInterval();
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
	 * @see VelocityComponent#setYVelocity(int)
	 */
	public void setYVelocity(final int yVel)
	{
		vc.setYVelocity(yVel);
	}

	/**
	 * @see GravityComponent#getLastUpdate()
	 */
	public void setLastUpdate(final long lastUpdate)
	{
		gc.setLastUpdate(lastUpdate);
	}
}