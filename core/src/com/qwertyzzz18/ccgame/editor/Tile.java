package com.qwertyzzz18.ccgame.editor;

import java.util.HashMap;

public class Tile {
    private int tid;
    private HashMap<String,String> params;
    private boolean dirty;

    public Tile(int tid)
    {
        this.tid=tid;
        this.params=new HashMap<String,String>();
        this.dirty=true;
    }
    
    public void setParam(String param, String value) {
        this.params.put(param,value);
        this.dirty=true;
    }
    
    public String getParam(String param){
        return this.params.get(param);
    }

    
    public int getTid() {
        return tid;
    }

    
    public HashMap<String, String> getParams() {
        return params;
    }

    
    public void setTid(int tid) {
        this.tid = tid;
        this.dirty=true;
    }

    
    public void setParams(HashMap<String, String> params) {
        this.params = params;
        this.dirty=true;
    }

    public boolean isDirty() {
        return this.dirty;
    }

    public void clean() {
        this.dirty=false;
        
    }
}
