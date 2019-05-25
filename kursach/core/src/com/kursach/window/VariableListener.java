package com.kursach.window;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class VariableListener extends ClickListener {
    VariableField variableField;

    public VariableListener(VariableField field) {
        super();
        variableField = field;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        variableField.selectClick();
        return false;
    }
}
