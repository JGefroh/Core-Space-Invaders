package com.jgefroh.infopacks;

import com.jgefroh.components.BodyComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * @author Joseph Gefroh
 *
 */
public class BodyInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The owner of this BodynInfoPack.*/
	private IEntity owner;
	
	private BodyComponent bc;
	
	
	
	/**
	 * Create a new BodyInfoPack belonging to a specific entity.
	 * @param owner	the owner of this InfoPack
	 */
	public BodyInfoPack(final IEntity owner)
	{
		this.owner = owner;
	}
	
	@Override
	public boolean updateReferences()
	{
		bc = owner.getComponent(BodyComponent.class);
		if(bc!=null)
		{
			return true;
		}
		return false;
	}
	
	
	//////////
	// GETTERS
	//////////
	/**
	 * Get the owner of this InfoPack.
	 * @return	the owner of this InfoPack
	 */
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	
	/**
	 * Get the time the component was last updated, in milliseconds.
	 * @return
	 */
	public long getLastUpdateTime()
	{
		return bc.getLastUpdateTime();
	}
	
	/**
	 * Get the time to wait between frame updates.
	 * @return	the time to wait, in ms.
	 */
	public long getTimeUntilDecay()
	{
		return bc.getTimeUntilDecay();
	}

	//////////
	// SETTERS
	//////////
	/**
	 * Set the time the component was last checked.
	 * @param updateTime	the long time the component was last updated, in ms.
	 */
	public void setLastUpdateTime(final long updateTime)
	{
		bc.setLastUpdateTime(updateTime);
	}

}
