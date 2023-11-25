package com.matt.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.matt.snek.helper.Coordinate;

public class GameScreen implements Screen {

    private static final int HORIZONTAL_SAFE_AREA = 800 - 25;
    private static final int VERTICAL_SAFE_AREA = 600 - 25;
    private Direction currentDirection;
    private Coordinate initialCoordinate;

    private Sound bopSound;

    private Texture dotImage;
    private Texture foodImage;

    private Rectangle head;
    private Rectangle food;

    private GameEngine game;
    private OrthographicCamera camera;
    private Array<Node> nodes;


    public GameScreen(GameEngine game) {
        this.game = game;
        dotImage = new Texture(Gdx.files.internal("dot.png"));
        foodImage = new Texture(Gdx.files.internal("yum.png"));
        bopSound = Gdx.audio.newSound(Gdx.files.internal("bop1.mp3"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        nodes = initialSnek(4);
        initialCoordinate = new Coordinate(HORIZONTAL_SAFE_AREA / 2, VERTICAL_SAFE_AREA / 2);
//        head = new Rectangle();
//
//        head.x = 600 / 2;
//        head.y = 600 / 2;
//        head.width = 25;
//        head.height = 25;
        food = drawFood();
//        nodes = new Array<>();
//        nodes.add(head);
//        currentDirection = Direction.RIGHT;
    }

    private Array<Node> initialSnek(int size) {
        Array<Node> newNodes = new Array<>();
        for (int i = 0; i < size; i++) {
            var node = new Node(i);
            node.setWidth(25);
            node.setHeight(25);
            newNodes.add(node);
        }
        return newNodes;
    }

    private Rectangle drawFood() {
       float verticalPos = MathUtils.random(0, VERTICAL_SAFE_AREA);
       float horizontalPos = MathUtils.random(0, HORIZONTAL_SAFE_AREA);
       Rectangle yum = new Rectangle();
       yum.x = verticalPos;
       yum.y = horizontalPos;
       yum.width = 15;
       yum.height = 15;
       return yum;
    }

    private void moveFood() {
        float verticalPos = MathUtils.random(0, VERTICAL_SAFE_AREA);
        float horizontalPos = MathUtils.random(0, HORIZONTAL_SAFE_AREA);
        food.x = verticalPos;
        food.y = horizontalPos;
    }

//    private void addNode() {
//        Rectangle node = new Rectangle();
//        node.width = 25;
//        node.height = 25;
//        nodes.add(node);
//        System.out.println("Nodes: " + nodes.size);
//    }

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
        game.getBatch().draw(foodImage, food.x, food.y);
        for (int i = 0; i < nodes.size; i++) {
            Node currentNode = nodes.get(i);
            if (currentNode.getNodePosition() == 0) {
                currentNode.setX(initialCoordinate.getX());
                currentNode.setY(initialCoordinate.getY());
            } else {
                Node lastNode = nodes.get(i-1);
                currentNode.setX(lastNode.getX() - lastNode.getWidth());
                currentNode.setY(lastNode.getY());
            }
            game.getBatch().draw(dotImage, currentNode.getX(), currentNode.getY());
        }
        game.getFont().draw(game.getBatch(), "Score: " + nodes.size, HORIZONTAL_SAFE_AREA - 50, VERTICAL_SAFE_AREA - 50);
        game.getBatch().end();
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            move(Direction.LEFT);
            bopSound.play();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            move(Direction.RIGHT);
            bopSound.play();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            move(Direction.UP);
            bopSound.play();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            move(Direction.DOWN);
            bopSound.play();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();

//        if (head.overlaps(food)) {
//            moveFood();
////            addNode();
//        }
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
