package com.jgefroh.infopacks;

import com.jgefroh.components.AIComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;


public class AIInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(TransformComponent.class)!=null
				&&entity.getComponent(AIComponent.class)!=null)
		{
			return new AIInfoPack(entity);
		}
		return null;
	}

}
