package systems;

import infopacks.CameraInfoPack;
import infopacks.RenderInfoPack;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import data.TextureCoordinateData;
import data.TextureData;

/**
 * This system handles the rendering and drawing of entities, as well as the 
 * loading and tracking of textures.
 * 
 * @author Joseph Gefroh
 */
public class RenderSystem implements ISystem
{
	/**The minimum X position visible on the screen.*/
	private int screenXMin;
	
	/**The maximum X position visible on the screen.*/
	private int screenXMax;
	
	/**The minimum Y position visible on the screen.*/
	private int screenYMin;
	
	/**The maximum Y position visible on the screen.*/
	private int screenYMax;
	
	/**The width of the display area, in pixels.*/
	private int windowWidth;
	
	/**The height of the window, in pixels.*/
	private int windowHeight;
	
	/**A reference to the core powering this system.*/
	private Core core;
	
	/**The texture that is currently bound.*/
	private Texture currentBound;
	
	/**Loaded textures are stored here for easy reference later.*/
	private HashMap<Integer, Texture> textures;
	
	/**Texture Meta Data is stored here.*/
	private HashMap<Integer, TextureData> textureMetaData;
	
	/**FLAG: Determine whether to render objects.*/
	private boolean isRunning;
	
	private final static Logger LOGGER = Logger.getLogger(RenderSystem.class.getName());
	
	private void initLogger()
	{
		LOGGER.setLevel(Level.ALL);
	}
	
	/**
	 * Initialize a new renderer with the given width and height.
	 * @param width		the width of the drawing area	(should match window)
	 * @param height	the height of the drawing area (should match window)
	 */
	public RenderSystem(final Core core, final int width, final int height)
	{		
		this.core = core;
		/*Set up the little options and hints, etc.*/
		GL11.glDepthFunc(GL11.GL_LEQUAL);
	//	GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);	//Hint to increase performance (do once)
		GL11.glEnable(GL11.GL_BLEND);	//Enables blending? (do once)
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, -1, 100);
		
		this.screenXMin = 0;
		this.screenXMax = width;		
		this.screenYMin = 0;
		this.screenYMax = height;
		
		this.windowWidth = width;
		this.windowHeight = height;
		
		textures = new HashMap<Integer, Texture>();
		textureMetaData = new HashMap<Integer, TextureData>();
	}

	///////////////////////////////////////////////////////////////////////

	/**
	 * Bind the texture and use it for rendering.
	 * @param texturePath	the path of the texture to bind
	 */
	public void bind(final int textureID)
	{
		Texture texture = textures.get(textureID);
		if(texture!=null&&currentBound!=texture)
		{
			texture.bind();
			currentBound = texture;
		}
	}
	
	/**
	 * Render the entities that have render components.
	 */
	public void render()
	{
		//Clear the current frame.
		newFrame();
		
		//Get the render information from Core.
		ArrayList<RenderInfoPack> infoPacks = 
				core.getInfoPacksOfType(RenderInfoPack.class);
		for(RenderInfoPack pack:infoPacks)
		{//For each renderable entity
			if(pack.updateReferences()&&pack.isVisible())
			{//If the entity is visible...
				//if(isOnScreen(pack.getXPos(), pack.getYPos(), pack.getWidth(), pack.getHeight()))
				{//If the entity is on the screen
					//Bind the texture the entity should be drawn with
					bind(pack.getTextureID());
					//Get the texture coordinates for its current sprite
					//TextureCoordinateData tcd = getCoordinatesFor(pack.getTextureID(), pack.getSpriteIndex());
					//Draw the sprite
					drawQuadAt(pack.getXPos(), pack.getYPos(), pack.getZPos(),
								pack.getWidth(), pack.getHeight(),
								0, 1, 0, 1);
						//		tcd.getXMin(), tcd.getXMax(), tcd.getYMin(), tcd.getYMax());
				}
			}
		}
	}
	
	public void updateCamera()
	{
		ArrayList<CameraInfoPack> infoPacks 
			= core.getInfoPacksOfType(CameraInfoPack.class);
		
		for(CameraInfoPack pack:infoPacks)
		{
			if(pack.updateReferences()&&pack.isActive())
			{
				setScreenXMin(pack.getXPos());
				setScreenXMax(screenXMin+pack.getWidth());
				setScreenYMin(pack.getYPos());
				setScreenYMax(screenYMin+pack.getHeight());
				break;	//quit loop
			}
		}
	}
	private TextureCoordinateData getCoordinatesFor(final int textureID, final int spriteNum)
	{
		return textureMetaData.get(textureID).getCoordinatesFor(spriteNum);
	}
	public void setScreenXMin(final int screenXMin)
	{
		this.screenXMin = screenXMin;
	}

	public void setScreenYMin(final int screenYMin)
	{
		this.screenYMin = screenYMin;;
	}

	public void setScreenXMax(final int screenXMax)
	{
		this.screenXMax = screenXMax;;
	}

	public void setScreenYMax(final int screenYMax)
	{
		this.screenYMax = screenYMax;;
	}

	///////////////////////////////////////////////////////////////////////
	
	
	public void setOrtho(final int width, final int height)
	{
		GL11.glOrtho(0, width, height, 0, 1, -1);
	}

	///////////////////////////////////////////////////////////////////////
	public int getScreenXMin()
	{
		return this.screenXMin;
	}
	public int getScreenYMin()
	{
		return this.screenYMin;
	}
	public int getScreenXMax()
	{
		return this.screenXMax;
	}
	public int getScreenYMax()
	{
		return this.screenYMax;
	}
	public int getWindowWidth()
	{
		return this.windowWidth;
	}
	public int getWindowHeight()
	{
		return this.windowHeight;
	}

	/**
	 * Get the Texture object for the image at the given path.
	 * @param texturePath	the image path
	 * @return		the Texture object if the image at the path was loaded
	 * 				</br>null if the image has not been loaded
	 */
	public Texture getTexture(final int textureID)
	{
		return textures.get(textureID);
	}

	/**
	 * Check to see if any part of the quad is visible on the screen.
	 * @param x		the int X-position of the quad
	 * @param y		the int Y-position of the quad
	 * @param xSize	the int width of the quad
	 * @param ySize	the int height of the quad
	 * @return	true if the quad is visible on the screen, false otherwise
	 */
	public boolean isOnScreen(final int x, final int y, final int xSize, final int ySize)
	{
		if(xSize>=screenXMin&&x<=screenXMax
			&&ySize>=screenYMin&&y<=screenYMax)
		{
			return true;
		}
		return false;
	}

	/**
	 * Check to see if the image found at the given path is already loaded
	 * @param path		the path of the texture to check
	 * @return	true if loaded, false otherwise
	 */
	public boolean isLoaded(final int textureID)
	{
		if(textures.get(textureID)!=null)
		{
			return true;
		}
		return false;
	}

	/**
	 * Generate the texture coordinates for a given sprite index.
	 * @param spriteCol		the column the sprite is in
	 * @param spriteRow		the row the sprite is in
	 * @param numCols		the number of columns the sheet has
	 * @param numRows		the number of rows the sheet has
	 * @param normalWidth	the normal width of the texture
	 * @param normalHeight	the normal height of the texture
	 * @return				the Data with the coordinate info
	 */
	private TextureCoordinateData calcCoords(final int spriteCol, 
			final int spriteRow, final int numCols, final int numRows, 
			final float normalWidth, final float normalHeight)
	{
		TextureCoordinateData tcd = new TextureCoordinateData();
		int spriteNum = calcSpriteNum(spriteCol, spriteRow, numCols);
		tcd.setSpriteNum(spriteNum);
		tcd.setXMin(calcXMin(spriteCol, numCols, normalWidth));
		tcd.setXMax(calcXMax(spriteCol, numCols, normalWidth));
		tcd.setYMin(calcYMin(spriteRow, numRows, normalHeight));
		tcd.setYMax(calcYMax(spriteRow, numRows, normalHeight));

		return tcd;
	}
	
	///////////////////////////////
	/*
	private void calculateSpriteLocations(final TextureData textureData)
	{
		int numCols = textureData.getNumCols();
		int numRows = textureData.getNumRows();
		float normalWidth = textureData.getNormalWidth();
		float normalHeight  = textureData.getNormalHeight();
		
		for(int spriteCol=0;spriteCol<textureData.getNumCols();spriteCol++)
		{
			for(int spriteRow=0;spriteRow<textureData.getNumRows();spriteRow++)
			{
				textureData.setCoords(calcCoords(spriteCol, spriteRow, numCols, numRows, normalWidth, normalHeight));
			}
		}
	}*/

	/**
	 * Get the sprite number of a sprite at a given column and row.
	 * @param col	the column the sprite is in
	 * @param row	the row the sprite is in
	 * @return		the int sprite number
	 */
	private int calcSpriteNum(final int col, final int row, final int numCols)
	{
		return row*numCols+col;
	}
	private float calcXMin(final int spriteCol, final int numCols, final float normalWidth)
	{
		return ((float)spriteCol/numCols)*normalWidth;
	}
	private float calcXMax(final int spriteCol, final int numCols, final float normalWidth)
	{
		return ((float)(spriteCol+1)/numCols)*normalWidth;
	}
	private float calcYMin(final int spriteRow, final int numRows, final float normalHeight)
	{
		return ((float)spriteRow/numRows)*normalHeight;
	}
	private float calcYMax(final int spriteRow, final int numRows, final float normalHeight)
	{
		return ((float)(spriteRow+1)/numRows)*normalHeight;
	}
	//////////////////////

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
	 * Draw a quad at the given position and size.
	 * @param x			the x-position on screen to draw.
	 * @param y			the y-position on screen to draw.
	 * @param sizeX		the width of the quad
	 * @param sizeY		the height of the quad
	 */
	private void drawQuadAt(final long x, final long y, final long z, final int sizeX, final int sizeY,
							final float texXMin, final float texXMax, final float texYMin, final float texYMax)
	{

		GL11.glPushMatrix();		//Save current matrix
		GL11.glTranslatef(x+screenXMin, y+screenYMin, z);	//Move to specified draw location
		GL11.glScalef(1, 1, 0);
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_QUADS);
		{

			GL11.glTexCoord3f(texXMin, texYMin, z);	//Top Left
			GL11.glVertex3f(0, 0, z);		//Top Left

			GL11.glTexCoord3f(texXMax, texYMin, z);	//Top Right
			GL11.glVertex3f(sizeX, 0, z);	//Top Right

			GL11.glTexCoord3f(texXMax, texYMax, z);	//Bottom Right
			GL11.glVertex3f(sizeX, sizeY, z);	//Bottom Right

			GL11.glTexCoord3f(texXMin, texYMax, z);	//Bottom Left
			GL11.glVertex3f(0, sizeY, z);	//Bottom Left
		}
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	
	@Override
	public void start()
	{
		LOGGER.log(Level.INFO, "Starting System: RenderSystem.");
		this.isRunning = true;
	}

	@Override
	public void work()
	{
		if(this.isRunning)
		{
			updateCamera();
			render();
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "Stopping System: Renderystem.");
		this.isRunning = false;
	}
	
	
	public void createTexture(final ByteBuffer buffer, final TextureData meta)
	{
		//Get a new texture ID from OpenGL
		int textureID = GL11.glGenTextures();
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB8, 4, 4, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		
	}
}
