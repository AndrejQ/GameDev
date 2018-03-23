package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.levels.Level;

/**
 * Created by Asus123 on 22.03.2018.
 */

public class GrayGG extends GG {
    public GrayGG(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.DARK_GRAY);
        renderer.circle(position.x, position.y, 10, 10);
    }

    @Override
    public void init(Vector2 position) {
        super.init(position);
    }

    @Override
    public Vector2 missileSpawnPosition(Vector2 tapPosition) {
        return super.missileSpawnPosition(tapPosition);
    }

    @Override
    public Vector2 missileSpawnDirection(Vector2 tapPosition) {
        return super.missileSpawnDirection(tapPosition);
    }

    @Override
    public void missileSpawn(Vector2 tapPosition) {
        super.missileSpawn(tapPosition);
    }
}
