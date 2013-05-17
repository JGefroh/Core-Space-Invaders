package com.jgefroh.components;

import com.jgefroh.core.IComponent;
import com.jgefroh.core.IEntity;

/**
 * This class contains data necessary to keep track of bullets
 * and bullet ownership.
 * @author Joseph Gefroh
 */
public class BulletComponent implements IComponent
{
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**The owner of the bullet (the entity that fired the bullet*/
	private IEntity bulletOwner;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new bullet component.
	 * @param owner the IEntity owner of this component
	 */
	public BulletComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}
	
	@Override
	public void init()
	{
		
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
	 * Return a reference to the entity that fired the bullet.
	 * @return	the entity that fired the bullet
	 */
	public IEntity getBulletOwner()
	{
		return this.bulletOwner;
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
	 * Set the firer of the bullet.
	 * @param bulletOwner the entity that fired the bullet
	 */
	public void setBulletOwner(final IEntity bulletOwner)
	{
		this.bulletOwner = bulletOwner;
	}
}
