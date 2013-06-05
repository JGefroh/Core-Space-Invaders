package com.jgefroh.systems;

import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.core.LoggerFactory;



/**
 * Handles the displayi and position tracking of the cursor according to LWJGL.
 * 
 * Date: 04JUN13
 * @author Joseph Gefroh
 * @version 0.1.0
 */
public class CursorSystem implements ISystem
{	
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	@SuppressWarnings("unused")
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
	
	
	//////////
	// INIT
	//////////
	/**
	 * Creates a new instance of this {@code System}.
	 * @param core	a reference to the Core controlling this system
	 */
	public CursorSystem(final Core core)
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
		loadCursor();
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
	}
	
	//////////
	// SYSTEM METHODS
	//////////
	private void loadCursor()
	{
		ResourceLoader rl =
					core.getSystem(ResourceLoader.class);
		if(rl!=null)
		{
			IntBuffer ib = rl.loadCursorFromImage("res\\cursor.png");
			ib.rewind();
			Cursor cursor;
			try
			{
				cursor = new Cursor(16, 16, 8, 8, 1, ib, null);
				Mouse.setCursorPosition(1680,1050);
				Mouse.setNativeCursor(cursor);
				Mouse.setClipMouseCoordinatesToWindow(true);
				Mouse.setGrabbed(false);
			}
			catch (LWJGLException e)
			{
				e.printStackTrace();
			}
		}

	}
}
