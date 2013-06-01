package com.jgefroh.input;


import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.jgefroh.actions.ActionClick;
import com.jgefroh.actions.ActionMenu;
import com.jgefroh.actions.ActionMoveLeft;
import com.jgefroh.actions.ActionMoveRight;
import com.jgefroh.actions.ActionPause;
import com.jgefroh.actions.ActionShoot;
import com.jgefroh.actions.ActionStopX;
import com.jgefroh.actions.IAction;
import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;
import com.jgefroh.infopacks.InputInfoPack;





/**
 * This class receives input information from the input systems and acts
 * according to the binds associated with the inputs.
 * @author Joseph Gefroh
 */
public class InputSystem implements ISystem, IInputSystem
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
	
	private InputDevice_Keyboard kir;
	private InputDevice_Mouse mir;
	private IBindMap kbs;
	private IBindMap mbs;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new InputSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public InputSystem(final Core core)
	{
		this.core = core;
		init();
	}
	
	
	/**
	 * Initialize binds.
	 */
	private void initBinds()
	{
		//TODO: Move somewhere else.
		BindMap kbs = new BindMap();
		kbs.bind(Keyboard.KEY_A, new ActionMoveLeft(core), InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_D, new ActionMoveRight(core), InputSystem.HOLD);
		kbs.bind(Keyboard.KEY_P, new ActionPause(core), InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_A, new ActionStopX(core), InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_D, new ActionStopX(core), InputSystem.RELEASE);
		kbs.bind(Keyboard.KEY_SPACE, new ActionShoot(core), InputSystem.PRESS);
		kbs.bind(Keyboard.KEY_ESCAPE, new ActionMenu(core), InputSystem.PRESS);
		setBindSystem(IInputSystem.KEYBOARD, kbs);
		BindMap mb = new BindMap();
			mb.bind(0, new ActionClick(core), InputSystem.RELEASE);
		setBindSystem(IInputSystem.MOUSE, mb);
	}
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		kir = new InputDevice_Keyboard(this);
		mir = new InputDevice_Mouse(this);
		initBinds();
		core.setInterested(this,"REQUEST_CURSOR_POSITION");
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
			kir.processAllEvents();
			mir.processAllEvents();			
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
	
	@Override
	public void recv(final String id, final String... message)
	{
		if(id.equals("REQUEST_CURSOR_POSITION"))
		{
			core.send("INPUT_CURSOR_POSITION", Mouse.getX() +"", Mouse.getY()+"");
		}
	}
	//////////
	// IIINPUTSYSTEM INTERFACE
	//////////
	@Override
	public void poll()
	{
		kir.processAllEvents();
	}
	
	/**
	 * Notify the response system that an input has been received.
	 * @param device		the device that has sent the input
	 * @param keyCode		the code of the key that has generated the input
	 * @param typeEvent		the type of input (what has happened to the key)
	 */
	@Override
	public void notify(final int device, final int keyCode, final int typeEvent)
	{
		if(device==KEYBOARD)
		{
			handleKeyboardEvent(keyCode, typeEvent);
		}
		else if(device==MOUSE)
		{
			handleMouseEvent(keyCode, typeEvent);
		}
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////	
	/**
	 * Handle the keyboard event.
	 * @param keyCode	the code of the key that has generated the input
	 * @param type		the type of input (what has happened to the key)
	 */
	private void handleKeyboardEvent(final int keyCode, final int type)
	{
		IAction action = null;
		switch(type)
		{
			case PRESS:
				action = kbs.getActionOnPress(keyCode);
				break;
			case HOLD:
				action = kbs.getActionOnHold(keyCode);
				break;
			case RELEASE:
				action = kbs.getActionOnRelease(keyCode);
				break;
		}
		processAction(action);
	}
	
	/**
	 * Handle the mouse events.
	 * @param keyCode	the code of the key (or button) that has generated input
	 * @param type		the type of input (what has happened to the key)
	 */
	private void handleMouseEvent(final int keyCode, final int type)
	{
		IAction action = null;
		switch(type)
		{
			case PRESS:
				action = mbs.getActionOnPress(keyCode);
				break;
			case HOLD:
				action = mbs.getActionOnHold(keyCode);
				break;
			case RELEASE:
				action = mbs.getActionOnRelease(keyCode);
				break;
			case MOVE:
				core.send("CURSOR_POSITION_CHANGE", Mouse.getX()+"", Mouse.getY()+"");
		}
		processAction(action);
	}
	
	/**
	 * Execute the action associated with a command.
	 * @param action	the action to execute
	 */
	public void processAction(final IAction action)
	{
		if(action!=null)
		{
			String command = action.getCommand();
			Iterator<InputInfoPack> packs = core.getInfoPacksOfType(InputInfoPack.class);
			while(packs.hasNext())
			{
				InputInfoPack each = packs.next();
				if(each.isInterested(command))
				{
					action.execute(each.getOwner());
				}
			}
		}
	}
	
	
	//////////
	// SETTERS
	//////////
	/**
	 * Set the bind system for a specific input device
	 * @param device		the code of the device
	 * @param bindSystem	the bind system
	 */
	public void setBindSystem(final int device, final IBindMap bindSystem)
	{
		if(device==KEYBOARD)
		{
			kbs = bindSystem;
		}
		else if(device==MOUSE)
		{
			mbs = bindSystem;
		}
	}
}
