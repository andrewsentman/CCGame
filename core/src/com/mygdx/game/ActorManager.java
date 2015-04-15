package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ActorManager {
	static ArrayList<Actor> actors = new ArrayList<Actor>();
	static HashMap<Integer,InputActor> inputActors = new HashMap<Integer,InputActor>();
	static ArrayList<Actor> toRemove = new ArrayList<Actor>();
	static boolean endtick=false;
	static void add(Actor a)
	{
		actors.add(a);
	}
	static void clear()
	{
		ActorManager.endtick=true;
		actors = new ArrayList<Actor>();
		inputActors = new HashMap<Integer,InputActor>();
	}
	static void addInputActor(InputActor a, int id)
	{
		inputActors.put(id,a);
		ActorManager.add(a);
	}
	static void deleteActor(Actor a)
	{
		toRemove.add(a);
	}
	static void sendInput(int id, int type)
	{
		inputActors.get(id).recieveInput(type);
	}
	static void tick()
	{
		for (Actor a : actors)
		{
			a.tick();
			if (ActorManager.endtick)
			{
				ActorManager.endtick=false;
				return;
			}
		}
		for (Actor a : toRemove)
		{
			actors.remove(a);
		}
		toRemove.clear();
	}
	public static void draw(SpriteBatch batch) {
		for (Actor a : actors)
		{
			a.draw(batch);
		}
	}
}