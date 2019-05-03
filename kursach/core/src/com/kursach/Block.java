package com.kursach;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

public abstract class Block extends Actor{
    public ArrayList<Block> internalBlocks;
    public ShapeRenderer shape;
    public static float defaultWidth = 200;
    public static float defaultHeight = 250;

    public Block(float x, float y) {
        super();
        setPosition(x, y);
        setSize(defaultWidth, defaultHeight);
        shape = new ShapeRenderer();
        shape.setColor(Color.GREEN);
    }
    public Block() {
        super();
    }


    public void run() {
        for (Block block : internalBlocks) {
            block.run();
        }
    }

    public void addBlock(Block block) {
        internalBlocks.add(block);
    }

    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        drawShape();
    }

    public void drawShape() {
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.rect(getX(), getY(), getWidth(), getHeight());
        shape.end();
    }

    public boolean hoveringOverBorder(float mousePositionX, float mousePositionY) {
        float x1 = getX() - 2;
        float y1 = getY() - 2;
        float x2 = getX() + getWidth() + 2;
        float y2 = getY() + getHeight() + 2;
        if (x1 <= mousePositionX && mousePositionX <= x2) {
            if (y1 <= mousePositionY && mousePositionY <= y1 + 4) {
                return true;
            }
            else if (y2 - 4 <= mousePositionY && mousePositionY <= y2) {
                return true;
            }
        }
        if (y1 <= mousePositionY && mousePositionY <= y2) {
            System.out.println(y1 + " " + y2 + " " + mousePositionY);
            if (x1 <= mousePositionX && mousePositionX <= x1 + 4) {
                return true;
            }
            else if (x2 - 4 <= mousePositionX && mousePositionX <= x2) {
                return true;
            }
        }
        return false;
    }
}
