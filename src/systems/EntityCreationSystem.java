package systems;

import components.AIComponent;
import components.BulletComponent;
import components.CollisionComponent;
import components.InputComponent;
import components.RenderComponent;
import components.TransformComponent;
import components.VelocityComponent;
import components.WeaponComponent;

import entities.Entity;
import entities.IEntity;

/**
 * @author Joseph Gefroh
 */
public class EntityCreationSystem implements ISystem
{
	private Core core;
	
	public EntityCreationSystem(final Core core)
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
	}

	@Override
	public void stop()
	{	
	}
	

	//////////
	public void createPlayer(final int x, final int y)
	{	
		//0 = player
		//1 = alien
		//2 = player bullet
		//3 = alien bullet
		//4 = fort
		IEntity player = new Entity();
		player.setName("Player");
		player.addComponent(TransformComponent.class, new TransformComponent(player));
		player.getComponent(TransformComponent.class).setYPos(y);
		player.getComponent(TransformComponent.class).setXPos(x);
		player.getComponent(TransformComponent.class).setWidth(64);
		player.getComponent(TransformComponent.class).setHeight(64);
		player.addComponent(RenderComponent.class, new RenderComponent(player, 0, true));
		player.addComponent(VelocityComponent.class, new VelocityComponent(player));
		player.getComponent(VelocityComponent.class).setInterval(4);
		player.getComponent(VelocityComponent.class).setXVelocity(0);
		player.addComponent(InputComponent.class, new InputComponent(player));
		player.getComponent(InputComponent.class).setInterested("MOVE_RIGHT");
		player.getComponent(InputComponent.class).setInterested("MOVE_LEFT");
		player.getComponent(InputComponent.class).setInterested("STOPX");
		player.getComponent(InputComponent.class).setInterested("STOPY");
		player.getComponent(InputComponent.class).setInterested("SHOOT");
		player.addComponent(CollisionComponent.class, new CollisionComponent(player));
		player.getComponent(CollisionComponent.class).setCollisionGroup(0);
		
		player.addComponent(WeaponComponent.class, new WeaponComponent(player));
		core.addEntity(player);
	}
	
	public void createAlien(final int x, final int y)
	{
		IEntity alien = new Entity();
		alien.setName("alien");
		alien.addComponent(TransformComponent.class, new TransformComponent(alien));
		alien.getComponent(TransformComponent.class).setXPos(x);
		alien.getComponent(TransformComponent.class).setYPos(y);
		alien.getComponent(TransformComponent.class).setWidth(32);
		alien.getComponent(TransformComponent.class).setHeight(32);
		alien.addComponent(RenderComponent.class, new RenderComponent(alien, 0, true));
		alien.addComponent(VelocityComponent.class, new VelocityComponent(alien));
		alien.getComponent(VelocityComponent.class).setInterval(4);
		alien.getComponent(VelocityComponent.class).setXVelocity(0);
		alien.addComponent(CollisionComponent.class, new CollisionComponent(alien));
		alien.getComponent(CollisionComponent.class).setCollisionGroup(1);
		alien.addComponent(AIComponent.class, new AIComponent(alien));

		alien.addComponent(WeaponComponent.class, new WeaponComponent(alien));
		core.addEntity(alien);
	}
	
	public void createFort(final int x, final int y)
	{
		IEntity fort = new Entity();
		fort.setName("Alien");
		fort.addComponent(TransformComponent.class, new TransformComponent(fort));
		fort.getComponent(TransformComponent.class).setXPos(x);
		fort.getComponent(TransformComponent.class).setYPos(y);
		fort.getComponent(TransformComponent.class).setWidth(128);
		fort.getComponent(TransformComponent.class).setHeight(64);
		fort.addComponent(RenderComponent.class, new RenderComponent(fort, 0, true));
		fort.addComponent(CollisionComponent.class, new CollisionComponent(fort));
		fort.getComponent(CollisionComponent.class).setCollisionGroup(4);
		core.addEntity(fort);
	}
	public void createBullet(final IEntity owner)
	{
		IEntity bullet = new Entity();
		
		int xPos = owner.getComponent(TransformComponent.class).getXPos();
		xPos+= owner.getComponent(TransformComponent.class).getWidth()/2;
		xPos-=8;
		int yPos = owner.getComponent(TransformComponent.class).getYPos();
		bullet.setName("bullet");
		bullet.addComponent(BulletComponent.class, new BulletComponent(bullet));
		bullet.getComponent(BulletComponent.class).setBulletOwner(owner);
		bullet.addComponent(TransformComponent.class, new TransformComponent(bullet));
		bullet.getComponent(TransformComponent.class).setXPos(xPos);
		bullet.getComponent(TransformComponent.class).setYPos(yPos);
		bullet.getComponent(TransformComponent.class).setWidth(16);
		bullet.getComponent(TransformComponent.class).setHeight(32);
		bullet.addComponent(VelocityComponent.class, new VelocityComponent(bullet));
		bullet.getComponent(VelocityComponent.class).setInterval(4);
		bullet.getComponent(VelocityComponent.class).setXVelocity(0);
		bullet.addComponent(RenderComponent.class, new RenderComponent(bullet, 0, true));
		bullet.addComponent(CollisionComponent.class, new CollisionComponent(bullet));
		if(owner.getName().equals("alien"))
		{
			bullet.getComponent(CollisionComponent.class).setCollisionGroup(3);		
			bullet.getComponent(VelocityComponent.class).setYVelocity(10);
		}
		else
		{
			bullet.getComponent(CollisionComponent.class).setCollisionGroup(2);		
			bullet.getComponent(VelocityComponent.class).setYVelocity(-10);
		}
		core.addEntity(bullet);
	}
}
