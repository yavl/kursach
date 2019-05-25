package com.kursach.lua;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.kursach.menu.MenuManager;
import com.kursach.window.Block;
import com.kursach.window.MyWindow;
import com.kursach.window.VariableField;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LuaConverter {
    private Writer writer;
    private Block block;

    public LuaConverter(Block block) {
        this.block = block;
    }

    public void exportAsLua(String filename) {
        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), "utf-8"));
            writer.write("function " + block.getWindow().getName() + "()\n\t"); // each block is a function
            for (Actor actor : block.getWindow().getChildren()) {
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
                            writeToFile("end\n");
                        }
                    } break;
                    default: {
                    } break;
                }
            }
            writer.write("end\n");
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runLua(String filename) {
        String tempFileName = "temp.lua";
        try (Writer tempWriter = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(tempFileName), "utf-8"))) {
            for (Block block : MenuManager.blockStore.getBlocks()) {
                String blockFilename = block.getWindow().getName() + ".lua"; // файл почему-то со звездочкой, напр: factorial*.lua вместо factorial.lua
                if (filename.equals(blockFilename))
                    continue;
                tempWriter.write("require \"" + block.getWindow().getName() + "*\"\n");
            }
            String filenameContent = readFile(filename, StandardCharsets.UTF_8);
            tempWriter.write("\n" + filenameContent);
            tempWriter.write(block.getWindow().getName() + "()\n");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProcessBuilder pb = new ProcessBuilder("lua", tempFileName);
        try {
            Process proc = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null)
            {
                Label label = MenuManager.outputLabel;
                label.setText(label.getText() + s + '\n');
                label.setY(label.getY() + label.getHeight());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String text) {
        try {
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processCondition(Array<Actor> array) {
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

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
