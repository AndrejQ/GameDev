package com.mygdx.game.entities.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.utilits.Constants;

/**
 * Created by Asus123 on 19.12.2017.
 */

public class SparkleParticle extends Particle {

    private float size;

    public SparkleParticle(Vector2 position, Vector2 velocity) {
        this(null, position, velocity);
    }

    public SparkleParticle(Enemy enemy, Vector2 position, Vector2 velocity) {
        super(enemy, position, velocity);
        lifeTime = Constants.SPARKLE_PARTICLE_LIFE_TIME;
        //different size of sparkle
        size = MathUtils.random(Constants.SPARKLE_PARTICLE_SIZE - 0.7f) + 0.7f ;
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.setColor(Color.YELLOW);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.rect(position.x, position.y, size, size);
    }

    @Override
    public void update(float delta) {
        velocity.mulAdd(velocity, -Constants.SPARKLE_PARTICLE_FRICTION); // air friction
        super.update(delta);
    }
}
