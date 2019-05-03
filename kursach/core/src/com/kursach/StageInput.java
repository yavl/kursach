package com.kursach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.HashMap;
import java.util.Map;

public class StageInput implements InputProcessor {
    public static final int screenWidth = Gdx.graphics.getWidth();  //Разрешение
    public static final int screenHeight = Gdx.graphics.getHeight();  //Экрана
    public Map<String, Integer> commandMap;  //Список доступных команд, название и код
    int currentCommand;  //Переменная которая определяет текущую команду
    public Vector2 mousePos;
    public Vector2 touchedPos;  //Вектор хранящий координаты последнего клика мышкой
    public Vector2 releasedPos;
    Stage stage;

    public StageInput(Stage stage) {
        this.stage = stage;
        touchedPos = new Vector2(0, 0);
        releasedPos = new Vector2(0, 0);
        mousePos = new Vector2();
        commandMap = new HashMap<String, Integer>();
        commandMap.put("Nothing", 0);
        commandMap.put("Create if block", 1);
        commandMap.put("Adjust", 2);
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
            touchedPos.x = screenX;
            touchedPos.y = screenHeight - screenY;
            Block newBlock = new If(touchedPos.x - Block.defaultWidth / 2, touchedPos.y - Block.defaultHeight / 2);
            stage.addActor(newBlock);
            MainScreen.mainBlocks.add(newBlock);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (currentCommand == 1 && button == com.badlogic.gdx.Input.Buttons.LEFT) {
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (currentCommand == 1) {
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePos.x = screenX;
        mousePos.y = screenHeight - screenY;
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
