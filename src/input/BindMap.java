package input;



import java.util.HashMap;

import actions.IAction;

/**
 * An implementation of the IBindSystem interface that handles input binds.
 * @author Joseph Gefroh
 * @since 23FEB13
 */
public class BindMap implements IBindMap
{
	HashMap<Integer, IAction> bindsOnHold;
	HashMap<Integer, IAction> bindsOnPress;
	HashMap<Integer, IAction> bindsOnRelease;
	
	/**
	 * Create a default bind system with no binds.
	 */
	public BindMap()
	{
		bindsOnHold = new HashMap<Integer, IAction>();
		bindsOnPress = new HashMap<Integer, IAction>();
		bindsOnRelease = new HashMap<Integer, IAction>();
	}
	
	@Override	
	public IAction getActionOnHold(final int keyCode)
	{
		IAction action = null;
		if(bindsOnHold.get(keyCode)!=null)
		{
			action = bindsOnHold.get(keyCode);
		}
		return action;
	}
	
	@Override	
	public IAction getActionOnPress(final int keyCode)
	{
		IAction action = null;
		if(bindsOnPress.get(keyCode)!=null)
		{
			action = bindsOnPress.get(keyCode);
		}
		return action;
	}
	
	@Override
	public IAction getActionOnRelease(final int keyCode)
	{
		IAction action = null;
		if(bindsOnRelease.get(keyCode)!=null)
		{
			action = bindsOnRelease.get(keyCode);
		}
		return action;
	}

	@Override
	public void bind(final int keyCode, final IAction action, final int type)
	{
		switch(type)
		{
			case IBindMap.PRESS:
				bindsOnPress.put(keyCode, action);
				break;
			case IBindMap.HOLD:
				bindsOnHold.put(keyCode, action);
				break;
			case IBindMap.RELEASE:
				bindsOnRelease.put(keyCode, action);
				break;
			case IBindMap.ALL:
				bindsOnPress.put(keyCode,  action);
				bindsOnRelease.put(keyCode,  action);
				bindsOnHold.put(keyCode,  action);
				break;
		}
	}

	@Override
	public void unbind(final int keyCode, final int type)
	{
		switch(type)
		{
			case IBindMap.PRESS:
				bindsOnPress.remove(keyCode);
				break;
			case IBindMap.HOLD:
				bindsOnHold.remove(keyCode);
				break;
			case IBindMap.RELEASE:
				bindsOnRelease.remove(keyCode);
				break;
		}	
	}

}
