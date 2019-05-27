package com.kursach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kursach.window.Block;
import com.kursach.window.MyWindow;
import com.kursach.window.VariableField;

import java.util.HashMap;
import java.util.Map;

public class StageInput implements InputProcessor {
    public static final int screenWidth = Gdx.graphics.getWidth();
    public static final int screenHeight = Gdx.graphics.getHeight();
	public static float camSpeed = 500f;
    public Vector3 mousePos;
    public Vector3 touchedPos;  //Вектор хранящий координаты последнего клика мышкой
    public Vector3 releasedPos;
    public static Vector2 distance;
    Stage stage;
    public StageManager stageManager;
    public Map<String, Integer> commandMap;
    public static int currentCommand;
    private Vector2 dragOld = new Vector2(); // для перемещения камеры кнопкой мыши
    private Vector2 dragNew = new Vector2();
    public static Actor selected;

    public StageInput(Stage stage, StageManager stageManager) {
        MyWindow.stageInput = this;
        VariableField.stageInput = this;
        this.stage = stage;
        this.stageManager = stageManager;
        touchedPos = new Vector3();
        releasedPos = new Vector3();
        mousePos = new Vector3();
        commandMap = new HashMap<String, Integer>();
        distance = new Vector2();
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
            touchedPos = getNewPosition();

            Skin skin = new Skin(Gdx.files.internal("DefaultSkin/uiskin.json"));

            Block block = new Block(skin, touchedPos);
            stage.addActor(block);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public Vector3 getNewPosition() {
        Vector3 input = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        stageManager.cam.unproject(input);
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
        return false;
    }

    public void handleInput(float dt) {
        OrthographicCamera cam = stageManager.cam;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            cam.translate(-camSpeed * cam.zoom * dt, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            cam.translate(camSpeed * cam.zoom * dt, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.translate(0, -camSpeed * cam.zoom * dt, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.translate(0, camSpeed * cam.zoom * dt, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        if (Gdx.input.justTouched()) {
            dragNew.set(Gdx.input.getX(), Gdx.input.getY());
            dragOld.set(dragNew);
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            dragNew.set(Gdx.input.getX(), Gdx.input.getY());
            if (!dragNew.equals(dragOld)) {
                cam.translate((dragOld.x - dragNew.x) * cam.zoom, (dragNew.y - dragOld.y) * cam.zoom);
                dragOld.set(dragNew);
            }
        }
    }

    public void selectNew(MyWindow window) {
        if (selected != null) {
            if (selected.getClass().getSimpleName().equals("MyWindow"))
                ((MyWindow) selected).unselect();
            else if (selected.getClass().getSimpleName().equals("VariableField"))
                ((VariableField) selected).unselect();
        }
        selected = window;
    }

    public void selectNew(VariableField field) {
        if (selected != null) {
            if (selected.getClass().getSimpleName().equals("MyWindow"))
                ((MyWindow) selected).unselect();
            else if (selected.getClass().getSimpleName().equals("VariableField"))
                ((VariableField) selected).unselect();
        }
        selected = field;
    }

    public void unselect() {
        selected = null;
    }
}
