package com.mygdx.game;

public class TrackingBullet extends VectorBullet {

	Enemy target;

	public TrackingBullet(int sprite, Enemy target) {
		super(sprite);
		this.target=target;
	}
	
	

}
