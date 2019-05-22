package com.kursach.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kursach.StageInput;

public class Menubar extends Table {
    private Skin skin;
    private StageInput stageInput;

    public Menubar(Skin skin, final StageInput stageInput) {
        this.skin = skin;
        this.stageInput = stageInput;
        left().top();
        setDebug(true);

        final MenubarSelectBox fileMenu = new MenubarSelectBox(skin, "File");
        fileMenu.setItems("New", "Open", "Save", "Exit");
        fileMenu.getList().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String selected = (String) fileMenu.getSelected();

                switch (selected) {
                    case "Exit": {
                        Gdx.app.exit();
                    } break;
                }
            }
        });
        TextButton fake = new TextButton("fake hidden button", skin); // чтобы взять стиль из кнопки
        fileMenu.getStyle().background = fake.getBackground(); // заменяет фоны всех MenubarSelectBox (?)
        add(fileMenu);

        MenubarSelectBox editBox = new MenubarSelectBox(skin, "Edit");
        editBox.setItems("Cut", "Copy");
        add(editBox);

        final MenubarSelectBox addBox = new MenubarSelectBox(skin, "Add");
        addBox.setItems("Block", "asd");
        addBox.getList().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String selected = (String) addBox.getSelected();

                switch (selected) {
                    case "Block": {
                        StageInput.currentCommand = 1;
                    } break;
                }
            }
        });
        add(addBox);
    }

    public void onResize(int width, int height) {
        setPosition(0, height - getHeight());
    }
}
