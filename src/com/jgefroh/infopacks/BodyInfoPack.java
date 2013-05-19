package com.jgefroh.infopacks;

import com.jgefroh.components.BodyComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the BodySystem.
 * 
 * Controls access to the following components:
 * BodyComponent
 * 
 * @author Joseph Gefroh
 */
public class BodyInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private BodyComponent bc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public BodyInfoPack(final IEntity owner)
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
			bc = owner.getComponent(BodyComponent.class);
			if(bc==null)
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
	 * @see BodyComponent#getLastUpdateTime()
	 */
	public long getLastUpdateTime()
	{
		return bc.getLastUpdateTime();
	}
	
	/**
	 * @see BodyComponent#getTimeUntilDecay()
	 */
	public long getTimeUntilDecay()
	{
		return bc.getTimeUntilDecay();
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
	 * @see BodyComponent#setLastUpdateTime(long)
	 */
	public void setLastUpdateTime(final long updateTime)
	{
		bc.setLastUpdateTime(updateTime);
	}
}