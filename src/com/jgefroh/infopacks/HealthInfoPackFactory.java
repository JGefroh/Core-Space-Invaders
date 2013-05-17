package com.jgefroh.infopacks;

import com.jgefroh.components.HealthComponent;
import com.jgefroh.core.IEntity;


public class HealthInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(HealthComponent.class)!=null)
		{
			return new HealthInfoPack(entity);
		}
		return null;
	}

}
