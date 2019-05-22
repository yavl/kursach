package com.kursach.window;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class VariableField extends Table {
    TextField textField;
    public VariableField(Skin skin) {
        super(skin);
        textField = new TextField("", skin);
        textField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    getStage().unfocusAll();
                }
                return false;
            }
        });
        add(textField).expand().fill();
        final Button closeButton = new TextButton("X", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removeSelf();
            }
        });
        add(closeButton).size(10, 10).padRight(0).padTop(0);
    }

    public void removeSelf() {
        getParent().removeActor(this);
    }

    public String getText() {
        return textField.getText();
    }
}
