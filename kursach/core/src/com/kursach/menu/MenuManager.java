package com.kursach.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kursach.StageInput;
import com.kursach.window.RenameWindow;

public class MenuManager {
    private Stage stage;
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private StageInput stageInput;
    private TextButton button;
    private Label fpsLabel;
    private Skin skin;
    private Menubar menubar;
    public static BlockStore blockStore;
    public static RenameWindow renameWindow;
    public static Label outputLabel;

    public MenuManager(StageInput stageInput, Skin skin) {
        this.stageInput = stageInput;
        this.skin = skin;

        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();
        cam = new OrthographicCamera(windowWidth, windowHeight);
        cam.position.set(0, 0, 0);
        cam.zoom = 1f;
        cam.update();

        ScreenViewport viewp = new ScreenViewport(cam);
        batch = new SpriteBatch();
        stage = new Stage(viewp, batch);

        fpsLabel = new Label("asd", skin);
        fpsLabel.setColor(0, 0, 0, 1.0f);
        fpsLabel.setX(windowWidth - fpsLabel.getWidth());
        fpsLabel.setY(windowHeight - fpsLabel.getHeight());

        menubar = new Menubar(skin, stageInput, this);
        menubar.setPosition(0, windowHeight - menubar.getHeight());
        stage.addActor(menubar);
        stage.addActor(fpsLabel);

        blockStore = new BlockStore(skin);
        blockStore.setPosition((windowWidth - blockStore.getWidth()) / 2, (windowHeight - blockStore.getHeight()) / 2);
        blockStore.setVisible(false);
        stage.addActor(blockStore);

        renameWindow = new RenameWindow(skin);
        renameWindow.setVisible(false);
        stage.addActor(renameWindow);

        outputLabel = new Label("", skin);
        outputLabel.setPosition(Gdx.graphics.getWidth()/2, outputLabel.getHeight());
        outputLabel.setWidth(Gdx.graphics.getWidth()/2);
        outputLabel.getStyle().fontColor = Color.TEAL;
        stage.addActor(outputLabel);
    }

    public void createButton(String text, final int command) {
        Skin skin = new Skin(Gdx.files.internal("DefaultSkin/uiskin.json"));
        button = new TextButton(text, skin);
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
    }

    public void onResize(int width, int height) {
        stage.getViewport().update(width, height, true);
        fpsLabel.setX(width - fpsLabel.getWidth());
        fpsLabel.setY(height - fpsLabel.getHeight());
        menubar.onResize(width, height);
    }

    public Stage getStage() {
        return stage;
    }

    public Label getFpsLabel() {
        return fpsLabel;
    }
}
