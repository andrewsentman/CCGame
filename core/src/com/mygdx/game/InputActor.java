package com.mygdx.game;


public class InputActor extends PhysicsActor {
	boolean halted=false;
	public InputActor(int sprite) {
		super(sprite);
	}
	@Override
	void tick()
	{
		super.tick();
	}
	void recieveInput(int type)
	{
		if (type<4)
		{
			this.halted=false;
		}
		switch (type)
		{
		case Direction.UP:
			this.joyUp();
			break;
		case Direction.DOWN:
			this.joyDown();
			break;
		case Direction.LEFT:
			this.joyLeft();
			break;
		case Direction.RIGHT:
			this.joyRight();
			break;
		case Direction.NONE:
			this.halt();
			break;
		case Direction.STOP:
			this.halted=true;
			break;
		case Direction.ATTACK:
			this.attack();
			break;
		}
	}
	void halt()
	{
		this.direction=Direction.NONE;
	}
	void joyUp()
	{
		this.direction=Direction.UP;
	}
	void joyDown()
	{
		this.direction=Direction.DOWN;
	}
	void joyLeft()
	{
		this.direction=Direction.LEFT;
	}
	void joyRight()
	{
		this.direction=Direction.RIGHT;
	}
	void attack()
	{
		Bullet bullet = new Bullet(Constants.SPRITE_BULLET);
		bullet.put(this.tileX, this.tileY, this.pixelX, this.pixelY);
		bullet.direction=this.direction;
		ActorManager.add(bullet);
	}
}
