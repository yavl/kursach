package com.kursach;

import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.ArrayList;

public class If extends Block {
    public If(float x, float y, OrthographicCamera cam) {
        super(x, y, cam);
        internalBlocks = new ArrayList<Block>();
    }
    public If() {
        super();
    }

    public void act() {

    }
}
