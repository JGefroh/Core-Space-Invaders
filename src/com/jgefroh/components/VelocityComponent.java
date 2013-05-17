package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * This component contains data used to move objects smoothly.
 * @author Joseph Gefroh
 */
public class VelocityComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of the component.*/
	private IEntity owner;
	
	/**The number of units to move on the X axis every interval.*/
	private int xVelocity;
	
	/**The number of units to move on the Y axis every interval.*/
	private int yVelocity;
	
	/**The amount of time to wait in-between movements, in milliseconds.*/
	private long interval;
	
	/**The time the velocity was last updated.*/
	private long lastUpdated;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new VelocityComponent.
	 * @param owner	the IEntity owner of the component
	 */
	public VelocityComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{
		setXVelocity(0);
		setYVelocity(0);
		setLastUpdated(-1);
		setInterval(0);
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
	 * Get the horizontal (X) velocity of the component.
	 * @return	the horizontal (X) velocity of the component
	 */
	public int getXVelocity()
	{
		return this.xVelocity;
	}
	
	/**
	 * Get the vertical (Y) velocity of the component.
	 * @return	the vertical (Y) velocity of the component
	 */
	public int getYVelocity()
	{
		return this.yVelocity;
	}
	
	/**
	 * Get the update interval of the component.
	 * @return	the time, in ms, to wait before attempting an update
	 */
	public long getInterval()
	{
		return this.interval;
	}
	
	/**
	 * Get the time the component was last updated.
	 * @return	the time, in ms, the component was last updated
	 */
	public long getLastUpdated()
	{
		return this.lastUpdated;
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
	 * Set the horizontal (X) velocity of the component.
	 * @param xVelocity	the horizontal (X) velocity of the component
	 */
	public void setXVelocity(final int xVelocity)
	{
		this.xVelocity = xVelocity;
	}
	
	/**
	 * Set the vertical (Y) velocity of the component.
	 * @param yVelocity	the vertical (Y) velocity of the component
	 */
	public void setYVelocity(final int yVelocity)
	{
		this.yVelocity = yVelocity;
	}
	
	/**
	 * Set the update interval of the component.
	 * @param interval	the time, in ms, to wait before attempting a move
	 */
	public void setInterval(final long interval)
	{
		this.interval = interval;
	}
	
	/**
	 * Set the time the component was last updated.
	 * @param lastUpdated	the time, in ms, the component was last updated
	 */
	public void setLastUpdated(final long lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}
}