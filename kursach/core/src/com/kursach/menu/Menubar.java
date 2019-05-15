package com.kursach.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Menubar extends Table {
    private Skin skin;

    public Menubar(Skin skin) {
        this.skin = skin;
        left().top();
        setDebug(true);

        final MenubarSelectBox fileMenu = new MenubarSelectBox(skin, "File");
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
        fileMenu.getStyle().background = fake.getBackground(); // заменяет фоны всех MenubarSelectBox (?)
        add(fileMenu);

        MenubarSelectBox editBox = new MenubarSelectBox(skin, "Edit");
        editBox.setItems("Cut", "Copy");
        add(editBox);
    }

    public void onResize(int width, int height) {
        setPosition(0, height - getHeight());
    }
}
