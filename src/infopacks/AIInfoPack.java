package infopacks;

import components.AIComponent;
import components.TransformComponent;

import entities.IEntity;

/**
 * Acts as an intermediary between the AnimationSystem and an entity's
 * AnimationComponent.
 * @author Joseph Gefroh
 *
 */
public class AIInfoPack implements IInfoPack
{
	private IEntity parent;
	private AIComponent ac;
	private TransformComponent tc;
	

	public AIInfoPack(final IEntity parent)
	{
		this.parent = parent;
	}

	public IEntity getParent()
	{
		return this.parent;
	}

	
	public int getXPos()
	{
		return tc.getXPos();
	}
	@Override
	public boolean updateReferences()
	{
		tc = parent.getComponent(TransformComponent.class);
		ac = parent.getComponent(AIComponent.class);
		if(tc!=null&&ac!=null)
		{
			return true;
		}
		return false;
	}
	

}
