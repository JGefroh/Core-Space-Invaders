package systems;

import java.util.ArrayList;

import infopacks.MovementInfoPack;
import components.PositionComponent;

import entities.Entity;
import entities.IEntity;

/**
 * System that handles movement and repositioning of entities.
 * @author Joseph Gefroh
 */
public class MovementSystem implements ISystem
{
	private Core core;
	public MovementSystem(final Core core)
	{
		this.core = core;
	}
	
	/**
	 * Perform movements according to set velocities.
	 * @param infoPacks
	 */
	public void move(final ArrayList<MovementInfoPack> infoPacks)
	{//TODO: Process each axis independently.
		for(MovementInfoPack each:infoPacks)
		{
			if(each.isMoveRequested())
			{
				System.out.println("MOVING: " + each.getParent().getName() + "|"+ each);
				each.setPosX(each.getPosX()+each.getVelocityX());
				each.setPosY(each.getPosY()+each.getVelocityY());
				each.setNeedsCheck(true);
			}
		}
	}

	@Override
	public void start()
	{
	}

	@Override
	public void work()
	{
		move(core.getInfoPacksOfType(MovementInfoPack.class));
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
