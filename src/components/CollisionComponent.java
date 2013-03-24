package components;

import java.util.HashMap;

import entities.IEntity;

public class CollisionComponent implements IComponent
{
	private IEntity parent;
	private boolean isColliding = false;
	private int group;
	private static boolean[][] collisionTable;
	public CollisionComponent(final IEntity parent)
	{
		setParent(parent);
		if(collisionTable==null)
		{
			collisionTable = new boolean[32][32];
		}
	}

	@Override
	public void init()
	{	
	}
	public int getGroup()
	{
		return this.group;
	}
	
	public void setGroup(final int group)
	{
		if(group>=0
				&&group<collisionTable.length
				&&group<collisionTable[0].length)
		{			
			this.group = group;
		}
	}
	
	public void setCollision(final int group, final int group2, final boolean collides)
	{
		if(group>=0&&group2>=0
				&&group<collisionTable.length
				&&group<collisionTable[0].length
				&&group2<collisionTable.length
				&&group2<collisionTable[0].length)
		{
			collisionTable[group][group2]=collides;
		}

	}
	public boolean collidesWith(final int otherGroup)
	{
		if(group>=0&&group<collisionTable.length&&group<collisionTable[0].length)
		{
			return collisionTable[otherGroup][this.group];
		}
		return false;
	}
	@Override
	public void setParent(final IEntity parent)
	{
		this.parent = parent;
	}

	@Override
	public IEntity getParent()
	{
		return this.parent;
	}
	
}
