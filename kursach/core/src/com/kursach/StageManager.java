package com.kursach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/* Класс который манипулирует стейджем и вводимыми пользователем командами
 */

public class StageManager {
    public static final int screenWidth = Gdx.graphics.getWidth();  //Разрешение
    public static final int screenHeight = Gdx.graphics.getHeight();  //Экрана
    Stage stage;
    OrthographicCamera cam;
    Vector2 camSpeed;
    InputMultiplexer input;
    StageInput stageInput;
    InputMultiplexer inputs;
    SpriteBatch batch;

    public StageManager() {
        cam = new OrthographicCamera(screenWidth, screenHeight);
        cam.setToOrtho(true, 0, 0);
        cam.position.set(0, 0, 0);
        cam.update();
        camSpeed = new Vector2();

        FitViewport viewp = new FitViewport(1, 1, cam);
        batch = new SpriteBatch();
        stage = new Stage(viewp, batch);

        createButton();
        stageInput = new StageInput(stage, this);
        inputs = new InputMultiplexer();
        inputs.addProcessor(stageInput);
        inputs.addProcessor(stage);
        Gdx.input.setInputProcessor(inputs);
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
        stage.addActor(button);
        button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("If");
                stageInput.currentCommand = 1;
            }
        } );
    }

    public void buttonDebug() {

    }

    public void checkHovering() {
        if (stageInput.currentCommand == 2) {
            for (Block block: MainScreen.mainBlocks) {
                if (block.hoveringOverBorder(stageInput.mousePos.x, stageInput.mousePos.y)) {
                }
            }
        }
    }

    public void stageAct() {
        stage.act();
        checkHovering();
    }

    public void updateCam() {
        cam.update();
        stage.getBatch().setProjectionMatrix(cam.combined);
    }
}
