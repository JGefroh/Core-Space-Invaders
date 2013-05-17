package com.jgefroh.data;

import java.util.ArrayList;

import com.jgefroh.components.AnimationComponent;
import com.jgefroh.components.AnimationComponent.LoopType;


/**
 * Contains data of a single animation for use by the AnimationComponent.
 * @author Joseph Gefroh
 *
 */
public class AnimationData
{
	private LoopType loopType;
	private int frameDelay;
	private ArrayList<Integer> frames;
	private String name;
	
	public AnimationData()
	{
	}
	
	////////////////////////////////////////////
	public void setFrames(final ArrayList<Integer> frames)
	{
		this.frames = frames;
	}
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	public void setFrameDelay(final int frameDelay)
	{
		this.frameDelay = frameDelay;
	}
	
	public void setLoopType(final LoopType loopType)
	{
		this.loopType = loopType;
	}
	
	//////////////////////////////////////////
	public ArrayList<Integer> getFrames()
	{
		if(frames!=null)
		{			
			return this.frames;
		}
		return new ArrayList<Integer>();
	}
	
	public int getFrameSprite(final int frame)
	{
		if(frames!=null&&frame<frames.size())
		{
			return frames.get(frame);
		}
		return -1;
	}
	public String getName()
	{
		return this.name;
	}
	
	public int getFrameDelay()
	{
		return this.frameDelay;
	}
	
	public LoopType getLoopType()
	{
		return this.loopType;
	}
	
	public int getMaxFrameNumber()
	{
		if(frames!=null)
		{			
			return this.frames.size();
		}
		return -1;
	}
}
