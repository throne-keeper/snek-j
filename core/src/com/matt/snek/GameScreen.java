package com.matt.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class GameScreen implements Screen {

    private static final float UPDATE = 1f;
    private static final int HEAD_LENGTH = 32;

    private int headX = 0;
    private int headY = 0;

    private int foodX;
    private int foodY;

    private int headXBeforeUpdate = 0;
    private int headYBeforeUpdate = 0;
    private float timer = UPDATE;

    private Direction currentDirection = Direction.UP;
    private boolean foodAvailable = false;
    private boolean hit = false;

    private Sound bopSound;
    private Sound yumSound;

    private Texture dotImage;
    private Texture foodImage;
    private Rectangle food;
    private GameEngine game;
    private Array<Node> nodes;

    public GameScreen(GameEngine game) {
        this.game = game;
        dotImage = new Texture(Gdx.files.internal("dot.png"));
        foodImage = new Texture(Gdx.files.internal("yum.png"));
        bopSound = Gdx.audio.newSound(Gdx.files.internal("bop1.mp3"));
        yumSound = Gdx.audio.newSound(Gdx.files.internal("yumSound.mp3"));
        nodes = new Array<>();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        pollInput();
        updateSnek(delta);
        handleEatFood();
        placeFood();
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();
        game.getBatch().draw(dotImage, headX, headY);
        for (Node node : nodes) {
            node.draw(game.getBatch(), headX, headY);
        }
        if (foodAvailable) {
            game.getBatch().draw(foodImage, foodX, foodY);
        }
        game.getBatch().end();
    }

    private void placeFood() {
        if (!foodAvailable) {
            do {
                foodX = MathUtils.random(Gdx.graphics.getWidth() / HEAD_LENGTH - 1) * HEAD_LENGTH;
                foodY = MathUtils.random(Gdx.graphics.getHeight() / HEAD_LENGTH - 1) * HEAD_LENGTH;
                foodAvailable = true;
            } while (foodX == headX && foodY == foodY);
        }
    }

    private void handleEatFood() {
        if (foodAvailable && (foodX == headX) && (foodY == headY)) {
            Node node = new Node(dotImage);
            node.move(headX, headY);
            nodes.insert(0, node);
            foodAvailable = false;
            yumSound.play();
        }
    }

    private void updateSnek(float deltaTime) {
        if (!hit) {
            timer -= deltaTime;
            if (timer <= 0) {
                timer = UPDATE;
                moveSnek();
                checkBoundary();
                updateBody();
                checkSelfCollision();
            }
        }
    }

    private void pollInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            updateDirection(Direction.LEFT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            updateDirection(Direction.RIGHT);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            updateDirection(Direction.UP);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            updateDirection(Direction.DOWN);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    private void playMoveSound() {
        bopSound.play();
    }

    private void moveSnek() {
        headXBeforeUpdate = headX;
        headYBeforeUpdate = headY;
        playMoveSound();
        switch (currentDirection) {
            case RIGHT:
                headX += HEAD_LENGTH;
                break;
            case LEFT:
                headX -= HEAD_LENGTH;
                break;
            case UP:
                headY += HEAD_LENGTH;
                break;
            case DOWN:
                headY -= HEAD_LENGTH;
                break;
        }
    }

    private void updateDirection(Direction newDirection, Direction oppositeDirection) {
        if ((currentDirection != oppositeDirection) || (nodes.size == 0)) {
            currentDirection = newDirection;
        }
    }

    private void updateDirection(Direction direction) {
        switch (direction) {
            case LEFT:
                updateDirection(direction, Direction.RIGHT);
            case RIGHT:
                updateDirection(direction, Direction.LEFT);
            case UP:
                updateDirection(direction, Direction.DOWN);
            case DOWN:
                updateDirection(direction, Direction.UP);
        }
    }

    private void updateBody() {
        if (nodes.size > 0) {
            Node node = nodes.removeIndex(0);
            node.move(headXBeforeUpdate, headYBeforeUpdate);
            nodes.add(node);
        }
    }

    private void checkSelfCollision() {
        for (Node node : nodes) {
            if (node.getX() == headX && node.getY() == headY)
                hit = true;
        }
    }

    private void checkBoundary() {
        if (headX >= Gdx.graphics.getWidth())
            headX = 0;
        if (headX < 0)
            headX = Gdx.graphics.getWidth() - HEAD_LENGTH;
        if (headY >= Gdx.graphics.getHeight())
            headY = 0;
        if (headY < 0)
            headY = Gdx.graphics.getHeight() - HEAD_LENGTH;
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
        foodImage.dispose();
    }
}
