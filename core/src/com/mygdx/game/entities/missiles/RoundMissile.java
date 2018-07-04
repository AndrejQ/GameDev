package com.mygdx.game.entities.missiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.entities.particles.RoundParticle;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.ColorChanger;
import com.mygdx.game.utilits.Constants;

public class RoundMissile extends Missile {
    private RoundParticle mRoundParticle;

    public RoundMissile(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        radius = Constants.ROUND_MISSILE_RADIUS;
        setMass(Constants.ROUND_MISSILE_MASS);
        startTime = TimeUtils.nanoTime();
        damage = Constants.ROUND_MISSILE_DAMAGE;
        lifetime = Constants.ROUND_MISSILE_LIFETIME;
        mRoundParticle = new RoundParticle(
                this,
                this.position,
                new Vector2(),
                level,
                Constants.ROUND_PARTICLE_STANDART_COLORS,
                lifetime
        );
        level.particleManager.particles.add(mRoundParticle);
    }


    @Override
    public void missileExplode(Enemy hostEnemy) {
        super.missileExplode(hostEnemy);
        level.particleManager.particles.removeValue(mRoundParticle, false);
        level.particleManager.particles.add(
                new RoundParticle(
                        hostEnemy,
                        mRoundParticle.position,
                        mRoundParticle.velocity,
                        level,
                        new Color[]{Color.FIREBRICK, Color.YELLOW, Color.FIREBRICK},
                        0.6f,
                        Constants.ROUND_PARTICLE_SIZE / 2
                )
        );
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        return;
    }

    @Override
    public void update(float delta) {
        // friction
        velocity.mulAdd(velocity, -Constants.ROUND_MISSILE_FRICTION); // air friction
        super.update(delta);
    }
}
