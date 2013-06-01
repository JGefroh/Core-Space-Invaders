package com.jgefroh.infopacks;

import com.jgefroh.components.DecayComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the DecaySystem.
 * 
 * Controls access to the following components:
 * DecayComponent
 * 
 * @author Joseph Gefroh
 */
public class DecayInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private DecayComponent dc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public DecayInfoPack(final IEntity owner)
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
			dc = owner.getComponent(DecayComponent.class);
			if(dc==null)
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
	 * @see DecayComponent#getLastUpdateTime()
	 */
	public long getLastUpdateTime()
	{
		return dc.getLastUpdateTime();
	}
	
	/**
	 * @see DecayComponent#getTimeUntilDecay()
	 */
	public long getTimeUntilDecay()
	{
		return dc.getTimeUntilDecay();
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
	 * @see DecayComponent#setLastUpdateTime(long)
	 */
	public void setLastUpdateTime(final long updateTime)
	{
		dc.setLastUpdateTime(updateTime);
	}
}