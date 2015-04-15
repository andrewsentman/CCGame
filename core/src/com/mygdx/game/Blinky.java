package com.mygdx.game;


public class Blinky extends Enemy {

	Blinky(int sprite) {
		super(sprite, 1);
	}
	@Override
	void updateTargetScatter()
	{	this.targetX=25;
		this.targetY=35;
	}
	@Override
	void updateTarget()
	{
		this.targetX=this.pacman.tileX;
		this.targetY=this.pacman.tileY;
	}

}
