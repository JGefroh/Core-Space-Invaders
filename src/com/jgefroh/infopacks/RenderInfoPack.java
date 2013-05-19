package com.jgefroh.infopacks;

import com.jgefroh.components.RenderComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;


/**
 * Intended to be used by the RenderSystem.
 * 
 * Controls access to the following components:
 * TransformComponent
 * RenderComponent
 * 
 * @author Joseph Gefroh
 */
public class RenderInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private TransformComponent tc;
	
	/**A component this InfoPack depends on.*/
	private RenderComponent rc;
	
	/**Flag that indicates the InfoPack is invalid and unreliable.*/
	private boolean isDirty;
	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this InfoPack.
	 * @param owner	the entity associated with this InfoPack
	 */
	public RenderInfoPack(final IEntity owner)
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
			rc = owner.getComponent(RenderComponent.class);			
			if(tc==null||rc==null)
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
	public long getXPos()
	{
		return tc.getXPos();
	}	
	
	/**
	 * @see TransformComponent#getYPos() 
	 */
	public long getYPos()
	{
		return tc.getYPos();
	}
	
	/**
	 * @see TransformComponent#getZPos() 
	 */
	public long getZPos()
	{
		return tc.getZPos();
	}
	
	/**
	 * @see TransformComponent#getWidth() 
	 */
	public int getWidth()
	{
		return tc.getWidth();
	}
	
	/**
	 * @see TransformComponent#getHeight() 
	 */
	public int getHeight()
	{
		return tc.getHeight();
	}
	
	/**
	 * @see RenderComponent#isVisible() 
	 */
	public boolean isVisible()
	{
		return rc.isVisible();
	}
	
	/**
	 * @see RenderComponent#getSpriteID() 
	 */	
	public int getSpriteID()
	{
		return rc.getSpriteID();
	}
	
	/**
	 * @see RenderComponent#getTextureID() 
	 */
	public int getTextureID()
	{
		return rc.getTextureID();
	}
	
	/**
	 * @see RenderComponent#getTexturePath()
	 */
	public String getPath()
	{
		return rc.getTexturePath();
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
	 * @see RenderComponent#setTextureID(int)
	 */
	public void setTextureID(final int textureID)
	{
		rc.setTextureID(textureID);
	}
}
