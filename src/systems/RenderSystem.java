package systems;

import infopacks.RenderInfoPack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
	
	/**A reference to the core powering this system.*/
	private Core core;
	
	/**The texture that is currently bound.*/
	private Texture currentBound;
	
	/**Loaded textures are stored here for easy reference later.*/
	private HashMap<String, Texture> textures;
	
	/**Texture Meta Data is stored here.*/
	private HashMap<String, TextureData> textureMetaData;
	
	/**FLAG: Output debug messages into console*/
	private boolean debug = true;
	
	/**FLAG: Determine whether to render objects.*/
	private boolean isRunning;
	
	/**
	 * Initialize a new renderer with the given width and height.
	 * @param width		the width of the drawing area	(should match window)
	 * @param height	the height of the drawing area (should match window)
	 */
	public RenderSystem(final Core core, final int width, final int height)
	{		
		this.core = core;
		/*Set up the little options and hints, etc.*/
		GL11.glEnable(GL11.GL_TEXTURE_2D);	//Enables 2D Textures (do once)
		GL11.glEnable(GL11.GL_DEPTH_TEST);	//Disables depth testing (we are 2D!) (do once)
	//	GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);	//Hint to increase performance (do once)
		GL11.glEnable(GL11.GL_BLEND);	//Enables blending? (do once)
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 100, -1);
		
		this.screenXMin = 0;
		this.screenXMax = width;		
		this.screenYMin = 0;
		this.screenYMax = height;
		
		textures = new HashMap<String, Texture>();
		textureMetaData = new HashMap<String, TextureData>();
		
		loadTextures();
	}

	///////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Load a texture into memory.
	 * @param path		the String path of the texture
	 * @param numCols	the number of columns of sprites the texture contains
	 * @param numRows	the number of rows of sprites the texture contains
	 * @return			the texture id if loaded, -1 otherwise
	 */
	public int load(final String path, final int numCols, final int numRows)
	{
		Texture texture = null;
		TextureData textureData = null;
		if(isLoaded(path)==false)
		{//If the texture is not already loaded...
			try
			{
				texture = TextureLoader.getTexture(".PNG", 
						ResourceLoader.getResourceAsStream(path));
				textures.put(path, texture);	//Load the texture
				
				//Create meta data for the texture, including sprite info
				textureData = new TextureData(texture, numCols, numRows);
				
				//Calculate the locations of all of the sprites
				calculateSpriteLocations(textureData);
				textureMetaData.put(path, textureData);
				
				//Return the texture ID associated with the loaded texture.
				return texture.getTextureID();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		return -1;
	}

	/**
	 * Bind the texture and use it for rendering.
	 * @param texturePath	the path of the texture to bind
	 */
	public void bind(final String texturePath)
	{
		Texture texture = textures.get(texturePath);
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
				if(isOnScreen(pack.getXPos(), pack.getYPos(), pack.getWidth(), pack.getHeight()))
				{//If the entity is on the screen
					//Bind the texture the entity should be drawn with
					bind(pack.getTexturePath());
					//Get the texture coordinates for its current sprite
					TextureCoordinateData tcd = getCoordinatesFor(pack.getTexturePath(), pack.getSpriteIndex());
					//Draw the sprite
					drawQuadAt(pack.getXPos(), pack.getYPos(), pack.getZPos(),
								pack.getHeight(), pack.getHeight(),
								tcd.getXMin(), tcd.getXMax(), tcd.getYMin(), tcd.getYMax());
				}
			}
		}
	}
	
	private TextureCoordinateData getCoordinatesFor(final String texturePath, final int spriteNum)
	{
		return textureMetaData.get(texturePath).getCoordinatesFor(spriteNum);
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
	
	/**
	 * Get the ID of the texture for the image at the given path.
	 * @param path		the image path
	 * @return			the id of the texture if loaded, -1 otherwise
	 */
	public int getTextureID(final String path)
	{
		Texture texture = textures.get(path);
		if(texture!=null)
		{
			return texture.getTextureID();
		}
		return -1;
	}

	/**
	 * Get the Texture object for the image at the given path.
	 * @param texturePath	the image path
	 * @return		the Texture object if the image at the path was loaded
	 * 				</br>null if the image has not been loaded
	 */
	public Texture getTexture(final String texturePath)
	{
		return textures.get(texturePath);
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
	public boolean isLoaded(final String path)
	{
		if(textures.get(path)!=null)
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

		DEBUG(tcd.getSpriteNum() + " | " + spriteCol + "/" + numCols +", " 
					+ spriteRow + "/" + numRows +" | " 
					+ tcd.getXMin() + ", " + tcd.getXMax() 
					+  ", " + tcd.getYMin() +  ", "+ tcd.getYMax());
		return tcd;
	}
	
	///////////////////////////////
	
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
	}

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
	
	private void loadTextures()
	{
		DEBUG("Loaded texture with ID: " + load("res/player.png", 2, 3));
		DEBUG("Loaded texture with ID: " + load("res/enemy.png", 1, 1));
		DEBUG("Loaded texture with ID: " + load("res/gui.png", 1, 1));
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
	 * Draw a quad at the given position and size.
	 * @param x			the x-position on screen to draw.
	 * @param y			the y-position on screen to draw.
	 * @param sizeX		the width of the quad
	 * @param sizeY		the height of the quad
	 */
	private void drawQuadAt(final int x, final int y, final int z, final int sizeX, final int sizeY,
							final float texXMin, final float texXMax, final float texYMin, final float texYMax)
	{

		GL11.glPushMatrix();		//Save current matrix
		GL11.glTranslatef(x, y, z);	//Move to specified draw location
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
	/**
	 * Print a debug statement.
	 * @param msg	the String message to print.
	 */
	private void DEBUG(final String msg)
	{
		if(debug==true)
		{			
			System.out.println("[" + (System.currentTimeMillis()/100) +"] DBG: " + msg + "");
		}
	}
	@Override
	public void start()
	{
		this.isRunning = true;
	}

	@Override
	public void work()
	{
		if(this.isRunning)
		{			
			render();
		}
	}

	@Override
	public void stop()
	{
		this.isRunning = false;
	}

}
