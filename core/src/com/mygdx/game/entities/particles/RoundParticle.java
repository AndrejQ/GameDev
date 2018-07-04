package com.mygdx.game.entities.particles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.ColorChanger;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

public class RoundParticle extends Particle {
    private float currentRadius = 0f;
    private ColorChanger colorChanger;

    public RoundParticle(Particle particle) {
        super(particle);
    }

    public RoundParticle(Vector2 position, Vector2 velocity, Level level) {
       this(null, position, velocity, level, Constants.ROUND_PARTICLE_STANDART_COLORS, Constants.ROUND_PARTICLE_LIFE_TIME, Constants.ROUND_PARTICLE_SIZE);
    }

    public RoundParticle(GameObject hostObject, Vector2 position, Vector2 velocity, Level level) {
        this(hostObject, position, velocity, level, Constants.ROUND_PARTICLE_STANDART_COLORS, Constants.ROUND_PARTICLE_LIFE_TIME, Constants.ROUND_PARTICLE_SIZE);
    }

    public RoundParticle(GameObject hostObject, Vector2 position, Vector2 velocity, Level level, Color[] colors, float lifetime) {
        this(hostObject, position, velocity, level, colors, lifetime, Constants.ROUND_PARTICLE_SIZE);
    }

    public RoundParticle(GameObject hostObject, Vector2 position, Vector2 velocity, Level level, Color[] colors, float lifetime, float radius) {
        super(hostObject, position, velocity, level);
        this.radius = radius;
        startTime = TimeUtils.nanoTime();
        setLifeTime(lifetime);
        colorChanger = new ColorChanger();
        colorChanger.setAllColorsDuration(getLifeTime());
        for (Color color : colors) {
            colorChanger.addColorState(color);
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(colorChanger.getColor());
        renderer.circle(position.x, position.y, currentRadius, 7);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // increasing and decreasing current radius animation
        currentRadius = radius *
                (1 - Utils.pow((2 * Utils.timeElapsed(startTime) - lifeTime) / lifeTime, 4));
        colorChanger.act(delta);
    }
}
