package systems;

import infopacks.GravityInfoPack;

import java.util.ArrayList;

/**
 * This system accelerates objects downward up to a maximum velocity.
 * @author Joseph Gefroh
 */
public class GravitySystem implements ISystem
{
	private Core core;
	
	public GravitySystem(final Core core)
	{
		this.core = core;
	}
	
	@Override
	public void start() 
	{
	}
	
	
	@Override
	public void work()
	{
		ArrayList<GravityInfoPack> infoPacks = core.getInfoPacksOfType(GravityInfoPack.class);
		for(GravityInfoPack each:infoPacks)
		{
			if(each.updateReferences())
			{
				if(core.getSystem(TimerSystem.class).getNow()-each.getLastUpdate()>=each.getUpdateInterval())
				{//If it is time to make them fall again...
					accelerate(each);
					each.setLastUpdate(core.getSystem(TimerSystem.class).getNow());

				}
			}
		}
	}
	
	private void accelerate(final GravityInfoPack each)
	{
		if(each.getGravity()+each.getYVelocity()<each.getMaxGravity())
		{//If max gravity not yet reached...
			each.setYVelocity(each.getYVelocity()+each.getGravity());
		}
	}
	
	@Override
	public void stop()
	{	
	}

}
