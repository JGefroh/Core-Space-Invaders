package com.jgefroh.infopacks;

import com.jgefroh.components.ScoreComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the ScoreSystem.
 * 
 * Controls access to the following components:
 * ScoreComponent
 * 
 * @author Joseph Gefroh
 */
public class ScoreInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private ScoreComponent sc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public ScoreInfoPack(final IEntity owner)
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
			sc = owner.getComponent(ScoreComponent.class);	
			if(sc==null)
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
	 * @see ScoreComponent#getScore()
	 */
	public int getScore()
	{
		return sc.getScore();
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
	 * @see ScoreComponent#setScore(int)
	 */
	public void setScore(final int score)
	{
		sc.setScore(score);
	}
}