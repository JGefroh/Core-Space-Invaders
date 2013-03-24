package systems;

import infopacks.CursorInfoPack;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

public class GUISystem implements ISystem
{
	private Core core;
	public GUISystem(final Core core)
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
		updateCursorPosition();
	}

	private void updateCursorPosition()
	{
		ArrayList<CursorInfoPack> cip = 
				core.getInfoPacksOfType(CursorInfoPack.class);
		
		for(CursorInfoPack each:cip)
		{
			if(each.updateReferences())
			{
				each.setXPos(Mouse.getX());
				each.setYPos(core.getSystem(RenderSystem.class).getScreenYMax()-Mouse.getY());
			}
		}
	}
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
