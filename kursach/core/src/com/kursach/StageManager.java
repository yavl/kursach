package com.kursach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kursach.menu.MenuManager;

/* Класс который манипулирует стейджем и вводимыми пользователем командами
 */

public class StageManager {
    public static final int screenWidth = Gdx.graphics.getWidth();
    public static final int screenHeight = Gdx.graphics.getHeight();
    Stage stage;
    public static OrthographicCamera cam;
    InputMultiplexer input;
    StageInput stageInput;
    InputMultiplexer inputs;
    SpriteBatch batch;
    Vector3 mousePos;
    public MenuManager UI;
    public BitmapFont font;
    public float fps;
    public float sinceChange;
    public long lastTimeCounted;
    public Skin skin;

    public StageManager() {
        cam = new OrthographicCamera(screenWidth, screenHeight);
        cam.position.set(0, 0, 0);
        cam.zoom = 1f;
        cam.update();

        ScreenViewport viewp = new ScreenViewport(cam);
        batch = new SpriteBatch();
        stage = new Stage(viewp, batch);

        stageInput = new StageInput(stage, this);
        inputs = new InputMultiplexer();
        inputs.addProcessor(stage);
        inputs.addProcessor(stageInput);

        skin = new Skin(Gdx.files.internal("DefaultSkin/uiskin.json"));

        UI = new MenuManager(stageInput, skin);
        inputs.addProcessor(UI.getStage());
        inputs.addProcessor(UI.getInput());

        Gdx.input.setInputProcessor(inputs);
        mousePos = new Vector3();

        font = new BitmapFont();
        createButton("If", 1);
    }

    public void createButton(String text, final int command) {
        TextButton.TextButtonStyle textButtonStyle;
        BitmapFont font = new BitmapFont();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/button.atlas"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button");
        textButtonStyle.down = skin.getDrawable("button");
        textButtonStyle.checked = skin.getDrawable("button");
        UI.createButton(text, command);
    }

    public void buttonDebug() {

    }

    public void checkHovering() {
        mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(mousePos);

        if (stageInput.currentCommand == 2) {
            for (int i = 0; i < MainScreen.mainBlocks.size(); i++) {
                MainScreen.mainBlocks.get(i).hoveringOverBorder(mousePos.x, mousePos.y);
            }
        }
    }

    public void stageAct() {
        //checkHovering();
        stage.act();
        UI.getStage().act();
    }

    public void update(float dt) {
        cam.update();
        stage.getBatch().setProjectionMatrix(cam.combined);
        stageInput.handleInput(dt);
        updateFPS();
    }

    public void draw() {
        stage.draw();
        UI.getStage().draw();
        batch.begin();
        UI.getFpsLabel().setText((int)fps);
        batch.end();
    }

    public void updateFPS() {
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();

        sinceChange += delta;
        if(sinceChange >= 1000) {
            sinceChange = 0;
            fps = Gdx.graphics.getFramesPerSecond();
        }
    }
}
