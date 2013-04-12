package infopacks;

import components.GravityComponent;
import components.VelocityComponent;

import entities.IEntity;

public class GravityInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(GravityComponent.class)!=null
				&& entity.getComponent(VelocityComponent.class)!=null)
		{
			System.out.println("Generated gravity info pack for: " + entity);
			return new GravityInfoPack(entity);
		}
		return null;
	}

}
