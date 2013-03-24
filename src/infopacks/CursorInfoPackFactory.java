package infopacks;

import components.CursorComponent;
import components.InputComponent;
import components.PositionComponent;

import entities.IEntity;

public class CursorInfoPackFactory implements IInfoPackFactory
{
	@Override
	public IInfoPack generate(final IEntity entity)
	{
		if(entity.getComponent(CursorComponent.class)!=null
				&&entity.getComponent(PositionComponent.class)!=null)
		{
			System.out.println("Generated CursorInfoPack for: " + entity.getName() + "|" + entity);
			return new CursorInfoPack(entity);
		}
		return null;
	}
}
