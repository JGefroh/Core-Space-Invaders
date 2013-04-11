package systems;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import data.TextureData;

/**
 * This class handles the loading of resources.
 * It will call appropriate resource loaders and send the data to
 * the proper systems.
 * @author Joseph Gefroh
 *
 */
public class ResourceLoader
{
	private final static Logger LOGGER = Logger.getLogger(ResourceLoader.class.getName());
	private Core core;
	public ResourceLoader(final Core core)
	{
		this.core = core;
		initLogger();
	}
	/**
	 * Check to see if a file is valid.
	 * @param path	the path of the file to load.
	 */
	public boolean isValidFile(final String path)
	{
		LOGGER.log(Level.INFO, "Checking validity of: " + path);

		File loadThis = new File(path);
		if(loadThis.isFile())
		{
			if(loadThis.getName().endsWith(".png")
					||loadThis.getName().endsWith(".meta"))
			{
				return true;
			}
			else
			{
				LOGGER.log(Level.WARNING, "Unrecognized file type: " + path);
				return false;
			}
		}
		else
		{
			LOGGER.log(Level.SEVERE, "File not found: " + path);
			return false;
		}
	}
	
	/**
	 * Loads metadata for a texture, stored in JSON with extension .meta
	 * @param path	the path of the file to load
	 * @return the texture meta data
	 */
	public TextureData loadTextureMeta(final String path)
	{
		TextureData metaData = new TextureData();
		if(isValidFile(path))
		{
		}
		return null;
	}
	
	/**
	 * Load a texture into memory.
	 * @param path the path of the file to load
	 */
	public void loadTexture(final String path)
	{
		if(isValidFile(path)&&isValidFile(path.replace(".png", ".meta")))
		{
			File file = new File(path);
			ByteBuffer imageData = convertImageToBuffer(file);
			TextureData metaData = loadTextureMeta(path.replace(".png", ".meta"));
			core.getSystem(RenderSystem.class).createTexture(imageData, metaData);	
		}
	}
	
	/**
	 * Convert an image file into a byte buffer to send to OpenGL.
	 * @param file the image file
	 * @return	a ByteBuffer with the pixel color data of the image
	 */
	private ByteBuffer convertImageToBuffer(final File file)
	{
		try
		{
			//Explanation: http://hrboyceiii.blogspot.com/2007/02/bit-shifting-what-is-it-and-why.html
			int bpp = 4;
			//Convert the file into a buffered image.
			BufferedImage bImage = ImageIO.read(file);	
			//Turn the buffered image into an RGBArray.
			int[] rgbArray = bImage.getRGB(0, 0, bImage.getWidth(), bImage.getHeight(), null, 0, bImage.getWidth());
			//Create a buffer to hold the pixel data.
			ByteBuffer buffer = BufferUtils.createByteBuffer(bImage.getWidth()*bImage.getHeight()*bpp);
			//Load the buffer with the RGBArray data.
			for(int y=0;y<bImage.getHeight();y++)
			{
				for(int x=0;x<bImage.getWidth();x++)
				{
					//Get color data for the pixel at y
					int pixel = rgbArray[y*bImage.getWidth()+x];
					//Place data separately in buffer, in order of RGBA.
					byte r = (byte)((pixel >> 16) & 0xFF);	//Red
					byte g = (byte)((pixel >> 8) & 0xFF);	//Green
					byte b = (byte)((pixel & 0xFF));		//Blue (last)
					byte a = (byte)((pixel >> 24) & 0xFF);	//Alpha
					buffer.put(r);
					buffer.put(g);
					buffer.put(b);
					buffer.put(a);
				}
			}
			
			buffer.flip();
			LOGGER.log(Level.INFO, "Successfuly buffered: " + file.getName());
			return buffer;
		}
		catch(IOException e)
		{
			LOGGER.log(Level.SEVERE, "Error converting: " + file.getName());
			e.printStackTrace();
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			LOGGER.log(Level.SEVERE, "Error converting: " + file.getName());
			e.printStackTrace();
		}
		return null;
	}
	
	private void initLogger()
	{
		LOGGER.setLevel(Level.ALL);
	}
}
