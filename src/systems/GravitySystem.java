package systems;

import infopacks.GravityInfoPack;

import java.util.ArrayList;

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
				//For each entity affected by gravity...
				//Check the last time they fell
				if(core.getSystem(TimerSystem.class).getNow()-each.getLastUpdate()>=each.getUpdateInterval())
				{//If it is time to make them fall again...
					each.addGravity();
					each.setLastUpdate(core.getSystem(TimerSystem.class).getNow());

				}
				//Set velocity to gravity (so movement system can pick it up)
			}
		}
	}

	@Override
	public void stop()
	{
		// TODO Auto-generated method stub
		
	}

}
