package systems;

import infopacks.CollisionInfoPack;

import java.awt.Rectangle;
import java.util.ArrayList;

import components.WeaponComponent;

public class CollisionSystem implements ISystem
{
	private Core core;
	private boolean[][] collisionTable = new boolean[9][9];
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
	{//TODO: Make collision detection system independent of effects on collide
		ArrayList<CollisionInfoPack> packs =
				core.getInfoPacksOfType(CollisionInfoPack.class);

		for(CollisionInfoPack each:packs)
		{
			for(CollisionInfoPack pack:packs)
			{
				if(checkCollidesWith(each.getGroup(), pack.getGroup())&&each!=pack)
				{
					if(checkCollided(each, pack))
					{
						core.getSystem(EventSystem.class).notify("COLLISION", each.getOwner(), pack.getOwner());
					}
				}
			}
		}
	}

	@Override
	public void stop()
	{
		
	}
	
	public void alignX(final CollisionInfoPack one, final CollisionInfoPack two)
	{
		one.setXPos(two.getXPos()+one.getWidth());
	}
	public void alignY(final CollisionInfoPack one, final CollisionInfoPack two)
	{
		one.setYPos(two.getYPos()+one.getHeight());
	}
	public void setCollision(final int groupOne, final int groupTwo, 
								final boolean collides)
	{
		if(groupOne>=0&&groupTwo>=0
				&&groupOne<collisionTable.length
				&&groupTwo<collisionTable.length)
		{
			collisionTable[groupOne][groupTwo] = collides;
			collisionTable[groupTwo][groupOne] = collides;
		}
	}
	
	public boolean checkCollidesWith(final int groupOne, final int groupTwo)
	{
		return collisionTable[groupOne][groupTwo];
	}

	private boolean checkCollided(final CollisionInfoPack packOne, 
			final CollisionInfoPack packTwo)
	{
		Rectangle r1 = new Rectangle(packOne.getXPos(), packOne.getYPos(),
						packOne.getWidth(), packOne.getHeight());
		Rectangle r2 = new Rectangle(packTwo.getXPos(), packTwo.getYPos(),
				packTwo.getWidth(), packTwo.getHeight());
		if(r1.intersects(r2))
		{
			return true;
		}
		return false;
	}
	

}
