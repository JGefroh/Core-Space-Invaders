package systems;

import infopacks.CollisionInfoPack;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This system handles collision checking for entities.
 * @author Joseph Gefroh
 */
public class CollisionSystem implements ISystem
{
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**Logger for debug purposes.*/
	private final static Logger LOGGER 
		= Logger.getLogger(CollisionSystem.class.getName());
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	/**Contains the collision pairs that determines whether objects collide.*/
	private boolean[][] collisionTable;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new CollisionSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public CollisionSystem(final Core core)
	{
		this.core = core;
		init();
	}
	
	/**
	 * Initialize the Logger with default settings.
	 */
	private void initLogger()
	{
		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(debugLevel);
		LOGGER.addHandler(ch);
		LOGGER.setLevel(debugLevel);
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		initLogger();
		collisionTable = new boolean[9][9];	
		isRunning = true;
	}
	
	@Override
	public void start()
	{
		LOGGER.log(Level.INFO, "System started.");
		isRunning = true;
	}

	@Override
	public void work()
	{
		if(isRunning)
		{
			checkAll();
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "System stopped.");
		isRunning = false;
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Go through all of the collidable objects and check for collisions.
	 */
	public void checkAll()
	{
		//TODO: Use better method.
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
	
	/**
	 * Set the collision possibility of a pair of collision groups.
	 * @param groupOne	the id of the first group
	 * @param groupTwo	the id of the second group
	 * @param collides	true if they should collide, false otherwise
	 */
	public void setCollision(final int groupOne, final int groupTwo, 
								final boolean collides)
	{
		//TODO: Deal with out of bounds group ids/indexes
		if(groupOne>=0&&groupTwo>=0
				&&groupOne<collisionTable.length
				&&groupTwo<collisionTable.length)
		{
			collisionTable[groupOne][groupTwo] = collides;
			collisionTable[groupTwo][groupOne] = collides;
		}
	}
	
	/**
	 * Return the collision possibility between two collision groups.
	 * @param groupOne	the id of the first group
	 * @param groupTwo	the id of the second group
	 * @return	true if they should collide, false otherwise
	 */
	public boolean checkCollidesWith(final int groupOne, final int groupTwo)
	{
		return collisionTable[groupOne][groupTwo];
	}

	/**
	 * Check to see if a collision occurred between two entities.
	 * @param packOne	the CollisionInfoPack belonging to the first entity
	 * @param packTwo	the CollisionInfoPack belonging to the second entity
	 * @return	true if the entities are colliding, false otherwise
	 */
	private boolean checkCollided(final CollisionInfoPack packOne, 
			final CollisionInfoPack packTwo)
	{
		//TODO: This currently offers no way to determine exact collision pos.
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
