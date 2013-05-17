package com.jgefroh.systems;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;



/**
 * Handles the window the game runs in.
 * @author Joseph Gefroh
 *
 */
public class WindowSystem implements ISystem
{
	//TODO: Fix.
	
	//////////
	// DATA
	//////////
	/**A reference to the core engine controlling this system.*/
	private Core core;
	
	/**Flag that shows whether the system is running or not.*/
	private boolean isRunning;
	
	/**Logger for debug purposes.*/
	private final static Logger LOGGER 
		= Logger.getLogger(WindowSystem.class.getName());
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	/**Flag that shows if vSync is enabled or disabled.*/
	private boolean vSyncEnabled;
	
	/**Flag that shows if the window border is enabled or disabled.*/
	private boolean borderEnabled;
	
	/**The last number of Frames Per Second.*/
	private long lastFPS = 0;
	
	/**The current number of Frames Per Second.*/
	private long fps = 0;
	
	
	//////////
	// INIT
	//////////
	/**
	 * Create a window with the given width, height, and title.
	 * @param width		the desired width of the window
	 * @param height	the desired height of the window
	 * @param title		the desired title of the window
	 */
	public WindowSystem(final int width, final int height, final String title)
	{
		init();
		setSize(width, height);
		setTitle(title);
		setFullScreenEnabled(false);
		//setDisplayMode(findDisplayMode(2560, 1440));
		setDisplayMode(new DisplayMode(width, height));
		setVSyncEnabled(true);
		lastFPS = getTime();
		try
		{			
			Display.create();
		}
		catch(LWJGLException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
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
		this.isRunning = true;
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
	 * Set the size of the window.
	 * @param width		the int width of the window
	 * @param height	the int height of the window
	 * @throws IllegalArgumentException	thrown if either width or height <=0
	 */
	public void setSize(final int width, final int height) throws IllegalArgumentException
	{
		if(width>0&&height>0)
		{
			try
			{
				Display.setDisplayMode(new DisplayMode(width, height));
			}
			catch(LWJGLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			throw new IllegalArgumentException(
					"Error | Width and height must be > 0");
		}
	}
	
	/**
	 * Find a compatible display mode.
	 * @param width		the desired width
	 * @param height	the desired height
	 * @return	a compatible display mode
	 */
	public DisplayMode findDisplayMode(final int width, final int height)
	{
		DisplayMode[] displayModes;
		try
		{
			displayModes = Display.getAvailableDisplayModes();
			
			for(DisplayMode each:displayModes)
			{
				if(Display.isFullscreen()==true)
				{
					if(each.getWidth()==width&&each.getHeight()==height
							&&each.isFullscreenCapable()==true)
					{
						return each;
					}
				}
				else
				{
					if(each.getWidth()==width&&each.getHeight()==height)
					{
						return each;
					}
				}
			}
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		return new DisplayMode(width, height);
	}
	
	/**
	 * Calculate the Frames-Per-Second of the window and display as its title.
	 */
	public void updateFPS()
	{
		if(getTime() - lastFPS > 1000)
		{
			setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	/**
	 * Get the time, in ms.
	 * @return	the time
	 */
	private long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	//////////
	// GETTERS
	//////////
	/**
	 * Return the width of the window.
	 * @return	the int width of the window
	 */
	public int getWidth()
	{
		return Display.getWidth();
	}
	
	/**
	 * Return the height of the window.
	 * @return	the int height of the window
	 */
	public int getHeight()
	{
		return Display.getHeight();
	}
	
	/**
	 * Returns the window title.
	 * @return	the String value of the window title
	 */
	public String getTitle()
	{
		return Display.getTitle();
	}
	
	
	//////////
	// SETTERS
	//////////
	/**
	 * Set the display mode of the window.
	 * @param displayMode	the display mode that was selected
	 */
	public void setDisplayMode(final DisplayMode displayMode)
	{
		try
		{
			Display.setDisplayMode(displayMode);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Enable or disable full screen mode.
	 * @param fullScreen
	 */
	public void setFullScreenEnabled(final boolean fullScreen)
	{
		try
		{
			Display.setFullscreen(fullScreen);
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the title of the window.
	 * @param title		the String title of the window
	 */
	public void setTitle(final String title)
	{
		Display.setTitle(title);
	}
	/**
	 * Adjust whether the window's border is drawn.
	 * @param border	true to draw the border, false to remove.
	 */
	public void setBorderEnabled(final boolean border)
	{
		System.setProperty("org.lwjgl.opengl.Window.undecorated", 
							""+(!borderEnabled));
	}
	
	/**
	 * Adjust whether Vertical Sync is enabled or disabled.
	 * @param vSync	true to enable Vertical Sync, false to disable.
	 */
	public void setVSyncEnabled(final boolean vSync)
	{
		Display.setVSyncEnabled(vSync);
	}
}
