package systems;

import infopacks.AIInfoPack;
import infopacks.WeaponInfoPack;

import java.util.ArrayList;
import java.util.Random;

import entities.IEntity;


public class AISystem implements ISystem
{
	private Core core;
	private boolean[][] collisionTable = new boolean[9][9];
	private boolean isMovingLeft = false;
	public AISystem(final Core core)
	{
		this.core = core;
	}
	
	@Override
	public void start()
	{	
	}

	@Override
	public void work()
	{
		ArrayList<AIInfoPack> packs
			= core.getInfoPacksOfType(AIInfoPack.class);
		boolean switched = false;
		for(AIInfoPack each:packs)
		{
			//SHOOT
			shoot(each.getParent());
			if(isMovingLeft==true)
			{				
				if(each.getXPos()<=0)
				{
					switched = true;
				}
				core.getSystem(TransformSystem.class).setXVelocity(each.getParent(), -5);
			}
			else if(isMovingLeft==false)
			{
				if(each.getXPos()>=1648)
				{
					switched = true;
				}			
				core.getSystem(TransformSystem.class).setXVelocity(each.getParent(), 5);
			}
		}
		if(switched==true)
		{
			isMovingLeft = !isMovingLeft;
			for(AIInfoPack each:packs)
			{
				core.getSystem(TransformSystem.class).shiftPosition(each.getParent(), 0, 51);
			}
		}
	}

	private void shoot(final IEntity parent)
	{
		Random r = new Random();
		if(r.nextInt(1000)==5)
		{
			WeaponInfoPack wip = core.getInfoPackFrom(parent, WeaponInfoPack.class);
			wip.setFireRequested(true);
		}
	}
	@Override
	public void stop()
	{
		
	}

}
