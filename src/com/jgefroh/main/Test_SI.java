package com.jgefroh.main;





import org.lwjgl.opengl.Display;

import com.jgefroh.core.Core;
import com.jgefroh.infopacks.AIInfoPackFactory;
import com.jgefroh.infopacks.AnimationInfoPackFactory;
import com.jgefroh.infopacks.BulletInfoPackFactory;
import com.jgefroh.infopacks.CollisionInfoPackFactory;
import com.jgefroh.infopacks.DecayInfoPackFactory;
import com.jgefroh.infopacks.HealthInfoPackFactory;
import com.jgefroh.infopacks.InputInfoPackFactory;
import com.jgefroh.infopacks.MenuInfoPackFactory;
import com.jgefroh.infopacks.MovementInfoPackFactory;
import com.jgefroh.infopacks.RenderInfoPackFactory;
import com.jgefroh.infopacks.ScoreInfoPackFactory;
import com.jgefroh.infopacks.WeaponInfoPackFactory;
import com.jgefroh.input.InputSystem;
import com.jgefroh.systems.AISystem;
import com.jgefroh.systems.AnimationSystem;
import com.jgefroh.systems.CollisionSystem;
import com.jgefroh.systems.CursorSystem;
import com.jgefroh.systems.DamageSystem;
import com.jgefroh.systems.DeadCheckSystem;
import com.jgefroh.systems.DecaySystem;
import com.jgefroh.systems.EntityCreationSystem;
import com.jgefroh.systems.GUISystem;
import com.jgefroh.systems.GameOverCheckSystem;
import com.jgefroh.systems.GameStateSystem;
import com.jgefroh.systems.MenuSystem;
import com.jgefroh.systems.RenderSystem;
import com.jgefroh.systems.ResourceLoader;
import com.jgefroh.systems.StatSystem;
import com.jgefroh.systems.TransformSystem;
import com.jgefroh.systems.WaitSystem;
import com.jgefroh.systems.WeaponSystem;
import com.jgefroh.systems.WindowSystem;


/**
 * The main game loop.
 * @author Joseph Gefroh
 *
 */
public class Test_SI
{
	private Core core;
	private ResourceLoader rl;
	
	public static void main(String[] args)
	{
		Test_SI ts = new Test_SI();
		ts.loop();
		System.exit(0);
	}
	public Test_SI()
	{
		init();
	}
	

	private void init()
	{
		initSystems();
		initFactories();
		loadTexture();
	}
	
	/**
	 * Initialize all of the various systems of the game.
	 */
	private void initSystems()
	{
		core = new Core();
		core.add(new WindowSystem(core, 1680, 1050, "Core - Void Attackers"), true);
		RenderSystem rs = new RenderSystem(core);
			//rs.setOrtho(core.getSystem(WindowSystem.class).getWidth(), 
				//	core.getSystem(WindowSystem.class).getHeight());
		core.add(rs, true);
		
		TransformSystem tranSys = new TransformSystem(core);
			tranSys.setWait(30);
		core.add(tranSys);
		core.add(new InputSystem(core), true);
		core.add(new CollisionSystem(core));
		core.add(new WeaponSystem(core));
		AISystem as = new AISystem(core);
		core.add(as);
		core.add(new EntityCreationSystem(core));
		core.add(new DeadCheckSystem(core));
		core.add(new DamageSystem(core));
		GameOverCheckSystem wcs = new GameOverCheckSystem(core);
			wcs.setWait(100);
		core.add(wcs);
			core.getSystem(GameOverCheckSystem.class).stop();
		core.add(new AnimationSystem(core));
		GUISystem gs = new GUISystem(core);
			gs.setWait(100);
			core.add(gs);
			core.getSystem(GUISystem.class).stop();
		rl = new ResourceLoader(core);
		core.add(rl);
		core.add(new StatSystem(core));
		core.add(new MenuSystem(core));
		core.add(new DecaySystem(core));
		GameStateSystem sys = new GameStateSystem(core);
		//core.add(new GameStateSystem(core));
		//WaitSystem n = new WaitSystem(core);
		core.add(new WaitSystem(core));
		core.add(new CursorSystem(core));
	}
	
	private void initFactories()
	{
		core.add(new RenderInfoPackFactory());
		core.add(new MovementInfoPackFactory());
		core.add(new InputInfoPackFactory());
		core.add(new CollisionInfoPackFactory());
		core.add(new WeaponInfoPackFactory());
		core.add(new BulletInfoPackFactory());
		core.add(new AIInfoPackFactory());
		core.add(new HealthInfoPackFactory());
		core.add(new AnimationInfoPackFactory());
		core.add(new ScoreInfoPackFactory());
		core.add(new MenuInfoPackFactory());
		core.add(new DecayInfoPackFactory());
	}
	
	/**
	 * Loop through game functions (main game loop).
	 */
	public void loop()
	{
		core.send("REQUEST_SHOW_MENU");
		while(!Display.isCloseRequested())
		{
			Display.update();
			core.work();
			Display.sync(240);
		}
	}
	////////////

	
	public void loadTexture()
	{
		rl.loadTexture("res/enemy.png");
		rl.loadTexture("res/fort.png");
		rl.loadTexture("res/player.png");
		rl.loadTexture("res/bullet.png");
		rl.loadTexture("res/alphabet.png");
		rl.loadTexture("res/gui.png");
	}
	

}
