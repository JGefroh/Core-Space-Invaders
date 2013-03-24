package systems;

import infopacks.AnimationInfoPack;
import infopacks.InputInfoPack;

import java.util.ArrayList;

import components.AnimationComponent;
import components.RenderComponent;

import entities.Entity;

/**
 * This system handles changing the sprite image at the required intervals.
 * The AnimationSystem keeps track of frames and animation sequences for 
 * entities. It provides methods to start and stop animations. 
 * @author Joseph Gefroh
 *
 */
public class AnimationSystem implements ISystem
{
	private Core core;
	
	public AnimationSystem(final Core core)
	{
		this.core = core;
	}
	
	/**
	 * Go through all of the entities with AnimationInfoPacks and update
	 * their frames.
	 * @param entities the ArrayList of entities
	 */
	public void update()
	{
		ArrayList<AnimationInfoPack> infoPacks = 
				core.getInfoPacksOfType(AnimationInfoPack.class);
		for(AnimationInfoPack pack:infoPacks)
		{
			if(pack.updateReferences());
			{
				if(pack.isAnimating()
						&&isUpdateTime(pack.getLastUpdateTime(), pack.getFrameDelay()))
					{//If animations are active and it is time to update...
						pack.setLastUpdateTime(core.getSystem(TimerSystem.class).getNow());
						if((pack.isReversing()==false
								&&pack.getMaxFrame()>pack.getCurrentFrame())
								|| (pack.isReversing()==true&&0<pack.getCurrentFrame()))
						{//If it is not at the last frame in either direction
							pack.nextFrame();
						}
						else
						{//else if at the last frame in either direction
							if(pack.getLoopType()==AnimationComponent.LoopType.LOOPFROMSTART)
							{//if looping from beginning of the animation
								pack.setCurrentFrame(0);
							}
							else if(pack.getLoopType()==AnimationComponent.LoopType.LOOPSCAN)
							{//if playing the animation in the opposite direction
								pack.setReversing(!pack.isReversing());
							}
							else
							{//stop playing the animation
								pack.setAnimating(false);
								pack.setCurrentFrame(0);
							}
						}
						pack.setSpriteIndex(pack.getFrameSprite());
					}
			}
		}
	}
	
	/**
	 * Check to see if it is time to update the animation.
	 * @param lastUpdateTime	the time the animation was last updated
	 * @param frameDelay		the time to wait until updating the animation
	 * @return					true if time to update, false otherwise.
	 */
	public boolean isUpdateTime(final long lastUpdateTime, final long frameDelay)
	{
		if(core.getSystem(TimerSystem.class).getNow()-lastUpdateTime>=frameDelay)
		{
			return true;
		}
		return false;
	}
	
	@Override
	public void start()
	{
	}

	@Override
	public void work()
	{		
		update();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
}
