package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * Contains data to allow an entity to experience simple gravity.
 * @author Joseph Gefroh
 */
public class GravityComponent implements IComponent
{
	//TODO: Merge with velocity component, somehow? Does it even need it?
	
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The time the gravity was last updated, as a long*/
	private long lastUpdate;
	
	/**The acceleration per update*/
	private int acceleration;
	
	/**Time to wait between updates*/
	private long updateInterval;
	
	/**The maximum acceleration*/
	private int maxAcceleration;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new GravityComponent
	 * @param owner	the IEntity owner of this component
	 */
	public GravityComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	@Override
	public void init()
	{
		setLastUpdate(-1);
		setUpdateInterval(0);
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
	 * Get the current acceleration of the entity.
	 * @return	the current acceleration of the entity
	 */
	public int getAcceleration()
	{
		return this.acceleration;
	}
	
	/**
	 * Get the maximum acceleration of the entity.
	 * @return	the maximum acceleration of the entity
	 */
	public int getMaxAcceleration()
	{
		return this.maxAcceleration;
	}
	
	/**
	 * Get the time of the last update, in ms.
	 * @return	the time of the last update, in ms
	 */
	public long getLastUpdate()
	{
		return this.lastUpdate;
	}
	
	/**
	 * Get the time to wait between updates, in ms.
	 * @return	the time to wait between updates, in ms
	 */
	public long getUpdateInterval()
	{
		return this.updateInterval;
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
	 * Set the update interval.
	 * @param interval	the time to wait between updates, in ms
	 */
	public void setUpdateInterval(final long interval)
	{
		this.updateInterval = interval;
	}
	
	/**
	 * Set the time of the last update.
	 * @param lastUpdate	the time of the last update, in ms
	 */
	public void setLastUpdate(final long lastUpdate)
	{
		this.lastUpdate = lastUpdate;
	}
	
	/**
	 * Set the current acceleration of the entity.
	 * @param acceleration	the current acceleration of the entity
	 */
	public void setAcceleration(final int acceleration)
	{
		this.acceleration = acceleration;
	}
	
	/**
	 * Set the maximum acceleration of the entity.
	 * @param maxAcceleration	the maximum acceleration of the entity
	 */
	public void setMaxAcceleration(final int maxAcceleration)
	{
		this.maxAcceleration = maxAcceleration;
	}
}
