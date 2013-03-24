package systems;

import infopacks.CollisionInfoPack;

import java.util.ArrayList;

public class CollisionSystem implements ISystem
{//TODO: Allow for multiple types of collision detection
	//TODO: Including bounding box, sphereicals, malformed shapes, pixel level
	//Check all entities that moved since the last collision check.
	
	//Get the collision group for each entity.
	//Go through the collision groups.
	
	private Core core;
	public CollisionSystem(final Core core)
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
		checkCollided();
	}

	@Override
	public void stop()
	{
		
	}
	
	private void checkCollided()
	{
		ArrayList<CollisionInfoPack> infoPacks =
					core.getInfoPacksOfType(CollisionInfoPack.class);
		for(CollisionInfoPack each:infoPacks)
		{
			if(each.needsCheck())
			{//Only check collision on objects that require collision checks
				CollisionInfoPack collidedWith = checkCollision(each, infoPacks);
				if(collidedWith!=null)
				{//If a collision was detected, roll back the last move.
					each.rollback();
					handleCollision(each, collidedWith);
				}
				else
				{
					each.approve();
				}
			}
		}
	}

	private void handleCollision(final CollisionInfoPack each,
			final CollisionInfoPack collidedWith)
	{
		//IEvent source = each.getCollisionEvent();
		//source.execute()
	}

	public CollisionInfoPack checkCollision(final CollisionInfoPack pack, 
			final ArrayList<CollisionInfoPack> infoPacks)
	{
		boolean isColliding = false;
		for(CollisionInfoPack each:infoPacks)
		{
			if(pack.collidesWith(each.getGroup()))
			{//If the pack collides with this object group....
				isColliding = performBoundingBoxCalc(pack, each);
				if(isColliding == true)
				{
					return each;
				}
			}
		}
		return null;
	}

	private boolean performBoundingBoxCalc(final CollisionInfoPack pack, 
			final CollisionInfoPack each)
	{
		//Check to see if there is a collision
		if(pack.getXMin()>each.getXMax()
				||pack.getXMax()<each.getXMin()
				||pack.getYMin()>each.getYMax()
				||pack.getYMax()<each.getYMin())
		{
			return false;
		}
		return true;
		
	}
}
