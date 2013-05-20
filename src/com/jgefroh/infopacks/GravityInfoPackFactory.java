package com.jgefroh.infopacks;

import com.jgefroh.components.GravityComponent;
import com.jgefroh.components.VelocityComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class GravityInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(GravityComponent.class)!=null
				&& entity.getComponent(VelocityComponent.class)!=null)
		{
			return new GravityInfoPack(entity);
		}
		return null;
	}

}
