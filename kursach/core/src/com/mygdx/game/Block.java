package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class Block {
    public static Array<Block> mainBlocks;
    public ArrayList<Block> internalBlocks;
    public Actor entity;
    public ShapeRenderer shape;

    public void run() {
        for (Block block : internalBlocks) {
            block.run();
        }
    }

    public void addBlock(Block block) {
        internalBlocks.add(block);
    }
    public void draw(Batch batch, float alpha) {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.rect();
        shape.end();
    }
}
