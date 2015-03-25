package com.mygdx.game;

public class Stage {
	static int width;
	static int height;
	static int[] blocks;
	static void resetStage()
	{
		Stage.loadmap(0);
	}
	private static void loadmap(int map) {
		Stage.width=Map.width(map);
		Stage.height=Map.height(map);
		Stage.blocks=Map.blocks(map);
	}
	static int get(int x, int y) {
		int idx=x+y*Stage.width;
		return blocks[idx];
	}
	static void set(int x, int y, int val) {
		int idx=x+y*Stage.width;
		blocks[idx]=val;
	}
}

								