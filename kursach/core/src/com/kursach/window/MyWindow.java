package com.kursach.window;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.SnapshotArray;
import com.kursach.StageInput;
import com.kursach.menu.BlockStore;

/** A table that can be dragged and act as a modal window. The top padding is used as the window's title height.
 * <p>
 * The preferred size of a window is the preferred size of the title text and the children as laid out by the table. After adding
 * children to the window, it can be convenient to call {@link #pack()} to size the window to the size of the children.
 * @author Nathan Sweet */
public class MyWindow extends Table {
    static private final Vector2 tmpPosition = new Vector2();
    static private final Vector2 tmpSize = new Vector2();
    static private final int MOVE = 1 << 5;
    public static BlockStore blockStore;
    public static RenameWindow renameWindow;

    private com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle style;
    boolean isMovable = true, isModal, isResizable;
    int resizeBorder = 8;
    private int variableIndex = 0;
    boolean keepWithinStage = true;
    Label titleLabel;
    Table titleTable;
    boolean drawTitleTable;
    boolean isMain = false;
    boolean selected = false;
    public static StageInput stageInput;

    protected int edge;
    protected boolean dragging;
    private Skin skin;
    private Block parentBlock;
    MyListener listener;

    public MyWindow (String title, Skin skin, Block parentBlock) {
        this(title, skin.get(com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle.class));
        this.skin = skin;
        isMain = true;
        this.parentBlock = parentBlock;
        listener = new MyListener(this);

        setSkin(skin);
        top();
        setKeepWithinStage(false);

        Button saveButton = new TextButton("S", skin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                save();
            }
        });
        getTitleTable().add(saveButton).size(10, 10);

        Button renameButton = new TextButton("R", skin);
        renameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openRenameWindow();
            }
        });
        getTitleTable().add(renameButton).size(10, 10).padRight(0).padTop(0);

        Button addVariableField = new TextButton("+", skin);
        addVariableField.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClick();
            }
        });
        getTitleTable().add(addVariableField).size(10, 10);

        final Button closeButton = new TextButton("X", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });
        getTitleTable().add(closeButton).size(10, 10).padRight(0).padTop(0);

        setClip(false);
        setTransform(true);
        setResizable(true);
        setResizeBorder(8);
        addListener(listener);
    }

    public MyWindow (String title, Skin skin) {
        this(title, skin.get(com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle.class));
        this.skin = skin;
        listener = new MyListener(this);

        setSkin(skin);
        top();
        setKeepWithinStage(false);

        Button addVariableField = new TextButton("+", skin);
        addVariableField.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClick();
            }
        });
        getTitleTable().add(addVariableField).size(10, 10);

        Button renameButton = new TextButton("C", skin);
        renameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openRenameWindow();
            }
        });
        getTitleTable().add(renameButton).size(10, 10).padRight(0).padTop(0);

        final Button closeButton = new TextButton("X", skin);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });
        getTitleTable().add(closeButton).size(10, 10);

        setClip(false);
        setTransform(true);
        setMovable(false);

        addListener(listener);
    }

    public void unselect() {
        stageInput.unselect();
        selected = false;
        getTitleLabel().setColor(Color.WHITE);
    }

    public void selected() {
        stageInput.selectNew(this);
        selected = true;
        getTitleLabel().setColor(Color.GREEN);
    }

    public void selectClick() {
        if (!selected) selected();
        else unselect();
    }

    public boolean check() {
        for (int i = 0; i < getChildren().size; i++) {
            Actor obj = getChildren().get(i);
            for (EventListener event: obj.getListeners()) {
                if (event.getClass().getSimpleName().equals(MyListener.class.getSimpleName())) {
                    if (((MyListener) event).isOver()) {
                        return true;
                    }
                }

                if (event.getClass().getSimpleName().equals("VariableListener")) {
                    if (((VariableListener) event).isOver()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getIndex(Actor actor) {
        if ((actor.getClass().getSimpleName().equals("MyWindow") && ((MyWindow) actor).isMain)) {
            return 1;
        }
        SnapshotArray<Actor> temp = getChildren();
        System.out.println(actor.getClass().getSimpleName());
        for (int i = 0; i < temp.size; i++) {
            if (temp.get(i) == actor) {
                return i;
            }
        }
        return -1;
    }

    public void openRenameWindow() {
        renameWindow.update(this);
    }

    public void save() {
        blockStore.addBlock(parentBlock);
    }

    public void close() {
        getParent().removeActor(this);
        if (isMain) {
            parentBlock.remove();
        }
    }

    public void onClick() {
        VariableField field = new VariableField(skin);
        addFieldAtIndex(1, field);
    }

    public void addFieldAtIndex(int index, Actor actor) {
        SnapshotArray<Actor> temp = new SnapshotArray<Actor>(getChildren());
        for (int i = index + variableIndex; i < getChildren().size; i++) {
            removeActor(getChildren().get(i));
            i--;
        }
        add(actor).expandX().fillX(); // делали для textfield
        System.out.println(variableIndex);
        row();
        for (int i = index + variableIndex; i < temp.size; i++) {
            add(temp.get(i)).expandX().fillX();
            row();
        }
        variableIndex++;
    }

    public void addAtIndex(int index, Actor actor) {
        SnapshotArray<Actor> temp = new SnapshotArray<Actor>(getChildren());
        System.out.println(index);
        for (int i = index; i < getChildren().size; i++) {
            removeActor(getChildren().get(i));
            i--;
        }
        add(actor).expandX().fillX();
        row();
        for (int i = index; i < temp.size; i++) {
            add(temp.get(i)).expandX().fillX();
            row();
        }
    }



    public MyWindow (String title, com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle style) {
        if (title == null) throw new IllegalArgumentException("title cannot be null.");
        setTouchable(Touchable.enabled);
        setClip(true);

        titleLabel = new Label(title, new LabelStyle(style.titleFont, style.titleFontColor));
        titleLabel.setEllipsis(true);

        titleTable = new Table() {
            public void draw (Batch batch, float parentAlpha) {
                if (drawTitleTable) super.draw(batch, parentAlpha);
            }
        };
        titleTable.add(titleLabel).expandX().fillX().minWidth(50);
        addActor(titleTable);

        setStyle(style);
        setWidth(200);
        setHeight(150);

        addCaptureListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                toFront();
                return false;
            }
        });
        addListener(new InputListener() {
            float startX, startY, lastX, lastY;

            private void updateEdge (float x, float y) {
                float border = resizeBorder / 2f;
                float width = getWidth(), height = getHeight();
                float padTop = getPadTop(), padLeft = getPadLeft(), padBottom = getPadBottom(), padRight = getPadRight();
                float left = padLeft, right = width - padRight, bottom = padBottom;
                edge = 0;
                if (isResizable && x >= left - border && x <= right + border && y >= bottom - border) {
                    if (x < left + border) edge |= Align.left;
                    if (x > right - border) edge |= Align.right;
                    if (y < bottom + border) edge |= Align.bottom;
                    if (edge != 0) border += 25;
                    if (x < left + border) edge |= Align.left;
                    if (x > right - border) edge |= Align.right;
                    if (y < bottom + border) edge |= Align.bottom;
                }
                if (isMovable && edge == 0 && y <= height && y >= height - padTop && x >= left && x <= right) edge = MOVE;
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (button == 0 && StageInput.currentCommand == 2) {
                    updateEdge(x, y);
                    dragging = edge != 0;
                    startX = x;
                    startY = y;
                    lastX = x - getWidth();
                    lastY = y - getHeight();
                }
                return edge != 0 || isModal;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                dragging = false;
            }

            public void touchDragged (InputEvent event, float x, float y, int pointer) {
                if (!dragging || StageInput.currentCommand != 2) return;
                sizeChange();
                float width = getWidth(), height = getHeight();
                float windowX = getX(), windowY = getY();

                float minWidth = getMinWidth(), maxWidth = getMaxWidth();
                float minHeight = getMinHeight(), maxHeight = getMaxHeight();
                Stage stage = getStage();
                boolean clampPosition = keepWithinStage && getParent() == stage.getRoot();

                if ((edge & MOVE) != 0) {
                    float amountX = x - startX, amountY = y - startY;
                    windowX += amountX;
                    windowY += amountY;
                }
                if ((edge & Align.left) != 0) {
                    float amountX = x - startX;
                    if (width - amountX < minWidth) amountX = -(minWidth - width);
                    if (clampPosition && windowX + amountX < 0) amountX = -windowX;
                    width -= amountX;
                    windowX += amountX;
                }
                if ((edge & Align.bottom) != 0) {
                    float amountY = y - startY;
                    if (height - amountY < minHeight) amountY = -(minHeight - height);
                    if (clampPosition && windowY + amountY < 0) amountY = -windowY;
                    height -= amountY;
                    windowY += amountY;
                }
                if ((edge & Align.right) != 0) {
                    float amountX = x - lastX - width;
                    if (width + amountX < minWidth) amountX = minWidth - width;
                    if (clampPosition && windowX + width + amountX > stage.getWidth()) amountX = stage.getWidth() - windowX - width;
                    width += amountX;
                }
                if ((edge & Align.top) != 0) {
                    float amountY = y - lastY - height;
                    if (height + amountY < minHeight) amountY = minHeight - height;
                    if (clampPosition && windowY + height + amountY > stage.getHeight())
                        amountY = stage.getHeight() - windowY - height;
                    height += amountY;
                }
                if (width >= minWidth) {
                    setWidth(Math.round(width));
                    parentBlock.sizeChanged();
                }
                if (height >= minHeight) {
                    setHeight(Math.round(height));
                    parentBlock.sizeChanged();
                }
                setPosition(Math.round(windowX), Math.round(windowY));
                parentBlock.changePosition();
            }

            public boolean mouseMoved (InputEvent event, float x, float y) {
                if (StageInput.currentCommand != 2) return false;
                int count = 0;

                if ((edge & (Align.bottom)) != 0) {
                    count = 1;
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.VerticalResize);
                }
                else if ((edge & (Align.left | Align.right)) != 0) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.HorizontalResize);
                }
                else {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Arrow);
                }
                if ((edge & (Align.left | Align.right)) != 0) {
                    count++;
                }
                if (count == 2) {
                    Gdx.graphics.setSystemCursor(Cursor.SystemCursor.Crosshair);
                }
                updateEdge(x, y);
                return isModal;
            }

            public boolean scrolled (InputEvent event, float x, float y, int amount) {
                return isModal;
            }

            public boolean keyDown (InputEvent event, int keycode) {
                return isModal;
            }

            public boolean keyUp (InputEvent event, int keycode) {
                return isModal;
            }

            public boolean keyTyped (InputEvent event, char character) {
                return isModal;
            }
        });
    }


    public void setStyle (com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle style) {
        if (style == null) throw new IllegalArgumentException("style cannot be null.");
        this.style = style;
        setBackground(style.background);
        titleLabel.setStyle(new LabelStyle(style.titleFont, style.titleFontColor));
        invalidateHierarchy();
    }

    /** Returns the window's style. Modifying the returned style may not have an effect until {@link #setStyle(com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle)} is
     * called. */
    public com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle getStyle () {
        return style;
    }

    public void keepWithinStage () {
        if (!keepWithinStage) return;
        Stage stage = getStage();
        if (stage == null) return;
        Camera camera = stage.getCamera();
        if (camera instanceof OrthographicCamera) {
            OrthographicCamera orthographicCamera = (OrthographicCamera)camera;
            float parentWidth = stage.getWidth();
            float parentHeight = stage.getHeight();
            if (getX(Align.right) - camera.position.x > parentWidth / 2 / orthographicCamera.zoom)
                setPosition(camera.position.x + parentWidth / 2 / orthographicCamera.zoom, getY(Align.right), Align.right);
            if (getX(Align.left) - camera.position.x < -parentWidth / 2 / orthographicCamera.zoom)
                setPosition(camera.position.x - parentWidth / 2 / orthographicCamera.zoom, getY(Align.left), Align.left);
            if (getY(Align.top) - camera.position.y > parentHeight / 2 / orthographicCamera.zoom)
                setPosition(getX(Align.top), camera.position.y + parentHeight / 2 / orthographicCamera.zoom, Align.top);
            if (getY(Align.bottom) - camera.position.y < -parentHeight / 2 / orthographicCamera.zoom)
                setPosition(getX(Align.bottom), camera.position.y - parentHeight / 2 / orthographicCamera.zoom, Align.bottom);
        } else if (getParent() == stage.getRoot()) {
            float parentWidth = stage.getWidth();
            float parentHeight = stage.getHeight();
            if (getX() < 0) setX(0);
            if (getRight() > parentWidth) setX(parentWidth - getWidth());
            if (getY() < 0) setY(0);
            if (getTop() > parentHeight) setY(parentHeight - getHeight());
        }
    }

    public void draw (Batch batch, float parentAlpha) {
        Stage stage = getStage();
        if (stage.getKeyboardFocus() == null) stage.setKeyboardFocus(this);

        keepWithinStage();

        if (style.stageBackground != null) {
            stageToLocalCoordinates(tmpPosition.set(0, 0));
            stageToLocalCoordinates(tmpSize.set(stage.getWidth(), stage.getHeight()));
            drawStageBackground(batch, parentAlpha, getX() + tmpPosition.x, getY() + tmpPosition.y, getX() + tmpSize.x,
                    getY() + tmpSize.y);
        }

        super.draw(batch, parentAlpha);
    }

    protected void drawStageBackground (Batch batch, float parentAlpha, float x, float y, float width, float height) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        style.stageBackground.draw(batch, x, y, width, height);
    }

    protected void drawBackground (Batch batch, float parentAlpha, float x, float y) {
        super.drawBackground(batch, parentAlpha, x, y);

        // Manually draw the title table before clipping is done.
        titleTable.getColor().a = getColor().a;
        float padTop = getPadTop(), padLeft = getPadLeft();
        titleTable.setSize(getWidth() - padLeft - getPadRight(), padTop);
        titleTable.setPosition(padLeft, getHeight() - padTop);
        drawTitleTable = true;
        titleTable.draw(batch, parentAlpha);
        drawTitleTable = false; // Avoid drawing the title table again in drawChildren.
    }

    public Actor hit (float x, float y, boolean touchable) {
        if (!isVisible()) return null;
        Actor hit = super.hit(x, y, touchable);
        if (hit == null && isModal && (!touchable || getTouchable() == Touchable.enabled)) return this;
        float height = getHeight();
        if (hit == null || hit == this) return hit;
        if (y <= height && y >= height - getPadTop() && x >= 0 && x <= getWidth()) {
            // Hit the title bar, don't use the hit child if it is in the Window's table.
            Actor current = hit;
            while (current.getParent() != this)
                current = current.getParent();
            if (getCell(current) != null) return this;
        }
        return hit;
    }

    public boolean isMovable () {
        return isMovable;
    }

    public void setMovable (boolean isMovable) {
        this.isMovable = isMovable;
    }

    public boolean isModal () {
        return isModal;
    }

    public void setModal (boolean isModal) {
        this.isModal = isModal;
    }

    public void setKeepWithinStage (boolean keepWithinStage) {
        this.keepWithinStage = keepWithinStage;
    }

    public boolean isResizable () {
        return isResizable;
    }

    public void setResizable (boolean isResizable) {
        this.isResizable = isResizable;
    }

    public void setResizeBorder (int resizeBorder) {
        this.resizeBorder = resizeBorder;
    }

    public boolean isDragging () {
        return dragging;
    }

    public float getPrefWidth () {
        return Math.max(super.getPrefWidth(), titleTable.getPrefWidth() + getPadLeft() + getPadRight());
    }

    public Table getTitleTable () {
        return titleTable;
    }

    public Label getTitleLabel () {
        return titleLabel;
    }

    /** The style for a window, see {@link com.badlogic.gdx.scenes.scene2d.ui.Window}.
     * @author Nathan Sweet */
    static public class WindowStyle {
        /** Optional. */
        public Drawable background;
        public BitmapFont titleFont;
        /** Optional. */
        public Color titleFontColor = new Color(1, 1, 1, 1);
        /** Optional. */
        public Drawable stageBackground;

        public WindowStyle () {
        }

        public WindowStyle (BitmapFont titleFont, Color titleFontColor, Drawable background) {
            this.background = background;
            this.titleFont = titleFont;
            this.titleFontColor.set(titleFontColor);
        }

        public WindowStyle (com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle style) {
            this.background = style.background;
            this.titleFont = style.titleFont;
            this.titleFontColor = new Color(style.titleFontColor);
        }
    }

    public void sizeChange() {
        for (int i = 1; i < getChildren().size; i++) {
            getChildren().get(i).setWidth(getWidth()-2);
        }
    }

    @Override
    public boolean removeActor (Actor actor) {
        if (actor.getClass() == VariableField.class)
            variableIndex--;
        return removeActor(actor, true);
    }

    public void changeName(String newName) {
        titleLabel.setText(newName);
    }

    @Override
    public String getName() {
        return titleLabel.getText().toString();
    }

    public String getCondition() {
        String title = getTitleLabel().getText().toString();
        String condition = title.substring(3);
        return condition;
    }

    public String getFunctionName() {
        String name = getTitleLabel().getText().toString().substring(0, 2);
        return name;
    }
}
