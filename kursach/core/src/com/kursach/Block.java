package com.kursach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class Block extends Actor {
    public ArrayList<Block> internalBlocks;
    public ShapeRenderer shape;
    public static float defaultWidth = 200;
    public static float defaultHeight = 250;
    public static float minWidth = 70;
    public static float minHeight = 50;
    OrthographicCamera cam;
    public boolean overBorder = false;


    public Block(float x, float y, OrthographicCamera cam) {
        super();
        this.cam = cam;
        setPosition(x, y);
        setSize(defaultWidth, defaultHeight);
        shape = new ShapeRenderer();
        addListener( new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (overBorder) {
                    borderClickEvent();
                    return false;
                }
                if (StageInput.currentCommand == 2) {
                    clickEvent(x, y);
                    return false;
                }
                return false;
            }
        } );
    }

    public void borderClickEvent() {
        StageInput.tempBlock = this;
    }

    public void clickEvent(float x, float y) {
        StageInput.currentCommand = 3;
        StageInput.tempBlock = this;
        StageInput.distance.set(x, y);
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

    public void hoveringOverBorder(float mousePositionX, float mousePositionY) {
        float x1 = getX() - 2;
        float y1 = getY() - 2;
        float x2 = getX() + getWidth() + 2;
        float y2 = getY() + getHeight() + 2;

        //Проверяется лежит ли мышь:
        if (x1 <= mousePositionX && mousePositionX <= x1 + 4) {
            if (y1 <= mousePositionY && y1 + 4 >= mousePositionY) {
                StageInput.blockResizeDirection = 7;
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
                overBorder = true;
                return;
            }
            else if (y2 - 4 <= mousePositionY && y2 >= mousePositionY) {
                overBorder = true;
                StageInput.blockResizeDirection = 8;
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
                return;
            }
        }  //В нижних точках
        if (x2 - 4 <= mousePositionX && mousePositionX <= x2) {
            if (y1 <= mousePositionY && y1 + 4 >= mousePositionY) {
                StageInput.blockResizeDirection = 6;
                overBorder = true;
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
                return;
            }
            else if (y2 - 4 <= mousePositionY && y2 >= mousePositionY) {
                StageInput.blockResizeDirection = 5;
                overBorder = true;
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
                return;
            }
        }  //В верхних точках

        if (x1 <= mousePositionX && mousePositionX <= x2) {
            if (y1 <= mousePositionY && mousePositionY <= y1 + 4) {
                overBorder = true;
                StageInput.blockResizeDirection = 3;
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
                return;
            }
            else if (y2 - 4 <= mousePositionY && mousePositionY <= y2) {
                overBorder = true;
                StageInput.blockResizeDirection = 1;
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
                return;
            }
        }  //В горизонтальной прямой
        if (y1 <= mousePositionY && mousePositionY <= y2) {
            if (x1 <= mousePositionX && mousePositionX <= x1 + 4) {
                overBorder = true;
                StageInput.blockResizeDirection = 4;
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
                return;
            }
            else if (x2 - 4 <= mousePositionX && mousePositionX <= x2) {
                overBorder = true;
                StageInput.blockResizeDirection = 2;
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
                return;
            }
        }  //В вертикальной прямой
        overBorder = false;
        StageInput.blockResizeDirection = 0;
        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        return;
    }
}
