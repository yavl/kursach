package com.kursach;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class MainScreen implements Screen {
    public static final int screenWidth = Gdx.graphics.getWidth();
    public static final int screenHeight = Gdx.graphics.getHeight();
    Texture img;
    StageManager stageManager;

    public MainScreen() {
        stageManager = new StageManager();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stageManager.update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        stageManager.stageAct();
        stageManager.draw();
    }

    @Override
    public void resize(int width, int height) {
        stageManager.stage.getViewport().update(width, height);
        stageManager.UI.onResize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
