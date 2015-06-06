package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class TrackingBullet extends VectorBullet {

	Actor target;
	double angle=0;
	double ta=0;
	double err=0;
	double prerr=0;
	double av=0;
	double aa=0;
	BitmapFont pfont;
	public TrackingBullet(int sprite, Actor target) {
		super(sprite);
		this.target=target;
		this.pfont = new BitmapFont();
		this.pfont.setColor(Color.YELLOW);
	}
	@Override
	void tick()
	{
		int xo=(this.getScreenX()-this.target.getScreenX());
		int yo=(this.getScreenY()-this.target.getScreenY());
		//int xo=(this.tileX-this.target.tileX);
		//int yo=(this.tileY-this.target.tileY);
		ta=(Math.atan2(-yo, -xo));
		this.err=ta-this.angle;
		/*if (Math.abs(this.angle-ta)>Math.PI)
		{
			this.aa=Math.PI/16;
		} else {*/
		this.aa=this.err-3*this.av;
		//}
		this.av+=this.aa/64;
		if (this.av>1)
		{
			this.av=1;
		}
		if (this.av<-1)
		{
			this.av=-1;
		}
		this.angle+=this.av/4;
		this.angle%=(Math.PI*2);
		this.dx=Math.cos(angle);
		this.dy=Math.sin(angle);
		super.tick();
	}
	@Override
	void draw(SpriteBatch batch)
	{
		super.draw(batch);
		//System.out.println(String.format("%+06.1f", this.angle*180/Math.PI));
		this.pfont.draw(batch, String.format("%+06.1f", this.angle*180/Math.PI), 300, 200);
		this.pfont.draw(batch, ".", 300, 100);
		this.pfont.draw(batch, ".", (int)(300+Math.cos(ta)*16), (int)(100+Math.sin(ta)*16));
		this.pfont.draw(batch, ".", (int)(300+Math.cos(angle)*24), (int)(100+Math.sin(angle)*24));
	}

}
