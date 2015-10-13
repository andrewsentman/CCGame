package com.mygdx.game;

import java.util.Arrays;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.qwertyzzz18.ccgame.editor.GameMap;
import com.qwertyzzz18.ccgame.editor.SpriteManager;
import com.qwertyzzz18.ccgame.editor.Tile;

public class Stage {
	static int width;
	static int height;
	static int[] blocks;
	static int curmap;
	static GameMap map;
	
	static void resetStage()
	{
		Stage.respawn(0);
	}
	static void evalWarps(int x, int y)
	{
		for (int[] warp : Map.warps(Stage.curmap))
		{
			if ((warp[0]==x)&&(warp[1]==y))
			{
				int[] destwarp=Map.warps(warp[2])[warp[3]];
				System.out.println(Arrays.toString(destwarp));
				loadmap(warp[2],destwarp[0],destwarp[1]);
				return;
			}
		}
	}
	static void respawn(int map)
	{
		StageTimer.ticks=0;
		int tmap = Map.respawn(map);
		loadmap(tmap,Map.charx(tmap),Map.chary(tmap));
	}
	private static void loadmap(int map,int x, int y) {
		Stage.curmap=map;
		Stage.width=Map.width(map);
		Stage.height=Map.height(map);
		Stage.blocks=Map.blocks(map);
		ActorManager.clear();
		ActorManager.addInputActor(new Pacman(Constants.SPRITE_PACMAN,20), 0);
		ActorManager.inputActors.get(0).put(x, y, ((Constants.TILE_SIZE/2)-1), ((Constants.TILE_SIZE/2)-1));
		for (int[] enemy : Map.enemies(map))
		{
			Enemy ac = EnemyManager.getEnemy(enemy[2]);
			ac.put(enemy[0], enemy[1], ((Constants.TILE_SIZE/2)-1), ((Constants.TILE_SIZE/2)-1));
			ActorManager.add(ac);
		}
		for (int[] item : Map.items(map))
		{
			Item ac = new Item(item[2]);
			ac.put(item[0], item[1], ((Constants.TILE_SIZE/2)-1), ((Constants.TILE_SIZE/2)-1));
			ActorManager.add(ac);
		}
	}
	static Tile get(int x, int y) {
	    if (map.getStage(curmap).getTile(x, y)==null)
	    {
	        Tile t = new Tile(-1);
	        t.setParam("solid", "0");
	        return t;
	    }
	    return map.getStage(curmap).getTile(x, y);
		//int idx=x+y*Stage.width;
		//return blocks[idx];
	}
	static void render(SpriteBatch batch)
	{
	    com.qwertyzzz18.ccgame.editor.Stage cstage = map.getStage(curmap);
	    for (int x = 0; x < cstage.getWidth(); x++)
        {
            for (int y = 0; y < cstage.getHeight(); y++)
            {
                Tile ct = cstage.getTile(x, y);
                if (ct != null)
                    batch.draw(SpriteManager.getSprite(ct.getTid()), 35 * x, 35 * y, 0, 0, 35, 35, 1f, 1f, 0f);
            }
        }
	}
	static void set(int x, int y, int val) {
		int idx=x+y*Stage.width;
		blocks[idx]=val;
	}
}

								