package systems;

import infopacks.AIInfoPack;
import infopacks.WeaponInfoPack;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.IEntity;

/**
 * This system controls the behavior of the AI for the Space Invaders
 * test.
 * @author Joseph Gefroh
 */
public class AISystem implements ISystem
{
	private Core core;
	
	/**Contains the collision pairs that determines whether objects collide.*/
	private boolean[][] collisionTable = new boolean[9][9];
	
	/**A flag for the aliens to see if the squadron is moving left or right.*/
	private boolean isMovingLeft = false;

	//////////LOGGER//////////
	private final static Logger LOGGER 
	= Logger.getLogger(AISystem.class.getName());
	private void initLogger()
	{
		LOGGER.setLevel(Level.ALL);
	}
	
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
				core.getSystem(TransformSystem.class).shiftPosition(each.getParent(), 0, 25);
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
