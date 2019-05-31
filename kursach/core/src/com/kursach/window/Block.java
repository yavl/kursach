package com.kursach.window;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kursach.StageInput;
import com.kursach.lua.LuaConverter;
import com.kursach.menu.MenuManager;

import java.io.*;

public class Block extends Group {
    private MyWindow window;
    private TextField argumentField;
    private Table toolbar;
    private Writer writer;

    public Block(Skin skin, Vector3 touchedPos) {
        window = new MyWindow("Новая функция", skin,this);
        window.setPosition(touchedPos.x, touchedPos.y);

        argumentField = new TextField("", skin);
        argumentField.setPosition(touchedPos.x, touchedPos.y + window.getHeight());
        argumentField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    getStage().unfocusAll();
                }
                return false;
            }
        });
        argumentField.setSize(window.getWidth(), 30);

        toolbar = new Table(skin);
        toolbar.setPosition(touchedPos.x + window.getWidth(), touchedPos.y + window.getHeight() - 150);
        toolbar.setSize(55, 150);

        final Button runButton = new TextButton("Запуск", skin);
        runButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                run();
            }
        });
        toolbar.add(runButton).expandX().fillX();
        toolbar.row();

        final Button ifButton = new TextButton("Если", skin);
        ifButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onIfButtonClick();
            }
        });
        toolbar.add(ifButton).expandX().fillX();
        toolbar.row();

        final Button whileButton = new TextButton("Пока", skin);
        whileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onWhileButtonClick();
            }
        });
        toolbar.add(whileButton).expandX().fillX();

        addActor(window);
        addActor(argumentField);
        addActor(toolbar);
        window.addListener(new HoverListener(window));
        window.row();

    }

    public void changePosition() {
        argumentField.setPosition(window.getX(), window.getY() + window.getHeight());
        toolbar.setPosition(window.getX() + window.getWidth(), window.getY() + window.getHeight() - 150);
    }

    public void sizeChanged() {
        argumentField.setWidth(window.getWidth());
        toolbar.setPosition(window.getX() + window.getWidth(), window.getY() + window.getHeight() - 150);
    }

    public void run() {
        String filename = window.getName() + ".lua";
        for (Block block : MenuManager.blockStore.getBlocks()) {
            // export other blocks as Lua first
            String blockFilename = block.getWindow().getName() + ".lua";
            if (filename.equals(blockFilename))
                continue;
            LuaConverter blockLua = new LuaConverter(block);
            blockLua.exportAsLua(blockFilename);
        }
        LuaConverter lua = new LuaConverter(this);
        lua.exportAsLua(filename);
        lua.runLua(filename);
    }

    public MyWindow getWindow() {
        return window;
    }

    public TextField getArgumentField() {
        return argumentField;
    }

    public void newBlock() {
        //Block block = new Block();
        //getStage().addActor(block);
    }

    public void onIfButtonClick() {
        if (StageInput.selected == null) return;
        MyWindow ifBlock = new MyWindow("Если", window.getSkin());
        if ((StageInput.selected.getClass().getSimpleName().equals("MyWindow"))) {
            if (((MyWindow) StageInput.selected).isMain) {
                window.addAtIndex(1, ifBlock);
                return;
            }
            else if (((MyWindow) StageInput.selected).getChildren().size == 1) {
                ((MyWindow) StageInput.selected).add(ifBlock).expandX().fillX();
                ((MyWindow) StageInput.selected).row();
                return;
            }
        }
        MyWindow parent = ((MyWindow) StageInput.selected.getParent());
        parent.addAtIndex(parent.getIndex(StageInput.selected), ifBlock);
    }

    public void onWhileButtonClick() {
        if (StageInput.selected == null) return;
        MyWindow whileBlock = new MyWindow("Пока", window.getSkin());
        if ((StageInput.selected.getClass().getSimpleName().equals("MyWindow"))) {
            if (((MyWindow) StageInput.selected).isMain) {
                window.addAtIndex(1, whileBlock);
                return;
            }
            else if (((MyWindow) StageInput.selected).getChildren().size == 1) {
                ((MyWindow) StageInput.selected).add(whileBlock).expandX().fillX();
                ((MyWindow) StageInput.selected).row();
                return;
            }
        }
        MyWindow parent = ((MyWindow) StageInput.selected.getParent());
        parent.addAtIndex(parent.getIndex(StageInput.selected), whileBlock);
    }
}
