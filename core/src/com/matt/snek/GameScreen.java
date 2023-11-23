package com.matt.snek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;


public class GameScreen implements Screen {

    private Texture dotImage;
    private Rectangle head;
    private GameEngine game;
    private OrthographicCamera camera;

    public GameScreen(GameEngine game) {
        this.game = game;
        dotImage = new Texture(Gdx.files.internal("dot.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 600, 600);
        head = new Rectangle();
        head.x = 600 / 2;
        head.y = 600 / 2;
        head.width = 20;
        head.height = 20;
    }

    private void move() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            head.x -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            head.y += 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            head.y -= 200 * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            head.x += 200 * Gdx.graphics.getDeltaTime();
        if (head.x < 0)
            head.x = 0;
        if (head.y > 600)
            head.y = 600;
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
