package com.kursach;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {
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

	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}
}
