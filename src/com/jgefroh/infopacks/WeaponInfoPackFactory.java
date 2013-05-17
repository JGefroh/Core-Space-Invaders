package com.jgefroh.infopacks;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.IEntity;


public class WeaponInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(WeaponComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new WeaponInfoPack(entity);
		}
		return null;
	}

}
