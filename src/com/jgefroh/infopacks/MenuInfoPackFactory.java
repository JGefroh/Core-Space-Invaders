package com.jgefroh.infopacks;

import com.jgefroh.components.MenuComponent;
import com.jgefroh.components.TransformComponent;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.IInfoPack;
import com.jgefroh.core.IInfoPackFactory;

/**
 * Produces an instance of an InfoPack if the entity has the proper components.
 * @author Joseph Gefroh
 */
public class MenuInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(MenuComponent.class)!=null
				&&entity.getComponent(TransformComponent.class)!=null)
		{
			return new MenuInfoPack(entity);
		}
		return null;
	}

}
