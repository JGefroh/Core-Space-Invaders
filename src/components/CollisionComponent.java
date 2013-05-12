package components;

import entities.IEntity;

/**
 * This class contains data that allows an object to collide.
 * @author Joseph Gefroh
 */
public class CollisionComponent implements IComponent
{
	//TODO: Collision system is still incomplete.
	
	//////////
	// DATA
	//////////
	/**The owner of this component.*/
	private IEntity owner;
	
	/**Used to determine what collides with what.*/
	private int collisionGroup;
	
	/**Flag to determine if there is a collision on the left side.*/
	private boolean left;

	/**Flag to determine if there is a collision on the right side.*/
	private boolean right;

	/**Flag to determine if there is a collision on the top side.*/
	private boolean top;
	
	/**Flag to determine if there is a collision on the bottom side.*/
	private boolean bottom;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new CollisionComponent
	 * @param owner	the IEntity owner of this component
	 */
	public CollisionComponent(final IEntity owner)
	{
		setOwner(owner);
		init();
	}

	@Override
	public void init()
	{	
		setCollisionGroup(0);
		setCollidingLeft(false);
		setCollidingRight(false);
		setCollidingTop(false);
		setCollidingBottom(false);
	}
	
	
	//////////
	// GETTERS
	//////////
	@Override
	public IEntity getOwner()
	{
		return this.owner;
	}
	/**
	 * Return the collision group the entity belongs to.
	 * @return	the collision group the entity is a part of
	 */
	public int getCollisionGroup()
	{
		return this.collisionGroup;
	}
	
	/**
	 * Return a flag that shows whether there is a collision on the left side.
	 * @return	true if there is a collision on the left, false otherwise
	 */
	public boolean isCollidingLeft()
	{
		return left;
	}
	/**
	 * Return a flag that shows whether there is a collision on the right side.
	 * @return	true if there is a collision on the right, false otherwise
	 */
	public boolean isCollidingRight()
	{
		return right;
	}
	
	/**
	 * Return a flag that shows whether there is a collision on the top side.
	 * @return	true if there is a collision on the top, false otherwise
	 */
	public boolean isCollidingTop()
	{
		return top;
	}
	
	/**
	 * Return a flag that shows whether there is a collision on the bottom side.
	 * @return	true if there is a collision on the bottom, false otherwise
	 */
	public boolean isCollidingBottom()
	{
		return bottom;
	}
	
	
	//////////
	// SETTERS
	//////////
	@Override
	public void setOwner(final IEntity owner)
	{
		this.owner = owner;
	}
	
	/**
	 * Set the collision group the entity belongs to.
	 * @param collisionGroup	the collision group the entity belongs to
	 */
	public void setCollisionGroup(final int collisionGroup)
	{
		this.collisionGroup = collisionGroup;
	}
	
	/**
	 * Set the flag that tracks whether there is a collision on the left.
	 * @param left	true if there is a collision, false otherwise
	 */
	public void setCollidingLeft(final boolean left)
	{
		this.left = left;
	}
	
	/**
	 * Set the flag that tracks whether there is a collision on the right.
	 * @param right	true if there is a collision, false otherwise
	 */
	public void setCollidingRight(final boolean right)
	{
		this.right = right;
	}
	
	/**
	 * Set the flag that tracks whether there is a collision on the top.
	 * @param top	true if there is a collision, false otherwise
	 */
	public void setCollidingTop(final boolean top)
	{
		this.top = top;
	}
	
	/**
	 * Set the flag that tracks whether there is a collision on the bottom.
	 * @param bottom true if there is a collision, false otherwise
	 */
	public void setCollidingBottom(final boolean bottom)
	{
		this.bottom = bottom;
	}
	


	
	
}
