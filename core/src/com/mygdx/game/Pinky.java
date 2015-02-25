package com.mygdx.game;

public class Pinky extends Ghost {

	Pinky(Pacman pacman, StageTimer timer) {
		super(pacman, timer);
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
