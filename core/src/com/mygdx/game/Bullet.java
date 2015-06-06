package com.mygdx.game;

public class Bullet extends Actor {

	int life=-1;
	
	public Bullet(int sprite) {
		super(sprite);
		this.collisions=false;
	}
	
	void tick()
	{
		super.tick();
		for (Actor a : ActorManager.actors)
		{
			if (a instanceof Enemy)
			{
				if ((a.tileX==this.tileX)&&(a.tileY==this.tileY))
				{
					((Enemy)a).damage(1);
					this.kill();
					break;
				}
			}
		}
		if (this.life>0)
		{
			this.life--;
			if (this.life==0)
			{
				this.kill();
			}
		}
	}
	
	@Override
	void move(int dir) {
		this.pixelX+=2*Direction.oX[dir];
		this.pixelY+=2*Direction.oY[dir];
	}

}
