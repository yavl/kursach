package com.kursach.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.List;

public class MenubarVlad extends Table {
    private Skin skin;

    public MenubarVlad(Skin skin) {
        this.skin = skin;
        left().top();
        setDebug(true);

        final VladSelectBox fileMenu = new VladSelectBox(skin, "File");
        fileMenu.setItems("New", "Open", "Save", "Exit");
        fileMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (fileMenu.getSelected().equals("Exit")) {
                    Gdx.app.exit();
                }
            }
        });
        TextButton fake = new TextButton("fake hidden button", skin); // чтобы взять стиль из кнопки
        fileMenu.setWidth(999);
        fileMenu.getStyle().background = fake.getBackground(); // заменяет фоны всех VladSelectBox (?)
        add(fileMenu);

        VladSelectBox editBox = new VladSelectBox(skin, "Edit");
        editBox.setItems("Cut", "Copy");
        add(editBox);
    }

    public void onResize(int width, int height) {
        setPosition(0, height - getHeight());
    }
}
