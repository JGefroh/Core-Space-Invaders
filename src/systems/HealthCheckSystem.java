package systems;

import infopacks.HealthInfoPack;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Joseph Gefroh
 */
public class HealthCheckSystem implements ISystem
{
	private Core core;
	
	//////////
	private final static Logger LOGGER 
		= Logger.getLogger(HealthCheckSystem.class.getName());
	private void initLogger()
	{
		LOGGER.setLevel(Level.ALL);
	}
	
	//////////
	public HealthCheckSystem(final Core core)
	{
		this.core = core;
		initLogger();
	}
	
	//////////
	@Override
	public void start()
	{
		LOGGER.log(Level.INFO, "Starting System: HealthCheckSystem.");
	}

	@Override
	public void work()
	{
		ArrayList<HealthInfoPack> packs 
			= core.getInfoPacksOfType(HealthInfoPack.class);
		for(HealthInfoPack each:packs)
		{
			if(each.getCurHealth()<=0)
			{
				core.removeEntity(each.getParent());
				core.getSystem(EventSystem.class)
					.notify("DEAD", each.getParent(), each.getParent());
			}
		}
	}

	@Override
	public void stop()
	{
		LOGGER.log(Level.INFO, "Stopping System: HealthCheckSystem.");
	}

}
