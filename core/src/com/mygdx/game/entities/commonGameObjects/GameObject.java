package com.mygdx.game.entities.commonGameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilits.Constants;

/**
 * Created by Asus123 on 15.12.2017.
 */

public abstract class GameObject {

    public Vector2 position;
    public Vector2 velocity;

    protected float radius;

    public void update(float delta) {
        position.mulAdd(velocity, delta);

        //Bounds collision
        if (position.x < Constants.WORLD_BOUNDS.x + radius) {
            this.reflect_left();
        } else if (position.x > Constants.WORLD_BOUNDS.x + Constants.WORLD_BOUNDS.width - radius) {
            this.reflect_right();
        } else if (position.y < Constants.WORLD_BOUNDS.y + radius) {
            this.reflect_down();
        } else if (position.y > Constants.WORLD_BOUNDS.y + Constants.WORLD_BOUNDS.height - radius) {
            this.reflect_up();
        }
    }

    public void render(ShapeRenderer renderer){
        //TODO: Dont forget to change ShapeRenderer to Spritebatch
        renderer.setColor(Color.BLACK);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, radius);
    }

    public boolean collideWithWalls(){
        return Constants.WORLD_BOUNDS.contains(new Circle(position, radius));
    }

    // TODO: change reflections. They are glitchy
    public void reflect_up(){
        velocity.y -= 2 * velocity.y;
    }
    public void reflect_down(){
        velocity.y -= 2 * velocity.y;
    }
    public void reflect_left(){
        velocity.x -= 2 * velocity.x;
    }
    public void reflect_right(){
        velocity.x -= 2 * velocity.x;
    }
}
