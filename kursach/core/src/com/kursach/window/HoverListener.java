package com.kursach.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HoverListener extends ClickListener {
    private MyWindow window;

    public HoverListener(MyWindow window) {
        this.window = window;
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        if (!window.isDragging()) {
            Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
        }
    }
}
