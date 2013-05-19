package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;



/**
 * This class contains data neccessary to keep track of corpses.
 * @author Joseph Gefroh
 */
public class BodyComponent implements IComponent
{
	//TODO: Make this less complicated.
	
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The ms time the component was last updated.*/
	private long lastUpdateTime = 0;
	
	/**Time until the body is removed.*/
	private long timeUntilDecay;

	
	//////////
	// INIT
	//////////
	/**
	 * Create a new body component.
	 * @param entity	the owner of this component
	 */
	public BodyComponent(final IEntity entity)
	{	
		setOwner(entity);
		init();
	}

	@Override
	public void init()
	{
		lastUpdateTime = 0;
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * Get the time the component was last updated.
	 * @return	the time the component was last updated, in ms
	 */
	public long getLastUpdateTime()
	{
		return this.lastUpdateTime;
	}
	
	/**
	 * Get the time to wait before removing the body.
	 * @return	the time to wait until the body is removed.
	 */
	public long getTimeUntilDecay()
	{
		return this.timeUntilDecay;
	}

	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	/**
	 * Set the time the component was last updated
	 * @param lastUpdateTime the time the component was last updated, in ms
	 */
	public void setLastUpdateTime(final long lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public void setTimeUntilDecay(final long timeUntilDecay)
	{
		this.timeUntilDecay = timeUntilDecay;
	}
}
