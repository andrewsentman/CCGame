package com.mygdx.game;


public class Pinky extends Enemy {

	Pinky(int sprite, int health, float tpm) {
		super(sprite, health, tpm);
	}
	@Override
	void updateTarget()
	{
		this.targetX=this.pacman.tileX + 4*Direction.oX[this.pacman.direction];
		this.targetY=this.pacman.tileY + 4*Direction.oY[this.pacman.direction];
	}

}
