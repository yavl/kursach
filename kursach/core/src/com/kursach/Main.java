package com.kursach;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Gdx;

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
