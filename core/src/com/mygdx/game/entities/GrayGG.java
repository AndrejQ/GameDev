package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.missiles.Missile;
import com.mygdx.game.entities.missiles.RoundMissile;
import com.mygdx.game.entities.particles.RoundParticle;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 22.03.2018.
 */

public class GrayGG extends GG {
    public GrayGG(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        setMass(Constants.GG_MASS);
        radius = Constants.GG_RADIUS;

    }

    @Override
    public void update(float delta) {
        velocity.mulAdd(velocity, -Constants.FRICTION * 60 * delta); // air friction
        super.update(delta);

        // spawn harmless round particles
        if (Constants.ROUND_PARTICLE_SPAWN_PER_SECOND * delta > MathUtils.random()) {
            this.level.particleManager.particles.
                    add(new RoundParticle(Utils.randomRoundVector(radius).add(position),
                            new Vector2(),
                            level));
        }

        // Round missile spawn
        if (isPlayerTouching){
            if (Utils.timeElapsed(startTime) > 1 / Constants.ROUND_MISSILE_SPAWN_PER_SECOND){
                missileSpawn(tapPosition);
                startTime = TimeUtils.nanoTime();
            }
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.DARK_GRAY);
        renderer.circle(position.x, position.y, radius, 10);
    }

    @Override
    public void missileSpawn(Vector2 tapPosition) {
        Missile missile =new RoundMissile(missileSpawnPosition(tapPosition),
                missileSpawnDirection(tapPosition).scl(Constants.ROUND_MISSILE_VELOCITY), level);

        level.missilesManager.missiles.add(missile);
        velocity.add(new Vector2(missile.velocity)
                .scl(- Constants.ROUND_MISSILE_MASS / Constants.GG_MASS));
    }

    //    @Override
//    public Vector2 missileSpawnPosition(Vector2 tapPosition) {
//        return super.missileSpawnPosition(tapPosition);
//    }
//
//    @Override
//    public Vector2 missileSpawnDirection(Vector2 tapPosition) {
//        return super.missileSpawnDirection(tapPosition);
//    }
//
//    @Override
//    public void missileSpawn(Vector2 tapPosition) {
//        super.missileSpawn(tapPosition);
//    }
}
