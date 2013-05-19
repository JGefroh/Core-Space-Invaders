package com.jgefroh.infopacks;

import com.jgefroh.components.AIComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the AISystem.
 * 
 * Controls access to the following components:
 * AIComponent
 * TransformComponent
 * 
 * @author Joseph Gefroh
 */
public class AIInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private AIComponent ac;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;

	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public AIInfoPack(final IEntity owner)
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
			ac = owner.getComponent(AIComponent.class);			
			if(tc==null||ac==null)
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
	 * @see TransformComponent#getXPos()
	 */
	public int getXPos()
	{
		return tc.getXPos();
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
