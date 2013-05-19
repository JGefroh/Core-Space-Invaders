package com.jgefroh.infopacks;

import com.jgefroh.components.BodyComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;


public class BodyInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(BodyComponent.class)!=null)
		{
			return new BodyInfoPack(entity);
		}
		return null;
	}

}
