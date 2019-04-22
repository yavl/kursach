package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

public class If extends Block {
    public void Block(float x, float y) {
        entity = new Actor();
        entity.setX(x);
        entity.setY(y);
        internalBlocks = new ArrayList<Block>();
    }

    public void act() {

    }

    @Override
    public void draw(Batch batch, float alpha) {
        asd.draw(batch, alpha);
    }
}
