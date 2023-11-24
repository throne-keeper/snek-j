package com.matt.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;


public class GameScreen implements Screen {

    private static final int HORIZONTAL_SAFE_AREA = 750;
    private static final int VERTICAL_SAFE_AREA = 550;
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

    private void move() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
            head.x -= head.width;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            head.y += head.height;
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            head.y -= head.height;
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            head.x += head.width;
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();

    }

    private boolean outsideSafeArea() {
        if (head.x < 25) {
            head.x = 25;
            return true;
        }
        if (head.x > HORIZONTAL_SAFE_AREA) {
            head.x = HORIZONTAL_SAFE_AREA;
            return true;
        }

        if (head.y < 25) {
            head.y = 25;
            return true;
        }

        if (head.y > VERTICAL_SAFE_AREA) {
            head.y = VERTICAL_SAFE_AREA;
            return true;
        }
        return false;
    }

    private void move(Direction direction) {
        System.out.println("(" + head.x + ", " + head.y + ")");
        switch (direction) {
            case UP:
                if (!outsideSafeArea()) {
                    head.y += head.height;
                    break;
                }
            case DOWN:
                if (!outsideSafeArea()) {
                    head.y -= head.height;
                    break;
                }
            case LEFT:
                if (!outsideSafeArea()) {
                    head.x -= head.width;
                    break;
                }
            case RIGHT:
                if (!outsideSafeArea()) {
                    head.x += head.width;
                    break;
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
