package com.matt.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;


public class GameScreen implements Screen {

    private static final int SAFE_AREA = 600 - 20;


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

        if (head.x < 0)
            head.x = 0;
        if (head.x > SAFE_AREA)
            head.x = SAFE_AREA;

        if (head.y < 0)
            head.y = 0;
        if (head.y > SAFE_AREA)
            head.y = SAFE_AREA;

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
        move();
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
