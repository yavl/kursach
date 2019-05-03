package com.kursach;

import java.util.ArrayList;

public class If extends Block {
    public If(float x, float y) {
        super(x, y);
        internalBlocks = new ArrayList<Block>();
    }
    public If() {
        super();
    }

    public void act() {

    }
}
