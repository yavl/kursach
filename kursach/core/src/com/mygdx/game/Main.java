package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {
	MainScreen screen;
	
	@Override
	public void create () {
        screen = new MainScreen();
	}

	@Override
	public void render () {
	    float dt = Gdx.graphics.getDeltaTime();
		screen.render(dt);
	}
	
	@Override
	public void dispose () {
		screen.dispose();
	}
}
