package com.mygdx.game.entities.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;

/**
 * Created by Asus123 on 19.12.2017.
 */

public class SparkleParticle extends Particle {

    private float size;
    Light lightParticle;

    public SparkleParticle(Vector2 position, Vector2 velocity, Level level) {
        this(null, position, velocity, level);
    }

    public SparkleParticle(Enemy enemy, Vector2 position, Vector2 velocity, Level level) {
        super(enemy, position, velocity, level);
        setLifeTime(Constants.SPARKLE_PARTICLE_LIFE_TIME);
        //different size of sparkle
        size = MathUtils.random(Constants.SPARKLE_PARTICLE_SIZE - 0.7f) + 0.7f ;

        // adding light of sparkle
        lightParticle = new Light(position, velocity, 2 * size, level);
        lightParticle.setLifeTime(this.getLifeTime());
        level.particleManager.particles.add(lightParticle);

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
//        lightParticle.render(batch, renderer);
        renderer.setColor(Color.YELLOW);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.rect(position.x - size / 2, position.y - size / 2, size, size);
    }

    @Override
    public void update(float delta) {
        velocity.mulAdd(velocity, -Constants.SPARKLE_PARTICLE_FRICTION); // air friction
        super.update(delta);
    }
}
