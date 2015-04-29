package com.mygdx.game;


public class Blinky extends Enemy {

	Blinky(int sprite, int health, float tpm) {
		super(sprite, health, tpm);
	}
	@Override
	void updateTarget()
	{
		this.targetX=this.pacman.tileX;
		this.targetY=this.pacman.tileY;
	}

}
