package com.mygdx.game.entities.missiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.particles.Light;
import com.mygdx.game.entities.particles.SparkleParticle;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

import static com.mygdx.game.utilits.Utils.timeElapsed;

/**
 * Created by Asus123 on 15.12.2017.
 */

public class LightMissile extends Missile {

    Light lightParticle;

    public LightMissile(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        startTime = TimeUtils.nanoTime();
        damage = Constants.LIGHT_MISSILE_DAMAGE;

        // add light
        lightParticle = new Light(this.position, this.velocity);
        level.particleManager.particles.add(lightParticle);
    }


    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.setColor(Color.YELLOW);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        //(position - velocity.nor, position)
        renderer.rectLine(new Vector2(position).add( new Vector2(velocity).nor().scl(-Constants.LIGHT_MISSILE_LENGTH)),
                new Vector2(position), Constants.LIGHT_MISSILE_WIDTH);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public boolean timeIsOver(){
        return timeElapsed(startTime) > Constants.LIGHT_MISSILE_LIFETIME;
    }

    @Override
    public void missileExplode(Enemy hostEnemy, Vector2 direction) {
        level.particleManager.particles.removeValue(lightParticle, false);

        float Bound = Constants.SPARKLE_PARTICLE_START_VELOCITY;
        float startVelosity = Constants.SPARKLE_PARTICLE_START_VELOCITY;

        // TODO: 19.12.2017 redo directioned sparckles (to be able to make regural explosions of sparkles)
        for (int i = 0; i < Constants.SPARKLE_PARTICLE_NUMBER; i++) {
            //speed of particles are random + direction
            level.particleManager.particles.add(new SparkleParticle(hostEnemy, new Vector2(position),
                    //directed velocity of sparkle
                    Utils.randomVector(Bound).add(new Vector2(direction).scl(startVelosity))));
        }
    }
}
