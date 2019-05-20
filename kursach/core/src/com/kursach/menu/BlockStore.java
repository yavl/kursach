package com.kursach.menu;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.kursach.window.Block;

public class BlockStore extends Table {
    List<Block> blocks;
    public BlockStore(Skin skin) {
        super(skin);
        blocks = new List<Block>(skin);
    }

}
