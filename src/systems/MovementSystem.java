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
				if(isValidMoveX(each.getPosX(), each.getVelocityX()))
				{
					each.setPosX(each.getPosX()+each.getVelocityX());
					each.setNeedsCheck(true);
				}
				else
				{
					each.setXVelocity(0);
				}
				
				if(isValidMoveY(each.getPosY(), each.getVelocityY()))
				{
					each.setPosY(each.getPosY()+each.getVelocityY());
					each.setNeedsCheck(true);
				}
				else
				{
					each.setYVelocity(0);
				}
				
				if(each.getVelocityY()==0&&each.getVelocityX()==0)
				{
					each.setMoveRequested(false);
				}
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
	
	private boolean isValidMoveX(final int currentPos, final int deltaPos)
	{
		if(currentPos+deltaPos>=0
				&&currentPos+deltaPos<=core.getSystem(RenderSystem.class).getScreenXMax())
		{
			return true;
		}
		return false;
	}
	
	private boolean isValidMoveY(final int currentPos, final int deltaPos)
	{
		if(currentPos+deltaPos>=0
				&&currentPos+deltaPos<=core.getSystem(RenderSystem.class).getScreenYMax())
		{
			return true;
		}
		return false;
	}
}
