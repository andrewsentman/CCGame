package com.mygdx.game;


public class PhysicsActor extends Actor{

	public PhysicsActor(int sprite) {
		super(sprite);
	}

	void move(int dir) {
		if (Stage.get(this.tileX+Direction.oX[dir],this.tileY+Direction.oY[dir])!=1 || 
				(((this.pixelX<Constants.TILE_SIZE?0:(this.pixelX>Constants.TILE_SIZE?2:4))==dir) || ((this.pixelY<Constants.TILE_SIZE?3:(this.pixelY>Constants.TILE_SIZE?1:4))==dir)) ||
									this.collisions==false)
		{
			this.pixelX+=Direction.oX[dir];
			this.pixelY+=Direction.oY[dir];
		}
	}

}