package com.jgefroh.systems;


import java.awt.Point;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jgefroh.core.Core;
import com.jgefroh.core.Entity;
import com.jgefroh.core.IEntity;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.MenuInfoPack;



/**
 * Creates and positions the menu elements and responds to clicks.
 * @author Joseph Gefroh
 */
public class MenuSystem implements ISystem
{	
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**The time to wait between executions of the system.*/
	private long waitTime;
	
	/**The time this System was last executed, in ms.*/
	private long last;
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	/**Logger for debug purposes.*/
	private final Logger LOGGER 
		= LoggerFactory.getLogger(this.getClass(), debugLevel);
	
	/**The ID of the currently selected menu item.*/
	private String selectedMenuItemID;
	
	/**The current X-position of the mouse.*/
	private int mouseX;
	
	/**The current Y-position of the mouse.*/
	private int mouseY;
	
	/**Flag that indicates the mouse is hovering over a clickable menu item.*/
	private boolean hovered = false;
	

	//////////
	// INIT
	//////////
	/**
	 * Create a new instance of this {@code System}.
	 * @param core	 a reference to the Core controlling this system
	 */
	public MenuSystem(final Core core)
	{
		this.core = core;
		init();
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		this.isRunning = true;
		core.setInterested(this, "REQUEST_SHOW_MENU");
		core.setInterested(this, "REQUEST_NEW_GAME");
		core.setInterested(this, "REQUEST_QUIT");
		core.setInterested(this, "INPUT_CURSOR_POSITION");
		core.setInterested(this, "INPUT_LEFT_CLICK");
	}
	
	@Override
	public void start() 
	{
		LOGGER.log(Level.INFO, "System started.");
		isRunning = true;
	}

	@Override
	public void work(final long now)
	{
		if(isRunning)
		{
			Iterator<MenuInfoPack> packs
				= core.getInfoPacksOfType(MenuInfoPack.class);
			core.send("REQUEST_CURSOR_POSITION", "");
			while(packs.hasNext())
			{
				hovered = false;
				MenuInfoPack each = packs.next();
				if(checkIfHover(each))
				{
					each.setSelected(true);
					selectedMenuItemID = each.getOwner().getID();
					hovered = true;
					
					break;
				}
				else
				{
					each.setSelected(false);
				}
			}
		}
		if(hovered==false)
		{
			selectedMenuItemID = "";
		}
	}

	@Override
	public void stop()
	{	
		LOGGER.log(Level.INFO, "System stopped.");
		isRunning = false;
	}
	
	@Override
	public long getWait()
	{
		return this.waitTime;
	}

	@Override
	public long	getLast()
	{
		return this.last;
	}
	
	@Override
	public void setWait(final long waitTime)
	{
		this.waitTime = waitTime;
	}
	
	@Override
	public void setLast(final long last)
	{
		this.last = last;
	}
	
	/**
	 * Acts on messages sent by Core.
	 * 
	 * 
	 * The following messages are processed by this System.
	 * <p>REQUEST_SHOW_MENU
	 * 		- Creates and shows the menu. </p>
	 * <p>REQUEST_NEW_GAME
	 * 		- Tells the EntityCreationSystem to create aliens. </p>
	 * <p>REQUEST_QUIT
	 * 		- Quits the program.</p>
	 * <p>INPUT_CURSOR_POSITION [int,int]
	 * 		- Updates this system's last known cursor's X and Y coordinates.
	 * <p>INPUT_LEFT_CLICK
	 * 		- Sends the command associated with a menu item if it was clicked
	 * </p>
	 */
	@Override
	public void recv(final String id, final String... message)
	{
		if(id.equals("REQUEST_SHOW_MENU"))
		{
			showTitleScreen();
		}
		else if(id.equals("REQUEST_NEW_GAME"))
		{
			startNewGame();
			core.send("START_NEW_GAME", core.now() + "");
		}
		else if(id.equals("REQUEST_QUIT"))
		{
			quit();
		}
		else if(id.equals("INPUT_CURSOR_POSITION"))
		{
			if(message.length>=2)
			{
				try
				{
					mouseX = Integer.parseInt(message[0]);				
					mouseY = Integer.parseInt(message[1]);					
				}
				catch(NumberFormatException e)
				{
					LOGGER.log(Level.SEVERE, 
							"Error updating cursor position - bad format."
							+ "Quitting...");
					e.printStackTrace();
					System.exit(-1);
				}
			}
			else
			{
				LOGGER.log(Level.SEVERE, 
						"Error updating cursor position - unexpected response."
						+ "Quitting...");
				System.exit(-1);
			}
		}
		else if(id.equals("INPUT_LEFT_CLICK"))
		{
			MenuInfoPack pack = 
					core.getInfoPackFrom(this.selectedMenuItemID, 
										MenuInfoPack.class);
			if(pack!=null)
			{
				core.send(pack.getCommand(), "");
			}
		}
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Removes all entities from the game and creates the menu screen.
	 */
	private void showTitleScreen()
	{
		core.removeAllEntities();
		if(core.isPaused())
		{
			core.send("REQUEST_STATE_UNPAUSED", core.now() + "");
		}
		EntityCreationSystem ecs = core.getSystem(EntityCreationSystem.class);
		IEntity entity = new Entity();
			entity.setName("ALIEN");
			ecs.makePositionable(entity, 1680/2, 200, 128, 128);
			ecs.makeRenderable(entity, "res\\enemy.png", 0);
			ecs.makeAnimated(entity, "IDLE", 500, 0, 1);
			ecs.makeArmed(entity);
			ecs.makeAIControlled(entity);
			ecs.makeMoving(entity, 200);
			core.add(entity);
			ecs.createDrawableText("VOID ATTACKERS", 1680/2-300, 600, 48, -5);
		entity = new Entity();
		entity.setName("MENU_PLAYER");
			ecs.makePositionable(entity, 1680/2, 900, 64, 64);
			ecs.makeRenderable(entity, "res\\player.png", 0);
			ecs.makeArmed(entity);
			ecs.makeAIControlled(entity);
			ecs.makeMoving(entity, 0);
			core.add(entity);
		ecs.createMenuOption(1680/2, 800, 128, 48, 1, "REQUEST_NEW_GAME");
		ecs.createMenuOption(1680/2, 850, 128, 48, 2, "REQUEST_QUIT");
		ecs.createInputReceiver("LEFTCLICK");
		core.send("IN_MENU", true + "");
	}
	
	private boolean checkIfHover(final MenuInfoPack pack)
	{
		//TODO:Remove hardcode
		Rectangle r1 = new Rectangle(pack.getXPos()-(pack.getWidth()/2), pack.getYPos()-(pack.getHeight()/2),
						pack.getWidth(), pack.getHeight());
		Point point = new Point(mouseX, 1050-mouseY);
		if(r1.contains(point))
		{
			return true;
		}
		return false;
	}
	
	private void startNewGame()
	{
		core.getSystem(EntityCreationSystem.class).newGame();
	}
	
	private void quit()
	{
		System.exit(0);
	}
	

}
