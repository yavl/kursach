package com.kursach.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import java.util.List;

public class Menubar extends Table {
    private Array<Table> tables;
    private Skin skin;

    public Menubar(Skin skin) {
        this.skin = skin;
        tables = new Array<Table>();
        left().top();

        Array<String> fileButtons = new Array<String>();
        fileButtons.add("New");
        fileButtons.add("Open");

        addTable("File");
        addTable("Edit");
        row();
        left();

        addDropDownTable("New");
        addDropDownTable("Cancel");
        row();
        setDebug(true);
    }

    public void addTable(String text) {
        Table newTable = new Table();
        //newTable.setSize();
        newTable.setDebug(true);
        tables.add(newTable);
        add(newTable).left().top();
        TextButton button = new TextButton(text, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tables.get(2).setVisible(true);
            }
        });
        newTable.add(button).left();
    }

    public void addDropDownTable(String text) {
        Table newTable = new Table();
        newTable.setDebug(true);
        tables.add(newTable);
        add(newTable).fillY();
        newTable.top().left();
        TextButton button = new TextButton(text, skin);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
        newTable.add(button);
        newTable.row();
        newTable.add(new TextButton(text+"1", skin));
        newTable.setVisible(false);
    }

    public void onResize(int width, int height) {
        setPosition(0, height - getHeight());
    }
}
