package com.matt.snek;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    private int x;
    private int y;
    private Texture texture;

    public Node(Texture texture) {
        this.texture = texture;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(SpriteBatch batch, int headX, int headY) {
        if (!(x == headX && y == headY))
            batch.draw(texture, x, y);
    }

}