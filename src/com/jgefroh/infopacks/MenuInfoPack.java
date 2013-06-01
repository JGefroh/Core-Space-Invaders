package com.jgefroh.infopacks;

import com.jgefroh.components.HealthComponent;
import com.jgefroh.components.MenuComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;

/**
 * Intended to be used by the MenuSystem.
 * 
 * Controls access to the following components:
 * MenuComponent
 * TransformComponent
 * @author Joseph Gefroh
 */
public class MenuInfoPack implements IInfoPack
{
	//////////
	// DATA
	//////////
	/**The entity associated with this InfoPack.*/
	private IEntity owner;
	
	/**A component this InfoPack depends on.*/
	private MenuComponent mc;
	
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
	public MenuInfoPack(final IEntity owner)
	{
		this.owner = owner;
		isDirty();
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
			mc = owner.getComponent(MenuComponent.class);
			tc = owner.getComponent(TransformComponent.class);
			if(mc==null||tc==null)
			{
				setDirty(true);
				return true;
			}
		}
		setDirty(false);
		return false;
	}
	
	/**
	 * @see MenuComponent#isSelected()
	 */
	public boolean isSelected()
	{
		return mc.isSelected();
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
	 * @see TransformComponent#getZPos() 
	 */
	public int getZPos()
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
	//////////
	// SETTERS
	//////////
	@Override
	public void setDirty(final boolean isDirty)
	{
		this.isDirty = isDirty;
	}
	
	public void setSelected(final boolean isSelected)
	{
		mc.setSelected(isSelected);
	}


	public String getCommand()
	{
		return mc.getCommand();
	}

}
