package com.kursach.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.kursach.window.Block;
import com.kursach.window.MyWindow;

import java.util.HashMap;
import java.util.Map;

public class BlockStore extends Window {
    private Array<Block> blocks;
    private int count = 0;
    private Map<String, Integer> blockMap;

    public BlockStore(Skin skin) {
        super("Open", skin.get(com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle.class));
        blocks = new Array<Block>();
        blockMap = new HashMap<String, Integer>();
        setSkin(skin);

        top();
        setKeepWithinStage(false);
        setClip(false);
        setTransform(true);
        debugAll();
        setResizable(true);
        setResizeBorder(8);
        MyWindow.blockStore = this;
    }

    public void addBlock(final Block block) {
        blocks.add(block);
        blockMap.put(block.getName(), blocks.size - 1);
        System.out.println(block.getWindow().getName());
        TextButton blockButton = new TextButton(block.getWindow().getName(), getSkin());
        blockButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                block.newBlock();
            }
        });
        add(blockButton).expandX().fillX();
        row();
    }

    public Array<Block> getBlocks() {
        return blocks;
    }
}
