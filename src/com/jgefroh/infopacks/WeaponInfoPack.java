package com.jgefroh.infopacks;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the WeaponSystem.
 * 
 * Controls access to the following components:
 * TransformComponent
 * WeaponComponent
 * 
 * @author Joseph Gefroh
 */
public class WeaponInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private WeaponComponent wc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public WeaponInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	

	//////////
	// GETTERS
	//////////
	@Override
	public boolean isDirty()
	{
		if(owner.hasChanged())
		{
			tc = owner.getComponent(TransformComponent.class);
			wc = owner.getComponent(WeaponComponent.class);			
			if(tc==null||wc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}
	
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}	
	
	/**
	 * @see WeaponComponent#isReady()
	 */
	public boolean isReady()
	{
		return wc.isReady();
	}
	
	/**
	 * @see WeaponComponent#isFireRequested()
	 */
	public boolean isFireRequested()
	{
		return wc.isFireRequested();
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
	 * @see WeaponComponent#setReady(boolean)
	 */
	public void setReady(final boolean isReady)
	{
		wc.setReady(isReady);
	}
	
	/**
	 * @see WeaponComponent#setFireRequested(boolean)
	 */
	public void setFireRequested(final boolean isFireRequested)
	{
		wc.setFireRequested(isFireRequested);
	}
}