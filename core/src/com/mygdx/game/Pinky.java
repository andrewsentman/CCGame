package com.mygdx.game;


public class Pinky extends Enemy {

	Pinky(int sprite) {
		super(sprite,1);
	}
	@Override
	void updateTargetScatter()
	{	this.targetX=2;
		this.targetY=35;
	}
	@Override
	void updateTarget()
	{
		this.targetX=this.pacman.tileX + 4*Direction.oX[this.pacman.direction];
		this.targetY=this.pacman.tileY + 4*Direction.oY[this.pacman.direction];
	}

}
