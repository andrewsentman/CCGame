package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Pinky extends Ghost {

	Pinky(TextureRegion sprite, Pacman pacman, StageTimer timer) {
		super(sprite, pacman, timer);
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
