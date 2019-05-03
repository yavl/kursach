package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/* Класс который манипулирует стейджем и вводимыми пользователем командами
 */

public class MainStage extends Stage {
    Cursor cursor = new Cursor(Cursor.DEFAULT_CURSOR);
    public static final int screenWidth = Gdx.graphics.getWidth();  //Разрешение
    public static final int screenHeight = Gdx.graphics.getHeight();  //Экрана
    public OrthographicCamera camera;
    public Map<String, Integer> commandMap;  //Список доступных команд, название и код
    int currentCommand;  //Переменная которая определяет текущую команду
    public Vector2 mousePos;
    public Vector2 touchedPos;  //Вектор хранящий координаты последнего клика мышкой
    public Vector2 releasedPos; //Вектор хранящий координаты последнего отпускания кнопки мыши

    public MainStage() {
        touchedPos = new Vector2(0, 0);
        releasedPos = new Vector2(0, 0);
        mousePos = new Vector2();
        commandMap = new HashMap<String, Integer>();
        commandMap.put("Nothing", 0);
        commandMap.put("Create if block", 1);
        commandMap.put("Adjust", 2);
        currentCommand = 0;
        createButton();
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
            touchedPos.x = screenX;
            touchedPos.y = screenHeight - screenY;
            Block newBlock = new If(touchedPos.x - Block.defaultWidth / 2, touchedPos.y - Block.defaultHeight / 2);
            addActor(newBlock);
            MainScreen.mainBlocks.add(newBlock);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (currentCommand == 1 && button == com.badlogic.gdx.Input.Buttons.LEFT) {
        }
        return true;
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

    /*public void createNewBlock() {
        float width = Math.abs(touchedPos.x - releasedPos.x);
        float height = Math.abs(touchedPos.y - releasedPos.y);
        if (currentCommand == 1) {
            If block = new If(Math.min(touchedPos.x, releasedPos.x), Math.min(touchedPos.y, releasedPos.y), width, height);
            MainScreen.mainBlocks.add(block);
            addActor(block);

            System.out.println("New if have been created");
        }
    }*/

    public void createButton() {
        TextButton button;
        TextButton.TextButtonStyle textButtonStyle;
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/button.atlas"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button");
        textButtonStyle.down = skin.getDrawable("button");
        textButtonStyle.checked = skin.getDrawable("button");
        button = new TextButton("If", textButtonStyle);
        button.setPosition(0, 0);
        button.setSize(50, 50);
        button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("If");
                currentCommand = 1;
            }
        } );
        addActor(button);
    }

    public void checkHovering() {
        if (currentCommand == 2) {
            for (Block block: MainScreen.mainBlocks) {
                if (block.hoveringOverBorder(mousePos.x, mousePos.y)) {
                }
            }
        }
    }

    @Override
    public void act() {
        super.act();
        checkHovering();
    }
}
