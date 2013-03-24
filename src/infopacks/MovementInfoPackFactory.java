package infopacks;

import components.PositionComponent;
import components.VelocityComponent;

import entities.IEntity;

public class MovementInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(PositionComponent.class)!=null
				&& entity.getComponent(VelocityComponent.class)!=null)
		{
			return new MovementInfoPack(entity);
		}
		return null;
	}

}
