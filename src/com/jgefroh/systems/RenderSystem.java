package com.jgefroh.systems;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.opengl.GL11;

import com.jgefroh.core.Core;
import com.jgefroh.core.ISystem;
import com.jgefroh.data.Sprite;
import com.jgefroh.data.Texture;
import com.jgefroh.infopacks.RenderInfoPack;


/**
 * This system handles the rendering and drawing of entities.
 * 
 * @author Joseph Gefroh
 */
public class RenderSystem implements ISystem
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
		= Logger.getLogger(RenderSystem.class.getName());
	
	/**The level of detail in debug messages.*/
	private Level debugLevel = Level.FINE;
	
	private HashMap<Integer, Texture> textures;
	
	//////////
	// INIT
	//////////
	/**
	 * Create a new RenderSystem.
	 * @param core	 a reference to the Core controlling this system
	 */
	public RenderSystem(final Core core)
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
	
	/**
	 * Initialize OpenGL settings.
	 */
	private void initOpenGL()
	{
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);	//Hint to increase performance (do once)
		GL11.glEnable(GL11.GL_BLEND);	//Enables blending? (do once)
		GL11.glEnable(GL11.GL_TEXTURE_2D);	//Enables blending? (do once)
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 1680, 1050, 0, -1, 100);
	}
	
	
	//////////
	// ISYSTEM INTERFACE
	//////////
	@Override
	public void init()
	{
		initLogger();
		initOpenGL();
		textures = new HashMap<Integer, Texture>();
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
			render();
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "System stopped.");
		isRunning = false;
	}
	
	//////////
	// GETTERS
	//////////
	/**
	 * Get the uMin texture coordinates stored for a given sprite and texture.
	 * @param textureID		the OpenGL assigned ID of the texture
	 * @param spriteIndex	the index of the sprite who's coordinate to return
	 * @return	the uMin texture coordinate of the sprite, 0 if error
	 */
	public float getUMin(final int textureID, final int spriteIndex)
	{
		Texture texture = textures.get(textureID);
		if(texture!=null)
		{
			return texture.getUMin(spriteIndex);
		}
		return 0.0f;
	}
	
	/**
	 * Get the uMax texture coordinates stored for a given sprite and texture.
	 * @param textureID		the OpenGL assigned ID of the texture
	 * @param spriteIndex	the index of the sprite who's coordinate to return
	 * @return	the uMax texture coordinate of the sprite, 0 if error
	 */
	public float getUMax(final int textureID, final int spriteIndex)
	{
		Texture texture = textures.get(textureID);
		if(texture!=null)
		{
			return texture.getUMax(spriteIndex);
		}
		return 0.0f;
	}
	
	/**
	 * Get the vMin texture coordinates stored for a given sprite and texture.
	 * @param textureID		the OpenGL assigned ID of the texture
	 * @param spriteIndex	the index of the sprite who's coordinate to return
	 * @return	the vMin texture coordinate of the sprite, 0 if error
	 */
	public float getVMin(final int textureID, final int spriteIndex)
	{
		Texture texture = textures.get(textureID);
		if(texture!=null)
		{
			return texture.getVMin(spriteIndex);
		}
		return 0;
	}
	
	/**
	 * Get the vMax texture coordinates stored for a given sprite and texture.
	 * @param textureID		the OpenGL assigned ID of the texture
	 * @param spriteIndex	the index of the sprite who's coordinate to return
	 * @return	the vMax texture coordinate of the sprite, 0 if error
	 */
	public float getVMax(final int textureID, final int spriteIndex)
	{
		Texture texture = textures.get(textureID);
		if(texture!=null)
		{
			return texture.getVMax(spriteIndex);
		}
		return 0.0f;
	}
	
	
	//////////
	// SYSTEM METHODS
	//////////
	/**
	 * Render the entities that have render components.
	 */
	public void render()
	{
		newFrame();
		ArrayList<RenderInfoPack> infoPacks = 
				core.getInfoPacksOfType(RenderInfoPack.class);
		for(RenderInfoPack pack:infoPacks)
		{
			//Bind the texture
			drawQuadAt(pack.getXPos(), pack.getYPos(), pack.getZPos(),
					pack.getWidth(), pack.getHeight(),
					getUMin(pack.getTextureID(), pack.getSpriteIndex()),
					getUMax(pack.getTextureID(), pack.getSpriteIndex()),
					getVMin(pack.getTextureID(), pack.getSpriteIndex()),
					getVMax(pack.getTextureID(), pack.getSpriteIndex()));
		}
	}

	/**
	 * Clear the previous frame.
	 */
	private void newFrame()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
	}
	
	/**
	 * Draw a textured quad.
	 * @param x			the x-position on screen to draw.
	 * @param y			the y-position on screen to draw.
	 * @param width		the width of the quad
	 * @param height	the height of the quad
	 * @param uMin		the u-texture coordinate minimum
	 * @param uMax		the u-texture coordinate maximum
	 * @param vMin		the v-texture coordinate minimum
	 * @param vMax		the v-texture coordinate maximum
	 */
	private void drawQuadAt(final long x, final long y, final long z, final int width, final int height,
							final float uMin, final float uMax, final float vMin, final float vMax)
	{

		GL11.glPushMatrix();		//Save current matrix
		GL11.glTranslatef(x, y, z);	//Move to specified draw location
		//GL11.glRotatef(x, 0, 0, 1);
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 1);

			GL11.glTexCoord3f(uMin, vMin, z);	//Top Left
			GL11.glVertex3f(0, 0, z);		//Top Left

			GL11.glTexCoord3f(uMax, vMin, z);	//Top Right
			GL11.glVertex3f(width, 0, z);	//Top Right

			GL11.glTexCoord3f(uMax, vMax, z);	//Bottom Right
			GL11.glVertex3f(width, height, z);	//Bottom Right

			GL11.glTexCoord3f(uMin, vMax, z);	//Bottom Left
			GL11.glVertex3f(0, height, z);	//Bottom Left
		}
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	
	/**
	 * Load a texture and its metadata into the Render system.
	 * @param buffer	the buffer containing the pixel data of the texture
	 * @param meta		the metadata related to the texture
	 */
	public void createTexture(final ByteBuffer buffer, final Texture meta)
	{
		//TODO: Set it so glTexImage2D is automatically fed the size of the texture.
		//TODO: Make metadata part work.
		int textureID = GL11.glGenTextures();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        
        int width = meta.getImageWidth();
        int height = meta.getImageHeight();
        
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        
        calcTextureCoordinates(meta);
        textures.put(textureID, meta);
	}
	
	/**
	 * Go through a texture's sprites and calculate their texture coordinates.
	 * @param meta	the Texture you want to go through
	 */
	public void calcTextureCoordinates(final Texture meta)
	{
		for(int index=0;index<meta.getNumSprites();index++)
		{ 
			float uMin = ((float)meta.getXMin(index))/meta.getImageWidth();
			float uMax = ((float)meta.getXMax(index))/meta.getImageWidth();
			float vMin = ((float)meta.getYMin(index))/meta.getImageHeight();
			float vMax = ((float)meta.getYMax(index))/meta.getImageHeight();
			meta.setSpriteUMin(index, uMin);
			meta.setSpriteUMax(index, uMax);
			meta.setSpriteVMin(index, vMin);
			meta.setSpriteVMax(index, vMax);
		}
	}
}
