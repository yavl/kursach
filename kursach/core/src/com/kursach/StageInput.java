package com.kursach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashMap;
import java.util.Map;

public class StageInput implements InputProcessor {
    public static final int screenWidth = Gdx.graphics.getWidth();
    public static final int screenHeight = Gdx.graphics.getHeight();
    public Map<String, Integer> commandMap;
    public static int currentCommand;
    public Vector3 mousePos;
    public static Vector3 touchedPos;
    public Vector3 releasedPos;
    public static Vector2 distance;
    Stage stage;
    public StageManager stageManager;
    public static Block tempBlock;

    public StageInput(Stage stage, StageManager stageManager) {
        this.stage = stage;
        this.stageManager = stageManager;
        touchedPos = new Vector3();
        releasedPos = new Vector3();
        distance = new Vector2();
        mousePos = new Vector3();
        commandMap = new HashMap<String, Integer>();
        commandMap.put("Nothing", 0);
        commandMap.put("Create if block", 1);
        commandMap.put("Adjust", 2);
        commandMap.put("Dragging", 3);
        currentCommand = 0;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.NUM_1) {
            System.out.println("Switched to if");
            currentCommand = 1;
        }
        else if (keycode == Input.Keys.NUM_0) {
            System.out.println("Do nothing");
            currentCommand = 0;
        }
        else if (keycode == Input.Keys.NUM_2) {
            System.out.println("Adjusting");
            currentCommand = 2;
        }
        else if (keycode == Input.Keys.LEFT) {
            stageManager.stage.getCamera().position.x = -100;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (currentCommand == 1 && button == com.badlogic.gdx.Input.Buttons.LEFT) {
            touchedPos = getNewPosition(screenX, screenY);
            Block newBlock = new If(touchedPos.x - Block.defaultWidth / 2, touchedPos.y - Block.defaultHeight / 2, stageManager.cam);
            stage.addActor(newBlock);
            MainScreen.mainBlocks.add(newBlock);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (currentCommand == 3 && button == com.badlogic.gdx.Input.Buttons.LEFT) {
            currentCommand = 2;
            tempBlock = null;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (currentCommand == 3) {
            mousePos = getNewPosition(screenX, screenY);
            tempBlock.setPosition(mousePos.x, mousePos.y);
        }
        return false;
    }

    public Vector3 getNewPosition(int x, int y) {
        touchedPos.x = x;
        touchedPos.y = screenHeight - y;
        Vector3 input = new Vector3(touchedPos.x, touchedPos.y, 0);
        stageManager.cam.unproject(input);
        input.y = - input.y;
        return input;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        OrthographicCamera cam = stageManager.cam;
        switch (amount) {
            case 1:
                cam.zoom += 0.15f * cam.zoom;
                break;
            case -1:
                cam.zoom -= 0.15f * cam.zoom;
                break;
        }
        System.out.println(cam.zoom);
        return false;
    }
}
