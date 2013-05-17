package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * This class contains data related to the object's positioning.
 * @author Joseph Gefroh
 */
public class TransformComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of the component.*/
	private IEntity owner;
	
	/**The global X position of the object.*/
	private int xPos;
	
	/**The global Y position of the object.*/
	private int yPos;
	
	/**The global Z position of the object.*/
	private int zPos;
	
	/**The last global X position of the object.*/
	private int lastXPos;
	
	/**The last global Y position of the object.*/
	private int lastYPos;
	
	/**The last global Z position of the object.*/
	private int lastZPos;
	
	/**The global width of the object.*/
	private int width;
	
	/**The global height of the object.*/
	private int height;

	
	//////////
	// INIT
	//////////
	/**
	 * Create a new TransformComponent
	 * @param owner	the IEntity owner of the component
	 */
	public TransformComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{	
		setXPos(0);
		setYPos(0);
		setZPos(0);
		setLastXPos(0);
		setLastYPos(0);
		setLastZPos(0);
		setWidth(0);
		setHeight(0);
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
	 * Get the X position of the entity
	 * @return	the X coordinate of the entity
	 */
	public int getXPos()
	{
		return this.xPos;
	}
	
	/**
	 * Get the Y position of the entity
	 * @return	the Y coordinate of the entity
	 */
	public int getYPos()
	{
		return this.yPos;
	}
	
	/**
	 * Get the Z position of the entity
	 * @return	the Z coordinate of the entity
	 */
	public int getZPos()
	{
		return this.zPos;
	}
	
	/**
	 * Get the previous X position of the entity
	 * @return	the previous X position of the entity
	 */
	public int getLastXPos()
	{
		return this.lastXPos;
	}
	
	/**
	 * Get the previous Y position of the entity
	 * @return	the previous Y position of the entity
	 */
	public int getLastYPos()
	{
		return this.lastYPos;
	}
	
	/**
	 * Get the previous Z position of the entity
	 * @return	the previous Z position of the entity
	 */
	public int getLastZPos()
	{
		return this.lastZPos;
	}
	
	/**
	 * Get the width of the entity
	 * @return	the width of the entity
	 */
	public int getWidth()
	{
		return this.width;
	}
	
	/**
	 * Get the height of the entity
	 * @return	the height of the entity
	 */
	public int getHeight()
	{
		return this.height;
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
	 * Set the X coordinate position of the entity.
	 * @param xPos	the X coordinate of the entity
	 */
	public void setXPos(final int xPos)
	{
		this.xPos = xPos;
	}
	
	/**
	 * Set the Y coordinate position of the entity.
	 * @param yPos	the Y coordinate of the entity
	 */
	public void setYPos(final int yPos)
	{
		this.yPos = yPos;
	}
	
	/**
	 * Set the Z coordinate position of the entity.
	 * @param zPos	the Z coordinate of the entity
	 */
	public void setZPos(final int zPos)
	{
		this.zPos = zPos;
	}
	
	/**
	 * Set the previous X coordinate position of the entity.
	 * @param xPos	the previous coordinate of the entity
	 */
	public void setLastXPos(final int lastXPos)
	{
		this.lastXPos = lastXPos;
	}	
	
	/**
	 * Set the previous Y coordinate position of the entity.
	 * @param yPos	the previous coordinate of the entity
	 */
	public void setLastYPos(final int lastYPos)
	{
		this.lastYPos = lastYPos;
	}
	
	/**
	 * Set the previous Z coordinate position of the entity.
	 * @param zPos	the previous coordinate of the entity
	 */
	public void setLastZPos(final int lastZPos)
	{
		this.lastZPos = lastZPos;
	}
	
	/**
	 * Set the width of the entity.
	 * @param width	the width of the entity
	 */
	public void setWidth(final int width)
	{
		this.width = width;
	}
	
	/**
	 * Set the height of the entity.
	 * @param height the height of the entity
	 */
	public void setHeight(final int height)
	{
		this.height = height;
	}
}
