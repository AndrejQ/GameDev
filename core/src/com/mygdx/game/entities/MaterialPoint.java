package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;

public class MaterialPoint {
    float mass;
    public Vector2 position, velocity;
    public Level level;
    protected float friction;

    public MaterialPoint(Vector2 position, Vector2 velocity, Level level) {
        this.position = position;
        this.velocity = velocity;
        this.level = level;
        friction = Constants.FRICTION;
    }

    public void update(float delta) {
        position.mulAdd(velocity, delta);
        if (friction > 0) {
            velocity.mulAdd(velocity, -friction * 60 * delta); // air friction
        }
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }
}
