package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import systems.ISystem;

/**
 * Handles the window the game runs in.
 * @author Joseph Gefroh
 *
 */
public class WindowSystem implements ISystem
{
	private boolean vSyncEnabled;
	private boolean borderEnabled;
	private long lastFPS = 0;
	private long fps = 0;
	
	/**
	 * Create a window with the given width, height, and title.
	 * @param width		the desired width of the window
	 * @param height	the desired height of the window
	 * @param title		the desired title of the window
	 */
	public WindowSystem(final int width, final int height, final String title)
	{
		setSize(width, height);
		setTitle(title);
		setFullScreenEnabled(false);
		setDisplayMode(findDisplayMode(2560, 1440));
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
	
	
	//////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////
	
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
	//////////////////////////////////////////////
	
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
	
	private long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}


	@Override
	public void start()
	{
	}


	@Override
	public void work()
	{		
	}


	@Override
	public void stop()
	{		
	}
}
