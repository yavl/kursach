package com.kursach.window;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;

import java.io.*;

public class Block extends Group {
    private MyWindow window;
    private TextField argumentField;
    private Table toolbar;
    private Writer writer;

    public Block(Skin skin, Vector3 touchedPos) {
        MyWindow innerWindow = new MyWindow("if", skin);
        window = new MyWindow("Title", skin,this);
        window.setPosition(touchedPos.x, touchedPos.y);
        window.add(innerWindow).expandX().fillX();

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
        toolbar.setSize(75, 150);
        toolbar.setDebug(true);

        final Button runButton = new TextButton("Run", skin);
        runButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                run();
            }
        });
        toolbar.add(runButton).size(10, 10).padRight(0).padTop(0);

        addActor(window);
        addActor(argumentField);
        addActor(toolbar);
        window.addListener(new HoverListener(window));
        window.row();

    }

    public Block(Block copyThis, Skin skin) {
        MyWindow innerWindow = new MyWindow("if", skin);
        window = new MyWindow("Title", skin,this);
        window.setPosition(0, 0);
        window.add(innerWindow).expandX().fillX();

        window.getChildren();

        argumentField = new TextField("", skin);
        argumentField.setPosition(window.getX(), window.getY() + window.getHeight());
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
        toolbar.setPosition(window.getX() + window.getWidth(), window.getY() + window.getHeight() - 150);
        toolbar.setSize(75, 150);
        toolbar.setDebug(true);

        final Button runButton = new TextButton("Run", skin);
        runButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                run();
            }
        });
        toolbar.add(runButton).size(10, 10).padRight(0).padTop(0);

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
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("filename.txt"), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Actor actor: window.getChildren()) {
            String className = actor.getClass().getSimpleName();

            switch (className) {
                case "VariableField": {
                    VariableField temp = (VariableField) actor;
                    writeToFile(temp.getText() + "\n");
                } break;
                case "MyWindow": {
                    MyWindow temp = (MyWindow) actor;
                    System.out.println(temp.getTitleLabel().getText().substring(0, 2));
                    if (temp.getTitleLabel().getText().substring(0, 2).equals("if")) {
                        writeToFile("if " + temp.getCondition() + " then\n\t");
                        processCondition(temp.getChildren());
                        writeToFile("\nend");
                    }
                } break;
                default: {
                } break;
            }
        }

        try {writer.close();} catch (Exception ex) {/*ignore*/}
    }

    public void processCondition(SnapshotArray<Actor> array) {
        for (Actor child: array) {
            String className = child.getClass().getSimpleName();
            switch (className) {
                case "VariableField": {
                    VariableField temp = (VariableField) child;
                    writeToFile(temp.getText() + "\n");
                } break;
                case "MyWindow": {
                    MyWindow temp = (MyWindow) child;
                    if (temp.getTitleLabel().getText().substring(0, 2).equals("if")) {
                        writeToFile("if " + temp.getCondition() + " then\n\t");
                        processCondition(((MyWindow) child).getChildren());
                        writeToFile("\nend");
                    }
                } break;
                default: {
                } break;
            }
        }
    }

    public void writeToFile(String text) {
        try {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MyWindow getWindow() {
        return window;
    }

    public void newBlock() {
        //Block block = new Block();
        //getStage().addActor(block);
    }
}
