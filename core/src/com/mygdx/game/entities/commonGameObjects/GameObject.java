package com.mygdx.game.entities.commonGameObjects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Asus123 on 15.12.2017.
 */

public abstract class GameObject {

    public Vector2 position;
    public Vector2 velocity;

    protected float radius;

    public void update(float delta){
        position.mulAdd(velocity, delta);
    }

    public void render(ShapeRenderer renderer){
        //TODO: Dont forget to change ShapeRenderer to Spritebatch
        renderer.circle(position.x, position.y, radius);
    }

}
