package com.matt.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.matt.snek.helper.Coordinate;

public class GameScreen implements Screen {

    private static final int HORIZONTAL_SAFE_AREA = 800 - 25;
    private static final int VERTICAL_SAFE_AREA = 600 - 25;
    private Direction currentDirection;


    private Texture dotImage;
    private Rectangle head;
    private GameEngine game;
    private OrthographicCamera camera;

    public GameScreen(GameEngine game) {
        this.game = game;
        dotImage = new Texture(Gdx.files.internal("dot.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        head = new Rectangle();

        head.x = 600 / 2;
        head.y = 600 / 2;
        head.width = 25;
        head.height = 25;
        currentDirection = Direction.RIGHT;
    }

    private void move(Direction direction) {
        switch (direction) {
        case UP:
            if ((head.y + head.height) > VERTICAL_SAFE_AREA) {
                head.y = VERTICAL_SAFE_AREA;
            } else {
                head.y += head.height;
            }
            break;
        case DOWN:
            if ((head.y + head.height) < 50) {
                head.y = 0;
            } else {
                head.y -= head.height;
            }
            break;
        case LEFT:
            if ((head.x + head.x) < 25) {
                head.x = 0;
            } else {
                head.x -= head.height;
            }
            break;
        case RIGHT:
            if ((head.x > HORIZONTAL_SAFE_AREA - head.height)) {
                head.x = HORIZONTAL_SAFE_AREA;
            } else {
                head.x += head.height;
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        game.getBatch().draw(dotImage, head.x, head.y, head.width, head.height);
        game.getBatch().end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
            move(Direction.LEFT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            move(Direction.RIGHT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            move(Direction.UP);
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            move(Direction.DOWN);
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        dotImage.dispose();
    }
}
