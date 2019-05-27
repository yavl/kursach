package com.kursach.window;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kursach.StageInput;

public class VariableField extends Table {
    TextField textField;
    public static StageInput stageInput;
    boolean selected;
    VariableListener listener;

    public VariableField(Skin skin) {
        super(skin);
        listener = new VariableListener(this);
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
        addListener(listener);
    }

    public void removeSelf() {
        getParent().removeActor(this);
    }

    public String getText() {
        return textField.getText();
    }

    public void unselect() {
        System.out.println("unselected");
        stageInput.unselect();
        selected = false;
        textField.setColor(Color.WHITE);
    }

    public void selected() {
        System.out.println("selected");
        stageInput.selectNew(this);
        selected = true;
        textField.setColor(Color.GREEN);
    }

    public void selectClick() {
        if (!selected) selected();
        else unselect();
    }

    public void moveDown() {

    }

    public void moveUp() {

    }
}
