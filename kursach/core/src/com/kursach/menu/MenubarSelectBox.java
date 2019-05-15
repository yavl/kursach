package com.kursach.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class MenubarSelectBox extends SelectBox {
    public MenubarSelectBox(Skin skin, String name) {
        super(skin);
        setName(name);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        validate();

        Drawable background;
        if (isDisabled() && getStyle().backgroundDisabled != null)
            background = getStyle().backgroundDisabled;
        else if (getList().hasParent() && getStyle().backgroundOpen != null)
            background = getStyle().backgroundOpen;
        //else if (clickListener.isOver() && getStyle().backgroundOver != null)
        //    background = getStyle().backgroundOver;
        else if (getStyle().background != null)
            background = getStyle().background;
        else
            background = null;
        BitmapFont font = getStyle().font;
        Color fontColor = (isDisabled() && getStyle().disabledFontColor != null) ? getStyle().disabledFontColor : getStyle().fontColor;

        Color color = getColor();
        float x = getX(), y = getY();
        float width = getWidth() - getScrollPane().getScrollBarWidth(), height = getHeight();

        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        if (background != null) background.draw(batch, x, y, width, height);

        Object selected = getSelection().first();
        if (selected != null) {
            if (background != null) {
                width -= background.getLeftWidth() + background.getRightWidth();
                height -= background.getBottomHeight() + background.getTopHeight();
                x += background.getLeftWidth();
                y += (int)(height / 2 + background.getBottomHeight() + font.getData().capHeight / 2);
            } else {
                y += (int)(height / 2 + font.getData().capHeight / 2);
            }
            font.setColor(fontColor.r, fontColor.g, fontColor.b, fontColor.a * parentAlpha);
            drawItem(batch, font, getName(), x, y, width);
        }
    }
}
