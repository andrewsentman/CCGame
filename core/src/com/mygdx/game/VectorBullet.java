package com.mygdx.game;

public class VectorBullet extends Bullet {

	double dx=0;
	double dy=0;
	double rx=0;
	double ry=0;
	public VectorBullet(int sprite) {
		super(sprite);
	}
	@Override
	public void move(int dir)
	{
		this.pixelX+=Math.floor(this.dx+this.rx);
		this.pixelY+=Math.floor(this.dy+this.ry);
		this.rx=this.dx+this.rx-Math.floor(this.dx+this.rx);
		this.ry=this.dy+this.ry-Math.floor(this.dy+this.ry);
	}
}
