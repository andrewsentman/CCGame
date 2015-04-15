package com.mygdx.game;

public class Bullet extends Actor {

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
	}
	
	int getScreenX() {
		return this.tileX*8+this.pixelX-3;
	}

	int getScreenY() {
		return this.tileY*8+this.pixelY-3;
	}
	
	@Override
	void move(int dir) {
		this.pixelX+=2*Direction.oX[dir];
		this.pixelY+=2*Direction.oY[dir];
	}

}
