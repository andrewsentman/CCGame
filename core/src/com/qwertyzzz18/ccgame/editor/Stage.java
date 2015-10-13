package com.qwertyzzz18.ccgame.editor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Stage {

    private int               id;
    private int               x;
    private int               y;
    private String            name;
    private boolean           dirty;
    private int               width;
    private int               height;
    private Map<String, Tile> tiles;

    public Stage(int id, int x, int y, String name, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.name = name;
        this.width = width;
        this.height = height;
        this.tiles = new HashMap<String, Tile>();
        this.dirty = true;
    }

    public void resize() {
        Object[] ks = tiles.keySet().toArray();
        for (Object o : ks)
        {
            String s = (String) o;
            if (Integer.parseInt(s.substring(0, s.indexOf(' '))) >= this.width)
                tiles.remove(s);
            if (Integer.parseInt(s.substring(s.indexOf(' ') + 1, s.length())) >= this.width)
                tiles.remove(s);
        }
    }

    public Tile getTile(int x, int y) {
        return tiles.get(x + " " + y);
    }

    public void setTile(int x, int y, Tile t) {
        tiles.put(x + " " + y, t);
        this.dirty = true;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Map<String, Tile> getTiles() {
        return tiles;
    }

    public void setWidth(int width) {
        this.width = width;
        this.dirty = true;
    }

    public void setId(int id) {
        this.id = id;
        this.dirty = true;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public void setTiles(Map<String, Tile> tiles) {
        this.tiles = tiles;
        this.dirty = true;
    }

    public void setHeight(int height) {
        this.height = height;
        this.dirty = true;
    }

    public void setX(int x) {
        this.x = x;
        this.dirty = true;
    }

    public void setY(int y) {
        this.y = y;
        this.dirty = true;
    }

    public void setName(String name) {
        this.name = name;
        this.dirty = true;
    }

    public boolean isDirty() {
        if (this.dirty)
            return true;
        for (Tile tile : this.tiles.values())
            if (tile != null)
                if (tile.isDirty())
                    return true;
        return false;
    }

    public void clean() {
        this.dirty = false;
        for (Tile tile : this.tiles.values())
            if (tile != null)
                tile.clean();
    }
}
