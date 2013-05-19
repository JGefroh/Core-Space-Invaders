package com.jgefroh.infopacks;

import com.jgefroh.components.BulletComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the WeaponSystem.
 * 
 * Controls access to the following components:
 * TransformComponent
 * BulletComponent
 * 
 * @author Joseph Gefroh
 */
public class BulletInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private BulletComponent bc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public BulletInfoPack(final IEntity owner)
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
			bc = owner.getComponent(BulletComponent.class);			
			if(tc==null||bc==null)
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
	 * @see BulletComponent#getBulletOwner()
	 */	
	public IEntity getBulletOwner()
	{
		return bc.getBulletOwner();
	}
	
	
	//////////
	// SETTERS
	//////////	
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
}
