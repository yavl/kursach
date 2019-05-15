package com.kursach.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kursach.StageInput;

public class MenuManager {
    private Stage stage;
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private MenuInput input;
    private StageInput stageInput;
    private TextButton button;
    private Label fpsLabel;
    private Skin skin;
    private MenubarVlad menubar;

    public MenuManager(StageInput stageInput, Skin skin) {
        this.stageInput = stageInput;

        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(windowWidth, windowHeight);
        cam.position.set(0, 0, 0);
        cam.zoom = 1f;
        cam.update();

        ScreenViewport viewp = new ScreenViewport(cam);
        batch = new SpriteBatch();
        stage = new Stage(viewp, batch);

        input = new MenuInput();
        fpsLabel = new Label("asd", skin);
        fpsLabel.setColor(0, 0, 0, 1.0f);
        fpsLabel.setX(windowWidth - fpsLabel.getWidth());
        fpsLabel.setY(windowHeight - fpsLabel.getHeight());

        menubar = new MenubarVlad(skin);
        menubar.setPosition(0, windowHeight - menubar.getHeight());
        stage.addActor(menubar);
        stage.addActor(fpsLabel);
    }

    public void createButton(String text, final int command) {
        TextButton.TextButtonStyle textButtonStyle;
        BitmapFont font = new BitmapFont();
        Skin skin = new Skin(Gdx.files.internal("DefaultSkin/uiskin.json"));
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/button.atlas"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button");
        textButtonStyle.down = skin.getDrawable("button");
        textButtonStyle.checked = skin.getDrawable("button");
        button = new TextButton(text, textButtonStyle);
        button.setPosition(0, 0);
        button.setSize(128, 64);
        stage.addActor(button);
        button.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("If");
                stageInput.currentCommand = command;
            }
        } );
        System.out.println(button.getWidth() + ", " + button.getHeight());
    }

    public void onResize(int width, int height) {
        stage.getViewport().update(width, height, true);
        fpsLabel.setX(width - fpsLabel.getWidth());
        fpsLabel.setY(height - fpsLabel.getHeight());
        menubar.onResize(width, height);
    }

    public MenuInput getInput() {
        return input;
    }

    public Stage getStage() {
        return stage;
    }

    public Label getFpsLabel() {
        return fpsLabel;
    }
}
