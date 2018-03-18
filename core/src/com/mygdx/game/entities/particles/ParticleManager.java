package com.mygdx.game.entities.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;

/**
 * Created by Asus123 on 19.12.2017.
 */

public class ParticleManager {
    public DelayedRemovalArray<Particle> particles;
    public DelayedRemovalArray<Star> stars;

    public ParticleManager() {
        particles = new DelayedRemovalArray<Particle>();

        //star adding in GG
        stars = new DelayedRemovalArray<Star>();
    }

    public void update(float delta) {

        for (Particle particle : particles) {
            particle.update(delta);
            //removing particle
            if (particle.isTimeElapsed()) {
                particles.removeValue(particle, false);
            }
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        for (Particle particle : particles) {
            particle.render(batch, renderer);
        }
    }
}
