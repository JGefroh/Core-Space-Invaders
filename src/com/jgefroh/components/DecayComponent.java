package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;



/**
 * Contains data that controls decay times.
 * @author Joseph Gefroh
 */
public class DecayComponent implements IComponent
{
	//TODO: Make this less complicated.
	
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The ms time the component was last updated.*/
	private long lastUpdateTime = 0;
	
	/**Time until the entity is removed.*/
	private long timeUntilDecay;

	
	//////////
	// INIT
	//////////
	/**
	 * Create a new decay component.
	 * @param entity	the owner of this component
	 */
	public DecayComponent(final IEntity entity)
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
	 * Get the time to wait before removing the object
	 * @return	the time to wait until the decay is removed.
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
