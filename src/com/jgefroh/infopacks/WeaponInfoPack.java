package com.jgefroh.infopacks;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.IEntity;


/**
 * @author Joseph Gefroh
 */
public class WeaponInfoPack implements IInfoPack
{
	private IEntity parent;
	private TransformComponent tc;
	private WeaponComponent wc;
	
	public WeaponInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}
	@Override
	public IEntity getOwner()
	{
		return this.parent;
	}	
	@Override
	public boolean updateReferences()
	{
		wc = parent.getComponent(WeaponComponent.class);
		tc = parent.getComponent(TransformComponent.class);
		if(wc!=null)
		{
			return true;
		}
		parent.setChanged(true);
		return false;
	}
	////////////////////
	public boolean isReady()
	{
		return wc.isReady();
	}
	public boolean isFireRequested()
	{
		return wc.isFireRequested();
	}
	//////////
	public void setReady(final boolean isReady)
	{
		wc.setReady(isReady);
	}
	public void setFireRequested(final boolean isFireRequested)
	{
		wc.setFireRequested(isFireRequested);
	}


}
