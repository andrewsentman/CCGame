package com.qwertyzzz18.ccgame.editor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SpriteManager {
    static ArrayList<SpriteStorage> sprites;
    static Map<Integer,TextureRegion> textures;
    static void init()
    {
        textures=new HashMap<Integer,TextureRegion>();
            try
            {
                String json = new String(Files.readAllBytes(Paths.get(("sprites.json"))));
                Gson gson = new Gson();
                sprites = gson.fromJson(json, new TypeToken<ArrayList<SpriteStorage>>() {}.getType());
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            for (SpriteStorage s:sprites)
            {
                textures.put(s.id,new TextureRegion(new Texture(s.name)));
            }
    }
    static TextureRegion getSprite(int id)
    {
        if (textures.get(id)==null)
        {
            SpriteStorage s=sprites.get(id);
            textures.put(s.id,new TextureRegion(new Texture(s.name)));
        }
        return textures.get(id);
    }
    static int getSpriteParamSet(int id)
    {
        return sprites.get(id).paramset;
    }
    static ArrayList<Integer> getSpritesType(int type)
    {
        ArrayList<Integer> al = new ArrayList<Integer>();
        for (SpriteStorage s : sprites)
        {
            if (s.type==type)
            {
                al.add(s.id);
            }
        }
       return al;
    }
}
