package com.kursach.window;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class RenameWindow extends TextField {
    MyWindow window;
    public RenameWindow(Skin skin) {
        super("", skin);
        setSize(100, 25);
        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    getStage().unfocusAll();
                    clicked();
                }
                return false;
            }
        });
    }

    public void update(MyWindow window) {
        setVisible(true);
        this.window = window;
        setPosition(window.getX(), window.getY() + window.getHeight() + 25);
    }

    public void clicked() {
        setVisible(false);
        if (window.isMain)  window.changeName(getText());
        else if (window.getName().startsWith("if")) { // todo: сделать нормальнее проверку на if
            window.changeName("if " + getText());
        }
    }

}
