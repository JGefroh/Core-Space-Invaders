package infopacks;

import components.TransformComponent;
import components.WeaponComponent;

import entities.IEntity;

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
