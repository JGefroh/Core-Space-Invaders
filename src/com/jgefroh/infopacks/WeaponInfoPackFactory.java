package com.jgefroh.infopacks;

import com.jgefroh.components.TransformComponent;
import com.jgefroh.components.WeaponComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;


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
