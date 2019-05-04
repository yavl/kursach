package com.kursach;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class Block extends Actor {
    public ArrayList<Block> internalBlocks;
    public ShapeRenderer shape;
    public static float defaultWidth = 200;
    public static float defaultHeight = 250;
    OrthographicCamera cam;


    public Block(float x, float y, OrthographicCamera cam) {
        super();
        this.cam = cam;
        setPosition(x, y);
        setSize(defaultWidth, defaultHeight);
        shape = new ShapeRenderer();
        addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (StageInput.currentCommand == 2) {
                    System.out.println("working");
                    clickEvent(x, y);
                }
                return false;
            }
        } );
    }

    public void clickEvent(float x, float y) {
        System.out.println("Moving");
        StageInput.currentCommand = 3;
        StageInput.tempBlock = this;
        StageInput.distance.set(x, y, 0);
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
        shape.setProjectionMatrix(cam.combined);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.setColor(Color.GREEN);
        shape.rect(getX(), getY(), getWidth(), getHeight());
        shape.end();
        shape.setColor(Color.LIGHT_GRAY);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(getX() + 1, getY() + 1, getWidth() - 2, getHeight() - 2);
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
