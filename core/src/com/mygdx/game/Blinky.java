package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Blinky extends Ghost {

	Blinky(TextureRegion sprite, Pacman pacman, StageTimer timer) {
		super(sprite, pacman, timer);
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
