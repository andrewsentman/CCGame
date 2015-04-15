package com.mygdx.game;


public class PhysicsActor extends Actor{

	public PhysicsActor(int sprite) {
		super(sprite);
	}

	void move(int dir) {
		if (Stage.get(this.tileX+Direction.oX[dir],this.tileY+Direction.oY[dir])!=1 || 
									((lX[this.pixelX]==dir) || (lY[this.pixelY]==dir)) ||
									this.collisions==false)
		{
			this.pixelX+=Direction.oX[dir];
			this.pixelY+=Direction.oY[dir];
		}
	}

}