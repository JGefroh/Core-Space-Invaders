package input;

import infopacks.InputInfoPack;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import systems.Core;
import systems.ISystem;
import actions.ActionCameraMoveDown;
import actions.ActionCameraMoveLeft;
import actions.ActionCameraMoveRight;
import actions.ActionCameraMoveUp;
import actions.ActionCameraStopX;
import actions.ActionCameraStopY;
import actions.ActionExit;
import actions.ActionMoveDown;
import actions.ActionMoveLeft;
import actions.ActionMoveRight;
import actions.ActionMoveUp;
import actions.ActionStopX;
import actions.ActionStopY;
import actions.IAction;



/**
 * This class receives input information from the input systems and acts
 * according to the binds associated with the inputs.
 * @author Joseph Gefroh
 */
public class InputSystem implements IInputSystem, ISystem
{
	private InputDevice_Keyboard kir;
	private InputDevice_Mouse mir;
	private IBindMap kbs;
	private IBindMap mbs;
	private Core core;
	
	public InputSystem(final Core core)
	{
		this.core = core;
		kir = new InputDevice_Keyboard(this);
		mir = new InputDevice_Mouse(this);
	}
	
	/**
	 * Notify the response system that an input has been received.
	 * @param device		the device that has sent the input
	 * @param keyCode		the code of the key that has generated the input
	 * @param typeEvent		the type of input (what has happened to the key)
	 */
	@Override
	public void notify(final int device, final int keyCode, final int typeEvent)
	{
		if(device==KEYBOARD)
		{
			handleKeyboardEvent(keyCode, typeEvent);
		}
		else if(device==MOUSE)
		{
			handleMouseEvent(keyCode, typeEvent);
		}
	}
	
	/**
	 * Handle the keyboard event.
	 * @param keyCode	the code of the key that has generated the input
	 * @param type		the type of input (what has happened to the key)
	 */
	private void handleKeyboardEvent(final int keyCode, final int type)
	{
		IAction action = null;
		switch(type)
		{
			case PRESS:
				action = kbs.getActionOnPress(keyCode);
				break;
			case HOLD:
				action = kbs.getActionOnHold(keyCode);
				break;
			case RELEASE:
				action = kbs.getActionOnRelease(keyCode);
				break;
		}
		processAction(action);
	}
	
	/**
	 * Handle the mouse events.
	 * @param keyCode	the code of the key (or button) that has generated input
	 * @param type		the type of input (what has happened to the key)
	 */
	private void handleMouseEvent(final int keyCode, final int type)
	{
		IAction action = null;
		switch(type)
		{
			case PRESS:
				action = mbs.getActionOnPress(keyCode);
				break;
			case HOLD:
				action = mbs.getActionOnHold(keyCode);
				break;
			case RELEASE:
				action = mbs.getActionOnRelease(keyCode);
				break;
			case MOVE:
				action = mbs.getActionOnPress(type);
		}
		processAction(action);
	}
	
	/**
	 * Set the bind system for a specific input device
	 * @param device		the code of the device
	 * @param bindSystem	the bind system
	 */
	public void setBindSystem(final int device, final IBindMap bindSystem)
	{
		if(device==KEYBOARD)
		{
			kbs = bindSystem;
		}
		else if(device==MOUSE)
		{
			mbs = bindSystem;
		}
	}
	
	@Override
	public void poll()
	{
		kir.processAllEvents();
	}
	
	public void processAction(final IAction action)
	{
		if(action!=null)
		{
			String command = action.getCommand();
			ArrayList<InputInfoPack> entities = core.getInfoPacksOfType(InputInfoPack.class);
			for(InputInfoPack each:entities)
			{
				if(each.isEnabled()&&each.isInterested(command))
				{
					action.execute(each.getParent());
				}
			}
		}
	}
	

	@Override
	public void start()
	{
		initBinds();
	}

	@Override
	public void work()
	{
		kir.processAllEvents();
		mir.processAllEvents();
	}

	@Override
	public void stop()
	{
		// TODO Auto-generated method stub
		
	}
	private void initBinds()
	{
		BindMap kbs = new BindMap();
		kbs.bind(Keyboard.KEY_A, new ActionMoveLeft(core, IAction.MOVELEFT), kbs.HOLD);
		kbs.bind(Keyboard.KEY_D, new ActionMoveRight(core, IAction.MOVERIGHT), kbs.HOLD);
		kbs.bind(Keyboard.KEY_W, new ActionMoveUp(core, IAction.MOVEUP), kbs.HOLD);
		kbs.bind(Keyboard.KEY_S, new ActionMoveDown(core, IAction.MOVEDOWN), kbs.HOLD);
		kbs.bind(Keyboard.KEY_J, new ActionCameraMoveLeft(core, "MOVECLEFT"), kbs.HOLD);
		kbs.bind(Keyboard.KEY_L, new ActionCameraMoveRight(core, "MOVECRIGHT"), kbs.HOLD);
		kbs.bind(Keyboard.KEY_I, new ActionCameraMoveUp(core, "MOVECUP"), kbs.HOLD);
		kbs.bind(Keyboard.KEY_K, new ActionCameraMoveDown(core, "MOVECDOWN"), kbs.HOLD);
		kbs.bind(Keyboard.KEY_W, new ActionStopY(core, IAction.STOPY), kbs.RELEASE);
		kbs.bind(Keyboard.KEY_S, new ActionStopY(core, IAction.STOPY), kbs.RELEASE);
		kbs.bind(Keyboard.KEY_D, new ActionStopX(core, IAction.STOPX), kbs.RELEASE);
		kbs.bind(Keyboard.KEY_A, new ActionStopX(core, IAction.STOPX), kbs.RELEASE);
		kbs.bind(Keyboard.KEY_J, new ActionCameraStopX(core, "STOPCX"), kbs.RELEASE);
		kbs.bind(Keyboard.KEY_L, new ActionCameraStopX(core, "STOPCX"), kbs.RELEASE);
		kbs.bind(Keyboard.KEY_I, new ActionCameraStopY(core, "STOPCY"), kbs.RELEASE);
		kbs.bind(Keyboard.KEY_K, new ActionCameraStopY(core, "STOPCY"), kbs.RELEASE);
		kbs.bind(Keyboard.KEY_ESCAPE, new ActionExit("EXIT"), kbs.HOLD);
		setBindSystem(IInputSystem.KEYBOARD, kbs);
		
		setBindSystem(IInputSystem.MOUSE, new BindMap());
	}
	

}
