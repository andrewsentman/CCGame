package com.mygdx.game;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GfxManager {

	static HashMap<Integer,TextureRegion> textures = new HashMap<Integer,TextureRegion>();
	
	static void add(TextureRegion a, int id)
	{
		textures.put(id,a);
	}
	static TextureRegion get(int id)
	{
		return textures.get(id);
	}
}
