package com.kursach.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MyListener extends ClickListener {
    MyWindow window;

    public MyListener(MyWindow window) {
        super();
        this.window = window;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        if (!window.check())
            window.selectClick();
        return false;
    }
}
