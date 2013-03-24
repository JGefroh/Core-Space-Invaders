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
		ArrayList<GravityInfoPack> infoPacks = new ArrayList<GravityInfoPack>();
		for(GravityInfoPack each:infoPacks)
		{
			
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
