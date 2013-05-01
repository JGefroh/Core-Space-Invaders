package infopacks;

import components.CameraComponent;
import components.TransformComponent;

import entities.IEntity;

public class CameraInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(TransformComponent.class)!=null
			&&entity.getComponent(CameraComponent.class)!=null)
		{
			System.out.println("Made new CameraInfoPack for " + entity);
			return new CameraInfoPack(entity);
		}
		return null;
	}

}
