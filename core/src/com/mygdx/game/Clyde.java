package com.mygdx.game;


public class Clyde extends Enemy {

	Clyde(int sprite, int health, float tpm) {
		super(sprite, health, tpm);
	}
	@Override
	void updateTarget()
	{
		if (Enemy.distance(this.tileX,this.tileY,this.pacman.tileX,this.pacman.tileY)<8)
		{
			this.targetX=0;
			this.targetY=-1;
		}
		else
		{
			this.targetX=this.pacman.tileX;
			this.targetY=this.pacman.tileY;
		}
	}

}
